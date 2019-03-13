package com.simbirsoft.kondratyev.ruslan;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;
import java.util.logging.LogManager;


@SpringBootApplication
public class Application{

    public static void main(String[] args){
   try {
            LogManager
                    .getLogManager()
                    .readConfiguration(Application.class.getResourceAsStream("/logging.properties"));
        } catch (IOException ex) {
            System.err.println("Could not setup logger configuration: " + ex.toString());
        }
        SpringApplication.run(Application.class,args);
    }
}