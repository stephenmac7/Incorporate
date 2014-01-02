package com.stephenmac.incorporate.commands;

import java.util.List;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Company;
import com.stephenmac.incorporate.Executor;

public final class ListCommand extends Command {
	public ListCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
		needsCorp = false;
	}

	@Override
	public String execute() {
		StringBuilder r = new StringBuilder();
		List<Company> companyList = cmdExec.companyDAO.find().asList();

		r.append("Companies (" + Integer.toString(companyList.size()) + "):\n");
		for (Company c : companyList){
			r.append("* " + c.getName() + "\n");
		}
		return r.toString();
	}
}
