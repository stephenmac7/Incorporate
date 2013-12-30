package com.stephenmac.incorporate;

import java.util.HashSet;
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
	public static Economy econ;
	 
	public UserCommandExecutor(Incorporate plugin) {
		companyDAO = plugin.companyDAO;
		econ = plugin.econ;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length > 0){
			String action = args[0];
			String result;

			if (action.equalsIgnoreCase("list")){
				result = listCompanies();
			}
			else if (action.equalsIgnoreCase("create")){
				if (args.length == 2 && sender instanceof Player){
					result = createCompany(args[1], ((Player) sender).getName());
				}
				else if (args.length == 3 && !(sender instanceof Player)){
					result = createCompany(args[1], args[2]);
				}
				else{
					result = usageMessage("create <company> <console: player>");
				}
			}
			else if (action.equalsIgnoreCase("delete")){
				if (args.length == 2){
					Company corp = getByName(args[1]);
					if (hasPerm(sender, corp, Permission.DELETE))
						result = deleteCompany(corp);
					else
						result = permMessage("DELETE");
				}
				else{
					result = usageMessage("delete <company>");
				}
			}
			else if (action.equalsIgnoreCase("clean")){
				if (sender instanceof Player){
					result = "Sorry, players cannot execute this command";
				}
				else{
					if (args.length == 2)
						result = cleanDb(args[1] == "y" ? true : false);
					else
						result = cleanDb(false);
				}
			}
			else if (args.length > 1){
				Company corp = getByName(args[1]);
				String nConsole = "Must be a player to use this command";
				
				if (corp != null){
					switch (action.toLowerCase()){
					case "rename":
						if (args.length == 3){
							if (hasPerm(sender, corp, Permission.RENAME))
								result = renameCompany(corp, args[2]);
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
						if (args.length == 4){
							if (hasPerm(sender, corp, Permission.MANAGERANKS))
								result = addRank(corp, args[2], args[3]);
							else
								result = permMessage("MANAGERANKS");
						}
						else{
							result = usageMessage("addRank <company> <rank> <wage>");
						}
						break;
					
					case "rr": case "removerank":
						if (args.length == 3){
							if (hasPerm(sender, corp, Permission.MANAGERANKS))
								result = removeRank(corp, args[2]);
							else
								result = permMessage("MANAGERANKS");
						}
						else{
							result = usageMessage("removeRank <company> <rank>");
						}
						break;
					
					case "gw": case "getwage":
						if (args.length == 3){
							result = getWage(corp, args[2]);
						}
						else{
							result = usageMessage("getWage <company> <rank>");
						}
						break;

					case "sw": case "setwage":
						if (args.length == 4){
							if (hasPerm(sender, corp, Permission.MANAGERANKS))
								result = setWage(corp, args[2], args[3]);
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
						if (args.length == 3){
							if (hasPerm(sender, corp, Permission.MANAGERANKS)){
								if(corp.setDefault(args[2]))
									result = "Successfully changed " + corp.getName() + "'s default rank to " + args[2];
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
						if (args.length == 4){
							if (hasPerm(sender, corp, Permission.MANAGERANKS))
								result = grantPerm(corp, args[2], args[3]);
							else
								result = permMessage("MANAGERANKS");
						}
						else{
							result = usageMessage("grantPerm <company> <rank> <perm>");
						}
						break;
					
					case "rp": case "revokeperm":
						if (args.length == 4){
							if (hasPerm(sender, corp, Permission.MANAGERANKS))
								result = revokePerm(corp, args[2], args[3]);
							else
								result = permMessage("MANAGERANKS");
						}
						else{
							result = usageMessage("revokePerm <company> <rank> <perm>");
						}
						break;
					
					case "lp": case "listperms":
						if(args.length == 3){
							result = listPerms(corp, args[2]);
						}
						else{
							result = usageMessage("listPerms <company> <rank>");
						}
						break;

					case "gr": case "getrank":
						if (args.length == 3){
							result = getRank(corp, args[2]);
						}
						else{
							result = usageMessage("getRank <company> <employee>");
						}
						break;
					
					case "sr": case "setrank":
						if (args.length == 4){
							if (hasPerm(sender, corp, Permission.MANAGEEMPLOYEES))
								result = setRank(corp, args[2], args[3]);
							else
								result = permMessage("MANAGEEMPLOYEES");
						}
						else{
							result = usageMessage("setRank <company> <employee> <newRank>");
						}
						break;
					
					case "fire":
						if (args.length == 3){
							if (hasPerm(sender, corp, Permission.FIRE))
								result = fire(corp, args[2]);
							else
								result = permMessage("FIRE");
						}
						else{
							result = usageMessage("fire <company> <employee>");
						}
						break;
					
					case "resign":
						if (args.length == 2 && sender instanceof Player){
							result = resign(corp, ((Player) sender).getName());
						}
						else{
							if (args.length == 3){
								result = resign(corp, args[2]);
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
							if (args.length == 3){
								result = apply(corp, args[2]);
							}
							else{
								result = usageMessage("apply <company> <console: player>");
							}
						}
						break;
					
					case "reject":
						if (args.length == 3){
							if (hasPerm(sender, corp, Permission.HIRE))
								result = reject(corp, args[2]);
							else
								result = permMessage("HIRE");
						}
						else{
							result = usageMessage("reject <company> <applicant>");
						}
						break;
					
					case "hire":
						if (args.length == 3){
							if (hasPerm(sender, corp, Permission.HIRE))
								result = hire(corp, args[2]);
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
						if (sender instanceof Player && args.length == 3)
							result = deposit(corp, ((Player) sender).getName(), args[2]);
						else if (!(sender instanceof Player) && args.length == 4)
							result = deposit(corp, args[2], args[3]);
						else
							result = usageMessage("deposit <company> <console: player> <amount>");
						break;
					
					case "wd": case "withdraw":
						if (sender instanceof Player && args.length == 3){
							if (hasPerm(sender, corp, Permission.WITHDRAW))
								result = withdraw(corp, ((Player) sender).getName(), args[2]);
							else
								result = permMessage("WITHDRAW");
						}
						else if (!(sender instanceof Player) && args.length == 4){
							result = withdraw(corp, args[2], args[3]);
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
						if (args.length == 4){
							if (sender instanceof Player){
								if (hasPerm(sender, corp, Permission.RECALL))
									result = recall(corp, (Player) sender, args[2], args[3]);
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
						if (args.length == 5){
							if (hasPerm(sender, corp, Permission.SETPRICE))
								result = price(corp, args[2], args[3], args[4]);
							else
								result = permMessage("SETPRICE");
						}
						else{
							result = usageMessage("price <company> <itemNumber> <buy/sell> <price>");
						}
						break;
					
					case "buy":
						if (args.length == 4){
							if (sender instanceof Player)
								result = buy(corp, (Player) sender, args[2], args[3]);
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
						if (args.length == 3){
							result = productInfo(corp, args[2]);
						}
						else{
							result = usageMessage("productInfo <company> <itemNumber>");
						}
						break;
		
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
				for (Rank r : c.getRanks()){
					if (!ranks.contains(r.name)){
						c.removeRank(r.name);
					}
				}
				// Clean up products
				for (Product p : c.getProducts()){
					if (p.getQuantity() == 0){
						if (aggressive){
							c.removeProduct(p);
						}
						else if(p.getBuyPrice() == null && p.getSellPrice() == null){
							c.removeProduct(p);
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
			Company company = new Company();
			company.setName(name);
			company.addEmployee(player);
			companyDAO.save(company);

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
	
	@SuppressWarnings("deprecation")
	private String restock(Company company, Player player){
		// Get item stack
		PlayerInventory pinv = player.getInventory();
		ItemStack cur_stack = pinv.getItemInHand();
		
		// Get information
		int quan = cur_stack.getAmount();
		if (quan > 0){
			Item item = new Item();
			item.setData(cur_stack.getData().getData());
			item.setId(cur_stack.getTypeId());
			
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
		Double pPrice = price == "null" ? null : Double.parseDouble(price);
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
					EconomyResponse r = econ.withdrawPlayer(player.getName(), p.getBuyPrice() * quan);
					if (r.transactionSuccess()){
						p.adjustQuantity(-quan);
						givePlayer(player, parsed, quan);
						return "Successfully bought " + quantity + " of " + item + " from " + company.getName();
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
	
	@SuppressWarnings("deprecation")
	private String sell(Company company, Player player){
		// Get item stack
		PlayerInventory pinv = player.getInventory();
		ItemStack cur_stack = pinv.getItemInHand();
		
		// Get information
		int quan = cur_stack.getAmount();
		if (quan > 0){
			Item item = new Item();
			item.setData(cur_stack.getData().getData());
			item.setId(cur_stack.getTypeId());
			Product p = company.getProduct(item);
			
			if (p != null && p.getSellPrice() != null){
				// Award money
				EconomyResponse r = econ.depositPlayer(player.getName(), p.getSellPrice() * quan);
				
				if (r.transactionSuccess()){
					// Delete it from player
					pinv.clear(pinv.getHeldItemSlot());
					
					// Add it to the company
					p.adjustQuantity(quan);
					
					// Take company's money
					company.adjustBalance(p.getSellPrice() * -quan);
					
					// Tell the player things worked
					return String.format("%d items of type %d:%d sold", quan, item.getId(), item.getData());
				}
				else{
					return r.errorMessage;
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
		for (Product p : company.getProducts()){
			if (p.getBuyPrice() != null || p.getSellPrice() != null)
				s.append(String.format("%s\n", p));
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
