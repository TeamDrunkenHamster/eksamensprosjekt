package logging;

import java.util.Collection;

import logic.ObserverSingleton;

public class Logger {

	public void log(String title, String message, ErrorTypes errorType) {
		
		LogObject logEntry = new LogObject();
		logEntry.setTitle(title);
		logEntry.setMessage(message);
		logEntry.setErrorType(errorType);
		LogContainer.instance().log(logEntry);
		
		// Add call to observer
		ObserverSingleton.instance().notifyObservers();
	}
	
	public Collection<LogObject> getLog() {
		
		return LogContainer.instance().getLog();
	}
	
	public void clearLog() {
		
	}
}
