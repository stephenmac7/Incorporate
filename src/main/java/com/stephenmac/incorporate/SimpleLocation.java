package com.stephenmac.incorporate;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Server;
import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class SimpleLocation {
	private UUID world;
	private int x;
	private int y;
	private int z;

	public void setByValues(UUID world, int x, int y, int z) {
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void fromLocation(Location loc) {
		this.world = loc.getWorld().getUID();
		this.x = loc.getBlockX();
		this.y = loc.getBlockY();
		this.z = loc.getBlockZ();
	}

	public Location toLocation(Server s) {
		return new Location(s.getWorld(world), x, y, z);
	}

	public UUID getWorld() {
		return world;
	}

	public void setWorld(UUID world) {
		this.world = world;
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
}
