package com.stephenmac.incorporate.commands;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;
import com.stephenmac.incorporate.Permission;

public class SetRankCommand extends Command {
	
	public SetRankCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
		usage = "<employee> <rank>";
		perms.add(Permission.MANAGEEMPLOYEES);
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
