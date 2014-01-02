package com.stephenmac.incorporate;

public class PendingAction {
	public String action;
	public Object spec;
	
	public Company corporation;
	
	public PendingAction(String action, Object spec, Company corporation){
		this.action = action;
		this.spec = spec;
		this.corporation = corporation;
	}
	
	public PendingAction(String action, Company corporation){
		this(action, null, corporation);
	}
}
