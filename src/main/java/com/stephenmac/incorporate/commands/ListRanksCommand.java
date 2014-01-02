package com.stephenmac.incorporate.commands;

import java.util.List;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;
import com.stephenmac.incorporate.Permission;
import com.stephenmac.incorporate.Rank;

public class ListRanksCommand extends Command {
	public static String[] names = {"listranks", "lr"};
	public static Permission[] perms = {Permission.BASIC};
	
	public ListRanksCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
	}

	@Override
	public String execute() {
		List<Rank> ranks = getCompany().getRanks();
		
		StringBuilder s = new StringBuilder();
		s.append("Ranks (" + Integer.toString(ranks.size()) + "):\n");
		for (Rank r : ranks){
			s.append("* " + r.name + "\n");
		}
		return s.toString();
	}

}
