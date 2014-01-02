package com.stephenmac.incorporate.commands;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;

public class UnselectCommand extends Command {
	public static String[] names = {"unselect"};
	public static boolean needsCorp = false;
	public static boolean needsPlayer = true;
	
	public UnselectCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
	}

	@Override
	public String execute() {
		cmdExec.selections.remove(p.playerName);
		return p.corp + " unselected";
	}

}
