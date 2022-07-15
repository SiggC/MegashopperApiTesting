package dev.megashopper.common.service;

import com.shippo.exception.InvalidRequestException;
import dev.megashopper.common.datasource.EntitySearcher;
import dev.megashopper.common.dtos.*;
import dev.megashopper.common.dtos.UserResponse;
import dev.megashopper.common.entities.User;
import dev.megashopper.common.repository.UserRepository;
import dev.megashopper.common.utils.Generation;
import dev.megashopper.common.utils.exceptions.ResourceNotFoundException;
import dev.megashopper.common.utils.exceptions.ResourcePersistenceException;
import dev.megashopper.common.utils.web.validators.groups.OnCreate;
import dev.megashopper.common.utils.web.validators.groups.OnUpdate;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.naming.AuthenticationException;
import javax.persistence.Entity;
import javax.transaction.Transactional;
import javax.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Validated
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final EntitySearcher entitySearcher;
    @Autowired
    public UserService(UserRepository userRepository, EntitySearcher entitySearcher) {
        this.userRepository = userRepository;
        this.entitySearcher = entitySearcher;
    }

    public List<UserResponse> fetchAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }
    public List<UserResponsePayload> search(Map<String, String> requestParamMap) {
        if (requestParamMap.isEmpty()) fetchAllUsers();
        Set<User> matchingUsers = entitySearcher.searchForEntity(requestParamMap, User.class);
        if (matchingUsers.isEmpty()) throw new ResourceNotFoundException();
        return matchingUsers.stream()
                .map(UserResponsePayload::new)
                .collect(Collectors.toList());
    }

    public boolean isUsernameAvailable(@Valid UsernameRequest request) {
        return !userRepository.existsByUsername(request.getUsername());
    }

    public boolean isEmailAvailability(@Valid EmailRequest request) {
        return !userRepository.existsByEmail(request.getEmail());
    }

    public UserResponsePayload fetchUserByEmail(@Valid EmailRequest request) {
        return userRepository.findUserByEmail(request.getEmail())
                .map(UserResponsePayload::new)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Validated(OnCreate.class)
    public ResourceCreationResponse createUser(@Valid UserRequestPayload newUserRequest) {
        User newUser = newUserRequest.extractResource();
        newUser.setPassword(Generation.generatePassword(newUserRequest.getPassword()));
        newUser.setAddress(newUserRequest.getAddress());
        if (userRepository.existsByUsername(newUser.getUsername())) {
            throw new ResourcePersistenceException("There is already a user with that username!");
        }
        if (newUser.getUsername().length() < 7)
            throw new ResourcePersistenceException("Username must be at least 8 characters in length");
        if (userRepository.existsByEmail(newUser.getEmail())) {
            throw new ResourcePersistenceException("There is already a user with that email!");
        }

        newUser.setCustomerId(UUID.randomUUID().toString());
        userRepository.save(newUser);
        return new ResourceCreationResponse(newUser.getCustomerId());

    }


    @Validated(OnUpdate.class)
    public void updateUser(@Valid UserRequestPayload updatedUserRequest) {

        User updatedUser = updatedUserRequest.extractResource();
        User userForUpdate = userRepository.findById(updatedUser.getCustomerId()).orElseThrow(ResourceNotFoundException::new);

        if (updatedUser.getFirstName() != null) {
            userForUpdate.setFirstName(updatedUser.getFirstName());
        }

        if (updatedUser.getLastName() != null) {
            userForUpdate.setLastName(updatedUser.getLastName());
        }

        if (updatedUser.getEmail() != null) {
            if (userRepository.existsByEmail(updatedUser.getEmail())) {
                throw new ResourcePersistenceException("There is already a user with that email!");
            }
            userForUpdate.setEmail(updatedUser.getEmail());
        }

        if (updatedUser.getUsername() != null) {
            if (userRepository.existsByUsername(updatedUser.getUsername())) {
                throw new ResourcePersistenceException("There is already a user with that username!");
            }
            userForUpdate.setUsername(updatedUser.getUsername());
        }

        if (updatedUser.getPassword() != null) {
            userForUpdate.setPassword(updatedUser.getPassword());
        }

    }

    public UserResponsePayload findById(String id) {
        return userRepository.findById(id)
                .map(UserResponsePayload::new)
                .orElseThrow(ResourceNotFoundException::new);
    }

    public void deleteCustomerById(String customerId) {
        userRepository.deleteById(customerId);
    }

}


