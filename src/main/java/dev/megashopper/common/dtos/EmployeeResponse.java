package dev.megashopper.common.dtos;

import dev.megashopper.common.entities.Employee;
import dev.megashopper.common.entities.Password;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeResponse {

        private String id;
        private String firstName;
        private String lastName;
        private String email;
        private Password password;

        public EmployeeResponse(Employee employee) {
            this.id = employee.getEmployeeId();
            this.firstName = employee.getFirstName();
            this.lastName = employee.getLastName();
            this.email = employee.getEmail();
            this.password = employee.getPassword();
        }

        public void createEmployee(Employee newEmployee) {
            this.id = newEmployee.getEmployeeId();
            this.firstName = newEmployee.getFirstName();
            this.lastName = newEmployee.getLastName();
            this.email = newEmployee.getEmail();
            this.password = newEmployee.getPassword();
        }
}
