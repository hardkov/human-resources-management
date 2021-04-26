package com.agh.hr.controllers.auth;

import com.agh.hr.persistence.dto.AuthRequest;
import com.agh.hr.persistence.model.User;
import com.agh.hr.security.JwtTokenProvider;
import lombok.val;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/public")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final Logger logger;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtTokenProvider jwtTokenProvider,
                          Logger logger) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.logger = logger;
    }


    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody @Valid AuthRequest request) {
        val username = request.getUsername();
        logger.info("Login attempt - username:{}", username);
        try {
            val authToken = new UsernamePasswordAuthenticationToken(username, request.getPassword());
            Authentication authenticate = authenticationManager
                    .authenticate(authToken);

            User user = (User) authenticate.getPrincipal();

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, jwtTokenProvider.generateAccessToken(user))
                    .build();

        } catch (BadCredentialsException ex) {
            logger.info("Login failed - {}.", ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
