package com.stephenmac.incorporate;

import org.bukkit.block.Block;
import org.bukkit.inventory.InventoryHolder;

public class ChestLinker implements ExpectingLocation {
	protected LinkedChestDAO lcDAO;
	protected LinkedChest c;

	@Override
	public String useLocation(SimpleLocation loc) {
		// Make sure it's not already linked
		if (lcDAO.findByLoc(loc) == null) {
			c.setLoc(loc);
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
