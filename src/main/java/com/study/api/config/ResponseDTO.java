package com.study.api.config;

import lombok.Data;

/**
 * 공통 응답 form
 *
 * code: 0000
 * message: -
 * data: []
 *
 * code: 9999
 * message: 처리 중 오류가 발생했습니다.
 *
 * @param <T>
 */
@Data
public class ResponseDTO<T> {
    private String responseCode;
    private String responseMessage;
    private T responseData;
}
