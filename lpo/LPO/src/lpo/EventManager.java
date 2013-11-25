package lpo;

import java.util.logging.Logger;

public class EventManager {
		
	private static final Logger log = Logger.getLogger(EventManager.class.getName());
	
	public static lpo.Event GetEvent(String key) {
		log.info("#### Get Event");
		return DataAccessManager.GetEvent(key);
	}
	
	public static void InsertEvent(lpo.Event event) {
		DataAccessManager.InsertEvent(event);
	}
	
}
