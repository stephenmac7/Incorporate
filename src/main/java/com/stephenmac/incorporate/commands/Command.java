package com.stephenmac.incorporate.commands;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Company;
import com.stephenmac.incorporate.Executor;
import com.stephenmac.incorporate.Item;
import com.stephenmac.incorporate.Permission;

public abstract class Command {
	// Info Variables
	/// Commands
	public static String[] names;
	/// Number of arguments required
	public static int nArgs = 0;
	/// Help Text
	public static String usage = null;
	/// Permissions required
	public static Permission[] perms = null;
	
	/// Requires a corporation?
	public static boolean needsCorp = true;
	//// If so, must it be valid?
	public static boolean validCorp = true;
	/// Requires a player name?
	public static boolean needsPlayer = false;
	
	// Runtime Variables
	/// Parsed arguments
	protected ArgParser p;
	/// Executor
	protected Executor cmdExec;
	/// Company, if needed
	protected Company corp = null;

	// Methods
	/// Basic
	public Command(ArgParser p, Executor cmdExec){
		this.p = p;
		this.cmdExec = cmdExec;
	}
	
	public abstract String execute();
	
	public boolean validate(){
		return (needsCorp ? p.ensureCorp() && (validCorp ? getCompany() != null : true) : true)
				&& (needsPlayer ? p.ensurePlayer() : true)
				&& p.args.size() == nArgs;
	}
	
	public void cleanup(){
		if (corp != null)
			cmdExec.companyDAO.save(corp);
	};
	
	/// Messages
	public String usageMessage(){
		StringBuilder s = new StringBuilder();
		s.append("/inc");
		if (names.length > 0)
			s.append("/inc " + names[0]);
		if (needsCorp)
			s.append(" <company>");
		if (needsPlayer)
			s.append(" <console: player>");
		if (usage != null)
			s.append(" " + usage);
		return s.toString();
	}
	
	public String notEmployeeMessage(String employee){
		return employee + " is not an employee of " + getCompany().getName();
	}
	
	public String permissionMessage(){
		StringBuilder r = new StringBuilder();
		r.append("To run this command, you must have the corporate permissions:");
		for (Permission p : perms){
			r.append("\n- " + p.toString());
		}
		return r.toString();
	}
	
	/// Company helper functions
	protected Company getCompany(){
		if (corp == null)
			corp = cmdExec.companyDAO.findByName(p.corp);
		return corp;
	}
	
	public boolean checkPermission(){
		return (perms != null && p.senderIsPlayer) ? p.player.hasPermission("inc.admin") || allPerms() : true;
	}
	
	protected boolean allPerms(){
		for (Permission perm : perms){
			if (!corp.hasPerm(p.playerName, perm))
				return false;
		}
		return true;
	}
	
	protected Item parseItem(String item){
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
}
