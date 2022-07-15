package dev.megashopper.common.dtos;

import dev.megashopper.common.entities.Password;
import dev.megashopper.common.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class UserResponsePayload {

    private String customerId;
    private String email;
    private String username;
    public UserResponsePayload(User user) {
        this.customerId = user.getCustomerId();
        this.email = user.getEmail();
        this.username = user.getUsername();
    }
}
