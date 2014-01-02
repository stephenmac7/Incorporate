package com.stephenmac.incorporate.commands;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;

public class ResignCommand extends Command {
	public static String[] names = {"resign"};
	public static boolean needsPlayer = true;
	
	public ResignCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
	}

	@Override
	public String execute() {
		String empl = p.args.get(0);
		if (corp.fire(empl))
			return empl + " has resigned";
		else
			return notEmployeeMessage(empl);
	}

}
