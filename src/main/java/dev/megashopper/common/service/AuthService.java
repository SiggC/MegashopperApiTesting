package dev.megashopper.common.service;

import dev.megashopper.common.dtos.AuthRequest;
import dev.megashopper.common.dtos.EmployeeResponsePayload;
import dev.megashopper.common.dtos.UserResponsePayload;
import dev.megashopper.common.entities.Employee;
import dev.megashopper.common.entities.Password;
import dev.megashopper.common.entities.User;
import dev.megashopper.common.repository.AuthEmployeeRepository;
import dev.megashopper.common.repository.AuthRepository;
import dev.megashopper.common.utils.Generation;
import dev.megashopper.common.utils.exceptions.AuthenticationException;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import dev.megashopper.common.dtos.Principal;
import javax.validation.Valid;
import java.util.Optional;
//import java.security.Principal;

@Service
@Transactional
public class AuthService {

    private final UserService userService;
    private final AuthEmployeeRepository authEmployeeRepo;
    private final AuthRepository authRepo;

    @Autowired
    public AuthService(UserService userService, AuthRepository authRepo, AuthEmployeeRepository authEmployeeRepo) {
        this.authRepo = authRepo;
        this.userService = userService;
        this.authEmployeeRepo = authEmployeeRepo;
    }

    public Principal authenticate(@Valid AuthRequest authRequest) {
        LogManager.getLogger().info(String.format("Email"));
            User u = authRepo.findByEmail(authRequest.getEmail()).orElseThrow(AuthenticationException::new);


        authRequest.setHashed(Generation.generatePassword(authRequest.getPassword(), u.getPassword().getSalt()));

        return authRepo.findUserByEmailAndPassword(authRequest.getEmail(), authRequest.getHashed())
                .map(UserResponsePayload::new)
                .map(Principal::new)
                .orElseThrow(AuthenticationException::new);

    }
    public Principal authenticateEmployee(@Valid AuthRequest authRequest) {
        LogManager.getLogger().info(String.format("Email"));
        Employee e = authEmployeeRepo.findEmployeeByEmail(authRequest.getEmail()).get();
        Password pass = Generation.generatePassword(authRequest.getPassword(), e.getPassword().getSalt());

        return authEmployeeRepo.findEmployeeByEmailAndPassword(authRequest.getEmail(), pass)
                .map(EmployeeResponsePayload::new)
                .map(Principal::new)
                .orElseThrow(AuthenticationException::new);
    }
}
