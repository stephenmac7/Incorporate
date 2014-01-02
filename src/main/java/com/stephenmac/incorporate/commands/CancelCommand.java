package com.stephenmac.incorporate.commands;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;
import com.stephenmac.incorporate.PendingAction;

public class CancelCommand extends Command {
	public static String[] names = {"cancel"};
	public static boolean needsPlayer = true;

	public CancelCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
	}

	@Override
	public String execute() {
		PendingAction cAction = cmdExec.pendingActions.remove(p.playerName);
		if (cAction == null)
			return "No pending action";
		return "Pending action (" + cAction.action + ") canceled";
	}

}
