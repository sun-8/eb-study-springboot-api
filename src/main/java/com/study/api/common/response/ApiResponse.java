package com.study.api.common.response;

import com.study.api.common.code.ApiCode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ApiResponse {
    private int status; // 에러 상태 코드
    private String resultCode; // 에러 구분 코드
    private String resultMessage; // 에러 메시지
    private List<FieldErrorCustom> errors; // 상세 에러 메시지
    private String reason; // 에러 이유

    /**
     * 생성자 1
     * @param apiCode ApiCode
     */
    @Builder
    public ApiResponse(final ApiCode apiCode) {
        this.status = apiCode.getStatus();
        this.resultCode = apiCode.getCode();
        this.resultMessage = apiCode.getMessage();
        this.errors = new ArrayList<>();
    }

    /**
     * 생성자 2
     * @param apiCode ApiCode
     * @param reason String
     */
    @Builder
    public ApiResponse(final ApiCode apiCode, final String reason) {
        this.status = apiCode.getStatus();
        this.resultCode = apiCode.getCode();
        this.resultMessage = apiCode.getMessage();
        this.reason = reason;
    }

    /**
     * 생성자 3
     * @param apiCode ApiCode
     * @param bindingResult BindingResult
     */
    @Builder
    public ApiResponse(final ApiCode apiCode, final BindingResult bindingResult) {
        List<FieldErrorCustom> errors = createFieldErrors(bindingResult);

        this.status = apiCode.getStatus();
        this.resultCode = apiCode.getCode();
        this.resultMessage = apiCode.getMessage();
        this.errors = errors;
    }

    /**
     * 커스텀한 FieldError 리스트를 생성하는 메소드
     * @param bindingResult BindingResult
     * @return List<FieldErrorCustom>
     */
    private List<FieldErrorCustom> createFieldErrors(final BindingResult bindingResult) {
        final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        List<FieldErrorCustom> errors = new ArrayList<>();
        for(FieldError fieldError : fieldErrors) {
            errors.add(new FieldErrorCustom(fieldError.getField(),
                    fieldError.getRejectedValue() == null ? "" : fieldError.getRejectedValue().toString(),
                    fieldError.getDefaultMessage()));
        }
        return errors;
    }

    /**
     * FieldError 를 커스텀한 class
     */
    public record FieldErrorCustom(String field, String value, String message) {
    }
}
