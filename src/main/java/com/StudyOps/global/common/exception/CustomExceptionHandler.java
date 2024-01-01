package com.StudyOps.global.common.exception;

import com.StudyOps.global.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomRuntimeException.class)
    public ResponseEntity<ApiResponse<Object>> handleCustomRuntimeException(CustomRuntimeException e){
        ApiResponse<Object> response = ApiResponse.builder()
                .isSuccess(false)
                .status(e.getStatus().value())
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(e.getStatus()).body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Object>> handleBadCredentialsException(){
        ApiResponse<Object> response = ApiResponse.builder()
                .isSuccess(false)
                .status(400)
                .message("아이디 혹은 패스워드가 틀렸습니다.")
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
