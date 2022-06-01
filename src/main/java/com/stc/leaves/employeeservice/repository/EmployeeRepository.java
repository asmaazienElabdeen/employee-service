package com.stc.leaves.employeeservice.repository;

import com.stc.leaves.employeeservice.document.Employees;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends MongoRepository<Employees, String> {
    @Query(value = "{ 'email': ?0 }")
    Optional<Employees> findEmployeeByEmail(String email);
}