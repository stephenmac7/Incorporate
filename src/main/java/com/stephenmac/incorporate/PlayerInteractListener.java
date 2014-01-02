package com.stephenmac.incorporate;

import java.util.Map;
import java.util.UUID;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;

public class PlayerInteractListener implements Listener {
	private LinkedChestDAO lcDAO;
	private Map<String, PendingAction> pendingActions;
	
	public PlayerInteractListener(Incorporate plugin){
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		lcDAO = plugin.linkedChestDAO;
		pendingActions = plugin.pendingActions;
	}
	
	@EventHandler
	public void playerInteract(PlayerInteractEvent e){
		if (e.getAction() == Action.LEFT_CLICK_BLOCK){
			Player player = e.getPlayer();
			Block block = e.getClickedBlock();
			if (block.getState() instanceof InventoryHolder && pendingActions.containsKey(player.getName())){
				PendingAction action = pendingActions.get(player.getName());
				// Make location easily accessible
				int x = block.getX();
				int y = block.getY();
				int z = block.getZ();
				UUID world = block.getWorld().getUID();
				
				String result;
				switch(action.action){
				case "linkChest":
					result = linkChest(action.corporation, ((LinkType) action.spec), x, y, z, world);
					break;
				case "unlinkChest":
					result = unlinkChest(x, y, z, world);
					break;
				default:
					result = "Something went terribly wrong. Please file a bug report.";
					break;
				}
				
				player.sendMessage(result);
				pendingActions.remove(player.getName());
			}
		}
	}
	
	private String linkChest(Company corp, LinkType lt, int x, int y, int z, UUID world){
		// Make sure it's not already linked
		if (lcDAO.findByLoc(x, y, z, world) == null){
			LinkedChest c = new LinkedChest();
			c.setLoc(x, y, z);
			c.setWorld(world);
			c.setCorp(corp);
			c.setLinkType(lt);
			lcDAO.save(c);
			
			return "Now linked as a restock chest";
		}
		return "Already linked";
	}
	private String unlinkChest(int x, int y, int z, UUID world){
		LinkedChest lChest = lcDAO.findByLoc(x, y, z, world);
		if (lChest == null)
			return "Chest not linked";
		else{
			lcDAO.delete(lChest);
			return "Chest unlinked";
		}
	}
}
