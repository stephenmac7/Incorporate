package com.stephenmac.incorporate.commands;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;
import com.stephenmac.incorporate.Permission;

public class RevokePermCommand extends Command {
	public static String[] names = {"revokePerm", "rp"};
	public static int nArgs = 2;
	public static String usage = "<rank> <perm>";
	public static Permission[] perms = {Permission.MANAGERANKS};
	
	public RevokePermCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
	}

	@Override
	public String execute() {
		String rank = p.args.get(0);
		String perm = p.args.get(1);
		
		Permission p;
		try{
			p = Permission.valueOf(perm.toUpperCase());
		} catch (IllegalArgumentException e){
			return "No such permission";
		}

		if (getCompany().revokePermission(rank, p))
			return perm + " revoked from " + rank + " in " + getCompany().getName();
		else
			return "No such rank";
	}

}
