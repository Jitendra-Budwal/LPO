<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>	
<%@ page import="java.util.*" %>

<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%     UserService userService = UserServiceFactory.getUserService();%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>All Events</title>
<link href="../../css/reset.css" rel="stylesheet" type='text/css'>
<link href="../../css/style.css" rel="stylesheet" type='text/css'>
</head>
<body>
<div id="nav">
  <ul>
    <li><a href="/Menu" >LPO</a></li> <!-- TO DO: Change into LPO logo -->
    <li><a href="/CreateEvent">Create Event</a></li>
    <li><a href="/SubscribedEvents">My Events</a></li>
    <li><a href="/ViewAllEvents">All Events</a></li>
  </ul>

  <div class="logout">
    <a href="<%=userService.createLogoutURL("/Menu") %>">Logout</a>
  </div>
</div>

<div class="layoutBox">
<div class="title"> View events: </div>
<%
	List<lpo.Event> events = (ArrayList<lpo.Event>)request.getAttribute("events");
	if (events.size() > 0) {
		
		for (lpo.Event event : events) {
%>    <div class="eventBox">
			<div class="label">Event Name: </div><%=event.getName() %><br/>
			<div class="label">Event Description:</div> <%=event.getDescription() %><br/>
			<div class="label">Minimum Participants:</div> <%=event.getMinParticipants() %><br/>
			<div class="label">Date Created:</div> <%=event.getCreateDate() %><br/>
			<div class="label">Invited Members:</div><ul>
<% 			
			for (String person : event.getListInvitees()) {
%>
				<li><%=person %></li>				
<%				
			}
			%>
		</ul>
		<%
			System.out.println("lpo.UserManager.GetUser():"+lpo.UserManager.GetUser().getNickName());
			lpo.EventSubscription e = lpo.EventSubscriptionManager.GetEventSubscription(lpo.UserManager.GetUser().getEmailAddress(), event.getKey());
			if(e==null){System.out.println("ESub is null");}
			else{System.out.println("EventSubscription:"+e.getUserKey()+" : "+e.getEventKey());}
			
			if(lpo.EventSubscriptionManager.GetEventSubscription(lpo.UserManager.GetUser().getEmailAddress(), event.getKey())==null){
%>			
			<a class="button" href="ViewEvent?k=<%=event.getKey() %>">JOIN</a>
<% 
			} else{
%>			
			<a class="button" href="ViewEvent?k=<%=event.getKey() %>">VIEW</a>
<%
			}
			%>
		</div>
<%      
		}
	} else {
%>
    <div class="eventBox">
		<center> There are no events to view.</center>
    </div>
<% 	
	} 
%>
</div>
</body>
</html>
