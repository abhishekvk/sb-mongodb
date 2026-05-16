/*
 * Copyright (c) 2026 Abhishek Kumar V K and/or affiliates. All rights reserved
 */
package net.akvk.demo.error;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;

@ExtendWith(MockitoExtension.class)
class ExceptionAdviceTest {

    @InjectMocks
    ExceptionAdvice exceptionAdvice;

    ServletWebRequest webRequest;

    @BeforeEach
    void setUp() {
        MockHttpServletRequest servletRequest = new MockHttpServletRequest();
        servletRequest.setServerName("solvendo.io");
        servletRequest.setRequestURI("/users/signup");
        servletRequest.setMethod(HttpMethod.POST.name());
        webRequest = new ServletWebRequest(servletRequest);
    }

    @Test
    void testHandleEntityNotFound() {
        EntityNotFoundException exception = new EntityNotFoundException("Employee not found");
        ResponseEntity entity = exceptionAdvice.handleEntityNotFound(exception, webRequest);
        assertEquals(HttpStatus.NOT_FOUND, entity.getStatusCode());
        ErrorMessage response = (ErrorMessage) entity.getBody();
        assertEquals("Employee not found", response.getMessage());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    void testHandleException() {
        Exception exception = new Exception("Network error");
        ResponseEntity entity = exceptionAdvice.handleException(exception, webRequest);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, entity.getStatusCode());
        ErrorMessage response = (ErrorMessage) entity.getBody();
        assertEquals("Network error", response.getMessage());
        assertEquals("/users/signup", response.getPath());
    }

}
