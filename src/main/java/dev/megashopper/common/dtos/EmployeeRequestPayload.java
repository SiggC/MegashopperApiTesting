package dev.megashopper.common.dtos;

import dev.megashopper.common.entities.Employee;
import dev.megashopper.common.utils.Generation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequestPayload {
    @NotNull
    private String employeeId;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String email;
    @NotNull
    private String password;

    public Employee extractResource() {
        if (employeeId == null) {
            return new Employee(firstName, lastName, email);
        }
        return new Employee(employeeId, firstName, lastName, Generation.generatePassword(password), email);
    }
}
