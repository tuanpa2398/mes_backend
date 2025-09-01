package vn.mes.config;


public class AppException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1897353982974334128L;
	private int status;
	private String error = "App Error Exception";

    public AppException(int status, String message, String error) {
        super(message);
        this.status = status;
        this.error = error;
    }
    
    public AppException(int status, String message) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
    
    public String getError() {
    	return error;
    }
}
