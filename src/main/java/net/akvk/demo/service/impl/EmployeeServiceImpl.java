/*
 * Copyright (c) 2026 Abhishek Kumar V K and/or its affiliates. All rights reserved
 */
package net.akvk.demo.service.impl;

import java.util.Map;
import net.akvk.demo.entity.Employee;
import net.akvk.demo.error.EntityNotFoundException;
import net.akvk.demo.repository.EmployeeRepository;
import net.akvk.demo.service.EmployeeService;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository repository) {
        this.employeeRepository = repository;
    }

    @Override
    public Page<Employee> getEmployees(Map<String, String> filters) {
        return employeeRepository.findEmployees(MapUtils.emptyIfNull(filters));
    }

    @Override
    public Employee getEmployee(String id) {
        return employeeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Employee not found"));
    }

    @Override
    public Employee createEmployee(Employee emp) {
        return employeeRepository.insert(emp);
    }
}
