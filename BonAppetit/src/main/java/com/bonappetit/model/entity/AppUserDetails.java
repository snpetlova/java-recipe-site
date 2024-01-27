package com.bonappetit.model.entity;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public class AppUserDetails extends User {

    public AppUserDetails(String username, String password) {
        super(username, password, List.of());
    }
}
