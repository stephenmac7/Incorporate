package com.stephenmac.incorporate;

import java.util.UUID;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

public class LinkedChestDAO extends BasicDAO<LinkedChest, ObjectId> {
	public LinkedChestDAO(Datastore ds){
		super(ds);
	}
	
	public LinkedChest findByLoc(int x, int y, int z, UUID world){
		return this.findOne(this.createQuery()
				.filter("x =", x).filter("y =", y).filter("z =", z)
				.filter("world =", world));
	}
}
