package com.stephenmac.incorporate.commands;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;
import com.stephenmac.incorporate.Permission;

public class FireCommand extends Command {
	
	public FireCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
		usage = "<employee>";
		perms.add(Permission.FIRE);
	}

	@Override
	public String execute() {
		String empl = p.args.get(0);
		if (corp.fire(empl))
			return empl + " has been fired";
		else
			return notEmployeeMessage(empl);
	}

}
