package lpo;

import java.util.*;

public class Event {
	
	private String key;
	private String name;
	private String description;
	private int minParticipants;
	private List<String> listInvitees = new ArrayList<String>();
	private Date createDate = new Date(); 
	
	public Event()
	{}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getMinParticipants() {
		return minParticipants;
	}

	public void setMinParticipants(int minParticipants) {
		this.minParticipants = minParticipants;
	}

	public List<String> getListInvitees() {
		return listInvitees;
	}

	public void setListInvitees(List<String> listInvitees) {
		this.listInvitees = listInvitees;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int[][] getSubSum(){
		HashSet<EventSubscription> eSubs = DataAccessManager.GetEventAllSubs(key);
		int subSum[][]= new int[24][7];
		if(eSubs.isEmpty()){return subSum;}
		
		Iterator<EventSubscription> iESubs = eSubs.iterator();
		int[][] eSubSlots = new int[24][7];
		while(iESubs.hasNext()){
			eSubSlots = iESubs.next().getSubSlots();
			for(int i=0;i<24;i++){
				for(int j=0;j<7;j++){
					subSum[i][j] += eSubSlots[i][j];
				}
			}
		}
		return subSum;
	}

}
