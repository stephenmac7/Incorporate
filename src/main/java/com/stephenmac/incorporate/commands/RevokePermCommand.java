package com.stephenmac.incorporate.commands;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;
import com.stephenmac.incorporate.Permission;

public class RevokePermCommand extends Command {
	
	public RevokePermCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
		usage = "<rank> <perm>";
		perms.add(Permission.MANAGERANKS);
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
