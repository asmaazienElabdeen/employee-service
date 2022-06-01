package com.stc.leaves.employeeservice.controller;

import com.stc.leaves.employeeservice.model.EmployeeRequestDTO;
import com.stc.leaves.employeeservice.model.EmployeeResponseDTO;
import com.stc.leaves.employeeservice.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * API to create a new employee
     *
     * @param requestDTO used to get request body
     * @return a newly created employee
     */
    @PostMapping(path = "/employees", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponseDTO createEmployee(@RequestBody EmployeeRequestDTO requestDTO) {
        return employeeService.createEmployee(requestDTO);
    }

    /**
     * API to find employee by id
     *
     * @param id used to get id of needed employee
     * @return a specific employee
     */
    @GetMapping(path = "/employees/{id}")
    public EmployeeResponseDTO findEmployeesById(@PathVariable String id) {
        return employeeService.findEmployeeById(id);
    }

    /**
     * API to find all employees
     *
     * @return a list of employees
     */
    @GetMapping(path = "/employees")
    public List<EmployeeResponseDTO> findAllEmployees() {
        return employeeService.findAllEmployees();
    }

    /**
     * API to update specific employee
     *
     * @param id used to get employee id
     * @param requestDTO used to get request body
     * @return updated employee
     */
    @PatchMapping(path = "employees/{id}")
    public EmployeeResponseDTO updateEmployeeById(@PathVariable String id, @RequestBody EmployeeRequestDTO requestDTO) {
        return employeeService.updateEmployeeById(id, requestDTO);
    }

    /**
     * API to delete specific employee
     *
     * @param id used to get employee id
     */
    @DeleteMapping(path = "employees/{id}")
    public void deleteEmployeeById(String id) {
        employeeService.deleteById(id);
    }
}