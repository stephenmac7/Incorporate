package com.stephenmac.incorporate;

import java.util.HashMap;
import java.util.Map;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.stephenmac.incorporate.commands.Command;

public class Executor implements CommandExecutor {
	public CompanyDAO companyDAO;
	public Economy econ;
	public Server server;
	
	private CommandChooser chooser;
	
	public Map<String, String> selections = new HashMap<String, String>();
	public Map<String, PendingAction> pendingActions;
	
	public Executor(Incorporate plugin) throws Exception {
		companyDAO = plugin.companyDAO;
		econ = plugin.econ;
		pendingActions = plugin.pendingActions;
		server = plugin.getServer();
		
		chooser = new CommandChooser();
	}

	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
		ArgParser p = new ArgParser(sender, args, selections);
		Class<? extends Command> chosen = chooser.chooseCommand(p.action);
		
		if (chosen != null){
			Command runner;
			try {
				runner = chosen.getConstructor(ArgParser.class, Executor.class).newInstance(p, this);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			if (runner.validate()){
				if (runner.checkPermission())
					sender.sendMessage(runner.execute());
				else
					sender.sendMessage(runner.permissionMessage());
				return true;
			}
			else{
				sender.sendMessage(runner.usageMessage());
				return true;
			}
		}
		else
			return false;
	}
}
