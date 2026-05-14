/*
 * Copyright (c) 2026 Abhishek Kumar V K and/or its affiliates. All rights reserved
 */
package net.akvk.demo.entity;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.Strings;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    private String id;
    private String name;
    private LocalDate dateOfBirth;
    private LocalDate dateOfJoining;
    private String gender;
    private String designation;
    private String address;
    private String city;
    private String state;
    private String country;
    private String pin;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Employee other)) {
            return false;
        }
        return Strings.CS.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}
