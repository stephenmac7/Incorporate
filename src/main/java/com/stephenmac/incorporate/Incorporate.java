package com.stephenmac.incorporate;

import java.net.UnknownHostException;

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
		opts.objectFactory = new CustomCreator();
		Mapper mapper = new Mapper(opts);
		Datastore ds = new DatastoreImpl(mapper, mongoClient, "incorporate");
		// Setup DAO
        companyDAO = new CompanyDAO(ds);
        companyDAO.ensureIndexes();
        
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

