package com.stephenmac.incorporate.commands;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;
import com.stephenmac.incorporate.Product;

public class BrowseCommand extends Command {
	
	public BrowseCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
	}

	@Override
	public String execute() {
		StringBuilder s = new StringBuilder();
		s.append("      Id |     Buy |     Sell |   Qty\n");
		for (Product p : corp.getProducts()){
			s.append(String.format("%8s |%8.2f |%8.2f |%6d\n", p.getItem(), p.getBuyPrice(), p.getSellPrice(), p.getQuantity()));
		}
		return s.toString();
	}

}
