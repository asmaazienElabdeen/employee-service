package com.stc.leaves.employeeservice.model.mapper;

import com.stc.leaves.employeeservice.document.Employees;
import com.stc.leaves.employeeservice.document.EmployeesRevision;
import com.stc.leaves.employeeservice.document.enums.State;
import com.stc.leaves.employeeservice.model.EmployeeRequestDTO;
import com.stc.leaves.employeeservice.model.EmployeeResponseDTO;
import com.stc.leaves.employeeservice.model.LeavePayload;
import com.stc.leaves.employeeservice.model.LeaveRecord;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeMapper {

    public Employees mapToDocument(EmployeeRequestDTO requestDTO) {
        return Employees.builder()
                .name(requestDTO.getName())
                .leaves(requestDTO.getLeaves())
                .email(requestDTO.getEmail())
                .build();
    }

    public Employees mapToDocument(Employees employee, EmployeeRequestDTO requestDTO) {
        return Employees.builder()
                .id(employee.getId())
                .name(requestDTO.getName())
                .email(requestDTO.getEmail())
                .leaves(requestDTO.getLeaves())
                .build();
    }

    public EmployeesRevision mapToRevisionDocument(Employees employees) {
        return EmployeesRevision.builder()
                .employeeId(new ObjectId(employees.getId()))
                .name(employees.getName())
                .email(employees.getEmail())
                .leaves(employees.getLeaves())
                .created(employees.getCreated())
                .lastModified(employees.getLastModified())
                .revision(employees.getRevision())
                .build();
    }

    public EmployeeResponseDTO mapToDTO(Employees employee) {
        EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO();
        employeeResponseDTO.setId(employee.getId());
        employeeResponseDTO.setName(employee.getName());
        employeeResponseDTO.setEmail(employee.getEmail());
        employeeResponseDTO.setLeaves(employee.getLeaves());
        employeeResponseDTO.setCreated(employee.getCreated());
        employeeResponseDTO.setLastModified(employee.getLastModified());
        employeeResponseDTO.setRevision(employee.getRevision());
        return employeeResponseDTO;
    }

    public List<EmployeeResponseDTO> mapToDTO(List<Employees> employeesList) {
        if (employeesList == null || employeesList.isEmpty())
            return Collections.emptyList();
        else {
            return employeesList.parallelStream().map(this::mapToDTO).collect(Collectors.toList());
        }
    }

    public LeavePayload mapToPayload(String leaveId, Employees employees, State state) {
        return LeavePayload.builder()
                .employeeEmail(employees.getEmail())
                .leaveId(leaveId)
                .state(state)
                .build();
    }

    public LeaveRecord mapToLeaveRecord(LeavePayload leavePayload) {
        return LeaveRecord.builder()
                .action(LeaveRecord.Action.CREATE)
                .entity(LeaveRecord.Entity.EMPLOYEE.value())
                .payload(leavePayload)
                .build();
    }
}
