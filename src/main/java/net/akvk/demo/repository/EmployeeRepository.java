/*
 * Copyright (c) 2026 Abhishek Kumar V K and/or affiliates. All rights reserved
 */
package net.akvk.demo.repository;

import net.akvk.demo.entity.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String>, CustomEmployeeRepository {

}
