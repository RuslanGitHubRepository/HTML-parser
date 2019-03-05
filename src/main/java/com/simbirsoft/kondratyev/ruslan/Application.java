package com.simbirsoft.kondratyev.ruslan;


import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@SpringBootApplication()
public class Application  extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
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