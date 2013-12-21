package com.stephenmac.incorporate;

import java.util.Set;
import java.util.TreeSet;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class Rank {
	public String name;
	public double wage = 0;
	public Set<Permission> permissions = new TreeSet<Permission>();

	public Rank(){
		super();
	}

	public boolean addPermission(Permission permission){
		return permissions.add(permission);
	}
	
	public boolean removePermission(Permission permission){
		return permissions.remove(permission);
	}
}
