package com.stephenmac.incorporate.commands;

import net.milkbowl.vault.economy.EconomyResponse;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Company;
import com.stephenmac.incorporate.Executor;

public class CreateCommand extends Command {
	
	public CreateCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
		needsPlayer = true;
		validCorp = false;
	}

	@Override
	public String execute() {
		if (getCompany() == null){
			EconomyResponse r = cmdExec.econ.withdrawPlayer(p.playerName, 200);
			if (r.transactionSuccess()){
				corp = new Company();
				corp.setName(p.corp);
				corp.addEmployee(p.playerName);
			}
			else{
				return r.errorMessage;
			}

			return String.format("Successfully created %s with %s as owner", p.corp, p.playerName);
		}
		else{
			return String.format("%s already exists", p.corp);
		}
	}

}
