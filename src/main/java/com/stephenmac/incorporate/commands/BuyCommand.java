package com.stephenmac.incorporate.commands;

import net.milkbowl.vault.economy.EconomyResponse;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;
import com.stephenmac.incorporate.Item;
import com.stephenmac.incorporate.Product;

public class BuyCommand extends PlayerContextCommand {
	
	public BuyCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
		usage = "<item> <quantity>";
	}

	@Override
	public String execute() {
		String item = p.args.get(0);
		String quantity = p.args.get(1);
		
		int quan = Integer.parseInt(quantity);
		if (quan > 0){
			Item parsed = parseItem(item);
			Product prod = corp.getProduct(parsed, false);
			
			if (prod != null && prod.getBuyPrice() != null){
				if (prod.getQuantity() < quan){
					return "Not enough items in stock";
				}
				else{
					double totalPrice = prod.getBuyPrice() * quan;
					EconomyResponse r = cmdExec.econ.withdrawPlayer(p.playerName, totalPrice);
					if (r.transactionSuccess()){
						prod.adjustQuantity(-quan);
						corp.adjustBalance(totalPrice);
						givePlayer(parsed, quan);
						return String.format("Bought %d of %s from %s for %f", quan, item, corp.getName(), totalPrice);
					}
					else{
						return r.errorMessage;
					}
				}
			}
			else{
				return "Item not for sale";
			}
		}
		else{
			return "Cannot buy less than 1 item";
		}
	}

}
