package com.simbirsoft.kondratyev.ruslan.pizzeria.configuration;

import com.simbirsoft.kondratyev.ruslan.pizzeria.aspects.WebServiceLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableAspectJAutoProxy
@Configuration
public class AopConfig {
    @Bean
    @Profile("dev")
    public WebServiceLogger WebServiceLoggerBean(){
        return new WebServiceLogger();
    }
}
