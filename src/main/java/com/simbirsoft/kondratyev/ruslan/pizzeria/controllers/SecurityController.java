package com.simbirsoft.kondratyev.ruslan.pizzeria.controllers;


import com.simbirsoft.kondratyev.ruslan.pizzeria.DTO.authorizationDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/login")
public class SecurityController {
    @Autowired(required = false)
    private AuthenticationManager am;

    @PostMapping
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
