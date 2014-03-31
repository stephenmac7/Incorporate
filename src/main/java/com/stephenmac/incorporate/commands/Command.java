package com.stephenmac.incorporate.commands;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Company;
import com.stephenmac.incorporate.Executor;
import com.stephenmac.incorporate.Item;
import com.stephenmac.incorporate.Permission;

public abstract class Command {
	// Info Variables
	// / Help Text
	protected String usage = null;
	// / Permissions required
	protected List<Permission> perms = new ArrayList<>();

	// / Requires a corporation?
	protected boolean needsCorp = true;
	// // If so, must it be valid?
	protected boolean validCorp = true;
	// / Requires a player name?
	protected boolean needsPlayer = false;

	// Runtime Variables
	// / Parsed arguments
	protected ArgParser p;
	// / Executor
	protected Executor cmdExec;
	// / Company, if needed
	protected Company corp = null;

	// Methods
	// / Basic
	public Command(ArgParser p, Executor cmdExec) {
		this.p = p;
		this.cmdExec = cmdExec;
	}

	public abstract String execute();

	public boolean validate() {
        boolean corpValidated = !needsCorp || (p.ensureCorp() && (!validCorp || getCompany() != null));
		return corpValidated && (!needsPlayer || p.ensurePlayer()) && (p.args.size() == getNArgs());
	}

	private int getNArgs() {
		if (usage == null)
			return 0;
		else
			return usage.split(" ").length;
	}

	public void cleanup() {
		if (corp != null)
			cmdExec.companyDAO.save(corp);
	}

	// / Messages
	public String usageMessage() {
		StringBuilder s = new StringBuilder();
		s.append("/inc ").append(p.action);
		if (needsCorp && !p.corpSelected)
			s.append(" <company>");
		if (needsPlayer && !p.senderIsPlayer)
			s.append(" <player>");
		if (usage != null)
			s.append(" ").append(usage);
		return s.toString();
	}

	public String notEmployeeMessage(String employee) {
		return employee + " is not an employee of " + getCompany().getName();
	}

	public String permissionMessage() {
		StringBuilder r = new StringBuilder();
		r.append("To run this command, you must have the corporate permissions:");
		for (Permission p : perms) {
			r.append("\n- ").append(p.toString());
		}
		return r.toString();
	}

	// / General helper functions
    protected double toAmount(String aString){
        BigDecimal amount = new BigDecimal(aString);
        int roundsTo = cmdExec.econ.fractionalDigits();
        if (roundsTo > -1) {
            amount = amount.setScale(roundsTo, BigDecimal.ROUND_HALF_UP);
        }
        return amount.doubleValue();
    }

	// / Company helper functions
	protected Company getCompany() {
		if (corp == null)
			corp = cmdExec.companyDAO.findByName(p.corp);
		return corp;
	}

	public boolean checkPermission() {
		return (!(!perms.isEmpty() && p.senderIsPlayer)) || p.player
                .hasPermission("inc.admin") || allPerms();
	}

	protected boolean checkPermission(Permission perm) {
		return !p.senderIsPlayer || p.player.hasPermission("inc.admin")
                || corp.hasPerm(p.playerName, perm);
	}

	protected boolean allPerms() {
		for (Permission perm : perms) {
			if (!corp.hasPerm(p.playerName, perm))
				return false;
		}
		return true;
	}

	protected Item parseItem(String item) {
		Item i = new Item();
		if (item.contains(":")) {
			String[] itemArgs = item.split(":");

			i.setId(parseMaterial(itemArgs[0]));
			i.setData(Byte.parseByte(itemArgs[1]));
		} else {
			i.setId(parseMaterial(item));
		}
		return i;
	}

	@SuppressWarnings("deprecation")
	protected int parseMaterial(String item) {
		try {
			Material mat = Material.valueOf(item.toUpperCase());
			return mat.getId();
		} catch (IllegalArgumentException e) {
			return Integer.parseInt(item);
		}
	}
}
