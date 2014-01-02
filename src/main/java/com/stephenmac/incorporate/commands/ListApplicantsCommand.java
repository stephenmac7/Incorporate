package com.stephenmac.incorporate.commands;

import java.util.Set;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;
import com.stephenmac.incorporate.Permission;

public class ListApplicantsCommand extends Command {
	
	public ListApplicantsCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
		perms.add(Permission.HIRE);
	}

	@Override
	public String execute() {
		StringBuilder r = new StringBuilder();
		Set<String> applicants = corp.getApplicantSet();
		
		r.append("Applicants (" + Integer.toString(applicants.size()) + "):\n");
		for (String s : applicants){
			r.append("* " + s + "\n");
		}
		return r.toString();
	}

}
