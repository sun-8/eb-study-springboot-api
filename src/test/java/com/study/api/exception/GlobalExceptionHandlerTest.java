package com.study.api.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    void handleException_ShouldReturnInternalServerErrorStatus() {
        // given
        Exception exception = new Exception("테스트 예외");

        // when
        ResponseEntity<String> response = exceptionHandler.handleException(exception);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("오류가 발생했습니다", response.getBody());
    }
} 