package com.stephenmac.incorporate;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;

import com.mongodb.MongoClient;

public class CompanyDAO extends BasicDAO<Company, ObjectId> {
	public CompanyDAO(Morphia morphia, MongoClient mongo){
		super(mongo, morphia, "incorporate");
	}
}
