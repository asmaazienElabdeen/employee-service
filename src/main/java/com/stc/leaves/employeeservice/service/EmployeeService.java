package com.stc.leaves.employeeservice.service;

import com.stc.leaves.employeeservice.document.Employees;
import com.stc.leaves.employeeservice.document.enums.State;
import com.stc.leaves.employeeservice.event.KafkaProducer;
import com.stc.leaves.employeeservice.model.EmployeeRequestDTO;
import com.stc.leaves.employeeservice.model.EmployeeResponseDTO;
import com.stc.leaves.employeeservice.model.LeavePayload;
import com.stc.leaves.employeeservice.model.mapper.EmployeeMapper;
import com.stc.leaves.employeeservice.repository.EmployeeRepository;
import com.stc.leaves.employeeservice.repository.EmployeesRevisionRepository;
import com.stc.leaves.employeeservice.validator.EmployeeValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeesRevisionRepository employeesRevisionRepository;
    private final EmployeeMapper employeeMapper;
    private final EmployeeValidator employeeValidator;
    private final KafkaProducer kafkaProducer;

    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO requestDTO) {
        var employee = employeeMapper.mapToDocument(requestDTO);
        var savedEmployee = employeeRepository.save(employee);
        return employeeMapper.mapToDTO(savedEmployee);
    }

    public EmployeeResponseDTO findEmployeeById(String id) {
        Employees employee = employeeValidator.validateExistence(id);
        return employeeMapper.mapToDTO(employee);
    }

    public List<EmployeeResponseDTO> findAllEmployees() {
        return employeeMapper.mapToDTO(employeeRepository.findAll());
    }

    public EmployeeResponseDTO updateEmployeeById(String id, EmployeeRequestDTO requestDTO) {
        Employees employee = employeeValidator.validateExistence(id);
        saveEmployeeRevision(employee);
        employee = employeeMapper.mapToDocument(employee, requestDTO);
        Employees savedEmployee = employeeRepository.save(employee);
        return employeeMapper.mapToDTO(savedEmployee);
    }

    public void deleteById(String id) {
        Employees employee = employeeValidator.validateExistence(id);
        employeeRepository.delete(employee);
    }

    public void updateEmployeeLeaves(String leaveId, String email, int noOfDays) {
        Employees employees = employeeValidator.validateEmployeeExistenceByEmail(email);
        saveEmployeeRevision(employees);
        if (noOfDays <= employees.getLeaves().getAvailable()) {
            employees.getLeaves().setConsumed(employees.getLeaves().getConsumed() + noOfDays);
            employees.getLeaves().setAvailable(employees.getLeaves().getAvailable() - noOfDays);
            employees.setRevision(employees.getRevision() + 1);
            Employees savedEmployee = employeeRepository.save(employees);
            LeavePayload leavePayload = employeeMapper.mapToPayload(leaveId, savedEmployee, State.APPROVED);
            kafkaProducer.publish(employeeMapper.mapToLeaveRecord(leavePayload));
        }
    }

    private void saveEmployeeRevision(Employees employees) {
        var employeesRevision = employeeMapper.mapToRevisionDocument(employees);
        employeesRevisionRepository.save(employeesRevision);
    }
}
