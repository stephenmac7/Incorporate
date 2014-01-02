package com.stephenmac.incorporate.commands;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;
import com.stephenmac.incorporate.Permission;

public class RemoveRankCommand extends Command {
	
	public RemoveRankCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
		usage = "<rank>";
		perms.add(Permission.MANAGERANKS);
	}

	@Override
	public String execute() {
		byte val = getCompany().removeRank(p.args.get(0));
		switch (val){
			case 0:
				return "Successfully removed " + p.args.get(0) + " from " + getCompany().getName();
			case 1:
				return "Cannot delete default rank";
			default:
				return "Rank does not exist";
		}
	}

}
