package com.stephenmac.incorporate;

import org.mongodb.morphia.mapping.DefaultCreator;

import com.mongodb.DBObject;

public class CustomCreator extends DefaultCreator {
    protected ClassLoader getClassLoaderForClass(final String clazz, final DBObject object) {
        return Thread.currentThread().getContextClassLoader();
    }
}
