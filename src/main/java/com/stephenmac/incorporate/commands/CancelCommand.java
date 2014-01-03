package com.stephenmac.incorporate.commands;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;
import com.stephenmac.incorporate.ExpectingLocation;

public class CancelCommand extends Command {

	public CancelCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
		needsPlayer = true;
		needsCorp = false;
	}

	@Override
	public String execute() {
		ExpectingLocation action = cmdExec.eLActions.remove(p.playerName);
		if (action == null)
			return "No pending action";
		return "Pending action canceled";
	}

}
