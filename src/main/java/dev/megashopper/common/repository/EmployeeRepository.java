package dev.megashopper.common.repository;

import dev.megashopper.common.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,String> {

    boolean existsByEmail(String email);
    Optional<Employee> findEmployeeByEmail(String email);
    Optional<Employee> findEmployeeByPassword(String password);


}
