package com.stephenmac.incorporate.commands;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;
import com.stephenmac.incorporate.Permission;

public class SetDefaultRankCommand extends Command {
	public static String[] names = {"setDRank", "setDefaultRank", "sdr"};
	public static int nArgs = 1;
	public static String usage = "<rank>";
	public static Permission[] perms = {Permission.MANAGERANKS};
	
	public SetDefaultRankCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
	}

	@Override
	public String execute() {
		if(getCompany().setDefault(p.args.get(0)))
			return "Successfully changed " + corp.getName() + "'s default rank to " + p.args.get(0);
		return "Rank does not exist";
	}

}
