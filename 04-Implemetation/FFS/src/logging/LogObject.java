package logging;

public class LogObject {

	private String title;
	private String message;
	private ErrorTypes errorType;
	
	public String getTitle() {
	
		return title;
	}
	
	public String getMessage() {
	
		return message;
	}
	
	public ErrorTypes getErrorType() {
	
		return errorType;
	}
	
	public void setTitle(String title) {
	
		this.title = title;
	}
	
	public void setMessage(String message) {
	
		this.message = message;
	}
	
	public void setErrorType(ErrorTypes error) {
	
		this.errorType = error;
	}
	
	
}
