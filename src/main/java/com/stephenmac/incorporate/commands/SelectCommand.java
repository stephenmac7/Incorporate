package com.stephenmac.incorporate.commands;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;

public class SelectCommand extends Command {
	public static String[] names = {"select"};
	public static boolean needsPlayer = true;
	
	public SelectCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
	}

	@Override
	public String execute() {
		cmdExec.selections.put(p.playerName, p.corp);
		return p.corp + " selected";
	}

}
