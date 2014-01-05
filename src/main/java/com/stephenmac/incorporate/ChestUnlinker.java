package com.stephenmac.incorporate;


public class ChestUnlinker extends ChestLinker implements ExpectingLocation {

	@Override
	public String useLocation(SimpleLocation loc) {
		LinkedChest lChest = lcDAO.findByLoc(loc);
		if (lChest == null)
			return "Chest not linked";
		else {
			lcDAO.delete(lChest);
			return "Chest unlinked";
		}
	}

}
