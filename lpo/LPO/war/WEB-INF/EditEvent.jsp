<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%     UserService userService = UserServiceFactory.getUserService();%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit Event</title>
<link href="css/reset.css" rel="stylesheet" type='text/css'>
<link href="css/style.css" rel="stylesheet" type='text/css'>

</head>
<body>
<%
	lpo.Event event = (lpo.Event)request.getAttribute("event");
%>

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
  <div class="title"> Edit event: </div>
  <div class="eventBox">
	<form action="EditEvent?k=<%=event.getKey() %>" method="post" >
		<div class="label" value="<%=event.getName() %>">Event Name:</div>
		<input type="text" name="eventName" title= "Used in the subject line for email to invitees"/><br/>
		<div class="label" value="<%=event.getDescription() %>">Event Description:</div>
    <input type="text" name="description" title="include items such as: Location, expected duration, equipment needed, etc."/><br/>
		<div class="label">Mininum Participants:</div>
    <input type="text" name="minParticipants" value="<%=event.getMinParticipants() %>" title="Enter an integer value"/><br/>
		<button class="button" type="submit">Update Event</button>
	</form>
  </div>
</div>
</body>
</html>
