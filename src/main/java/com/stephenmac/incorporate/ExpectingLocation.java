package com.stephenmac.incorporate;

import java.util.UUID;

import org.bukkit.block.Block;

public interface ExpectingLocation {
	public void setPlugin(Incorporate plugin);

	public boolean checkBlock(Block b);

	public String useLocation(UUID world, int x, int y, int z);
}
