package com.stephenmac.incorporate.commands;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;
import com.stephenmac.incorporate.Permission;

public class RejectCommand extends Command {
	
	public RejectCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
		usage = "<applicant>";
		perms.add(Permission.HIRE);
	}

	@Override
	public String execute() {
		String applicant = p.args.get(0);
		if (corp.removeApplicant(applicant))
			return applicant + " has been rejected";
		else
			return applicant + " is not an applicant";
	}

}
