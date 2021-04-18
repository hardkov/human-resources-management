package com.agh.hr.persistence.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;


@Data
@RequiredArgsConstructor
public class Role implements GrantedAuthority {
    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";

    private final String authority;
}
