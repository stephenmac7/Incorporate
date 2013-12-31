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

public final class Incorporate extends JavaPlugin {
	public Economy econ = null;
	public CompanyDAO companyDAO;
	public LinkedChestDAO linkedChestDAO;
	
	// Pending actions: Player name to action map, used for commands which require action
	public Map<String, PendingAction> pendingActions = new HashMap<String, PendingAction>();

	@Override
	public void onEnable(){
		// Setup Economy, fail load if no vault
        if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        
        // Setup db
    	MongoClient mongoClient = null;
		try {
			mongoClient = new MongoClient("localhost");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
        Morphia morphia = new Morphia();
        morphia.map(Company.class).map(Rank.class);
        
        // Setup datastore
		MapperOptions opts = new MapperOptions();
		opts.objectFactory = new CustomCreator(this.getClassLoader());
		Mapper mapper = new Mapper(opts);
		Datastore ds = new DatastoreImpl(mapper, mongoClient, "incorporate");
		
		// Setup DAOs
        companyDAO = new CompanyDAO(ds);
        companyDAO.ensureIndexes();
        linkedChestDAO = new LinkedChestDAO(ds);
        linkedChestDAO.ensureIndexes();
        
        // Setup Listeners
        new PlayerInteractListener(this);
        
        // Setup Tasks
        new ProcessLinkedChests(this).runTaskTimer(this, 10, 60);
        
        // Setup Commands
        getCommand("inc").setExecutor(new UserCommandExecutor(this));
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

