package com.stc.leaves.employeeservice.repository;

import com.stc.leaves.employeeservice.document.EmployeesRevision;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeesRevisionRepository extends MongoRepository<EmployeesRevision, String> {
}
