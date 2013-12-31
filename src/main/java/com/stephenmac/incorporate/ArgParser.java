package com.stephenmac.incorporate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArgParser {
	public String action;
	public String corporation = null;
	public String senderName;
	public List<String> args = new ArrayList<String>();
	
	public ArgParser(CommandSender sender, String[] args, Map<String, String> selections){
		if(args.length > 0){
			action = args[0].toLowerCase();
			for(int i=1;i<args.length;i++){
				this.args.add(args[i]);
			}
			
			if(sender instanceof Player)
				senderName = ((Player) sender).getName();
			else
				senderName = "CONSOLE";
			corporation = selections.get(senderName);
		}
		else{
			action = null;
		}
	}
	
	public String getCorp(){
		if (corporation == null){
			if (args.size() > 0){
				corporation = args.get(0);
				args.remove(0);
			}
			else{
				return null;
			}
		}
		return corporation;
	}
}
