package com.StudyOps.global.common.exception;

import com.StudyOps.global.common.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomRuntimeException.class)
    public ResponseEntity<ApiResponse<Object>> handleException(CustomRuntimeException e){
        ApiResponse<Object> response = ApiResponse.builder()
                .isSuccess(false)
                .status(e.getStatus().value())
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(e.getStatus()).body(response);
    }

}
