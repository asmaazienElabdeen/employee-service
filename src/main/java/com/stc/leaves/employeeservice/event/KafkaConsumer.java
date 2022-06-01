package com.stc.leaves.employeeservice.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stc.leaves.employeeservice.model.LeaveRecord;
import com.stc.leaves.employeeservice.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumer {
    public static final String LEAVE_EVENTS_TOPIC = "leave_events";
    private final EmployeeService employeeService;
    private final ObjectMapper objectMapper;

    KafkaConsumer(EmployeeService employeeService) {
        this.employeeService = employeeService;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @KafkaListener(topics = LEAVE_EVENTS_TOPIC)
    public void consumeUserEventsTopic(String json) {
        try {
            var leaveRecord = objectMapper.readValue(json, LeaveRecord.class);
            var leavePayload = leaveRecord.getPayload();
            if (leavePayload.getState().toString().equals("REQUESTED")) {
                log.info("Updating {}", leavePayload.getEmployeeEmail());
                employeeService.updateEmployeeLeaves(leavePayload.getLeaveId(), leavePayload.getEmployeeEmail(), leavePayload.getNoOfDays());
            }
        } catch (JsonProcessingException ex) {
            log.error(ex.getMessage());
        }
    }
}
