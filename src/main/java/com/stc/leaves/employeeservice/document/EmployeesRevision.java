package com.stc.leaves.employeeservice.document;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Document(collection = "employees_revisions")
public class EmployeesRevision {
    @Id
    private String id;
    private ObjectId employeeId;
    private String name;
    private String email;
    private Leaves leaves;
    private int revision;
    @Indexed(name = "ttl_index", expireAfterSeconds = 7884000)
    private Instant created;
    private Instant lastModified;
}
