package dev.megashopper.common.service;

import dev.megashopper.common.datasource.EntitySearcher;
import dev.megashopper.common.dtos.*;
import dev.megashopper.common.entities.Employee;
import dev.megashopper.common.entities.User;
import dev.megashopper.common.repository.EmployeeRepository;
import dev.megashopper.common.repository.UserRepository;
import dev.megashopper.common.utils.Generation;
import dev.megashopper.common.utils.exceptions.AuthorizationException;
import dev.megashopper.common.utils.exceptions.ResourceNotFoundException;
import dev.megashopper.common.utils.exceptions.ResourcePersistenceException;
import dev.megashopper.common.utils.web.validators.groups.OnCreate;
import dev.megashopper.common.utils.web.validators.groups.OnUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@Validated
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EntitySearcher entitySearcher;
    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, EntitySearcher entitySearcher) {
        this.employeeRepository = employeeRepository;
        this.entitySearcher = entitySearcher;
    }

    public List<EmployeeResponsePayload> fetchAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(EmployeeResponsePayload::new)
                .collect(Collectors.toList());
    }
    public List<EmployeeResponsePayload> search(Map<String, String> requestParamMap) {
        if (requestParamMap.isEmpty()) fetchAllEmployees();
        Set<Employee> matchingEmployees = entitySearcher.searchForEntity(requestParamMap, Employee.class);
        if (matchingEmployees.isEmpty()) throw new ResourceNotFoundException();
        return matchingEmployees.stream()
                .map(EmployeeResponsePayload::new)
                .collect(Collectors.toList());
    }

    public boolean isEmailAvailability(@Valid EmailRequest request) {
        return !employeeRepository.existsByEmail(request.getEmail());
    }

    public EmployeeResponsePayload fetchUserByEmail(@Valid EmailRequest request) {
        return employeeRepository.findEmployeeByEmail(request.getEmail())
                .map(EmployeeResponsePayload::new)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Validated(OnCreate.class)
    public ResourceCreationResponse createEmployee(@Valid EmployeeRequestPayload newEmployeeRequest) {
        Employee newEmployee = newEmployeeRequest.extractResource();
        newEmployee.setPassword(Generation.generatePassword(newEmployeeRequest.getPassword()));

        if (!newEmployee.getEmail().endsWith("@megashopper.net"))
            throw new AuthorizationException("Must be a work email");
        if (employeeRepository.existsByEmail(newEmployee.getEmail())) {
            throw new ResourcePersistenceException("There is already a user with that email!");
        }

        newEmployee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.save(newEmployee);
        return new ResourceCreationResponse(newEmployee.getEmployeeId());

    }


    @Validated(OnUpdate.class)
    public void updateEmployee(@Valid EmployeeRequestPayload updatedEmployeeRequest) {

        Employee updatedEmployee = updatedEmployeeRequest.extractResource();
        Employee employeeForUpdate = employeeRepository.findById(updatedEmployee.getEmployeeId()).orElseThrow(ResourceNotFoundException::new);

        if (updatedEmployee.getFirstName() != null) {
            employeeForUpdate.setFirstName(updatedEmployee.getFirstName());
        }

        if (updatedEmployee.getLastName() != null) {
            employeeForUpdate.setLastName(updatedEmployee.getLastName());
        }

        if (updatedEmployee.getEmail() != null) {
            if (employeeRepository.existsByEmail(updatedEmployee.getEmail())) {
                throw new ResourcePersistenceException("There is already a user with that email!");
            }
            employeeForUpdate.setEmail(updatedEmployee.getEmail());
        }


        if (employeeForUpdate.getPassword() != null) {
            employeeForUpdate.setPassword(Generation.generatePassword(updatedEmployeeRequest.getPassword()));
        }

    }

    public EmployeeResponsePayload findById(String id) {
        return employeeRepository.findById(id)
                .map(EmployeeResponsePayload::new)
                .orElseThrow(ResourceNotFoundException::new);
    }

    public void deleteEmployeeById(String customerId) {
        employeeRepository.deleteById(customerId);
    }
}
