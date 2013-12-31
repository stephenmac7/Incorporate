package com.stephenmac.incorporate;

public class PendingAction {
	public String action;
	public String details;
	
	public Company corporation;
	
	public PendingAction(String action, String details, Company corporation){
		this.action = action;
		this.details = details;
		this.corporation = corporation;
	}
	
	public PendingAction(String action, Company corporation){
		this(action, null, corporation);
	}
}
