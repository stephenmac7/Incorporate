package com.stephenmac.incorporate.commands;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;
import com.stephenmac.incorporate.Permission;

public class FireCommand extends Command {
	public static String[] names = {"fire"};
	public static int nArgs = 1;
	public static String usage = "<employee>";
	public static Permission[] perms = {Permission.FIRE};
	
	public FireCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
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
