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
		
		// don't bother to save the user... as everything needed
		// resides in the google user class, and it has to be
		// present for the system to work.
		
		user.setEmailAddress(gUser.getEmail());
		user.setNickName(gUser.getNickname());
		
		return user;
		

/*
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
*/		
	}
	
//	public static void InsertUser (lpo.User user) {
//		
//		log.info("PERSIST USER TO DB");
//		
//        //Key lpoKey = KeyFactory.createKey("LPOSystem", "LPOSystem");
//        
//        Entity newUser = new Entity("User");
//        newUser.setProperty("nickName", user.getNickName());
//        newUser.setProperty("emailAddress", user.getEmailAddress());
//
//        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
//        datastore.put(newUser);
//
//        return;
//	}

	public static String InsertEvent(lpo.Event event)
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
        
        return KeyFactory.keyToString(eventKey);
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
	
	
	public static List<lpo.Event> GetEventList(String userKey)
	{
		log.info("########## GET LIST OF "+userKey+"'S EVENTS");
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Query.Filter filter2 = new Query.FilterPredicate("userKey", FilterOperator.EQUAL, userKey);
		Query q = new Query("EventSubscription").setFilter(filter2);
	    		
		Iterator<Entity> eEnts = datastore.prepare(q).asIterator();
		
		ArrayList<String> eKeys = new ArrayList<String>();
		
		while(eEnts.hasNext()){
			Entity ent = eEnts.next();
			eKeys.add((String)ent.getProperty("eventKey"));
		}
			
		List<lpo.Event> events = new ArrayList<Event>();

		for (String eKey : eKeys) {
			events.add(GetEvent(eKey));
		}
		
		return events;		
	}
	
	
	public static lpo.EventSubscription GetEventSubscription(String userKey, String eventKey) 
	{
		lpo.EventSubscription eventSubscription = null;
		
		// try to retrieve data from database 
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		System.out.println("Filter 1:"+userKey+"    Filter 2:"+eventKey);
		Query.Filter filter1 = new Query.FilterPredicate("userKey", FilterOperator.EQUAL, userKey);
		Query.Filter filter2 = new Query.FilterPredicate("eventKey", FilterOperator.EQUAL, eventKey);
		Query.Filter filterc = new Query.CompositeFilter(Query.CompositeFilterOperator.AND,Arrays.<Query.Filter>asList(filter1,filter2) );
		Query q = new Query("EventSubscription").setFilter(filterc);
		
	    		
		Entity ent = datastore.prepare(q).asSingleEntity();
		
		if (ent != null)
		{
			eventSubscription = new lpo.EventSubscription();
			eventSubscription.setKey(KeyFactory.keyToString(ent.getKey()));
			eventSubscription.setUserKey((String)ent.getProperty("userKey"));
			eventSubscription.setEventKey((String)ent.getProperty("eventKey"));
			eventSubscription.stringToSubSlots((String)ent.getProperty("subSlots"));
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
				ent.setProperty("subSlots", eventSubscription.subSlotsToString());
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
			ent.setProperty("subSlots", eventSubscription.subSlotsToString());
			
			datastore.put(ent);
		}
        
        return;
	}
	
	public static HashSet<lpo.EventSubscription> GetEventAllSubs(String eventKey) 
	{
		lpo.EventSubscription eventSubscription = null;
		
		// try to retrieve data from database 
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Query.Filter filter2 = new Query.FilterPredicate("eventKey", FilterOperator.EQUAL, eventKey);
		Query q = new Query("EventSubscription").setFilter(filter2);
	    		
		Iterator<Entity> eEnts = datastore.prepare(q).asIterator();
		
		HashSet<EventSubscription> eSubs = new HashSet<EventSubscription>();
		
		while(eEnts.hasNext()){
			Entity ent = eEnts.next();
			eventSubscription = new lpo.EventSubscription();
			eventSubscription.setKey(KeyFactory.keyToString(ent.getKey()));
			eventSubscription.setUserKey((String)ent.getProperty("userKey"));
			eventSubscription.setEventKey((String)ent.getProperty("eventKey"));
			eventSubscription.stringToSubSlots((String)ent.getProperty("subSlots"));
			eSubs.add(eventSubscription);
		}
		
		return eSubs;
	}
	
	
}
