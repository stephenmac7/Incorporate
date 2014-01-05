package com.stephenmac.incorporate;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

public class LinkedChestDAO extends BasicDAO<LinkedChest, ObjectId> {
	public LinkedChestDAO(Datastore ds) {
		super(ds);
	}

	public LinkedChest findByLoc(SimpleLocation loc) {
		return this.findOne(this.createQuery().filter("loc =", loc));
	}
}
