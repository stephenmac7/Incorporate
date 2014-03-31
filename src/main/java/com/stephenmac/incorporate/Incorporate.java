package com.stephenmac.incorporate;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.DatastoreImpl;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.mapping.Mapper;
import org.mongodb.morphia.mapping.MapperOptions;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public final class Incorporate extends JavaPlugin {
	public Economy econ = null;
	public CompanyDAO companyDAO;
	public LinkedChestDAO linkedChestDAO;

	// Pending actions: Player name to action map, used for commands which
	// require action
	public Map<String, ExpectingLocation> eLActions = new HashMap<>();

	@Override
	public void onEnable() {
		// Setup Economy, fail load if no vault
		if (!setupEconomy()) {
			getLogger().severe(
			        String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
			getServer().getPluginManager().disablePlugin(this);
			return;
		}

		// Setup config
		this.saveDefaultConfig();

		// Setup db
		MongoClient mongoClient = null;
        String dbName = null;
		try {
            MongoClientURI uri = new MongoClientURI(this.getConfig().getString("database"));
            getLogger().info(String.format("Using Database URI: %s", uri.toString()));
            dbName = uri.getDatabase();
			mongoClient = new MongoClient(uri);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			getServer().getPluginManager().disablePlugin(this);
		}
		setupDatabase(mongoClient, dbName);

		// Setup Listeners
		new PlayerInteractListener(this);

		// Setup Tasks
		new ProcessLinkedChests(this).runTaskTimer(this, 10, 60);

		// Setup Commands
		try {
			getCommand("inc").setExecutor(new Executor(this));
		} catch (Exception e) {
			e.printStackTrace();
			getServer().getPluginManager().disablePlugin(this);
		}
	}

	private void setupDatabase(MongoClient mongoCl, String dbName) {
		// Initialize Morphia
		Morphia morphia = new Morphia();
		morphia.map(Company.class).map(LinkedChest.class);

		// Setup datastore
		MapperOptions opts = new MapperOptions();
		opts.objectFactory = new CustomCreator(this.getClassLoader());
		Mapper mapper = new Mapper(opts);
		Datastore ds = new DatastoreImpl(mapper, mongoCl, dbName);

		// Setup DAOs
		companyDAO = new CompanyDAO(ds);
		companyDAO.ensureIndexes();
		linkedChestDAO = new LinkedChestDAO(ds);
		linkedChestDAO.ensureIndexes();
	}

	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}
}
