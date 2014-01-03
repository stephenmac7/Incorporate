package com.stephenmac.incorporate;

import java.util.UUID;

public class ChestUnlinker extends ChestLinker implements ExpectingLocation {

	@Override
	public String useLocation(UUID world, int x, int y, int z) {
		LinkedChest lChest = lcDAO.findByLoc(x, y, z, world);
		if (lChest == null)
			return "Chest not linked";
		else {
			lcDAO.delete(lChest);
			return "Chest unlinked";
		}
	}

}
