package dev.megashopper.common.dtos;


import dev.megashopper.common.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
public class Principal {
    private String authUserId;
    private String authEmail;

    public Principal(UserResponse user) {
        this.authUserId = String.valueOf(user.getCustomerId());
        this.authEmail = String.valueOf(user.getEmail());
    }

    public Principal(String authUserId, String authUserRole) {
        this.authUserId = authUserId;
        this.authEmail = authUserRole;
    }

    public Principal(UserResponsePayload userResponsePayload) {
        this(userResponsePayload.getCustomerId(), userResponsePayload.getEmail());
    }

    public Principal(EmployeeResponsePayload employeeResponsePayload) {
        this(employeeResponsePayload.getEmployeeId(), employeeResponsePayload.getEmail());

    }
    public String getAuthCustomerId() {
        return authUserId;
    }
}
