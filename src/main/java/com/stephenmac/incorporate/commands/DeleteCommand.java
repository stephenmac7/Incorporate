package com.stephenmac.incorporate.commands;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;
import com.stephenmac.incorporate.Permission;

public class DeleteCommand extends Command {
	
	public DeleteCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
		perms.add(Permission.DELETE);
	}

	@Override
	public String execute() {
		cmdExec.companyDAO.delete(getCompany());
		return "Successfully removed " + getCompany().getName();
	}
	
	@Override
	public void cleanup(){};

}
