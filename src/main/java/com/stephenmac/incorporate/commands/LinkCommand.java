package com.stephenmac.incorporate.commands;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.ChestLinker;
import com.stephenmac.incorporate.Executor;
import com.stephenmac.incorporate.LinkType;
import com.stephenmac.incorporate.LinkedChest;
import com.stephenmac.incorporate.Permission;

public class LinkCommand extends Command {
	public LinkCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
		usage = "<linkType> <buy/sell: targetCorp> <recall/buy: item> <recall/buy: amount>";
		needsPlayer = true;
		perms.add(Permission.BASIC);
	}

	@Override
	public String execute() {
		LinkType linkType = LinkType.valueOf(p.args.get(0).toUpperCase());

		// Check special permissions
		if (linkType == LinkType.RECALL && !checkPermission(Permission.RECALL))
			return "You do not have permission to create a recall chest. Try a buy chest instead.";
		else if (linkType == LinkType.BUY
		        && !checkPermission(Permission.WITHDRAW))
			return "You do not have permission to create a buy chest. Please request the chests creation by a higher-ranking employee.";

		// Setup our new linked chest
		LinkedChest c = new LinkedChest();
		c.setLinkType(linkType);
		if (linkType == LinkType.BUY || linkType == LinkType.SELL) {
			c.setCorp(cmdExec.companyDAO.findByName(p.args.get(1)));
			if (c.getCorp() == null)
				return "No such target corporation. Please make sure one exists.";

			c.setOwner(corp);

			if (linkType == LinkType.BUY) {
				if (!checkAmount(p.args.get(3)))
					return "Invalid amount";
				setupFillChest(c, p.args.get(2), p.args.get(3));
			}
		} else {
			c.setCorp(corp);
			if (linkType == LinkType.RECALL) {
				if (!checkAmount(p.args.get(2)))
					return "Invalid amount";
				setupFillChest(c, p.args.get(1), p.args.get(2));
			}
		}

		ChestLinker elAction = new ChestLinker();
		elAction.setLinkedChest(c);

		cmdExec.eLActions.put(p.playerName, elAction);

		return "Please click on the chest you would like to link.";
	}

	public void setupFillChest(LinkedChest c, String itemString,
	        String amountString) {
		c.setItem(parseItem(itemString));
		c.setAmount(Integer.parseInt(amountString));
	}

	public boolean checkAmount(String amountString) {
		int amount = Integer.parseInt(amountString);
		if (amount > 1024 || amount < 1)
			return false;
		return true;
	}

	@Override
	public boolean validate() {
		return (needsCorp ? (p.ensureCorp() && (validCorp ? getCompany() != null
		        : true))
		        : true)
		        && (needsPlayer ? p.ensurePlayer() : true)
		        && p.args.size() >= 1;
	}
}