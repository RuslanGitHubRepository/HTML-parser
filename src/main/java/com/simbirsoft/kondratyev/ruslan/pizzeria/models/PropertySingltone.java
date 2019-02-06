package com.simbirsoft.kondratyev.ruslan.pizzeria.models;
import java.util.Properties;
import java.io.*;

public class PropertySingltone {
    private final Properties propertyDataBase = new Properties();

    private static PropertySingltone object;

    private PropertySingltone(){}

    private static PropertySingltone getObject() {
        if(object == null){
            object = new PropertySingltone();
        }
        return object;
    }

    public static Properties getPropertyDataBase(){
        return getObject().propertyDataBase;
    }

    public static void load(InputStream stream){
        try{
            getObject().getPropertyDataBase().load(stream);
        }
        catch(IOException err){
            new MakerException(err.getMessage(),err.getCause());
        }
    }

    public static String getProperty(String property){
        return getObject().getPropertyDataBase().getProperty(property);
    }

}

