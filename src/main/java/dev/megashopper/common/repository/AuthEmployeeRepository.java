package dev.megashopper.common.repository;

import dev.megashopper.common.entities.Employee;
import dev.megashopper.common.entities.Password;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthEmployeeRepository extends JpaRepository <Employee, String> {
    Optional<Employee> findEmployeeByEmail(String email);
    Optional<Employee> findEmployeeByEmailAndPassword(String email, Password password);

}
