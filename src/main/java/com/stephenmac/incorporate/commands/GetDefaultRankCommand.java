package com.stephenmac.incorporate.commands;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;

public class GetDefaultRankCommand extends Command {

	public GetDefaultRankCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
	}

	@Override
	public String execute() {
		return getCompany().getName() + "'s default rank is: " + getCompany().getDefault();
	}

}
