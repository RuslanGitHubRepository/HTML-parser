package com.simbirsoft.kondratyev.ruslan.pizzeria.controllers;


import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Exceptions.MakerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.authority.GrantedAuthoritiesContainer;

import java.util.ArrayList;
import java.util.List;

class authorizationDataDTO
{
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String login;
    private String password;
}
@RestController
@RequestMapping("/public/login")
public class SecurityController {
    @Autowired(required = false)
    private AuthenticationManager am;

    @PostMapping
    @Secured("ROLE_USER")
    public HttpStatus authorization(@RequestBody authorizationDataDTO data) {
        try {
            Authentication request = new UsernamePasswordAuthenticationToken(data.getLogin(), data.getPassword());
            Authentication result = am.authenticate(request);
            SecurityContextHolder.getContext().setAuthentication(result);
            return HttpStatus.OK;
        } catch(AuthenticationException e) {
            return HttpStatus.UNAUTHORIZED;
        }
    }
}
