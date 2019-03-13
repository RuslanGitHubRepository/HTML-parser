package com.simbirsoft.kondratyev.ruslan.pizzeria.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizationDataDto {
    private String login;
    private String password;
}
