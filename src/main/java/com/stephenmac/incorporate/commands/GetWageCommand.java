package com.stephenmac.incorporate.commands;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;
import com.stephenmac.incorporate.Permission;

public class GetWageCommand extends Command {
	public static String[] names = {"getWage", "gw"};
	public static int nArgs = 1;
	public static String usage = "<rank>";
	public static Permission[] perms = {Permission.BASIC};
	
	public GetWageCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
	}

	@Override
	public String execute() {
		double wage = getCompany().getWage(p.args.get(0));
		if (wage >= 0)
			return String.format("The wage of a %s is %f", p.args.get(0), wage);
		else
			return "No such rank";
	}

}
