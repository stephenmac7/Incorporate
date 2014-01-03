package com.stephenmac.incorporate;

import java.util.UUID;

import org.bukkit.block.Block;
import org.bukkit.inventory.InventoryHolder;

public class ChestLinker implements ExpectingLocation {
	protected LinkedChestDAO lcDAO;
	protected LinkedChest c;

	@Override
	public String useLocation(UUID world, int x, int y, int z) {
		// Make sure it's not already linked
		if (lcDAO.findByLoc(x, y, z, world) == null) {
			c.setLoc(x, y, z);
			c.setWorld(world);
			lcDAO.save(c);

			return "Chest linked";
		}
		return "Already linked";
	}

	@Override
	public void setPlugin(Incorporate plugin) {
		lcDAO = plugin.linkedChestDAO;
	}

	public void setLinkedChest(LinkedChest c) {
		this.c = c;
	}

	@Override
	public boolean checkBlock(Block b) {
		return b.getState() instanceof InventoryHolder;
	}

}
