package com.bonappetit.model.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserRegisterDto {

    @NotNull
    @Size(min = 3, max = 20, message = "Username should be between 3 and 20 symbols!")
    private String username;
    @NotNull
    @Size(min = 3, max = 20, message = "Password should be between 3 and 20 symbols!")
    private String password;
    @NotNull
    private String confirmPassword;
    @NotNull
    @Email(message = "Email should be valid!")
    private String email;

    public UserRegisterDto() {
    }

    public String getUsername() {
        return username;
    }

    public UserRegisterDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegisterDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegisterDto setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegisterDto setEmail(String email) {
        this.email = email;
        return this;
    }
}
