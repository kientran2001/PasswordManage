package com.example.PasswordManage.models;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RegisterForm {
    @NotNull
    @NotBlank(message = "Username cannot be null")
    @Size(min = 1, max = 300)
    private String username;

    @NotNull
    @NotBlank(message = "Password cannot be null")
    private String password;

    @NotNull
    @NotBlank(message = "Confirm password cannot be null")
    private String confirmPassword;
}
