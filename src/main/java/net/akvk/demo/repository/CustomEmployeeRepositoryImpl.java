/*
 * Copyright (c) 2026 Abhishek Kumar V K and/or its affiliates. All rights reserved
 */
package net.akvk.demo.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import net.akvk.demo.entity.Employee;
import org.apache.commons.lang3.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomEmployeeRepositoryImpl implements CustomEmployeeRepository {

    private MongoTemplate mongoTemplate;

    @Autowired
    public CustomEmployeeRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Page<Employee> findEmployees(Map<String, String> filters) {
        int page = Integer.parseInt(filters.getOrDefault("page", "0"));
        int size = Integer.parseInt(filters.getOrDefault("size", "10"));
        Pageable pageable = PageRequest.of(page, Math.min(100, size));

        String validFilters = "^(name|designation|city|country|pin|gender|dateOfJoining)$|^(beg_|end_)(dateOfJoining)$";
        Map<String, Object> updatedFilters = new HashMap<>();

        filters.forEach((key, value) -> {
            if(key.matches(validFilters)) {
                if (Strings.CS.containsAny(key, "dateOfJoining") || Strings.CS.startsWithAny(key, "beg_", "end_")) {
                    updatedFilters.put(key, LocalDateTime.parse(value.replace(" ", "T")));
                } else {
                    updatedFilters.put(key, value);
                }
            }
        });
        return findEmployees(pageable, updatedFilters);
    }

    @Override
    public Page<Employee> findEmployees(Pageable pageable, Map<String, Object> filters) {
        Query query = new Query().with(pageable);
        final List<Criteria> andCriteria = new ArrayList<>();
        Set<String> keys = Set.copyOf(filters.keySet());
        keys.forEach(key -> {
            Object value = filters.get(key);
            if (Strings.CS.startsWith(key, "beg_") || Strings.CS.endsWith(key, "min_")) {
                String str = key.substring(4);
                andCriteria.add(Criteria.where(str).gte(value));
            } else if (Strings.CS.startsWith(key, "end_") || Strings.CS.startsWith(key, "max_")) {
                String varName = key.substring(4);
                andCriteria.add(Criteria.where(varName).lte(value));
            } else {
                if (Objects.nonNull(value) && String.class.isAssignableFrom(value.getClass())) {
                    andCriteria.add(Criteria.where(key).regex((String)value, "i"));
                } else {
                    andCriteria.add(Criteria.where(key).is(value));
                }
            }
        });

        if (!andCriteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(andCriteria));
        }
        return PageableExecutionUtils.getPage(mongoTemplate.find(query, Employee.class), pageable, () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Employee.class));
    }
}
