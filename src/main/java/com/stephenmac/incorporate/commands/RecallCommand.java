package com.stephenmac.incorporate.commands;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;
import com.stephenmac.incorporate.Item;
import com.stephenmac.incorporate.Permission;
import com.stephenmac.incorporate.Product;

public class RecallCommand extends PlayerContextCommand {
	
	public RecallCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
		usage = "<item> <quantity>";
		perms.add(Permission.RECALL);
	}

	@Override
	public String execute() {
		String quantity = p.args.get(1);
		int quan = Integer.parseInt(quantity);
		if (quan > 0){
			// Get product
			Item parsed = parseItem(p.args.get(0));
			Product prod = corp.getProduct(parsed);
			
			if (prod == null || prod.getQuantity() < quan){
				return "Not enough items to recall";
			}
			else{
				prod.adjustQuantity(-quan);
				givePlayer(parsed, quan);
				return "Successfully recalled " + quantity + " items";
			}
		}
		else{
			return "Cannot recall negative number of items";
		}
	}

}
