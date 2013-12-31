package com.stephenmac.incorporate;

import java.util.UUID;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

@Entity
public class LinkedChest {
	@Id private ObjectId id;
	@Reference private Company corp;
	private int x;
	private int y;
	private int z;
	private UUID world;
	
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
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getZ() {
		return z;
	}
	public void setZ(int z) {
		this.z = z;
	}
	public void setLoc(int x, int y, int z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public UUID getWorld() {
		return world;
	}
	public void setWorld(UUID world) {
		this.world = world;
	}
}
