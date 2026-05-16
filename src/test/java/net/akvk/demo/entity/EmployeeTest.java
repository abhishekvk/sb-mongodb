/*
 * Copyright (c) 2026 Abhishek Kumar V K and/or affiliates. All rights reserved
 */
package net.akvk.demo.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
 class EmployeeTest {

    private static final String TEST_EMP_ID_1 = "test-emp-1";
    private static final String TEST_EMP_ID_2 = "test-emp-2";


    @Test
    void testSameObjectEquals() {
        Employee emp = Employee.builder().id(TEST_EMP_ID_1).build();
        assertEquals(emp, emp);
    }

    @Test
    void testEquals() {
        Employee emp1 = Employee.builder().id(TEST_EMP_ID_1).build();
        Employee emp2 = Employee.builder().id(TEST_EMP_ID_1).build();
        assertEquals(emp1, emp2);
    }

    @Test
    void testNotEquals() {
        Employee emp1 = Employee.builder().id(TEST_EMP_ID_1).build();
        Employee emp2 = Employee.builder().id(TEST_EMP_ID_2).build();
        assertNotEquals(emp1, emp2);
    }

    @Test
    void testDifferentObjects() {
        Employee emp1 = Employee.builder().id(TEST_EMP_ID_1).build();
        String testStr = "";
        assertNotEquals(testStr, emp1);
    }

    @Test
    void testHashCode() {
        Employee emp1 = Employee.builder().id(TEST_EMP_ID_1).build();
        assertEquals(emp1.hashCode(), TEST_EMP_ID_1.hashCode());
    }

}
