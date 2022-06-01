package com.stc.leaves.employeeservice.document;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Document(collection = "employees", collation = "{locale: 'en', strength: 2}")
public class Employees {
    @Id
    private String id;
    private String name;
    @Indexed(unique = true)
    private String email;
    private Leaves leaves;
    @CreatedDate
    private Instant created;
    @LastModifiedDate
    private Instant lastModified;
    @Builder.Default
    private int revision = 1;
}
