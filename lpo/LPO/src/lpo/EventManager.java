package lpo;

import java.util.logging.Logger;
import java.util.*;

public class EventManager {
		
	private static final Logger log = Logger.getLogger(EventManager.class.getName());
	
	public static lpo.Event GetEvent(String key) {
		log.info("#### Get Event");
		return DataAccessManager.GetEvent(key);
	}
	
	public static void InsertEvent(lpo.Event event) {
		DataAccessManager.InsertEvent(event);
	}
	
	public static Map<String, List<String>> CheckEventFulfillment(lpo.Event event) {
		
		String[] daysofweek = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		
		boolean filled = false;
		int requiredPeople = event.getMinParticipants();
		int fillDay = 0;
		int fillTime = 0;
		
		HashSet<EventSubscription> eSubs = DataAccessManager.GetEventAllSubs(event.getKey());
		int subSum[][]= new int[24][7];
		
		if(eSubs.isEmpty()) {
			return map;
		}
		
		Iterator<EventSubscription> iESubs = eSubs.iterator();
		int[][] eSubSlots = new int[24][7];
		while(iESubs.hasNext()){
			if (filled)
				break;
			eSubSlots = iESubs.next().getSubSlots();
			for(int i=0;i<24;i++){
				if (filled)
					break;
				for(int j=0;j<7;j++){
					subSum[i][j] += eSubSlots[i][j];
					if (subSum[i][j] == requiredPeople){
						filled = true;
						fillDay = j;
						fillTime = i;
						break;
						
					}
				}
			}
		}
		
		// if filled, return the scheduled earliest time and the subscribers list
		if (filled) {
			String mapKey = daysofweek[fillDay] + " " + Integer.toString(fillTime) + ":00-" + Integer.toString(fillTime+1) + ":00";
			map.put(mapKey, new ArrayList<String>());
								
			iESubs = eSubs.iterator();
			while(iESubs.hasNext()) {
				lpo.EventSubscription e = iESubs.next();
				eSubSlots = e.getSubSlots();
				if (eSubSlots[fillTime][fillDay] == 1) {
					map.get(mapKey).add(e.getUserKey());
				}
			}
		}
		return map;
	}
	
}
