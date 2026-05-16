/*
 * Copyright (c) 2026 Abhishek Kumar V K and/or affiliates. All rights reserved
 */
package net.akvk.demo.repository;

import java.util.Map;
import net.akvk.demo.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomEmployeeRepository {
    Page<Employee> findEmployees(Pageable page, Map<String, Object> filters);
    Page<Employee> findEmployees(Map<String, String> filters);
}
