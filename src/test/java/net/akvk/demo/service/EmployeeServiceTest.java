/*
 * Copyright (c) 2026 Abhishek Kumar V K and/or affiliates. All rights reserved
 */
package net.akvk.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.akvk.demo.entity.Employee;
import net.akvk.demo.error.EntityNotFoundException;
import net.akvk.demo.repository.EmployeeRepository;
import net.akvk.demo.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    private static final String TEST_EMP_ID_1 = "test-emp-id-1";
    private static final String TEST_EMP_NAME_1 = "test-emp-name-1";
    private static final String TEST_EMP_DESIGNATION_1 = "test-emp-desingation-1";
    private static final LocalDate TEST_EMP_DOJ_1 = LocalDate.now();
    private static final LocalDate TEST_EMP_DOB_1 = LocalDate.of(2000, 04, 24);

    @Mock
    private EmployeeRepository employeeRepository;

    private EmployeeService employeeService;

    @BeforeEach
    void setupTest() {
        employeeService = new EmployeeServiceImpl(employeeRepository);
    }

    @Test
    void testGetEmployees() {
        when(employeeRepository.findEmployees(any())).thenReturn(getEmployeesPage());
        Page<Employee> employees = employeeService.getEmployees(Map.of());
        assertEquals(1, employees.getNumberOfElements());
    }

    @Test
    void testGetEmployeeById() {
        when(employeeRepository.findById(anyString())).thenReturn(Optional.of(Employee.builder().id(TEST_EMP_ID_1).build()));
        Employee emp = employeeService.getEmployee(TEST_EMP_ID_1);
        assertEquals(TEST_EMP_ID_1, emp.getId());
    }

    @Test
    void testGetEmployeeByIdNotFound() {
        when(employeeRepository.findById(TEST_EMP_ID_1)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> employeeService.getEmployee(TEST_EMP_ID_1));
    }

    @Test
    void testCreateEmployee() {
        Employee employee = Employee.builder()
                        .id(TEST_EMP_ID_1)
                        .name(TEST_EMP_NAME_1)
                        .designation(TEST_EMP_DESIGNATION_1)
                        .dateOfBirth(TEST_EMP_DOB_1)
                        .dateOfJoining(TEST_EMP_DOJ_1)
                        .build();

        when(employeeRepository.insert(any(Employee.class))).thenAnswer(invocationOnMock -> {
            Employee emp = invocationOnMock.getArgument(0);
            emp.setId(TEST_EMP_ID_1);
            return emp;
        });

        Employee emp = employeeService.createEmployee(employee);
        assertEquals(TEST_EMP_ID_1, emp.getId());
    }

    Page<Employee> getEmployeesPage() {
        List<Employee> empList = List.of(
                Employee.builder().id(TEST_EMP_ID_1).build()
        );
        return  new PageImpl<>(empList, PageRequest.of(0, 10), 1);
    }
}
