package com.simbirsoft.kondratyev.ruslan.parser.configuration;

import com.simbirsoft.kondratyev.ruslan.parser.exception.GlobalExceptionHandler;
import org.springframework.context.annotation.*;

@EnableAspectJAutoProxy
@Configuration
public class AopConfig {
    @Bean
    public GlobalExceptionHandler createExceptionBean(){
        return new GlobalExceptionHandler();
    }
}