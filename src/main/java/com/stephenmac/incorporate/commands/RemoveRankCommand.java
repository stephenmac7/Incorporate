package com.stephenmac.incorporate.commands;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;
import com.stephenmac.incorporate.Permission;

public class RemoveRankCommand extends Command {
	public static String[] names = {"removerank", "rr"};
	public static int nArgs = 1;
	public static String usage = "<rank>";
	public static Permission[] perms = {Permission.MANAGERANKS};
	
	public RemoveRankCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
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
