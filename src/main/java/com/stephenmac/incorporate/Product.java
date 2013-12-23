package com.stephenmac.incorporate;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class Product {
	private Item item;
	private Double buyPrice = null;
	private Double sellPrice = null;
	private int quantity = 0;

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Double getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(Double buyPrice) {
		this.buyPrice = buyPrice;
	}

	public Double getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(Double sellPrice) {
		this.sellPrice = sellPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void adjustQuantity(int delta) {
		this.quantity += delta;
	}
}
