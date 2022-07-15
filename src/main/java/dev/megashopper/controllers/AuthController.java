package dev.megashopper.controllers;

import dev.megashopper.common.dtos.*;
import dev.megashopper.common.service.AuthService;
import dev.megashopper.common.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final TokenService tokenService;

    @Autowired
    public AuthController(AuthService authService, TokenService tokenService) {
        this.authService = authService;
        this.tokenService = tokenService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public Principal authenticate(@RequestBody AuthRequest authRequest, HttpServletResponse resp) {
        Principal payload = authService.authenticate(authRequest);
        String token = tokenService.generateToken(payload);
        resp.setHeader("Authorization", token);
        return payload;
    }
}
