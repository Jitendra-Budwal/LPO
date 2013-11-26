package lpo;

import java.util.ArrayList;

public class EventSubscription {
	
	private String key;
	private String userKey;
	private String eventKey;
	private ArrayList<String> listDayHour = new ArrayList<String>();
	
	public EventSubscription()
	{}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	public ArrayList<String> getListDayHour() {
		return listDayHour;
	}

	public void setListDayHour(ArrayList<String> listDayHour) {
		this.listDayHour = listDayHour;
	}


}
