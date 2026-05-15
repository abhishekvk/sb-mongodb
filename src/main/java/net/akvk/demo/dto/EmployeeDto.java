/*
 * Copyright (c) 2026 Abhishek Kumar V K and/or its affiliates. All rights reserved
 */
package net.akvk.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDto {

    private String id;
    @NotEmpty(message = "Employee name is required")
    private String name;
    @NotNull(message = "Date of birth is required")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;
    @NotNull(message = "Date of Joining is required")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfJoining;
    @NotEmpty(message = "Designation is required")
    private String designation;
    @NotEmpty(message = "Gender is required")
    private String gender;
    @NotEmpty(message = "Address is required")
    private String address;
    @NotEmpty(message = "City is required")
    private String city;
    @NotEmpty(message = "State is required")
    private String state;
    @NotEmpty(message = "country is required")
    private String country;
    @NotEmpty(message = "PIN is required")
    private String pin;
}
