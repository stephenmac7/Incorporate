package com.stephenmac.incorporate.commands;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;
import com.stephenmac.incorporate.Permission;

public class AddRankCommand extends Command {
	public static String[] names = {"addrank", "ar"};
	public static int nArgs = 2;
	public static String usage = "<rank> <wage>";
	public static Permission[] perms = {Permission.MANAGERANKS};
	
	public AddRankCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
	}

	@Override
	public String execute() {
		if (getCompany().addRank(p.args.get(0), Double.parseDouble(p.args.get(1)))){
			return "Successfully added rank, " + p.args.get(0) + " to " + p.corp;
		}
		else{
			return "Rank exists";
		}
	}

}
