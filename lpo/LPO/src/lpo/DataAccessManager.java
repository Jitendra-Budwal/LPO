package lpo;

import java.util.*;

import com.google.appengine.api.users.User;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import java.util.logging.Logger;

public class DataAccessManager {
	
	private static final Logger log = Logger.getLogger(DataAccessManager.class.getName());
	
	public static lpo.User GetUser(User gUser) {
		
		log.info("Get User");
		
		lpo.User user = new lpo.User();
						
		// try to retrieve data from database 
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Query.Filter filter = new Query.FilterPredicate("emailAddress", Query.FilterOperator.EQUAL, gUser.getEmail());
		Query q = new Query("User").setFilter(filter);
	    
//		List<Entity> listUsers = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(5));
//		log.info("COUNT OF USER RECORDS : " + listUsers.size());
//		
//		for (Entity ent : listUsers) {
//			user = new lpo.User();
//			log.info("ENTITY KEY : " + ent.getKind() + " KEY : " + ent.getKey() + " PARENT : " + ent.getParent());
//			user.setNickName((String)ent.getProperty("nickName"));
//			user.setEmailAddress((String)ent.getProperty("emailAddress"));
//		}
		
		Entity retrievedUser = datastore.prepare(q).asSingleEntity();
		
		if (retrievedUser == null)
		{
			log.info("NO USER IN DB");
			user.setEmailAddress(gUser.getEmail());
			user.setNickName(gUser.getNickname());
			
			InsertUser(user);
		}
		else {
			user.setEmailAddress((String)retrievedUser.getProperty("emailAddress"));
			user.setNickName((String)retrievedUser.getProperty("nickName"));
		}
		
		return user;
		
	}
	
	public static void InsertUser (lpo.User user) {
		
		log.info("PERSIST USER TO DB");
		
        //Key lpoKey = KeyFactory.createKey("LPOSystem", "LPOSystem");
        
        Entity newUser = new Entity("User");
        newUser.setProperty("nickName", user.getNickName());
        newUser.setProperty("emailAddress", user.getEmailAddress());

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(newUser);

        return;
	}

	public static void InsertEvent(lpo.Event event)
	{
		log.info("PERSIST EVENT TO DB");

		// store the event
        Entity newEvent = new Entity("Event");
        newEvent.setProperty("name", event.getName());
        newEvent.setProperty("description", event.getDescription());
        newEvent.setProperty("minParticipants", event.getMinParticipants());
        newEvent.setProperty("listInvitees", event.getListInvitees());
        newEvent.setProperty("createDate", event.getCreateDate());
        
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Key eventKey = datastore.put(newEvent);
        
        return;
	}
	
	public static lpo.Event GetEvent(String key)
	{
		log.info("##### GET EVENT : " + key);
		lpo.Event event = new lpo.Event();
		
		Key dbKey = KeyFactory.stringToKey(key);
		
						
		// try to retrieve data from database 
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Query.Filter filter = new Query.FilterPredicate(Entity.KEY_RESERVED_PROPERTY, FilterOperator.EQUAL, dbKey );
		Query q = new Query("Event").setFilter(filter);
	    		
		Entity dbEvent = datastore.prepare(q).asSingleEntity();
		
		if (dbEvent == null)
		{
			log.info("BAD EVENT KEY!");
		}
		else {
			event.setKey(KeyFactory.keyToString(dbEvent.getKey()));
			event.setName((String)dbEvent.getProperty("name"));
			event.setDescription((String)dbEvent.getProperty("description"));
			event.setMinParticipants((int)((long)dbEvent.getProperty("minParticipants")));
			event.setListInvitees((List<String>)dbEvent.getProperty("listInvitees"));
			event.setCreateDate((Date)dbEvent.getProperty("createDate"));
		}
		
		return event;
	}

	public static List<lpo.Event> GetEventList()
	{
		log.info("########## GET LIST OF ALL EVENTS");
		
		List<lpo.Event> events = new ArrayList<Event>();

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("Event"); //.addSort("createDate", Query.SortDirection.DESCENDING);
		List<Entity> eventEntities = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(5));
		
		for (Entity ent : eventEntities) {
			lpo.Event newEvent = new lpo.Event();
			newEvent.setKey(KeyFactory.keyToString(ent.getKey()));
			newEvent.setName((String)ent.getProperty("name"));
			newEvent.setDescription((String)ent.getProperty("description"));
			newEvent.setMinParticipants((int)((long)ent.getProperty("minParticipants")));
			newEvent.setCreateDate((Date)ent.getProperty("createDate"));
			newEvent.setListInvitees((List<String>)ent.getProperty("listInvitees"));
			
			events.add(newEvent);
		}
		
		return events;
		
	}
	
	public static lpo.EventSubscription GetEventSubscription(String userKey, String eventKey) 
	{
		lpo.EventSubscription eventSubscription = null;
		
		// try to retrieve data from database 
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Query.Filter filter1 = new Query.FilterPredicate("userKey", FilterOperator.EQUAL, userKey);
		Query.Filter filter2 = new Query.FilterPredicate("eventKey", FilterOperator.EQUAL, eventKey);
		Query q = new Query("EventSubscription").setFilter(filter1).setFilter(filter2);
	    		
		Entity ent = datastore.prepare(q).asSingleEntity();
		
		if (ent != null)
		{
			eventSubscription = new lpo.EventSubscription();
			eventSubscription.setKey(KeyFactory.keyToString(ent.getKey()));
			eventSubscription.setKey(KeyFactory.keyToString(ent.getKey()));
			eventSubscription.setUserKey((String)ent.getProperty("userKey"));
			eventSubscription.setEventKey((String)ent.getProperty("eventKey"));
			eventSubscription.setListDayHour((ArrayList<String>)ent.getProperty("listDayHour"));
		}
		
		return eventSubscription;
	}
	
	public static void InsertEventSubscription(lpo.EventSubscription eventSubscription) 
	{
		log.info("PERSIST EVENT SUBSCRIPTION TO DB");
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		// check if we have a key (it already exists)
		if (eventSubscription.getKey() != null)
		{
			try {
				Entity ent = datastore.get(KeyFactory.stringToKey(eventSubscription.getKey()));
				ent.setProperty("listDayHour", eventSubscription.getListDayHour());
				datastore.put(ent);
			} 
			catch (EntityNotFoundException e)
			{
				log.info(e.toString());
			}
		}
		else
		{
			Entity ent = new Entity("EventSubscription");
			ent.setProperty("userKey", eventSubscription.getUserKey());
			ent.setProperty("eventKey", eventSubscription.getEventKey());
			ent.setProperty("listDayHour", eventSubscription.getListDayHour());
			
			datastore.put(ent);
		}
        
        return;
	}
}
