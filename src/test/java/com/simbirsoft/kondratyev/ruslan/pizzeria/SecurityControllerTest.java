package com.simbirsoft.kondratyev.ruslan.pizzeria;

import com.simbirsoft.kondratyev.ruslan.pizzeria.controllers.SecurityController;
import com.simbirsoft.kondratyev.ruslan.pizzeria.dto.AuthorizationDataDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("test")
public class SecurityControllerTest {
    @Autowired
    private SecurityController securityController;

    @Test
    public void testAuthorization(){
        //GIVEN
        final String login = "user";
        final String pass = "user";
        AuthorizationDataDto authorizationDataDto = new AuthorizationDataDto(login, pass);
        //WHEN
        HttpStatus httpStatus = securityController.authorization(authorizationDataDto);
        //THEN
        assertEquals(HttpStatus.OK, httpStatus);
    }
    @Test
    public void testNotAuthorization(){
        //GIVEN
        final String login = "serious";
        final String pass = "sam";
        AuthorizationDataDto authorizationDataDto = new AuthorizationDataDto(login, pass);
        //WHEN
        HttpStatus httpStatus = securityController.authorization(authorizationDataDto);
        //THEN
        assertEquals(HttpStatus.UNAUTHORIZED, httpStatus);
    }
}
