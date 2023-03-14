package com.thu.auth.domain.common;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AuthRequest {

    @Email
    @NotNull(message = "username is required")
    @NotBlank
    @NotEmpty
    private String username;

    @NotNull(message = "password is required")
    private String password;
}
