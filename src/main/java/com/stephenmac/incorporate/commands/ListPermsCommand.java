package com.stephenmac.incorporate.commands;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;
import com.stephenmac.incorporate.Permission;
import com.stephenmac.incorporate.Rank;

public class ListPermsCommand extends Command {
	public static String[] names = {"listPerms", "lp"};
	public static int nArgs = 1;
	public static String usage = "<rank>";
	public static Permission[] perms = {Permission.BASIC};
	
	public ListPermsCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
	}

	@Override
	public String execute() {
		Rank r = getCompany().getRank(p.args.get(0));
		if (r == null){
			return "No such rank";
		}
		else{
			StringBuilder s = new StringBuilder();
			for (Permission p : r.permissions){
				s.append(p.toString() + " ");
			}
			s.deleteCharAt(s.length()-1);
			return s.toString();
		}
	}

}
