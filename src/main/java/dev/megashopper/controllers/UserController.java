package dev.megashopper.controllers;

import dev.megashopper.common.dtos.*;
import dev.megashopper.common.entities.User;
import dev.megashopper.common.repository.UserRepository;
import dev.megashopper.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

// Rest controller combines @Controller and @ResponseBody(changes return value to HTTP response)
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }



    /* TODO: This code will not work until it has been implemented into UserRepository
     *   */
    @GetMapping(produces = "application/json")
    public List<UserResponse> getAllUsers() {
        return userService.fetchAllUsers();
    }

    //    @ResponseStatus(HttpStatus.CREATED)

//    @PostMapping(consumes = "application/json", produces = "application/json")
//    Public ResourceCreationResponse postNewUser(@RequestBody UserResponsePayload newUserInfo) {
//        return userService.createUser(newUserInfo);
//    }
//
//    @GetMapping("/id/{customerId}")
//    public <UserResponse> UserResponse getUserById(@PathVariable String customerId) {
//        return (UserResponse) userService.(customerId);
//    }
//
//    /* TODO: Uncomment/Need Import from UserRepository after implementing 'createUser' method
//     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResourceCreationResponse postNewUser(@RequestBody UserRequestPayload newUser) {
        return userService.createUser(newUser);

    }

    //
//    // TODO: Need Help, I don't think it should return 'null'
//    @GetMapping(produces = "application/json")
//    public List<UserResponsePayload> getAllUsers(@RequestHeader(value = "Authorization", required = false) String token) {
//        return null;
//    }
//
//    @GetMapping("/search")
//    public List<UserResponsePayload> findBy(@RequestParam Map<String, String> params) {
//        return userService.search(params);
//    }
//    @GetMapping("/availability")
//    public ResponseEntity<Void> checkAvailability(@RequestParam(required = false) String username, @RequestParam(required = false) String email) {
//        if (username != null) {
//            return userService.isUsernameAvailability(new UsernameRequest(username))
//                    ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.CONFLICT);
//        }
//
//        if (email != null ) {
//            return userService.isEmailAvailability(new EmailRequest(email))
//                    ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.CONFLICT);
//        }
//
//        throw new InvalidRequestException("No email or username provided");
//    }
//
//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping(consumes = "application/json", produces = "application/json")
//    public ResourceCreationResponse postNewUser(@RequestBody UserRequestPayload newUserInfo) {
//        return userService.createUser(newUserInfo);
//    }
//
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @PatchMapping(consumes = "application/json")
//    public void updateUserInfo(@RequestBody UserRequestPayload updatedUserInfo) {
//        userService.updateUser(updatedUserInfo);
//


    public List fetchAllUsers() {
        return userService.fetchAllUsers();
    }
}
