package com.simbirsoft.kondratyev.ruslan.pizzeria.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import java.util.Properties;

@Configuration
@EnableWebSecurity
@PropertySource("classpath:config.properties")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private Environment env;
    private final String userLogin = "user_login";
    private final String userPass = "user_password";
    private final String userRole = "user_role";
    private final String adminLogin = "admin_login";
    private final String adminPass = "admin_password";
    private final String adminRole = "admin_role";
    private final String rootLogin = "root_login";
    private final String rootPass = "root_password";
    private final String rootRole = "root_role";
    private final String publicDir = "public_directory";
    private final String privateDir = "private_derectory";

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder =  passwordEncoderBean();
        auth.inMemoryAuthentication().withUser(env.getProperty(userLogin)).password(passwordEncoder.encode(env.getProperty(userPass))).roles(env.getProperty(userRole));
        auth.inMemoryAuthentication().withUser(env.getProperty(adminLogin)).password(passwordEncoder.encode(env.getProperty(adminPass))).roles(env.getProperty(adminRole));
        auth.inMemoryAuthentication().withUser(env.getProperty(rootLogin)).password(passwordEncoder.encode(env.getProperty(rootPass))).roles(env.getProperty(rootRole));
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
        web.ignoring().mvcMatchers("/swagger-ui.html", "/webjars/**","/swagger-resources/**","/v2/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .logout().logoutUrl("/public/logout")
                .and()
                .authorizeRequests()
                .antMatchers(env.getProperty(publicDir)).permitAll()
                .antMatchers(env.getProperty(privateDir)).access("hasRole('ROLE_" + env.getProperty(userRole) + "')");


    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Bean
    public PasswordEncoder passwordEncoderBean() throws Exception {
        return new BCryptPasswordEncoder();
    }
}
