package com.stephenmac.incorporate.commands;

import net.milkbowl.vault.economy.EconomyResponse;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;

public class DepositCommand extends Command {
	
	public DepositCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
		usage = "<amount>";
		needsPlayer = true;
	}

	@Override
	public String execute() {
		double pAmount = toAmount(p.args.get(0));
		if (pAmount > 0){
			EconomyResponse r = cmdExec.econ.withdrawPlayer(p.playerName, pAmount);
			if (r.transactionSuccess()){
				corp.adjustBalance(pAmount);
				return "Successfully deposited " + cmdExec.econ.format(pAmount) + " into " + corp.getName();
			}
			else
				return r.errorMessage;
		}
		else{
			return "Cannot deposit that amount of money";
		}
	}

}
