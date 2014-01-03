package com.stephenmac.incorporate;

import java.util.HashMap;
import java.util.Map;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.stephenmac.incorporate.commands.AddRankCommand;
import com.stephenmac.incorporate.commands.ApplyCommand;
import com.stephenmac.incorporate.commands.BrowseCommand;
import com.stephenmac.incorporate.commands.BuyCommand;
import com.stephenmac.incorporate.commands.CancelCommand;
import com.stephenmac.incorporate.commands.CleanCommand;
import com.stephenmac.incorporate.commands.Command;
import com.stephenmac.incorporate.commands.CreateCommand;
import com.stephenmac.incorporate.commands.DeleteCommand;
import com.stephenmac.incorporate.commands.DepositCommand;
import com.stephenmac.incorporate.commands.FireCommand;
import com.stephenmac.incorporate.commands.GetBalanceCommand;
import com.stephenmac.incorporate.commands.GetDefaultRankCommand;
import com.stephenmac.incorporate.commands.GetRankCommand;
import com.stephenmac.incorporate.commands.GetWageCommand;
import com.stephenmac.incorporate.commands.GrantPermCommand;
import com.stephenmac.incorporate.commands.HelpCommand;
import com.stephenmac.incorporate.commands.HireCommand;
import com.stephenmac.incorporate.commands.LinkCommand;
import com.stephenmac.incorporate.commands.ListApplicantsCommand;
import com.stephenmac.incorporate.commands.ListCommand;
import com.stephenmac.incorporate.commands.ListEmployeesCommand;
import com.stephenmac.incorporate.commands.ListPermsCommand;
import com.stephenmac.incorporate.commands.ListRanksCommand;
import com.stephenmac.incorporate.commands.PayEmployeesCommand;
import com.stephenmac.incorporate.commands.ProductInfoCommand;
import com.stephenmac.incorporate.commands.RecallCommand;
import com.stephenmac.incorporate.commands.RejectCommand;
import com.stephenmac.incorporate.commands.RemoveRankCommand;
import com.stephenmac.incorporate.commands.RenameCommand;
import com.stephenmac.incorporate.commands.ResignCommand;
import com.stephenmac.incorporate.commands.RestockCommand;
import com.stephenmac.incorporate.commands.RevokePermCommand;
import com.stephenmac.incorporate.commands.SelectCommand;
import com.stephenmac.incorporate.commands.SellCommand;
import com.stephenmac.incorporate.commands.SetDefaultRankCommand;
import com.stephenmac.incorporate.commands.SetPriceCommand;
import com.stephenmac.incorporate.commands.SetRankCommand;
import com.stephenmac.incorporate.commands.SetWageCommand;
import com.stephenmac.incorporate.commands.UnlinkCommand;
import com.stephenmac.incorporate.commands.UnselectCommand;
import com.stephenmac.incorporate.commands.WithdrawCommand;

public class Executor implements CommandExecutor {
	public CompanyDAO companyDAO;
	public Economy econ;
	public Server server;

	public Map<String, String> selections = new HashMap<String, String>();
	public Map<String, ExpectingLocation> eLActions;

	public Executor(Incorporate plugin) throws Exception {
		companyDAO = plugin.companyDAO;
		econ = plugin.econ;
		eLActions = plugin.eLActions;
		server = plugin.getServer();
	}

	@Override
	public boolean onCommand(CommandSender sender,
	        org.bukkit.command.Command cmd, String label, String[] args) {
		ArgParser p = new ArgParser(sender, args, selections);

		Command runner;
		try {
			runner = createCommandInstance(p.action, p);
		} catch (Exception e) {
			return false;
		}

		if (runner.validate()) {
			if (runner.checkPermission()) {
				sender.sendMessage(runner.execute());
				runner.cleanup();
			} else
				sender.sendMessage(runner.permissionMessage());
		} else
			sender.sendMessage(runner.usageMessage());
		return true;
	}

	public Command createCommandInstance(String action, ArgParser p)
	        throws Exception {
		// @formatter:off
        switch(action.toLowerCase()){
        case "help":
        	return new HelpCommand(p, this);
        case "fire":
            return new FireCommand(p, this);
        case "productinfo": case "pi": case "info":
            return new ProductInfoCommand(p, this);
        case "unselect":
            return new UnselectCommand(p, this);
        case "recall": case "rc":
            return new RecallCommand(p, this);
        case "listperms": case "lp":
            return new ListPermsCommand(p, this);
        case "employees": case "empl":
            return new ListEmployeesCommand(p, this);
        case "reject":
            return new RejectCommand(p, this);
        case "clean":
            return new CleanCommand(p, this);
        case "removerank": case "rr":
            return new RemoveRankCommand(p, this);
        case "applicants": case "appl":
            return new ListApplicantsCommand(p, this);
        case "setdrank": case "setdefaultrank": case "sdr":
            return new SetDefaultRankCommand(p, this);
        case "grantperm": case "gp":
            return new GrantPermCommand(p, this);
        case "revokeperm": case "rp":
            return new RevokePermCommand(p, this);
        case "rename":
            return new RenameCommand(p, this);
        case "payemployees": case "pe":
            return new PayEmployeesCommand(p, this);
        case "cancel":
            return new CancelCommand(p, this);
        case "withdraw": case "wd":
            return new WithdrawCommand(p, this);
        case "addrank": case "ar":
            return new AddRankCommand(p, this);
        case "delete":
            return new DeleteCommand(p, this);
        case "apply":
            return new ApplyCommand(p, this);
        case "hire":
            return new HireCommand(p, this);
        case "getwage": case "gw":
            return new GetWageCommand(p, this);
        case "buy":
            return new BuyCommand(p, this);
        case "unlink":
            return new UnlinkCommand(p, this);
        case "listranks": case "lr":
            return new ListRanksCommand(p, this);
        case "resign":
            return new ResignCommand(p, this);
        case "setrank": case "sr":
            return new SetRankCommand(p, this);
        case "sell":
            return new SellCommand(p, this);
        case "browse": case "br":
            return new BrowseCommand(p, this);
        case "create":
            return new CreateCommand(p, this);
        case "balance": case "b":
            return new GetBalanceCommand(p, this);
        case "deposit": case "dp":
            return new DepositCommand(p, this);
        case "price": case "setprice": case "sp":
            return new SetPriceCommand(p, this);
        case "list":
            return new ListCommand(p, this);
        case "link":
            return new LinkCommand(p, this);
        case "select":
            return new SelectCommand(p, this);
        case "getdrank": case "getdefaultrank": case "gdr":
            return new GetDefaultRankCommand(p, this);
        case "setwage": case "sw":
            return new SetWageCommand(p, this);
        case "restock": case "rs":
            return new RestockCommand(p, this);
        case "getrank": case "gr":
            return new GetRankCommand(p, this);
        default:
            throw new Exception();
        }
    }
}
