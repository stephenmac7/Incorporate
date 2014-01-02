package com.stephenmac.incorporate.commands;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;
import com.stephenmac.incorporate.Permission;

public class SetWageCommand extends Command {
	public static String[] names = {"setWage", "sw"};
	public static int nArgs = 2;
	public static String usage = "<rank> <wage>";
	public static Permission[] perms = {Permission.MANAGERANKS};
	
	public SetWageCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
	}

	@Override
	public String execute() {
		if (getCompany().setWage(p.args.get(0), Double.parseDouble(p.args.get(1))))
			return "Successfully changed wage of " + p.args.get(0) + " to " + p.args.get(1);
		else
			return "No such rank";
	}

}
