package com.agh.hr.controllers.auth;

import com.agh.hr.dto.AuthRequest;
import com.agh.hr.dto.FooUserView;
import com.agh.hr.persistence.model.FooUser;
import com.agh.hr.security.JwtTokenUtil;
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

    private final JwtTokenUtil jwtTokenUtil;

    private final Logger logger;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtTokenUtil jwtTokenUtil,
                          Logger logger) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.logger = logger;
    }


    @PostMapping("/login")
    public ResponseEntity<FooUserView> login(@RequestBody @Valid AuthRequest request) {
        val username = request.getUsername();
        logger.info("Login attempt - username:{}", username);
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, request.getPassword()));

            FooUser user = (FooUser) authenticate.getPrincipal();

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, jwtTokenUtil.generateAccessToken(user))
                    .body(FooUserView.fromUser(user));
        } catch (BadCredentialsException ex) {
            logger.info("Login failed - {}.", ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
