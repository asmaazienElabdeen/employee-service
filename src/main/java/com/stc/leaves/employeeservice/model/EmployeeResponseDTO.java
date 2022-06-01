package com.stc.leaves.employeeservice.model;

import com.stc.leaves.employeeservice.document.Leaves;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class EmployeeResponseDTO {
    private String id;
    private String name;
    private String email;
    private Leaves leaves;
    private Instant created;
    private Instant lastModified;
    private int revision;
}
