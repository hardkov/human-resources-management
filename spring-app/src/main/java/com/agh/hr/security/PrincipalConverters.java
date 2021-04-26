package com.agh.hr.security;

import com.agh.hr.persistence.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.security.Principal;

public class PrincipalConverters {
    public static User toUser(Principal principal) {
        return (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
    }
}
