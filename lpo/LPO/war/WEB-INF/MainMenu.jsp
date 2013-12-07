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
<link href="/css/reset.css" rel="stylesheet" type='text/css'>
<link href="/css/style.css" rel="stylesheet" type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Loved+by+the+King' rel='stylesheet' type='text/css'>
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

<div id="welcome_logo">Let's Play Out!</div>
<div id="main_menu">
	<a class="img_box" style="margin-left: 120px" href="/CreateEvent">
	<img src="/img/cake.png">Create Event
	</a>
	<a class="img_box" style="margin-left: 395px" href="/SubscribedEvents">
	<img src="/img/my.png">View My Events
	</a>
	<a class="img_box" style="margin-left: 670px" href="/ViewAllEvents">
	<img src="/img/all.png">View All Events
	</a>
</div>



</body>
</html>
