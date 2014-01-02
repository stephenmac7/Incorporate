package com.stephenmac.incorporate.commands;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;
import com.stephenmac.incorporate.Permission;

public class DeleteCommand extends Command {
	public static String[] names = {"delete"};
	public static Permission[] perms = {Permission.DELETE};
	
	public DeleteCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
	}

	@Override
	public String execute() {
		cmdExec.companyDAO.delete(getCompany());
		return "Successfully removed " + getCompany().getName();
	}
	
	@Override
	public void cleanup(){};

}
