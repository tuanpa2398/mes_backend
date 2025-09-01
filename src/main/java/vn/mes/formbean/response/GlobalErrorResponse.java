package vn.mes.formbean.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GlobalErrorResponse {
	private int status;
    private String error;
    private String message;
    private String path;
    private LocalDateTime timestamp;
    
    public GlobalErrorResponse(int status, String error, String message, String path) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }
}
