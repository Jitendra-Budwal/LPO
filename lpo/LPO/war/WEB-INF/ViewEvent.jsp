<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>	
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>

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
	<br>
	Invited Members:<br/>
	
<% 
	for (String person : event.getListInvitees()) {
%>
		- <%=person %><br/>				
<%				
	}
%>	
	<br/>
<%	int[][] subSum = event.getSubSum(); 
	//int curDay = event.getCurDay();
	//int curTime = event.getCurTime();
	GregorianCalendar calcDate = event.getCalcDate();
	int cDay = calcDate.get(GregorianCalendar.DAY_OF_WEEK)-1;


%>	
	
	
	CURRENT STATUS FOR WEEK:<br>
	<hr>
	<div id="mtxStatus" style="display:none">
	<table border="1">
	<% 
	String[] daysofweek = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
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
	
	%><tr><th>Time Slot</th><%for(int j=0;j<7;j++){
		%><th><%=daysofweek[(j+cDay)%7] %></th><% 
	}
	%></tr><%
	
	for(int i =0;i<24;i++){
		timeslot = Integer.toString(i)+":00 - "+Integer.toString(i+1)+":00";
		%><tr><td><%=timeslot%></td><%
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
	
	<button title="Click to show/hide content" type="button" onclick="if(document.getElementById('mtxStatus') .style.display=='none') {document.getElementById('mtxStatus') .style.display=''}else{document.getElementById('mtxStatus') .style.display='none'}">Show/hide</button>
	<hr>
	<br>
	<br>
	
	<%if(eTime<25){
		%>NEXT TIME THIS EVENT WILL OCCUR: <%=daysofweek[eDay]+" , "+Integer.toString(eTime)+":00 - "+Integer.toString(eTime+1)+":00" %><br><br><br><%
		
	}else{
		%>There is currently not enough interest for this event to occur.</br></br></br><%
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
		YOUR CURRENTLY AVAILABLE TIME SLOTS :
<% 
				
		subSlots = eventSubscription.getSubSlots();
		
		
	}
%>
	<br/><br/>
	
	<table border="1">
	<% 
	String val;
	%><tr><th>Time Slot</th><th>Sunday</th><th>Monday</th><th>Tuesday</th><th>Wednesday</th><th>Thursday</th><th>Friday</th><th>Saturday</th></tr><%
	System.out.println("About to populate table");
	for(int i =0;i<24;i++){
		timeslot = Integer.toString(i)+":00 - "+Integer.toString(i+1)+":00";
		%><tr><td><%=timeslot%></td><%
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
	
		
	<br>
	<button type="submit">SUBMIT</button>	
	<br/>
</form>		
</body>
</html>