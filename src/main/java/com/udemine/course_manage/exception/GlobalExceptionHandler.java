package com.udemine.course_manage.exception;

import com.udemine.course_manage.dto.request.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse>handlingRuntimeException(Exception exception){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
        exception.printStackTrace(); // log ra console
        return ResponseEntity.badRequest().body(apiResponse);
    }
//    @ExceptionHandler(value = RuntimeException.class)
//    ResponseEntity<String>handlingRuntimeException(RuntimeException exception){
//        return ResponseEntity.badRequest().body(exception.getMessage());
//    }
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse>handlingRuntimeException(AppException exception){
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handlingRuntimeException(MethodArgumentNotValidException exception) {
        // Get the default error message from the exception
        String errorMessage = exception.getFieldError() != null
                ? exception.getFieldError().getDefaultMessage()
                : "Invalid input data";

        // Create ApiResponse with a default or specific error code
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(errorMessage); // Use the system's error message

        return ResponseEntity.badRequest().body(apiResponse);
    }
}
