package com.stephenmac.incorporate.commands;

import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;
import com.stephenmac.incorporate.Item;
import com.stephenmac.incorporate.Product;

public class SellCommand extends PlayerContextCommand {

	public SellCommand(ArgParser p, Executor cmdExec) {
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
			Product prod = corp.getProduct(item);
			
			if (prod != null && prod.getSellPrice() != null){
				double totalPrice = prod.getSellPrice() * quan;
				if (totalPrice > corp.getBalance()){
					return "Company does not have enough money to pay you!";
				}
				else{
					// Award money
					EconomyResponse r = cmdExec.econ.depositPlayer(p.playerName, totalPrice);
					
					if (r.transactionSuccess()){
						// Delete it from player
						pinv.clear(pinv.getHeldItemSlot());
						
						// Add it to the company
						prod.adjustQuantity(quan);
						
						// Take company's money
						corp.adjustBalance(-totalPrice);
						
						// Tell the player things worked
						return String.format("%d items of type %d:%d sold for %f", quan, item.getId(), item.getData(), totalPrice);
					}
					else{
						return r.errorMessage;
					}
				}
			}
			else{
				return "Item not sellable";
			}
		}
		return "Nothing in hand";
	}

}
