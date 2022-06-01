package com.stc.leaves.employeeservice.exception;

public class EmployeeNotFoundException extends RuntimeException {
    private final String id;

    public EmployeeNotFoundException(String id) {
        super(String.format("Employee with Id: [%s] doesn't exist", id));
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
