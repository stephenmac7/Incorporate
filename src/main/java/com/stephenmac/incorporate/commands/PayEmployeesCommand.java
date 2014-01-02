package com.stephenmac.incorporate.commands;

import java.util.Map;

import net.milkbowl.vault.economy.EconomyResponse;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;
import com.stephenmac.incorporate.Permission;
import com.stephenmac.incorporate.Rank;

public class PayEmployeesCommand extends Command {
	
	public PayEmployeesCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
		perms.add(Permission.MANAGEEMPLOYEES);
		perms.add(Permission.WITHDRAW);
	}

	@Override
	public String execute() {
		// Get employees
		Map<String, Rank> employees = corp.getEmployees();

		// Make sure we have enough money
		double amount = 0;
		for (Rank r : employees.values()){
			amount += r.wage;
		}
		if (amount > corp.getBalance())
			return "Not enough money to pay all employees. Please get more money or cut your employees' paychecks.";
		
		// Pay them
		StringBuilder s = new StringBuilder();
		for (Map.Entry<String, Rank> employee : employees.entrySet()){
			double wage = employee.getValue().wage;
			EconomyResponse r = cmdExec.econ.depositPlayer(employee.getKey(), wage);
			if (r.transactionSuccess()){
				corp.adjustBalance(-wage);
			}
			else{
				s.append("Unable to pay " + employee + ": " + r.errorMessage + "\n");
			}
		}
		s.append("Employees paid");
		return s.toString();
	}

}
