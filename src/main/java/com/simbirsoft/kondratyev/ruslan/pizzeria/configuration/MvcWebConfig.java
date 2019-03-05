package com.simbirsoft.kondratyev.ruslan.pizzeria.configuration;


import com.simbirsoft.kondratyev.ruslan.pizzeria.service.impl.Kitchen;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
@EnableWebMvc
@PropertySource("classpath:config.properties")
@ComponentScan(basePackages = {"com.simbirsoft.kondratyev.ruslan.pizzeria"})
public class MvcWebConfig implements WebMvcConfigurer {
    @Value("${swagger-ui}")
    private String swaggerUIPage;
    @Value("${META-INF_resources}")
    private String swaggerUIResources;
    @Value("${webjars}")
    private String swaggerUIWebjars;
    @Value("${META-INF_resources_webjars}")
    private String swaggerUIWebjarsResources;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(swaggerUIPage)
                .addResourceLocations(swaggerUIResources);

        registry.addResourceHandler(swaggerUIWebjars)
                .addResourceLocations(swaggerUIWebjarsResources);
}
    @Bean
    public Kitchen KitchenGenerate() {
         return new Kitchen(2, 10);
    }
}