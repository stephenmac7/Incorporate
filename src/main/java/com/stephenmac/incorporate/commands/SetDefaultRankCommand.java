package com.stephenmac.incorporate.commands;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;
import com.stephenmac.incorporate.Permission;

public class SetDefaultRankCommand extends Command {
	
	public SetDefaultRankCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
		usage = "<rank>";
		perms.add(Permission.MANAGERANKS);
	}

	@Override
	public String execute() {
		if(getCompany().setDefault(p.args.get(0)))
			return "Successfully changed " + corp.getName() + "'s default rank to " + p.args.get(0);
		return "Rank does not exist";
	}

}
