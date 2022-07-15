package dev.megashopper.controllers;

import dev.megashopper.common.dtos.*;
import dev.megashopper.common.entities.Employee;
import dev.megashopper.common.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResourceCreationResponse postNewUser(@RequestBody EmployeeRequestPayload newEmployee) {
        return employeeService.createEmployee(newEmployee);
    }

    @GetMapping(produces = "application/json")
    public List<EmployeeResponsePayload> getAllEmployees() {
        return employeeService.fetchAllEmployees();
    }

}
