package com.stephenmac.incorporate;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Reference;
import org.mongodb.morphia.utils.IndexDirection;

@Entity
public class LinkedChest {
	@Id private ObjectId id;

	// Link info
	@Reference private Company corp;
	private LinkType linkType;
	// For Buy/Sell chest
	@Reference private Company owner = null;
	// For Buy/Recall chest
	private Item item = null;
	private int amount = 0;

	// Location
	@Indexed(value = IndexDirection.ASC, unique = true) private SimpleLocation loc;

	public SimpleLocation getLoc() {
		return loc;
	}

	public void setLoc(SimpleLocation loc) {
		this.loc = loc;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public Company getCorp() {
		return corp;
	}

	public void setCorp(Company corp) {
		this.corp = corp;
	}

	public Company getOwner() {
		return owner;
	}

	public void setOwner(Company owner) {
		this.owner = owner;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public LinkType getLinkType() {
		return linkType;
	}

	public void setLinkType(LinkType linkType) {
		this.linkType = linkType;
	}
}
