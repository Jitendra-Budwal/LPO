<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>	
<%@ page import="java.util.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Join Event</title>
</head>
<body>
<%
	lpo.Event event = (lpo.Event)request.getAttribute("event");
	int subSlots[][] = new int[24][7];
%>

<form action="ViewEvent?k=<%=event.getKey() %>" method="post" >
	Event Name: <%=event.getName() %><br/>
	Description: <%=event.getDescription() %><br/>
	Minimum Participants: <%=event.getMinParticipants() %><br/>
	Date Created: <%=event.getCreateDate() %><br/>
<% 
	for (String person : event.getListInvitees()) {
%>
		- <%=person %><br/>				
<%				
	}
%>	
	<br/>
	CURRENTLY BOOKED TIME SLOTS :
<% 
	lpo.EventSubscription eventSubscription = (lpo.EventSubscription)request.getAttribute("eventSubscription");
	
	if (eventSubscription == null) {
%>
	- no time slots booked yet!
<% 
	} else {
		subSlots = eventSubscription.getSubSlots();
		System.out.println("Succesfully retrieved subSlots:");
		System.out.println();
		
	}
%>
	<br/><br/>
	Day Of Week:<input type="text" name="dayofweek"/></br>
	Hour of Day (24H):<input type="text" name="hour"/><br/>
	<button type="submit">SUBMIT</button>		
	<br/>
	<table border="1">
	<% 
	
	
	String[] daysofweek = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
	String val;
	String timeslot;
	%><tr><th>Time Slot</th><th>Sunday</th><th>Monday</th><th>Tuesday</th><th>Wednesday</th><th>Thursday</th><th>Friday</th><th>Saturday</th></tr><%
	System.out.println("About to populate table");
	for(int i =0;i<24;i++){
		timeslot = Integer.toString(i)+":00 - "+Integer.toString(i+1)+":00";
		%><tr><td><%=timeslot%></td><%
		for(int j=0;j<7;j++){ 
			val = Integer.toString(i) + "," + Integer.toString(j);
			System.out.println("checking ("+i+","+j+"): "+subSlots[i][j]);
		%>
			<td> <input type="checkbox" name="<%=val%>" <%if(subSlots[i][j]==1){%>checked="checked"<%}%>></td>
		<%
		}
		%></tr><%
	}
	%>
	</table>
	
		
	<br>
	
	<br/>
</form>		
</body>
</html>