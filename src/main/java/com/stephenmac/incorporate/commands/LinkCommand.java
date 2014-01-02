package com.stephenmac.incorporate.commands;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;
import com.stephenmac.incorporate.LinkType;
import com.stephenmac.incorporate.PendingAction;
import com.stephenmac.incorporate.Permission;

public class LinkCommand extends Command {
	public static String[] names = {"link"};
	public static int nArgs = 1;
	public static String usage = "<linkType> <recall/buy: item> <recall/buy: amount>";
	public static boolean needsPlayer = true;
	
	public LinkCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
	}

	@Override
	public String execute() {
		LinkType linkType = LinkType.valueOf(p.args.get(0).toUpperCase());
		
		if (linkType == LinkType.RECALL){
			if (!corp.hasPerm(p.playerName, Permission.RECALL))
				return "You do not have permission to create a recall chest. Try a buy chest instead.";
			else
				return "Not yet implemented.";
		}
		else if (linkType == LinkType.BUY || linkType == LinkType.SELL){
			return "Not yet implemented.";
		}
		
		PendingAction a = new PendingAction("linkChest", linkType, corp);
		cmdExec.pendingActions.put(p.playerName, a);
		return "Click on the chest you would like to link.";
	}

}
