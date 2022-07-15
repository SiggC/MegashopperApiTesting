package dev.megashopper.controllers;

import dev.megashopper.common.dtos.AuthRequest;
import dev.megashopper.common.dtos.Principal;
import dev.megashopper.common.dtos.TokenService;
import dev.megashopper.common.service.AuthService;
import dev.megashopper.common.utils.exceptions.AuthenticationException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class EmployeeAuthController {
    private final AuthService authService;
    private final TokenService tokenService;

    @Autowired
    public EmployeeAuthController(AuthService authService, TokenService tokenService) {
        this.authService = authService;
        this.tokenService = tokenService;
    }
    @GetMapping(value = "/employee", produces = "application/json", consumes = "application/json")
    public Principal authenticate(@RequestBody AuthRequest authRequest, HttpServletResponse resp) {
        Principal payload = authService.authenticateEmployee(authRequest);
        if (!payload.getAuthEmail().endsWith("@megashopper.net"))
            throw new AuthenticationException("Must be an employee");
        String token = tokenService.generateToken(payload);
        resp.setHeader("Authorization", token);
        return payload;
    }
}