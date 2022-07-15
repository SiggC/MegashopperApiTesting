package dev.megashopper.common.dtos;

import dev.megashopper.common.entities.Employee;
import dev.megashopper.common.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeResponsePayload {
    private String employeeId;
    private String email;

    public EmployeeResponsePayload(Employee employee) {
        this.employeeId = employee.getEmployeeId();
        this.email = employee.getEmail();
    }
}
