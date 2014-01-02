package com.stephenmac.incorporate.commands;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;

public class ApplyCommand extends Command {

	public ApplyCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
		needsPlayer = true;
	}

	@Override
	public String execute() {
		String applicant = p.playerName;
		if (corp.addApplicant(applicant))
			return "You've applied to " + corp.getName();
		else
			return "You're already an employee or applicant";
	}

}
