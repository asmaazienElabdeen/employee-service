package com.stc.leaves.employeeservice.model;

import com.stc.leaves.employeeservice.document.Leaves;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
public class EmployeeRequestDTO {
    @NotBlank(message = "Employee name is required")
    private String name;
    private Leaves leaves;
    @Email(message = "Invalid email address")
    @NotBlank(message = "The email is required")
    private String email;
}
