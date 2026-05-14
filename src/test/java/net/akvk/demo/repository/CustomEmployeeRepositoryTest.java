/*
 * Copyright (c) 2026 Abhishek Kumar V K and/or its affiliates. All rights reserved
 */
package net.akvk.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import net.akvk.demo.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;


@ExtendWith(MockitoExtension.class)
class CustomEmployeeRepositoryTest {

    private static final String TEST_EMP_ID_1 = "test-emp-id-1";
    private static final String TEST_EMP_ID_2 = "test-emp-id-2";

    @Mock
    MongoTemplate mongoTemplate;

    CustomEmployeeRepository customEmployeeRepository;

    @BeforeEach
    void setupTest() {
        customEmployeeRepository = new CustomEmployeeRepositoryImpl(mongoTemplate);
    }


    @Test
    void testGetEmployeesWithoutFilter() {
        Map<String, String> filters = Map.of();
        when(mongoTemplate.find(any(), any())).thenReturn(List.of(getEmployee(TEST_EMP_ID_1), getEmployee(TEST_EMP_ID_2)));
        Page<Employee> employees = customEmployeeRepository.findEmployees(filters);
        assertEquals(2, employees.getNumberOfElements());
    }

    @Test
    void testGetEmployeesWithFilter() {
        Map<String, String> filters = Map.of("page", "0", "size", "5", "dateOfJoining", "2024-01-02T00:00:00", "name", "test");
        when(mongoTemplate.find(any(), any())).thenReturn(List.of(getEmployee(TEST_EMP_ID_1)));
        Page<Employee> employees = customEmployeeRepository.findEmployees(filters);
        assertEquals(1, employees.getNumberOfElements());
    }

    private Employee getEmployee(String id) {
        return Employee.builder()
                .id(id)
                .build();
    }
}
