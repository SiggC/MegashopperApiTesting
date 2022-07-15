package dev.megashopper.common.dtos;

import dev.megashopper.common.entities.Password;
import dev.megashopper.common.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponse {

    private String customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String username;
    private Password password;


    public UserResponse(User user) {
        this.customerId = user.getCustomerId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.address = user.getAddress();
        this.password = user.getPassword();
    }

    public void createUser(User newUser) {
        this.customerId = newUser.getCustomerId();
        this.firstName = newUser.getFirstName();
        this.lastName = newUser.getLastName();
        this.email = newUser.getEmail();
        this.address = newUser.getAddress();
        this.username = newUser.getUsername();
        this.password = newUser.getPassword();
    }

}
