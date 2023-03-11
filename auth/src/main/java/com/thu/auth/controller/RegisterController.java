package com.thu.auth.controller;

import com.thu.auth.domain.common.AuthRequest;
import com.thu.auth.domain.common.Response;
import com.thu.auth.security.AuthUserDetail;
import com.thu.auth.security.JwtProvider;
import com.thu.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    //User trying to log in with username and password
    @PostMapping("/register")
    public Response register(@RequestBody AuthRequest request){

        boolean canUseUsername = userService.canUseUsername(request.getUsername());

        if (canUseUsername) {
            userService.createNewUser(request.getUsername(), request.getPassword());
            return Response.builder()
                    .message("Registered " + request.getUsername())
                    .build();
        } else {
            return Response.builder()
                    .message("Username " + request.getUsername() + " is already used!")
                    .build();
        }
    }

}
