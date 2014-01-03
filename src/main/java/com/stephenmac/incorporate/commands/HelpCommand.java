package com.stephenmac.incorporate.commands;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;

public class HelpCommand extends Command {

	public HelpCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
		needsCorp = false;
	}

	@Override
	public String execute() {
		return "Please see http://dev.bukkit.org/bukkit-plugins/incorporate/ for a list of commands.";
	}

}
