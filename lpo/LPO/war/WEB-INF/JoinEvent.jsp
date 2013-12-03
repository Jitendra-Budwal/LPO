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
%>

<form action="JoinEvent?k=<%=event.getKey() %>" method="post" >
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

		for (String timeslot : eventSubscription.getListDayHour())
		{
%>	
			<%=timeslot %><br/>
<% 
		}
	}
%>
	<br/><br/>
	Day Of Week:<input type="text" name="dayofweek"/></br>
	Hour of Day (24H):<input type="text" name="hour"/><br/>
	<button type="submit">ADD</button>		

	<br/>
</form>		
</body>
</html>