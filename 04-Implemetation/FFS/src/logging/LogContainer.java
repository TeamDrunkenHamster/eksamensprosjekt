package logging;

import java.util.ArrayList;
import java.util.Collection;


public class LogContainer {

	private Collection<LogObject> log = new ArrayList<LogObject>();
	
	private static class Holder {
		// private class and no lazy initialization makes a singleton thread safe
		static final LogContainer instance = new LogContainer();
	};
	
	public static LogContainer instance() {
		
		return Holder.instance;
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
