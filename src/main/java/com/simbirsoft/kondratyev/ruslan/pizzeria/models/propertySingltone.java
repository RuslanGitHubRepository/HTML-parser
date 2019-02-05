package com.simbirsoft.kondratyev.ruslan.pizzeria.models;
import java.util.Properties;
import java.io.*;

public class propertySingltone {
    private final Properties propertyDataBase = new Properties();

    private static propertySingltone object;

    private propertySingltone(){}

    private static propertySingltone getObject() {
        if(object == null){
            object = new propertySingltone();
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

