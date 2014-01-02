package com.stephenmac.incorporate.commands;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Company;
import com.stephenmac.incorporate.Executor;
import com.stephenmac.incorporate.Product;
import com.stephenmac.incorporate.Rank;

public class CleanCommand extends Command {
	public static String[] names = {"clean"};
	public static boolean needsCorp = false;
	
	public CleanCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
	}

	@Override
	public String execute() {
		if (!p.senderIsPlayer){
			boolean aggressive = p.args.size() == 1 && p.args.get(0).equalsIgnoreCase("y");
			
			List<Company> companyList = cmdExec.companyDAO.find().asList();
			for (Company c : companyList){
				// Make sure we still have employees
				if (c.getEmployeeSet().size() == 0){
					cmdExec.companyDAO.delete(c);
				}
				else{
					// Ranks need to be cleaned up, if no employees are of a rank, delete it
					Set<String> ranks = new HashSet<String>();
					for (String rank : c.getEmployeeValues()){
						ranks.add(rank);
					}
					Iterator<Rank> rankIt = c.getRanks().iterator();
					while (rankIt.hasNext()){
						if (!ranks.contains(rankIt.next().name)){
							rankIt.remove();
						}
					}
					
					// Clean up products
					Iterator<Product> productIt = c.getProducts().iterator();
					while (productIt.hasNext()){
						Product p = productIt.next();
						if (p.getQuantity() == 0){
							if (aggressive){
								productIt.remove();
							}
							else if(p.getBuyPrice() == null && p.getSellPrice() == null){
								productIt.remove();
							}
						}
					}
					cmdExec.companyDAO.save(c);
				}
			}
			return "Your DB should be clean now.";
		}
		else
			return "Only the console can run this command.";
	}
}
