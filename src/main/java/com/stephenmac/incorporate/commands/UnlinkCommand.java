package com.stephenmac.incorporate.commands;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;
import com.stephenmac.incorporate.PendingAction;

public class UnlinkCommand extends Command {
	
	public UnlinkCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
		needsPlayer = true;
	}

	@Override
	public String execute() {
		PendingAction a = new PendingAction("unlink", corp);
		cmdExec.pendingActions.put(p.playerName, a);
		return "Please click on the chest you would like to unlink.";
	}

}
