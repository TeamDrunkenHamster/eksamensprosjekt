package logging;

import java.util.ArrayList;
import java.util.Collection;


public class LogContainer {

	private Collection<LogObject> log = new ArrayList<LogObject>();
	private static LogContainer instance = null;
	
	public static LogContainer instance() {
		if (instance == null)
			instance = new LogContainer();
		return instance;
	}
	
	private LogContainer() {
	}
	
	public void log(LogObject logEntry) {

		log.add(logEntry);
	}

	public Collection<LogObject> getLog() {
		
		return log;
	}

	public void clearLog() {

		log.clear();
	}
	
	

}
