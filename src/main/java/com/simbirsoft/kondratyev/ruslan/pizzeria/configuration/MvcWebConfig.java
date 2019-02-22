package com.simbirsoft.kondratyev.ruslan.pizzeria.configuration;

import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Exceptions.MakerException;
import com.simbirsoft.kondratyev.ruslan.pizzeria.repository.Kitchen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.simbirsoft.kondratyev.ruslan.pizzeria"})
public class MvcWebConfig implements WebMvcConfigurer {
    private static String swaggerUIPage = "swagger-ui";
    private static String swaggerUIResources = "META-INF/resources";
    private static String swaggerUIWebjars = "webjars";
    private static String swaggerUIWebjarsResources = "META-INF/resources/webjars";
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Properties prop = new Properties();
        try
        {
            System.out.println();
            InputStream input;
            input = new FileInputStream("WEB-INF/config.properties");
            prop.load(input);

        }catch(IOException err){
            MakerException makerException = new MakerException("Can't open property files", err.getCause());
            throw  makerException;
        }
        registry.addResourceHandler(prop.getProperty(swaggerUIPage))
                .addResourceLocations(prop.getProperty(swaggerUIResources));

        registry.addResourceHandler(prop.getProperty(swaggerUIWebjars))
                .addResourceLocations(prop.getProperty(swaggerUIWebjarsResources));
}
    @Bean
    public Kitchen KitchenGenerate() {
         return new Kitchen(2, 10);
    }
}