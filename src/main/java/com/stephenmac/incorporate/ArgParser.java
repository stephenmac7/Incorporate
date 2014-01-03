package com.stephenmac.incorporate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArgParser {
	public String action;
	public String corp = null;

	public Player player = null;
	public String playerName = null;
	public boolean senderIsPlayer = false;
	public boolean corpSelected = false;

	public List<String> args = new ArrayList<String>();

	public ArgParser(CommandSender sender, String[] args,
	        Map<String, String> selections) {
		if (args.length > 0) {
			action = args[0].toLowerCase();
			for (int i = 1; i < args.length; i++) {
				this.args.add(args[i]);
			}

			if (sender instanceof Player) {
				player = (Player) sender;
				playerName = player.getName();
				corp = selections.get(playerName);
				if (corp != null)
					corpSelected = true;
				senderIsPlayer = true;
			}
		} else {
			action = null;
		}
	}

	public boolean ensureCorp() {
		if (corp == null) {
			if (args.size() > 0)
				corp = args.remove(0);
			else
				return false;
		}
		return true;
	}

	public boolean ensurePlayer() {
		if (playerName == null) {
			if (args.size() > 0)
				playerName = args.remove(0);
			else
				return false;
		}
		return true;
	}

	public boolean ensurePlayerContext(Server s) {
		if (player == null) {
			if (playerName == null)
				return false;
			else {
				player = s.getPlayer(playerName);
				return player != null;
			}
		}
		return true;
	}
}
