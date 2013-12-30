package com.stephenmac.incorporate;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class Item {
	byte data = 0;
	int id;
	
	public byte getData() {
		return data;
	}
	public void setData(byte data) {
		this.data = data;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public boolean equals(Object obj){
		if (this == obj){
			return true;
		}
		else if (obj instanceof Item){
			Item item = (Item) obj;
			if (item.getId() == this.id && item.getData() == this.data)
				return true;
			else
				return false;
		}
		else{
			return false;
		}
	}
	
	@Override
	public String toString(){
		return String.format("%d:%d", this.id, this.data);
	}
}
