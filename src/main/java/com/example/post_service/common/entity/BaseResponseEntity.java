package com.example.post_service.common.entity;

import com.example.post_service.common.response.BaseResponseStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Schema(description = "공통 API 응답 모델")
public record BaseResponseEntity<T>(
    @Schema(description = "HTTP 상태 코드", example = "200")
    HttpStatusCode httpStatus,
    
    @Schema(description = "요청 성공 여부", example = "true")
    Boolean isSuccess,
    
    @Schema(description = "응답 메시지", example = "요청에 성공하였습니다.")
    String message,
    
    @Schema(description = "응답 코드", example = "200")
    int code,
    
    @Schema(description = "응답 데이터")
    T result
) {

    /**
     * 필요값 : Http상태코드, 성공여부, 메시지, 에러코드, 결과값
     * 1. Return 객체가 필요한 경우 -> 성공
     * 2. Return 객체가 필요 없는 경우 -> 성공
     * 3. 요청에 실패한 경우
     */

    /**
     * 1. Return 객체가 필요한 경우 -> 성공
     * @param result
     */
    public BaseResponseEntity(T result) {
        this(HttpStatus.OK, true, "SUCCESS", 200, result);
    }

    /**
     * 2. Return 객체가 필요 없는 경우 -> 성공
     */
    public BaseResponseEntity() {
        this(HttpStatus.OK, true, "SUCCESS", 200, null);
    }

    /**
     * 3. 요청에 실패한 경우
     * @param status
     */
    public BaseResponseEntity(BaseResponseStatus status) {
        this(status.getHttpStatusCode(), status.isSuccess(), status.getMessage(), status.getCode(), null);
    }

    public BaseResponseEntity(BaseResponseStatus status, String message) {
        this(status.getHttpStatusCode(), status.isSuccess(), message, status.getCode(), null);
    }
}

