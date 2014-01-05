package com.stephenmac.incorporate;

import org.bukkit.block.Block;

public interface ExpectingLocation {
	public void setPlugin(Incorporate plugin);

	public boolean checkBlock(Block b);

	public String useLocation(SimpleLocation loc);
}
