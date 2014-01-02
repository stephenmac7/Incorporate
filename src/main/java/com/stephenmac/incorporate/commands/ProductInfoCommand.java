package com.stephenmac.incorporate.commands;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;
import com.stephenmac.incorporate.Item;
import com.stephenmac.incorporate.Product;

public class ProductInfoCommand extends Command {
	public static String[] names = {"productInfo", "pi", "info"};
	public static int nArgs = 1;
	public static String usage = "<item>";
	
	public ProductInfoCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
	}

	@Override
	public String execute() {
		String item = p.args.get(0);
		
		Item parsed = parseItem(item);
		Product p = corp.getProduct(parsed);
		if (p != null){
			return p.toString();
		}
		else{
			return "Company does not offer " + item;
		}
	}

}
