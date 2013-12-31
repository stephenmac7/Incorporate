package com.stephenmac.incorporate;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class ProcessLinkedChests extends BukkitRunnable {
	private final Incorporate plugin;
	
	public ProcessLinkedChests(Incorporate plugin){
		this.plugin = plugin;
	}
	
	@Override
	public void run() {
		List<LinkedChest> lChests = plugin.linkedChestDAO.find().asList();
		for (LinkedChest lChest : lChests){
			Block block = plugin.getServer().getWorld(lChest.getWorld())
					.getBlockAt(lChest.getX(), lChest.getY(), lChest.getZ());
			
			if (block.getState() instanceof InventoryHolder){
				Inventory inv = ((InventoryHolder) block.getState()).getInventory();
				
				for (ItemStack stack : inv.getContents()){
					if (stack != null){
						Item item = new Item();
						item.fromStack(stack);
						
						lChest.getCorp().getProduct(item, true).adjustQuantity(stack.getAmount());
					}
				}
				inv.clear();
				plugin.companyDAO.save(lChest.getCorp());
			}
			else{
				plugin.linkedChestDAO.delete(lChest);
			}
		}
	}

}
