package lpo;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.EntityNotFoundException;

public class EventSubscriptionManager {
		
	private static final Logger log = Logger.getLogger(EventSubscriptionManager.class.getName());
	
	public static void InsertEventSubscription(String userKey, String eventKey, ArrayList<String> listDayHour) {
		
		// see if we  have one first
		lpo.EventSubscription eventSubscription = DataAccessManager.GetEventSubscription(userKey, eventKey);

		// no - create new 
		if (eventSubscription == null) {
			eventSubscription = new lpo.EventSubscription();
			eventSubscription.setUserKey(userKey);
			eventSubscription.setEventKey(eventKey);
			eventSubscription.setListDayHour(listDayHour);
		}
		// yes - modify the list
		else {
			eventSubscription.setListDayHour(listDayHour);
		}
		
		DataAccessManager.InsertEventSubscription(eventSubscription);
	}
	
	public static lpo.EventSubscription GetEventSubscription(String userKey, String eventKey)
	{
		return DataAccessManager.GetEventSubscription(userKey, eventKey);
	}
}
