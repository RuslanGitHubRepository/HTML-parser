package com.simbirsoft.kondratyev.ruslan.pizzeria.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthorizationDataDto {
    private String login;
    private String password;
}
