package com.stephenmac.incorporate.commands;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;
import com.stephenmac.incorporate.Item;
import com.stephenmac.incorporate.Permission;
import com.stephenmac.incorporate.Product;

public class SetPriceCommand extends Command {

	public SetPriceCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
		usage = "<item> <buy/sell> <price>";
		perms.add(Permission.SETPRICE);
	}

	@Override
	public String execute() {
		String item = p.args.get(0);
		String buySell = p.args.get(1);
		String price = p.args.get(2);

		Double pPrice = price.equals("null") ? null : Double.parseDouble(price);
		if (pPrice >= 0) {
			Item parsed = parseItem(item);
			Product p = corp.getProduct(parsed, true);

			switch (buySell.toLowerCase()) {
			case "buy":
				p.setBuyPrice(pPrice);
				break;
			case "sell":
				p.setSellPrice(pPrice);
				break;
			default:
				return "You did not specify buy or sell";
			}
			if (p.getBuyPrice() == null && p.getSellPrice() == null
			        && p.getQuantity() == 0) {
				corp.removeProduct(p);
				return item + " removed from " + corp.getName() + "'s books.";
			} else {
				return "Price of " + item + " set to " + price;
			}
		} else {
			return "Cannot have negative price";
		}
	}

}
