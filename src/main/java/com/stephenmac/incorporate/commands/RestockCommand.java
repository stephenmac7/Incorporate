package com.stephenmac.incorporate.commands;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;
import com.stephenmac.incorporate.Item;
import com.stephenmac.incorporate.Product;

public class RestockCommand extends PlayerContextCommand {
	public static String[] names = {"restock", "rs"};
	
	public RestockCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
	}

	@Override
	public String execute() {
		// Get item stack
		PlayerInventory pinv = p.player.getInventory();
		ItemStack cur_stack = pinv.getItemInHand();
		
		// Get information
		int quan = cur_stack.getAmount();
		if (quan > 0){
			Item item = new Item();
			item.fromStack(cur_stack);
			
			// Delete it from player
			pinv.clear(pinv.getHeldItemSlot());
			
			// Add it to the company
			Product p = corp.getProduct(item, true);
			p.adjustQuantity(quan);
			
			// Tell the player things worked
			return String.format("%d items of type %d:%d added to stock", quan, item.getId(), item.getData());
		}
		return "Nothing in hand";
	}

}
