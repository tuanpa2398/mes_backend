package vn.wl.mes.config;

import javax.naming.ServiceUnavailableException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import jakarta.servlet.http.HttpServletRequest;
import vn.wl.mes.formbean.response.GlobalErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	// 404 Not Found
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<GlobalErrorResponse> handleNotFound(NoHandlerFoundException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GlobalErrorResponse(404, "Not Found", ex.getMessage(), request.getRequestURI())
        );
    }
    
    // 400 Bad Request (Validation)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .findFirst()
                .orElse(ex.getMessage());

        return ResponseEntity.badRequest().body(new GlobalErrorResponse(400, "Bad Request", errorMessage, request.getRequestURI()));
    }
    
    // 500 Internal Server Error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalErrorResponse> handleInternalError(Exception ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GlobalErrorResponse(500, "Internal Server Error", ex.getMessage(), request.getRequestURI())
        );
    }
    
    // Bắt Exception chung và trả về 503
    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<Object> handleServiceUnavailable(ServiceUnavailableException ex, HttpServletRequest request) {
    	return ResponseEntity.badRequest().body(new GlobalErrorResponse(503, "Service Unavailable", ex.getMessage(), request.getRequestURI()));
    }
    
    // Xử lý tất cả lỗi còn lại -> 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex, HttpServletRequest request) {
    	return ResponseEntity.badRequest().body(new GlobalErrorResponse(500, "Internal Server Error", ex.getMessage(), request.getRequestURI()));
    }
}
