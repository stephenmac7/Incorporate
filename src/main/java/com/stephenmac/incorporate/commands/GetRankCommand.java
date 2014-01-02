package com.stephenmac.incorporate.commands;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;

public class GetRankCommand extends Command {
	
	public GetRankCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
		usage = "<employee>";
	}

	@Override
	public String execute() {
		String empl = p.args.get(0);
		if (corp.isEmployee(empl))
			return empl + "'s rank is: " + corp.getEmployeeRank(empl).name;
		else
			return notEmployeeMessage(empl);
	}

}
