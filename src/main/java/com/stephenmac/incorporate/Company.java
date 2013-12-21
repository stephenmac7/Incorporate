package com.stephenmac.incorporate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.List;
import java.util.Set;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.utils.IndexDirection;

@Entity
public class Company {
	@Id private ObjectId id;
	@Indexed(value=IndexDirection.ASC, unique=true)
	private String name;
	private double balance = 0;
	private String defaultRank = "Owner";
	private List<Rank> ranks = new ArrayList<Rank>();
	private Set<String> applicants = new HashSet<String>();
	private Map<String, String> employees = new HashMap<String, String>();

	public Company(){
		Rank owner = new Rank();
		owner.name = "Owner";
		for(Permission perm : Permission.values()){
			owner.addPermission(perm);
		}
		this.ranks.add(owner);
		this.defaultRank = owner.name;
	}

	// Getters and setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public String getDefault() {
		return defaultRank;
	}
	
	public boolean setDefault(String defaultRank) {
		Rank r = getRank(defaultRank);
		if (r != null){
			this.defaultRank = r.name;
			return true;
		}
		return false;
	}

	// Misc Methods
	public boolean hasPerm(String employee, Permission perm){
		if (isEmployee(employee)){
			return getEmployeeRank(employee).permissions.contains(perm);
		}
		else{
			return false;
		}
	}
	
	// Rank Management
	public boolean addRank(String name, double wage){
		if (wage >= 0){
			Rank r = getRank(name);
			if (r == null){
				Rank newRank = new Rank();
				newRank.name = name;
				newRank.wage = wage;
				this.ranks.add(newRank);
				return true;
			}
		}
		return false;
	}
	
	public byte removeRank(String name){
		if (!name.equalsIgnoreCase(defaultRank)){
			Rank r = getRank(name);
			if (r != null){
				ranks.remove(r);
				return 0;
			}
			else{
				return -1;
			}
		}
		else{
			return 1;
		}
	}
	
	public double getWage(String name){
		Rank r = getRank(name);
		if (r != null){
			return r.wage;
		}
		else{
			return -1;
		}
	}
	
	public boolean setWage(String name, double wage){
		if (wage >= 0){
			Rank r = getRank(name);
			if (r != null){
				r.wage = wage;
				return true;
			}
		}
		return false;
	}
	
	//* Permissions
	public boolean grantPermission(String name, Permission p){
		Rank r = getRank(name);
		if (r != null){
			return r.permissions.add(p);
		}
		return false;
	}
	
	public boolean revokePermission(String name, Permission p){
		Rank r = getRank(name);
		if (r != null){
			r.permissions.remove(p);
			return true;
		}
		return false;
	}
	

	// Employee Management
	public boolean isEmployee(String name){
		return employees.containsKey(name);
	}
	
	public void addEmployee(String employee){
		employees.put(employee, defaultRank);
	}
	
	public Set<String> getEmployeeSet(){
		return employees.keySet();
	}

	public Rank getEmployeeRank(String name){
		Rank r = getRank(employees.get(name));
		if (r != null){
			return r;
		}
		else{
			employees.put(name, defaultRank);
			return getEmployeeRank(name);
		}
	}

	public boolean setEmployeeRank(String name, String rank) {
		if (getRank(rank) != null && isEmployee(name)){
			employees.put(name, rank);
			return true;
		}
		return false;
	}
	
	public boolean fire(String employee){
		if (isEmployee(employee)){
			employees.remove(employee);
			return true;
		}
		return false;
	}
	
	// Applicant Management
	public Set<String> getApplicantSet(){
		return applicants;
	}
	
	public boolean addApplicant(String name){
		if (!isEmployee(name)){
			return applicants.add(name);
		}
		return false;
	}
	
	public boolean removeApplicant(String name){
		return applicants.remove(name);
	}
	
	public boolean hire(String employee){
		if (applicants.remove(employee)){
			addEmployee(employee);
			return true;
		}
		return false;
	}
	
	// Rank informational operations
	public Rank getRank(String name){
		for (Rank r : ranks){
			if (r.name.equalsIgnoreCase(name)){
				return r;
			}
		}
		return null;
	}

	public List<Rank> getRanks(){
		return ranks;
	}
}