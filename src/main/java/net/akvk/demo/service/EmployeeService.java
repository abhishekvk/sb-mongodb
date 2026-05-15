/*
 * Copyright (c) 2026 Abhishek Kumar V K and/or its affiliates. All rights reserved
 */
package net.akvk.demo.service;

import java.util.Map;
import net.akvk.demo.entity.Employee;
import org.springframework.data.domain.Page;

public interface EmployeeService {

    Page<Employee> getEmployees(Map<String, String> query);
    Employee getEmployee(String id);
    Employee createEmployee(Employee employee);
}
