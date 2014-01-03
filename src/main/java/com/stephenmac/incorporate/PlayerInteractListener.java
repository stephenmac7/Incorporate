package com.stephenmac.incorporate;

import java.util.Map;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {
	private Incorporate plugin;
	private Map<String, ExpectingLocation> eLActions;

	public PlayerInteractListener(Incorporate plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
		eLActions = plugin.eLActions;
	}

	@EventHandler
	public void playerInteract(PlayerInteractEvent e) {
		if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
			Player player = e.getPlayer();
			Block block = e.getClickedBlock();
			if (eLActions.containsKey(player.getName())) {
				ExpectingLocation action = eLActions.get(player.getName());
				if (action.checkBlock(block)) {
					action.setPlugin(plugin);
					player.sendMessage(action.useLocation(block.getWorld()
					        .getUID(), block.getX(), block.getY(), block.getZ()));

					eLActions.remove(player.getName());
				}
			}
		}
	}
}
