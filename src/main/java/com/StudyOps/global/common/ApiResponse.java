package com.StudyOps.global.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonPropertyOrder({"isSuccess", "status", "message", "data"}) // 변수 순서를 지정
public class ApiResponse<T> {
    private boolean isSuccess;
    private int status;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public ApiResponse(ApiResponseStatus status) {
        this.isSuccess = status.isSuccess();
        this.status = status.getStatus();
        this.message = status.getMessage();
    }

    public ApiResponse(ApiResponseStatus status, T data) {
        this.isSuccess = status.isSuccess();
        this.status = status.getStatus();
        this.message = status.getMessage();
        this.data = data;
    }
    @JsonProperty("isSuccess") // 생성자에 변수 순서 지정
    public boolean isSuccess() {
        return isSuccess;
    }

    @JsonProperty("status") // 생성자에 변수 순서 지정
    public int getStatus() {
        return status;
    }

    @JsonProperty("message") // 생성자에 변수 순서 지정
    public String getMessage() {
        return message;
    }

    @JsonProperty("data") // 생성자에 변수 순서 지정
    public T getData() {
        return data;
    }
}
