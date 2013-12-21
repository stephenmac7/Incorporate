package com.stephenmac.incorporate;

import java.util.List;
//import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UserCommandExecutor implements CommandExecutor {
	private CompanyDAO companyDAO;
	 
	public UserCommandExecutor(Incorporate plugin) {
		companyDAO = plugin.companyDAO;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length > 0){
			String action = args[0];
			String result;

			if (action.equals("list")){
				result = listCompanies();
			}
			else if (action.equals("create")){
				if (args.length == 2 && sender instanceof Player){
					result = createCompany(args[1], ((Player) sender).getName());
				}
				else if (args.length == 3 && !(sender instanceof Player)){
					result = createCompany(args[2], args[1]);
				}
				else{
					result = usageMessage("create <company>");
				}
			}
			else if (action.equals("delete")){
				if (args.length == 2){
					Company corp = companyDAO.get(args[1]);
					if (hasPerm(sender, corp, Permission.DELETE))
						result = deleteCompany(corp);
					else
						result = permMessage("DELETE");
				}
				else{
					result = usageMessage("delete <company>");
				}
			}
			else if (args.length > 1){
				Company corp = companyDAO.get(args[1]);
				
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
						
					case "lr": case "listranks": case "listRanks":
						result = listRanks(corp);
						break;

					case "ar": case "addrank": case "addRank":
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
					
					case "rr": case "removerank": case "removeRank":
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
					
					case "gw": case "getwage": case "getWage":
						if (args.length == 3){
							result = getWage(corp, args[2]);
						}
						else{
							result = usageMessage("getWage <company> <rank>");
						}
						break;

					case "sw": case "setwage": case "setWage":
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
					
					case "getDRank": case "getdrank": case "gdr":
						result = corp.getName() + "'s default rank is: " + corp.getDefault();
						break;
					
					case "setDRank": case "setdrank": case "sdr":
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
					
					case "grantPerm": case "grantperm": case "gp":
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
					
					case "revokePerm": case "revokeperm": case "rp":
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

					case "gr": case "getrank": case "getRank":
						if (args.length == 3){
							result = getRank(corp, args[2]);
						}
						else{
							result = usageMessage("getRank <company> <employee>");
						}
						break;
					
					case "sr": case "setrank": case "setRank":
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
					
					case "apply":
						if (sender instanceof Player){
							result = apply(corp, ((Player) sender).getName());
						}
						else{
							if (args.length == 3){
								result = apply(corp, args[2]);
							}
							else{
								result = usageMessage("apply <company> <player>");
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

	// Company General (Create, Delete, Change Name, List_)
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
		if (companyDAO.get(name) == null){
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
	
	// Company Ranks
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
		Permission p = Permission.valueOf(perm);
		if (p == Permission.UNKNOWN){
			return "No such permission";
		}
		else{
			if (company.grantPermission(rank, p))
				return perm + " granted to " + rank + " in " + company.getName();
			else
				return "No such rank";
		}
	}
	
	private String revokePerm(Company company, String rank, String perm){
		Permission p = Permission.valueOf(perm);
		if (p == Permission.UNKNOWN){
			return "No such permission";
		}
		else{
			if (company.revokePermission(rank, p))
				return perm + " revoked from " + rank + " in " + company.getName();
			else
				return "No such rank";
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
	
	// Helping functions
	/*private Query<Company> getQuery(Company corp){
		return companyDAO.createQuery().field(Mapper.ID_KEY).equal(corp.getName());
	}*/
	
	private boolean hasPerm(CommandSender sender, Company corp, Permission perm){
		if (sender instanceof Player){
			Player player = (Player) sender;
			if (player.hasPermission("inc.admin") || corp.hasPerm(player.getName(), perm)){
				return true;
			}
			else{
				return false;
			}
		}
		else{
			return true;
		}
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
