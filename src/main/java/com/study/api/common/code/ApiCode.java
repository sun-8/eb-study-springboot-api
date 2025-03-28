package com.study.api.common.code;

import lombok.Getter;

/**
 * [공통코드] api 통신에 대한 '결과 코드'를 Enum 형태로 관리.
 *
 */
@Getter
public enum ApiCode {

    /* --- SUCCESS CODE --------------------------------------------*/
    SELECT_SUCCESS(200, "200", "SELECT SUCCESS"),
    INSERT_SUCCESS(201, "200", "INSERT SUCCESS"),
    UPDATE_SUCCESS(204, "200", "UPDATE SUCCESS"),
    DELETE_SUCCESS(204, "200", "DELETE SUCCESS"),

    /* --- GLOBAL ERROR CODE ---------------------------------------
     * HTTP Status Code
     * 400 Bad Request : 잘못된 요청
     * 401 Unauthorized : 인증 필요 (로그인 필요)
     * 403 Forbidden : 접근 금지
     * 404 Not Found : 요청한 리소스를 찾을 수 없음
     * 405 Method Not Allowed : 요청 메서드가 허용되지 않음
     * 408 Request Timeout : 요청 시간이 초과됨
     * 409 Conflict : 요청 충돌 (예: 중복 데이터)
     * 413 Payload Too Large : 요청 데이터가 너무 큼
     * 415 Unsupported Media Type : 지원되지 않는 미디어 타입
     *
     * 500 Internal Server Error : 서버 내부 오류
     * 501 Not Implemented : 요청 메서드가 지원되지 않음
     * 502 Bad Gateway : 게이트웨이 서버가 잘못된 응답을 받음
     * 503 Service Unavailable : 서버가 일시적으로 사용 불가
     * 504 Gateway Timeout : 게이트웨이 서버의 응답 시간 초과
     * */
    BAD_REQUEST(400, "4000", "잘못된 요청입니다."),
    INVALID_TYPE_VALUE(400, "4001", "유효하지 않은 값입니다."),
    VALIDATION_FAILED(400, "4002", "유효성 검증에 실패했습니다."),
    UNAUTHORIZED(401, "4100", "인증이 만료되었습니다."),
    TOKEN_EXPIRED(401, "4101", "토큰이 만료되었습니다."),
    ACCESS_DENIED(403, "4300", "접근이 거부되었습니다."),
    NOT_FOUND_BOARD(404, "4400", "게시글을 찾을 수 없습니다."),
    NOT_FOUND_COMMENT(404, "4401", "댓글을 찾을 수 없습니다."),
    NOT_FOUND_FILE(404, "4402", "파일을 찾을 수 없습니다."),
    NULL_POINT_ERROR(404, "4403", "Null point Exception."),
    INTERNAL_SERVER_ERROR(500, "5000", "서버에 오류가 발생했습니다."),
    DATABASE_ERROR(500, "5001", "데이터 작업에 실패했습니다."),
    ;

    private final int status;

    private final String code;

    private final String message;

    ApiCode(final int status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
