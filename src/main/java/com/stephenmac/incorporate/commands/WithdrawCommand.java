package com.stephenmac.incorporate.commands;

import net.milkbowl.vault.economy.EconomyResponse;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;
import com.stephenmac.incorporate.Permission;

public class WithdrawCommand extends Command {
	
	public WithdrawCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
		usage = "<amount>";
		perms.add(Permission.WITHDRAW);
		needsPlayer = true;
	}

	@Override
	public String execute() {
		String amount = p.args.get(0);
		double pAmount = Double.parseDouble(amount);
		if (pAmount > 0 && corp.getBalance() >= pAmount){
			EconomyResponse r = cmdExec.econ.depositPlayer(p.playerName, pAmount);
			if (r.transactionSuccess()){
				corp.adjustBalance(-pAmount);
				return "Successfully withdrew " + amount + " from " + corp.getName();
			}
			else
				return r.errorMessage;
		}
		else{
			return "Cannot withdraw that amount of money";
		}
	}

}
