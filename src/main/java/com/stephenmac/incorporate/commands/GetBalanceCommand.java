package com.stephenmac.incorporate.commands;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;
import com.stephenmac.incorporate.Permission;

public class GetBalanceCommand extends Command {
	public static String[] names = {"balance", "b"};
	public static Permission[] perms = {Permission.GETBALANCE};
	
	public GetBalanceCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
	}

	@Override
	public String execute() {
		return corp.getName() + "'s balance is: " + corp.getBalance();
	}

}
