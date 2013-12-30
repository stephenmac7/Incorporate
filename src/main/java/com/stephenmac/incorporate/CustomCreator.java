package com.stephenmac.incorporate;

import org.mongodb.morphia.mapping.DefaultCreator;

import com.mongodb.DBObject;

public class CustomCreator extends DefaultCreator {
	public ClassLoader cl;
	
	public CustomCreator(ClassLoader cl){
		this.cl = cl;
	}

    protected ClassLoader getClassLoaderForClass(final String clazz, final DBObject object) {
        return cl;
    }
}
