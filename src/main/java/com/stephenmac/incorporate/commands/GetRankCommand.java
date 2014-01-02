package com.stephenmac.incorporate.commands;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;

public class GetRankCommand extends Command {
	public static String[] names = {"getRank", "gr"};
	public static int nArgs = 1;
	public static String usage = "<employee>";
	
	public GetRankCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
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
