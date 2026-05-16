/*
 * Copyright (c) 2026 Abhishek Kumar V K and/or affiliates. All rights reserved
 */
package net.akvk.demo.controller;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import net.akvk.demo.dto.EmployeeDto;
import net.akvk.demo.entity.Employee;
import net.akvk.demo.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService service) {
        this.employeeService = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagedModel<EmployeeDto>> getEmployees(@RequestParam(required = false) Map<String, String> parameters) {
        log.info("API - Get Employees");
        Page<Employee> employees = employeeService.getEmployees(parameters);
        List<EmployeeDto> dtos = new ArrayList<>();

        employees.getContent().forEach(emp -> {
            EmployeeDto dto = new EmployeeDto();
            BeanUtils.copyProperties(emp, dto);
            dtos.add(dto);
        });

        Page<EmployeeDto> empPage = new PageImpl<>(dtos, PageRequest.of(employees.getNumber(), employees.getSize()), employees.getTotalElements());
        return ResponseEntity.ok(new PagedModel<>(empPage));
    }

    @GetMapping(value= "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable String id) {
        log.info("API - Get Employee by Id");
        Employee employee = employeeService.getEmployee(id);
        EmployeeDto dto = new EmployeeDto();
        BeanUtils.copyProperties(employee, dto);
        return ResponseEntity.ok(dto);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody @Valid EmployeeDto emp) {
        log.info("API - Create new employee");
        Employee employee = new Employee();
        BeanUtils.copyProperties(emp, employee);
        employee = employeeService.createEmployee(employee);
        EmployeeDto dto = new EmployeeDto();
        BeanUtils.copyProperties(employee, dto);
        return ResponseEntity.ok(dto);
    }

}
