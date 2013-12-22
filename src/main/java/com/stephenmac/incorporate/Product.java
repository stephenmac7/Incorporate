package com.stephenmac.incorporate;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class Product {
	private int id;
	private double buyPrice;
	private double sellPrice;
	private int quantity;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(double buyPrice) {
		this.buyPrice = buyPrice;
	}
	public double getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(double sellPrice) {
		this.sellPrice = sellPrice;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
