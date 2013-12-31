package com.stephenmac.incorporate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class UserCommandExecutor implements CommandExecutor {
	private CompanyDAO companyDAO;
	public Economy econ;
	public Map<String, String> selections = new HashMap<String, String>();
	public Map<String, PendingAction> pendingActions;
	
	public UserCommandExecutor(Incorporate plugin) {
		companyDAO = plugin.companyDAO;
		econ = plugin.econ;
		pendingActions = plugin.pendingActions;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] argArray) {
		ArgParser p = new ArgParser(sender, argArray, selections);
		if(p.action != null){
			String result;

			if (p.action.equals("list")){
				result = listCompanies();
			}
			else if (p.action.equals("create")){
				String corp = p.getCorp();
				if (corp != null && sender instanceof Player){
					result = createCompany(corp, ((Player) sender).getName());
				}
				else if (p.args.size() == 1 && !(sender instanceof Player)){
					result = createCompany(corp, p.args.get(0));
				}
				else{
					result = usageMessage("create <company> <console: player>");
				}
			}
			else if (p.action.equals("delete")){
				String corpName = p.getCorp();
				if (corpName != null){
					Company corp = getByName(corpName);
					if (hasPerm(sender, corp, Permission.DELETE))
						result = deleteCompany(corp);
					else
						result = permMessage("DELETE");
				}
				else{
					result = usageMessage("delete <company>");
				}
			}
			else if (p.action.equals("clean")){
				if (sender instanceof Player){
					result = "Sorry, players cannot execute this command";
				}
				else{
					if (p.args.size() == 1)
						result = cleanDb(p.args.get(0).equals("y") ? true : false);
					else
						result = cleanDb(false);
				}
			}
			else if (p.getCorp() != null){
				Company corp = getByName(p.getCorp());
				String nConsole = "Must be a player to use this command";
				
				if (corp != null){
					switch (p.action){
					case "select":
						selections.put(p.senderName, corp.getName());
						result = corp.getName() + " selected";
						break;
					
					case "deselect":
						selections.remove(p.senderName);
						result = corp.getName() + " deselected";
						break;

					case "rename":
						if (p.args.size() == 1){
							if (hasPerm(sender, corp, Permission.RENAME))
								result = renameCompany(corp, p.args.get(0));
							else
								result = permMessage("RENAME");
						}
						else{
							result = usageMessage("rename <company> <newName>");
						}
						break;
						
					case "lr": case "listranks":
						result = listRanks(corp);
						break;

					case "ar": case "addrank":
						if (p.args.size() == 2){
							if (hasPerm(sender, corp, Permission.MANAGERANKS))
								result = addRank(corp, p.args.get(0), p.args.get(1));
							else
								result = permMessage("MANAGERANKS");
						}
						else{
							result = usageMessage("addRank <company> <rank> <wage>");
						}
						break;
					
					case "rr": case "removerank":
						if (p.args.size() == 1){
							if (hasPerm(sender, corp, Permission.MANAGERANKS))
								result = removeRank(corp, p.args.get(0));
							else
								result = permMessage("MANAGERANKS");
						}
						else{
							result = usageMessage("removeRank <company> <rank>");
						}
						break;
					
					case "gw": case "getwage":
						if (p.args.size() == 1){
							result = getWage(corp, p.args.get(0));
						}
						else{
							result = usageMessage("getWage <company> <rank>");
						}
						break;

					case "sw": case "setwage":
						if (p.args.size() == 2){
							if (hasPerm(sender, corp, Permission.MANAGERANKS))
								result = setWage(corp, p.args.get(0), p.args.get(1));
							else
								result = permMessage("MANAGERANKS");
						}
						else{
							result = usageMessage("setWage <company> <rank> <wage>");
						}
						break;
					
					case "gdr": case "getdrank":
						result = corp.getName() + "'s default rank is: " + corp.getDefault();
						break;
					
					case "sdr": case "setdrank":
						if (p.args.size() == 1){
							if (hasPerm(sender, corp, Permission.MANAGERANKS)){
								if(corp.setDefault(p.args.get(0)))
									result = "Successfully changed " + corp.getName() + "'s default rank to " + p.args.get(0);
								else
									result = "Rank does not exist";
							}
							else{
								result = permMessage("MANAGERANKS");
							}
						}
						else{
							result = usageMessage("setDRank <company> <rank>");
						}
						break;
					
					case "gp": case "grantperm":
						if (p.args.size() == 2){
							if (hasPerm(sender, corp, Permission.MANAGERANKS))
								result = grantPerm(corp, p.args.get(0), p.args.get(1));
							else
								result = permMessage("MANAGERANKS");
						}
						else{
							result = usageMessage("grantPerm <company> <rank> <perm>");
						}
						break;
					
					case "rp": case "revokeperm":
						if (p.args.size() == 2){
							if (hasPerm(sender, corp, Permission.MANAGERANKS))
								result = revokePerm(corp, p.args.get(0), p.args.get(1));
							else
								result = permMessage("MANAGERANKS");
						}
						else{
							result = usageMessage("revokePerm <company> <rank> <perm>");
						}
						break;
					
					case "lp": case "listperms":
						if(p.args.size() == 1){
							result = listPerms(corp, p.args.get(0));
						}
						else{
							result = usageMessage("listPerms <company> <rank>");
						}
						break;

					case "gr": case "getrank":
						if (p.args.size() == 1){
							result = getRank(corp, p.args.get(0));
						}
						else{
							result = usageMessage("getRank <company> <employee>");
						}
						break;
					
					case "sr": case "setrank":
						if (p.args.size() == 2){
							if (hasPerm(sender, corp, Permission.MANAGEEMPLOYEES))
								result = setRank(corp, p.args.get(0), p.args.get(1));
							else
								result = permMessage("MANAGEEMPLOYEES");
						}
						else{
							result = usageMessage("setRank <company> <employee> <newRank>");
						}
						break;
					
					case "fire":
						if (p.args.size() == 1){
							if (hasPerm(sender, corp, Permission.FIRE))
								result = fire(corp, p.args.get(0));
							else
								result = permMessage("FIRE");
						}
						else{
							result = usageMessage("fire <company> <employee>");
						}
						break;
					
					case "resign":
						if (sender instanceof Player){
							result = resign(corp, ((Player) sender).getName());
						}
						else{
							if (p.args.size() == 1){
								result = resign(corp, p.args.get(0));
							}
							else{
								result = usageMessage("resign <company> <console: player>");
							}
						}
						break;
						
					case "empl": case "employees":
						result = listEmployees(corp);
						break;
					
					case "pe": case "payEmployees":
						Permission[] perms = {Permission.WITHDRAW, Permission.MANAGEEMPLOYEES};
						if (hasPerm(sender, corp, perms))
							result = payEmployees(corp);
						else
							result = permMessage("WITHDRAW and MANAGEEMPLOYEES");
						break;
					
					case "apply":
						if (sender instanceof Player){
							result = apply(corp, ((Player) sender).getName());
						}
						else{
							if (p.args.size() == 1){
								result = apply(corp, p.args.get(0));
							}
							else{
								result = usageMessage("apply <company> <console: player>");
							}
						}
						break;
					
					case "reject":
						if (p.args.size() == 1){
							if (hasPerm(sender, corp, Permission.HIRE))
								result = reject(corp, p.args.get(0));
							else
								result = permMessage("HIRE");
						}
						else{
							result = usageMessage("reject <company> <applicant>");
						}
						break;
					
					case "hire":
						if (p.args.size() == 1){
							if (hasPerm(sender, corp, Permission.HIRE))
								result = hire(corp, p.args.get(0));
							else
								result = permMessage("HIRE");
						}
						else{
							result = usageMessage("hire <company> <applicant>");
						}
						break;
					
					case "appl": case "applicants":
						if (hasPerm(sender, corp, Permission.HIRE))
							result = listApplicants(corp);
						else
							result = permMessage("HIRE");
						break;
					
					case "dp": case "deposit":
						if (sender instanceof Player && p.args.size() == 1)
							result = deposit(corp, ((Player) sender).getName(), p.args.get(0));
						else if (!(sender instanceof Player) && p.args.size() == 2)
							result = deposit(corp, p.args.get(0), p.args.get(1));
						else
							result = usageMessage("deposit <company> <console: player> <amount>");
						break;
					
					case "wd": case "withdraw":
						if (sender instanceof Player && p.args.size() == 1){
							if (hasPerm(sender, corp, Permission.WITHDRAW))
								result = withdraw(corp, ((Player) sender).getName(), p.args.get(0));
							else
								result = permMessage("WITHDRAW");
						}
						else if (!(sender instanceof Player) && p.args.size() == 2){
							result = withdraw(corp, p.args.get(0), p.args.get(1));
						}
						else{
							result = usageMessage("withdraw <company> <console: player> <amount>");
						}
						break;
						
					case "b": case "balance":
						if (hasPerm(sender, corp, Permission.GETBALANCE))
							result = balance(corp);
						else
							result = permMessage("GETBALANCE");
						break;
					
					case "restock":
						if (sender instanceof Player)
							result = restock(corp, (Player) sender);
						else
							result = nConsole;
						break;
					
					case "recall":
						if (p.args.size() == 2){
							if (sender instanceof Player){
								if (hasPerm(sender, corp, Permission.RECALL))
									result = recall(corp, (Player) sender, p.args.get(0), p.args.get(1));
								else
									result = permMessage("RECALL");
							}
							else{
								result = nConsole;
							}
						}
						else{
							result = usageMessage("recall <company> <itemNumber> <quantity>");
						}
						break;
					
					case "price":
						if (p.args.size() == 3){
							if (hasPerm(sender, corp, Permission.SETPRICE))
								result = price(corp, p.args.get(0), p.args.get(1), p.args.get(2));
							else
								result = permMessage("SETPRICE");
						}
						else{
							result = usageMessage("price <company> <itemNumber> <buy/sell> <price>");
						}
						break;
					
					case "buy":
						if (p.args.size() == 2){
							if (sender instanceof Player)
								result = buy(corp, (Player) sender, p.args.get(0), p.args.get(1));
							else
								result = nConsole;
						}
						else{
							result = usageMessage("buy <company> <itemNumber> <quantity>");
						}
						break;
						
					case "sell":
						if (sender instanceof Player)
							result = sell(corp, (Player) sender);
						else
							result = nConsole;
						break;
					
					case "browse":
						result = browse(corp);
						break;
					
					case "pi": case "productInfo":
						if (p.args.size() == 1){
							result = productInfo(corp, p.args.get(0));
						}
						else{
							result = usageMessage("productInfo <company> <itemNumber>");
						}
						break;
					
					case "rsc":
						if (sender instanceof Player){
							PendingAction rcAction = new PendingAction("linkChest", "restock", corp);
							pendingActions.put(((Player) sender).getName(), rcAction);
							result = "Please click on the chest which you would like to link as a restocking chest";
						}
						else
							result = nConsole;
						break;
					
					case "ulc":
						if (sender instanceof Player){
							PendingAction ulAction = new PendingAction("unlinkChest", corp);
							pendingActions.put(((Player) sender).getName(), ulAction);
							result = "Please click on the chest which you would like to unlink";
						}
						else
							result = nConsole;
						break;
					
					case "cancel":
						if (sender instanceof Player){
							pendingActions.remove(((Player) sender).getName());
							result = "Canceled";
						}
		
					default:
						result = "Action does not exist";
						break;
					}
					companyDAO.save(corp);
				}
				else{
					result = "No such company";
				}
			}
			else{
				result = "You must specify a company";
			}
			
			sender.sendMessage(result);
			return true;
		}
		return false;
	}

	private String cleanDb(boolean aggressive) {
		List<Company> companyList = companyDAO.find().asList();
		for (Company c : companyList){
			// Make sure we still have employees
			if (c.getEmployeeSet().size() == 0){
				companyDAO.delete(c);
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
				companyDAO.save(c);
			}
		}
		return "Your DB should be clean now.";
	}

	private String listCompanies(){
		StringBuilder r = new StringBuilder();
		List<Company> companyList = companyDAO.find().asList();

		r.append("Companies (" + Integer.toString(companyList.size()) + "):\n");
		for (Company c : companyList){
			r.append("* " + c.getName() + "\n");
		}
		return r.toString();
	}

	private String createCompany(String name, String player){
		if (getByName(name) == null){
			EconomyResponse r = econ.withdrawPlayer(player, 200);
			if (r.transactionSuccess()){
				Company company = new Company();
				company.setName(name);
				company.addEmployee(player);
				companyDAO.save(company);
			}
			else{
				return r.errorMessage;
			}

			return String.format("Successfully created %s with %s as owner", name, player);
		}
		else{
			return String.format("%s already exists", name);
		}
	}
	
	private String deleteCompany(Company company) {
		companyDAO.delete(company);
		return "Successfully removed " + company.getName();
	}
	
	private String renameCompany(Company company, String newName){
		company.setName(newName);
		return "Successfully renamed to " + company.getName();
	}
	
	private String listRanks(Company company){
		StringBuilder s = new StringBuilder();
		s.append("Ranks (" + Integer.toString(company.getRanks().size()) + "):\n");
		for (Rank r : company.getRanks()){
			s.append("* " + r.name + "\n");
		}
		return s.toString();
	}

	private String addRank(Company company, String rank, String wage){
		if (company.addRank(rank, Double.parseDouble(wage))){
			return "Successfully added rank, " + rank + " to " + company.getName();
		}
		else{
			return "Rank exists";
		}
	}
	
	private String removeRank(Company company, String rank){
		byte val = company.removeRank(rank);
		switch (val){
			case 0:
				return "Successfully removed " + rank + " from " + company.getName();
			case 1:
				return "Cannot delete default rank";
			default:
				return "Rank does not exist";
		}
	}
	
	private String getWage(Company company, String rank){
		double wage = company.getWage(rank);
		if (wage >= 0)
			return String.format("The wage of a %s is %f", rank, wage);
		else
			return "No such rank";		
	}

	private String setWage(Company company, String rank, String wage){
		if (company.setWage(rank, Double.parseDouble(wage))){
			return "Successfully changed wage of " + rank + " to " + wage;
		}
		else{
			return "No such rank";
		}
	}
	
	private String grantPerm(Company company, String rank, String perm){
		Permission p;
		try{
			p = Permission.valueOf(perm.toUpperCase());
		} catch (IllegalArgumentException e){
			return "No such permission";
		}

		if (company.grantPermission(rank, p))
			return perm + " granted to " + rank + " in " + company.getName();
		else
			return "No such rank or already granted";
	}
	
	private String revokePerm(Company company, String rank, String perm){
		Permission p;
		try{
			p = Permission.valueOf(perm.toUpperCase());
		} catch (IllegalArgumentException e){
			return "No such permission";
		}

		if (company.revokePermission(rank, p))
			return perm + " revoked from " + rank + " in " + company.getName();
		else
			return "No such rank";
	}
	
	private String listPerms(Company company, String rank){
		Rank r = company.getRank(rank);
		if (r == null){
			return "No such rank";
		}
		else{
			StringBuilder s = new StringBuilder();
			for (Permission p : r.permissions){
				s.append(p.toString() + " ");
			}
			s.deleteCharAt(s.length()-1);
			return s.toString();
		}
	}
	
	private String getRank(Company company, String employee){
		if (company.isEmployee(employee))
			return employee + "'s rank is: " + company.getEmployeeRank(employee).name;
		else
			return notEmployeeMessage(company, employee);
	}
	
	private String setRank(Company company, String employee, String rank){
		if (company.isEmployee(employee)){
			if(company.setEmployeeRank(employee, rank)){
				return "Successfully changed " + employee + "'s rank to " + rank;
			}
			else{
				return "No such rank";
			}
		}
		else{
			return notEmployeeMessage(company, employee);
		}
	}
	
	private String fire(Company company, String employee){
		if (company.fire(employee))
			return employee + " has been fired from " + company.getName();
		else
			return employee + " not an employee of " + company.getName();
	}
	
	private String resign(Company company, String employee){
		if (company.fire(employee))
			return employee + " has resigned from " + company.getName();
		else
			return employee + " not an employee of " + company.getName();
	}
	
	private String listEmployees(Company company){
		StringBuilder r = new StringBuilder();
		Set<String> employees = company.getEmployeeSet();
		
		r.append("Employees (" + Integer.toString(employees.size()) + "):\n");
		for (String s : employees){
			r.append("* " + s + "\n");
		}
		return r.toString();
	}
	
	private String payEmployees(Company company){
		// Get employees
		Map<String, Rank> employees = company.getEmployees();

		// Make sure we have enough money
		double amount = 0;
		for (Rank r : employees.values()){
			amount += r.wage;
		}
		if (amount > company.getBalance())
			return "Not enough money to pay all employees. Please get more money or cut your employees' paychecks.";
		
		// Pay them
		StringBuilder s = new StringBuilder();
		for (Map.Entry<String, Rank> employee : employees.entrySet()){
			double wage = employee.getValue().wage;
			EconomyResponse r = econ.depositPlayer(employee.getKey(), wage);
			if (r.transactionSuccess()){
				company.adjustBalance(-wage);
			}
			else{
				s.append("Unable to pay " + employee + ": " + r.errorMessage + "\n");
			}
		}
		s.append("Employees paid");
		return s.toString();
	}
	
	private String apply(Company company, String applicant){
		if (company.addApplicant(applicant))
			return "You've applied to " + company.getName();
		else
			return "You're already an employee or applicant";
	}
	
	private String reject(Company company, String applicant){
		if (company.removeApplicant(applicant))
			return applicant + " has been rejected";
		else
			return applicant + " is not an applicant";
	}
	
	private String hire(Company company, String employee){
		if (company.hire(employee))
			return employee + " has been hired as a " + company.getDefault();
		else
			return employee + " is not an applicant";
	}
	
	private String listApplicants(Company company){
		StringBuilder r = new StringBuilder();
		Set<String> applicants = company.getApplicantSet();
		
		r.append("Applicants (" + Integer.toString(applicants.size()) + "):\n");
		for (String s : applicants){
			r.append("* " + s + "\n");
		}
		return r.toString();
	}
	
	private String deposit(Company company, String player, String amount){
		double pAmount = Double.parseDouble(amount);
		if (pAmount > 0){
			EconomyResponse r = econ.withdrawPlayer(player, pAmount);
			if (r.transactionSuccess()){
				company.adjustBalance(pAmount);
				return "Successfully deposited " + amount + " into " + company.getName();
			}
			else{
				return r.errorMessage;
			}
		}
		else{
			return "Cannot deposit that amount of money";
		}
	}
	
	private String withdraw(Company company, String player, String amount){
		double pAmount = Double.parseDouble(amount);
		if (pAmount > 0 && company.getBalance() >= pAmount){
			EconomyResponse r = econ.depositPlayer(player, pAmount);
			if (r.transactionSuccess()){
				company.adjustBalance(-pAmount);
				return "Successfully withdrew " + amount + " from " + company.getName();
			}
			else{
				return r.errorMessage;
			}
		}
		else{
			return "Cannot withdraw that amount of money";
		}
	}
	
	private String balance(Company company){
		return company.getName() + "'s balance is: " + company.getBalance();
	}
	
	private String restock(Company company, Player player){
		// Get item stack
		PlayerInventory pinv = player.getInventory();
		ItemStack cur_stack = pinv.getItemInHand();
		
		// Get information
		int quan = cur_stack.getAmount();
		if (quan > 0){
			Item item = new Item();
			item.fromStack(cur_stack);
			
			// Delete it from player
			pinv.clear(pinv.getHeldItemSlot());
			
			// Add it to the company
			Product p = company.getProduct(item, true);
			p.adjustQuantity(quan);
			
			// Tell the player things worked
			return String.format("%d items of type %d:%d added to stock", quan, item.getId(), item.getData());
		}
		return "Nothing in hand";
	}
	
	private String recall(Company company, Player player, String item, String quantity){
		int quan = Integer.parseInt(quantity);
		if (quan > 0){
			// Get product
			Item parsed = parseItem(item);
			Product p = company.getProduct(parsed);
			
			if (p == null || p.getQuantity() < quan){
				return "Not enough items to recall";
			}
			else{
				p.adjustQuantity(-quan);
				givePlayer(player, parsed, quan);
				return "Successfully recalled " + quantity + " items";
			}
		}
		else{
			return "Cannot recall negative number of items";
		}
	}
	
	private String price(Company company, String item, String buySell, String price){
		Double pPrice = price.equals("null") ? null : Double.parseDouble(price);
		if (pPrice > 0){
			Item parsed = parseItem(item);
			Product p = company.getProduct(parsed, true);
			
			switch(buySell.toLowerCase()){
			case "buy":
				p.setBuyPrice(pPrice);
				break;
			case "sell":
				p.setSellPrice(pPrice);
				break;
			default:
				return "You did not specity buy or sell";
			}
			if (p.getBuyPrice() == null && p.getSellPrice() == null && p.getQuantity() == 0){
				company.removeProduct(p);
				return item + " removed from " + company.getName() + "'s books.";
			}
			else{
				return "Price of " + item + " set to " + price;
			}
		}
		else{
			return "Cannot have negative price or no charge";
		}
	}
	
	private String buy(Company company, Player player, String item, String quantity){
		int quan = Integer.parseInt(quantity);
		if (quan > 0){
			Item parsed = parseItem(item);
			Product p = company.getProduct(parsed, false);
			
			if (p != null && p.getBuyPrice() != null){
				if (p.getQuantity() < quan){
					return "Not enough items in stock";
				}
				else{
					double totalPrice = p.getBuyPrice() * quan;
					EconomyResponse r = econ.withdrawPlayer(player.getName(), totalPrice);
					if (r.transactionSuccess()){
						p.adjustQuantity(-quan);
						company.adjustBalance(totalPrice);
						givePlayer(player, parsed, quan);
						return String.format("Bought %d of %s from %s for %f", quan, item, company.getName(), totalPrice);
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
	
	private String sell(Company company, Player player){
		// Get item stack
		PlayerInventory pinv = player.getInventory();
		ItemStack cur_stack = pinv.getItemInHand();
		
		// Get information
		int quan = cur_stack.getAmount();
		if (quan > 0){
			Item item = new Item();
			item.fromStack(cur_stack);
			Product p = company.getProduct(item);
			
			if (p != null && p.getSellPrice() != null){
				double totalPrice = p.getSellPrice() * quan;
				if (totalPrice > company.getBalance()){
					return "Company does not have enough money to pay you!";
				}
				else{
					// Award money
					EconomyResponse r = econ.depositPlayer(player.getName(), totalPrice);
					
					if (r.transactionSuccess()){
						// Delete it from player
						pinv.clear(pinv.getHeldItemSlot());
						
						// Add it to the company
						p.adjustQuantity(quan);
						
						// Take company's money
						company.adjustBalance(-totalPrice);
						
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
	
	private String browse(Company company){
		StringBuilder s = new StringBuilder();
		s.append("      Id |     Buy |     Sell |   Qty\n");
		for (Product p : company.getProducts()){
			s.append(String.format("%8s |%8.2f |%8.2f |%6d\n", p.getItem(), p.getBuyPrice(), p.getSellPrice(), p.getQuantity()));
		}
		return s.toString();
	}
	
	private String productInfo(Company company, String item){
		Item parsed = parseItem(item);
		Product p = company.getProduct(parsed);
		if (p != null){
			return p.toString();
		}
		else{
			return "Company does not offer " + item;
		}
	}
	
	// Helping functions
	private Item parseItem(String item){
		Item i = new Item();
		if (item.contains(":")){
			String[] itemArgs = item.split(":");
			i.setId(Integer.parseInt(itemArgs[0]));
			i.setData(Byte.parseByte(itemArgs[1]));
		}
		else{
			i.setId(Integer.parseInt(item));
		}
		return i;
	}
	
	@SuppressWarnings("deprecation")
	private void givePlayer(Player player, Item item, int quantity){
		player.getInventory().addItem(new ItemStack(item.getId(), quantity, (short) 0, item.getData()));
	}
	
	private Company getByName(String name){
		return companyDAO.findOne("name", name);
	}
	
	private boolean hasPerm(CommandSender sender, Company corp, Permission[] perms){
		if (sender instanceof Player){
			Player player = (Player) sender;
			return player.hasPermission("inc.admin") || allPerms(player.getName(), corp, perms);
		}
		else{
			return true;
		}
	}
	
	private boolean hasPerm(CommandSender sender, Company corp, Permission perm){
		Permission[] a = {perm};
		return hasPerm(sender, corp, a);
	}
	
	private boolean allPerms(String player, Company corp, Permission[] perms){
		for (Permission perm : perms){
			if (!corp.hasPerm(player, perm))
				return false;
		}
		return true;
	}
	
	private String permMessage(String perm){
		return "You do not have " + perm + " permission"; 
	}

	private String usageMessage(String message){
		return "Usage: /inc " + message;
	}
	
	private String notEmployeeMessage(Company corp, String employee){
		return employee + " is not an employee of " + corp.getName();
	}
}
