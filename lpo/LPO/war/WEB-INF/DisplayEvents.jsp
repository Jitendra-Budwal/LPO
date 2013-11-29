<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>	
<%@ page import="java.util.*" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>All Events</title>
</head>
<body>
<%
	List<lpo.Event> events = (ArrayList<lpo.Event>)request.getAttribute("events");
	if (events.size() > 0) {
		
		for (lpo.Event event : events) {
%>
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
			System.out.println("lpo.UserManager.GetUser():"+lpo.UserManager.GetUser().getNickName());
			lpo.EventSubscription e = lpo.EventSubscriptionManager.GetEventSubscription(lpo.UserManager.GetUser().getEmailAddress(), event.getKey());
			if(e==null){System.out.println("ESub is null");}
			else{System.out.println("EventSubscription:"+e.getUserKey()+" : "+e.getEventKey());}
			
			if(lpo.EventSubscriptionManager.GetEventSubscription(lpo.UserManager.GetUser().getEmailAddress(), event.getKey())==null){
%>			
			<a href="ViewEvent?k=<%=event.getKey() %>">JOIN</a>
			<br/>
<% 
			} else{
%>			
			<a href="ViewEvent?k=<%=event.getKey() %>">VIEW</a>
			<br/>
<% 				
			}
		}
			 
		
	} else {
%>
		<p> No Events!</p>
<% 	
	} 
%>
</body>
</html>