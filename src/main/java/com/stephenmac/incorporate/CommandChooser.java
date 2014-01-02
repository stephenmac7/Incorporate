package com.stephenmac.incorporate;

import java.util.HashMap;
import java.util.Map;

import com.stephenmac.incorporate.commands.*;

public final class CommandChooser {
    private Map<String, Class<? extends Command>> cmdMap = new HashMap<String, Class<? extends Command>>();
    
	public CommandChooser() throws Exception {
		processCommand(FireCommand.class);
		processCommand(ProductInfoCommand.class);
		processCommand(UnselectCommand.class);
		processCommand(RecallCommand.class);
		processCommand(ListPermsCommand.class);
		processCommand(ListEmployeesCommand.class);
		processCommand(RejectCommand.class);
		processCommand(CleanCommand.class);
		processCommand(RemoveRankCommand.class);
		processCommand(ListApplicantsCommand.class);
		processCommand(SetDefaultRankCommand.class);
		processCommand(GrantPermCommand.class);
		processCommand(RevokePermCommand.class);
		processCommand(RenameCommand.class);
		processCommand(PayEmployeesCommand.class);
		processCommand(CancelCommand.class);
		processCommand(WithdrawCommand.class);
		processCommand(AddRankCommand.class);
		processCommand(DeleteCommand.class);
		processCommand(ApplyCommand.class);
		processCommand(HireCommand.class);
		processCommand(GetWageCommand.class);
		processCommand(BuyCommand.class);
		processCommand(UnlinkCommand.class);
		processCommand(ListRanksCommand.class);
		processCommand(ResignCommand.class);
		processCommand(SetRankCommand.class);
		processCommand(SellCommand.class);
		processCommand(BrowseCommand.class);
		processCommand(CreateCommand.class);
		processCommand(GetBalanceCommand.class);
		processCommand(DepositCommand.class);
		processCommand(SetPriceCommand.class);
		processCommand(ListCommand.class);
		processCommand(LinkCommand.class);
		processCommand(SelectCommand.class);
		processCommand(GetDefaultRankCommand.class);
		processCommand(SetWageCommand.class);
		processCommand(RestockCommand.class);
		processCommand(GetRankCommand.class);
	}
	
	public Class<? extends Command> chooseCommand(String cmd){
		return cmdMap.get(cmd);
	}
	
	private void processCommand(Class<? extends Command> c) throws Exception{
		for (String name : (String[]) c.getField("names").get(null)){
			cmdMap.put(name, c);
		}
	}
}
