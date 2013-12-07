<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>	
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%     UserService userService = UserServiceFactory.getUserService();%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="../../css/reset.css" rel="stylesheet" type='text/css'>
<link href="../../css/style.css" rel="stylesheet" type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Loved+by+the+King' rel='stylesheet' type='text/css'>
<title>Join Event</title>
</head>
<body>
<%
	lpo.Event event = (lpo.Event)request.getAttribute("event");
	int subSlots[][] = new int[24][7];
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

<div class="title"> Event <%=event.getName() %> </div>
<div class="eventBox" >
<form action="ViewEvent?k=<%=event.getKey() %>" method="post" >

	<div class="label">Event Name:</div> <%=event.getName() %><br/>
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
<%	int[][] subSum = event.getSubSum(); 
	//int curDay = event.getCurDay();
	//int curTime = event.getCurTime();
	GregorianCalendar calcDate = event.getCalcDate();
	int cDay = calcDate.get(GregorianCalendar.DAY_OF_WEEK)-1;


%>	
	
	
	<div class="label">Current Week Status:</div>
  <button title="Click to show/hide content" type="button" onclick="if(document.getElementById('mtxStatus') .style.display=='none') {document.getElementById('mtxStatus') .style.display=''}else{document.getElementById('mtxStatus') .style.display='none'}">Show/hide</button>

	<div id="mtxStatus" style="display:none">
	<table class="calendar" border="1">
	<% 
	String[] daysofweek = {"Sun","Mon","Tues","Wed","Thurs","Fri","Sat"};
	String timeslot;
	String cellColor;
	int eTime = 25;
	int eDay =8;
	
	SimpleDateFormat MMMdd = new SimpleDateFormat("MMM d");
	GregorianCalendar temp = calcDate;
	temp.add(GregorianCalendar.DAY_OF_MONTH,-1);
	%><tr><th></th><%for(int j=0;j<7;j++){
		
		temp.add(GregorianCalendar.DAY_OF_MONTH,1);
		String date = MMMdd.format(temp.getTime());
		
		%><th><%= date%></th><% 
	}
	%></tr><%
	
	%><tr><th></th><%for(int j=0;j<7;j++){
		%><th><%=daysofweek[(j+cDay)%7] %></th><% 
	}
	%></tr><%
	
	for(int i =0;i<24;i++){
		timeslot = Integer.toString(i)+":00 - "+Integer.toString(i+1)+":00";
		%><tr><td class="timeslot"><%=timeslot%></td><%
		for(int j=0;j<7;j++){ 
			if(subSum[i][(j+cDay)%7]>=event.getMinParticipants()){
				if((j+cDay)%7<eDay||(i<eTime&&(j+cDay)%7<=eDay)){
					eTime=i;
					eDay=(j+cDay)%7;					
				}
				cellColor = "61F361";
			}else{cellColor = "FFCCCA";}
		%>
			<td style="background-color:#<%=cellColor %>"> <%=subSum[i][(j+cDay)%7] %></td>
		<%
		}
		%></tr><%
	}
	%>
	</table>
	</div>
	

	<br>
	<br>
	
	<%if(eTime<25){
		%><div class="label">Next Occurance:</div> <%=daysofweek[eDay]+" , "+Integer.toString(eTime)+":00 - "+Integer.toString(eTime+1)+":00" %><br><br><br><%
		
	}else{
		%><div class="label">Next Occurance:</div> There is currently not enough interest for this event to occur.</br></br></br><%
	}
	
		%>

	
	
	
	
	
	
<% 
	lpo.EventSubscription eventSubscription = (lpo.EventSubscription)request.getAttribute("eventSubscription");
	lpo.User user = lpo.UserManager.GetUser();
	if (eventSubscription == null) {
%>
	Hi <%=user.getNickName() %>, </br>
	<br>
	You do not appear to be registered for this event. If you find this event interesting and would like
	to subscribe to it, please fill out your availabilities below and press 'Submit'.
<% 
	} else {
		
%>		
		Welcome back <%=user.getNickName() %>, </br>
		<br>
		You are currently subscribed to this event, with your availabilities as below. If this availability information has changed,
		please make the required changes and press 'Submit'.
		<br>
		<br>
		<div class="label">Your Currently Available Time Slots:</div>
<% 
				
		subSlots = eventSubscription.getSubSlots();
		
		
	}
%>
	<br/>
	
	<table class="calendar" border="1">
	<% 
	String val;
	%><tr><th></th><th>Sun</th><th>Mon</th><th>Tues</th><th>Wed</th><th>Thurs</th><th>Fri</th><th>Saturday</th></tr><%
	System.out.println("About to populate table");
	for(int i =0;i<24;i++){
		timeslot = Integer.toString(i)+":00 - "+Integer.toString(i+1)+":00";
		%><tr><td class="timeslot"><%=timeslot%></td><%
		for(int j=0;j<7;j++){ 
			val = Integer.toString(i) + "," + Integer.toString(j);
			//System.out.println("checking ("+i+","+j+"): "+subSlots[i][j]);
		%>
			<td> <input type="checkbox" name="<%=val%>" <%if(subSlots[i][j]==1){%>checked="checked"<%}%>></td>
		<%
		}
		%></tr><%
	}
	%>
	</table>
	
		
	<br><center>
	<button type="submit">Submit Time Slot</button>	
	</center><br/>
</form>		
</body>
</html>

