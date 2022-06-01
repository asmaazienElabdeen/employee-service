package com.stc.leaves.employeeservice.validator;

import com.stc.leaves.employeeservice.document.Employees;
import com.stc.leaves.employeeservice.exception.EmployeeNotFoundException;
import com.stc.leaves.employeeservice.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@AllArgsConstructor
public class EmployeeValidator {
    private final EmployeeRepository employeeRepository;

    public Employees validateExistence(String id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    public Employees validateEmployeeExistenceByEmail(String email) {
        return employeeRepository.findEmployeeByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Employee with email %s already exists", email)));
    }
}
