package com.study.api.config;

import com.study.api.common.code.ApiCode;
import com.study.api.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Hidden
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler{

    private final HttpStatus HTTP_STATUS_OK = HttpStatus.OK;

    // todo. 모든 예외처리를 각각 해주어야 하는가?
    /**
     * [Exception] 유효성 검증 실패
     * @param e MethodArgumentNotValidException
     * @return ResponseEntity<ApiResponse>
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException", e);
        BindingResult bindingResult = e.getBindingResult();
        final ApiResponse response = new ApiResponse(ApiCode.VALIDATION_FAILED, bindingResult);
        return new ResponseEntity<>(response, HTTP_STATUS_OK);
    }

    /**
     * [Exception] NULL 값이 발생한 경우
     * @param e NullPointerException
     * @return ResponseEntity<ApiResponse>
     */
    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<ApiResponse> handleNullPointerException(NullPointerException e) {
        log.error("handleNullPointerException", e);
        final ApiResponse response = new ApiResponse(ApiCode.NULL_POINT_ERROR, e.getMessage());
        return new ResponseEntity<>(response, HTTP_STATUS_OK);
    }

    /**
     * [Exception] 모든 Exception 경우 발생
     * @param e Exception
     * @return ResponseEntity<ApiResponse>
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiResponse> handleExcetpion(Exception e) {
        log.error("handleException", e);
        final ApiResponse response = new ApiResponse(ApiCode.INTERNAL_SERVER_ERROR, e.getMessage());
        return new ResponseEntity<>(response, HTTP_STATUS_OK);
    }
}
