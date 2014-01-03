package com.stephenmac.incorporate.commands;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.ChestUnlinker;
import com.stephenmac.incorporate.Executor;

public class UnlinkCommand extends Command {

	public UnlinkCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
		needsPlayer = true;
		needsCorp = false;
	}

	@Override
	public String execute() {
		cmdExec.eLActions.put(p.playerName, new ChestUnlinker());
		return "Please click on the chest you would like to unlink.";
	}

}
