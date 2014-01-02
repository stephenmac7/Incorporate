package com.stephenmac.incorporate.commands;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;
import com.stephenmac.incorporate.Permission;

public class RenameCommand extends Command {
	public static String[] names = {"rename"};
	public static int nArgs = 1;
	public static String usage = "<newName>";
	public static Permission[] perms = {Permission.RENAME};
	
	public RenameCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
	}

	@Override
	public String execute() {
		getCompany().setName(p.args.get(0));
		return "Successfully renamed to " + getCompany().getName();
	}

}
