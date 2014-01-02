package com.stephenmac.incorporate.commands;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;
import com.stephenmac.incorporate.Permission;

public class HireCommand extends Command {
	
	public HireCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
		usage = "<applicant>";
		perms.add(Permission.HIRE);
	}

	@Override
	public String execute() {
		String applicant = p.args.get(0);
		if (corp.hire(applicant))
			return applicant + " has been hired as a " + corp.getDefault();
		else
			return applicant + " is not an applicant";
	}

}
