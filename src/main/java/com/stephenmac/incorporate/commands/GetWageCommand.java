package com.stephenmac.incorporate.commands;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;
import com.stephenmac.incorporate.Permission;

public class GetWageCommand extends Command {
	
	public GetWageCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
		usage = "<rank>";
		perms.add(Permission.BASIC);
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
