package com.stephenmac.incorporate.commands;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;
import com.stephenmac.incorporate.Permission;

public class GetBalanceCommand extends Command {
	
	public GetBalanceCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
		perms.add(Permission.GETBALANCE);
	}

	@Override
	public String execute() {
		return corp.getName() + "'s balance is: " + corp.getBalance();
	}

}
