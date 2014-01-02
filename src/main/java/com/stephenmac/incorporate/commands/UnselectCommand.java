package com.stephenmac.incorporate.commands;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;

public class UnselectCommand extends Command {
	
	public UnselectCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
		needsCorp = false;
		needsPlayer = true;
	}

	@Override
	public String execute() {
		cmdExec.selections.remove(p.playerName);
		return p.corp + " unselected";
	}

}
