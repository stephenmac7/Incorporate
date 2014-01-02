package com.stephenmac.incorporate.commands;

import java.util.Set;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;

public class ListEmployeesCommand extends Command {
	
	public ListEmployeesCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
	}

	@Override
	public String execute() {
		StringBuilder r = new StringBuilder();
		Set<String> employees = corp.getEmployeeSet();
		
		r.append("Employees (" + Integer.toString(employees.size()) + "):\n");
		for (String s : employees){
			r.append("* " + s + "\n");
		}
		return r.toString();
	}

}
