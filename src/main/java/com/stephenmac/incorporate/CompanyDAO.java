package com.stephenmac.incorporate;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

public class CompanyDAO extends BasicDAO<Company, ObjectId> {
	public CompanyDAO(Datastore ds){
		super(ds);
	}
}
