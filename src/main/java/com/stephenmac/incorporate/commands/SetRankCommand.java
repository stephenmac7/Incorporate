package com.stephenmac.incorporate.commands;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;
import com.stephenmac.incorporate.Permission;

public class SetRankCommand extends Command {
	public static String[] names = {"setRank", "sr"};
	public static int nArgs = 2;
	public static String usage = "<employee> <rank>";
	public static Permission[] perms = {Permission.MANAGEEMPLOYEES};
	
	public SetRankCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
	}

	@Override
	public String execute() {
		String empl = p.args.get(0);
		String rank = p.args.get(1);
		
		if (corp.isEmployee(empl)){
			if(corp.setEmployeeRank(empl, rank)){
				return "Successfully changed " + empl + "'s rank to " + rank;
			}
			else{
				return "No such rank";
			}
		}
		else{
			return notEmployeeMessage(empl);
		}
	}

}
