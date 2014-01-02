package com.stephenmac.incorporate.commands;

import org.bukkit.inventory.ItemStack;

import com.stephenmac.incorporate.ArgParser;
import com.stephenmac.incorporate.Executor;
import com.stephenmac.incorporate.Item;

public abstract class PlayerContextCommand extends Command {
	
	public PlayerContextCommand(ArgParser p, Executor cmdExec) {
		super(p, cmdExec);
		needsPlayer = true;
	}
	
	public boolean validate(){
		return super.validate() && p.ensurePlayerContext(cmdExec.server);
	}
	
	@SuppressWarnings("deprecation")
	protected void givePlayer(Item item, int quantity){
		p.player.getInventory().addItem(new ItemStack(item.getId(), quantity, (short) 0, item.getData()));
	}

}
