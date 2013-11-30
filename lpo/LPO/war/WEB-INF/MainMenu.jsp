<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%     UserService userService = UserServiceFactory.getUserService();%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<a href="/CreateEvent">Create Event</a>
<br/>
<a href="/SubscribedEvents">View My Events</a>
<br/>
<a href="/ViewAllEvents">View All Events</a>
<br>
<a href="<%=userService.createLogoutURL("/Menu") %>">Logout</a>
</body>
</html>