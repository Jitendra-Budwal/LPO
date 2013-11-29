package lpo;

import java.util.ArrayList;

public class EventSubscription {
	
	private String key;
	private String userKey;
	private String eventKey;
	private ArrayList<String> listDayHour = new ArrayList<String>();
	private int[][] subSlots = new int[24][7];
	
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
	
	public void setSubSlots(int[][] newsub){
		this.subSlots=newsub;
	}
	
	public int[][] getSubSlots(){
		return subSlots;
	}
	
	public String subSlotsToString(){
		String subsString="";
		for(int i=0;i<24;i++){
			for(int j=0;j<7;j++){
				subsString=subsString+subSlots[i][j];
			}
		}
		return subsString;
	}
	
	public void stringToSubSlots(String subsString){
		for(int i=0;i<24;i++){
			for(int j=0;j<7;j++){
				subSlots[i][j]=new Integer(subsString.substring(i*7+j, i*7+j+1));
			}
		}
	}
	
	
	


}
