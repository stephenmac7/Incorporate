package com.stephenmac.incorporate.commands;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;
import com.stephenmac.incorporate.Permission;

public class RenameCommand extends Command {
	
	public RenameCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
		usage = "<newName>";
		perms.add(Permission.RENAME);
	}

	@Override
	public String execute() {
		getCompany().setName(p.args.get(0));
		return "Successfully renamed to " + getCompany().getName();
	}

}
