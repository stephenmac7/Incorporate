package com.stephenmac.incorporate;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class ProcessLinkedChests extends BukkitRunnable {
	private final Incorporate plugin;
	private int curAmount;

	public ProcessLinkedChests(Incorporate plugin) {
		this.plugin = plugin;
	}

	@Override
	public void run() {
		List<LinkedChest> lChests = plugin.linkedChestDAO.find().asList();
		curAmount = -1;
		for (LinkedChest lChest : lChests) {
			Block block = plugin.getServer().getWorld(lChest.getWorld())
			        .getBlockAt(lChest.getX(), lChest.getY(), lChest.getZ());

			if (block.getState() instanceof InventoryHolder) {
				Inventory inv = ((InventoryHolder) block.getState())
				        .getInventory();
				if (lChest.getLinkType() == LinkType.RESTOCK)
					processRestockChest(lChest, inv);
				else if (lChest.getLinkType() == LinkType.RECALL)
					processRecallChest(lChest, inv);
				else if (lChest.getLinkType() == LinkType.BUY)
					processBuyChest(lChest, inv);
				else if (lChest.getLinkType() == LinkType.SELL)
					processSellChest(lChest, inv);
				plugin.companyDAO.save(lChest.getCorp());
				if (lChest.getOwner() != null)
					plugin.companyDAO.save(lChest.getOwner());
			} else {
				plugin.linkedChestDAO.delete(lChest);
			}
		}
	}

	public void processRestockChest(LinkedChest c, Inventory inv) {
		for (ItemStack stack : inv.getContents()) {
			if (stack != null) {
				Item item = new Item();
				item.fromStack(stack);

				c.getCorp().getProduct(item, true)
				        .adjustQuantity(stack.getAmount());
			}
		}
		inv.clear();
	}

	public void processRecallChest(LinkedChest c, Inventory inv) {
		Product p = c.getCorp().getProduct(c.getItem());
		if (p != null) {
			int amount = getTotalAmount(p, c.getAmount(), inv);
			int added = amount - getCurAmount(inv, p.getItem());
			if (added > 0) {
				System.out.println(String.format("Amount: %d Added: %d",
				        amount, added));
				fillInventory(inv, p.getItem(), amount);
				p.adjustQuantity(-added);
			}
		}
	}

	public void processBuyChest(LinkedChest c, Inventory inv) {
		Product p = c.getCorp().getProduct(c.getItem());
		if (p != null && p.getBuyPrice() != null) {
			int amount = getTotalAmount(p, c.getAmount(), inv);
			int bought = amount - getCurAmount(inv, p.getItem());

			double totalPrice = p.getBuyPrice() * bought;
			if (bought > 0
			        && c.getOwner().canHandleTransfer(c.getCorp(), totalPrice)) {
				fillInventory(inv, p.getItem(), amount);
				c.getOwner().transferMoney(c.getCorp(), totalPrice);
				p.adjustQuantity(-bought);
			}
		}
	}

	public void processSellChest(LinkedChest c, Inventory inv) {
		ItemStack[] stacks = inv.getContents();
		for (int i = 0; i < stacks.length; i++) {
			ItemStack stack = stacks[i];
			if (stack != null) {
				Item item = new Item();
				item.fromStack(stack);
				Product p = c.getCorp().getProduct(item);

				if (p != null && p.getSellPrice() != null) {
					double totalPrice = stack.getAmount() * p.getSellPrice();
					if (c.getCorp().canHandleTransfer(c.getOwner(), totalPrice)) {
						c.getCorp().transferMoney(c.getOwner(), totalPrice);
						c.getCorp().getProduct(item, true)
						        .adjustQuantity(stack.getAmount());
						stack = null;
					}
				}
			}
			inv.setItem(i, stack);
		}
	}

	// Get amount to put in inventory
	public int getTotalAmount(Product p, int targetAmount, Inventory inv) {
		if (p.getQuantity() >= targetAmount)
			return targetAmount;
		else
			return p.getQuantity();
	}

	// Retuns the amount of an item in the inventory
	public int getCurAmount(Inventory inv, Item item) {
		if (curAmount == -1) {
			curAmount += 1;
			for (ItemStack stack : inv.getContents()) {
				if (stack != null) {
					Item testItem = new Item();
					testItem.fromStack(stack);
					if (item.equals(testItem))
						curAmount += stack.getAmount();
				}
			}
		}

		return curAmount;
	}

	// Adds amount of item to inventory, after clearing
	@SuppressWarnings("deprecation")
	public void fillInventory(Inventory inv, Item item, int amount) {
		int maxStack = Material.getMaterial(item.getId()).getMaxStackSize();
		int nStacks = (int) Math.floor((amount / maxStack) + 1);

		inv.clear();
		for (int i = 0; i < (nStacks - 1); i++) {
			inv.setItem(i, makeStack(item, maxStack));
		}
		inv.setItem(nStacks - 1, makeStack(item, amount % maxStack));
	}

	// Creates an itemstack for the given item.
	@SuppressWarnings("deprecation")
	public ItemStack makeStack(Item item, int amount) {
		return amount == 0 ? null : new ItemStack(item.getId(), amount,
		        (short) 0, item.getData());
	}
}
