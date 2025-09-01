package vn.mes.config;

public class AppException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1897353982974334128L;
	private int status;

    public AppException(int status, String message) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
