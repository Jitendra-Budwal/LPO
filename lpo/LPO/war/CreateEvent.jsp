<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Event</title>
</head>
<body>
	<form action="CreateEvent" method="get" >
		Name:<input type="text" name="eventName" title="Name for your Event should be unique.\n
			Used in teh subject line for email to invitees"/><br/>
		Description:<input type="text" name="description" title="include items such as: Location,
		 	expected duration, equipment needed, etc."/><br/>
		Min Participants:<input type="text" name="minParticipants" title="Enter an integer value"/><br/>
		Invitation List:<input type="text" name="invitationList" title="comma seperated list of email addresses.
			Used to send email inviting the recepient to use this web site."/><br/>
 		<button type="submit">Create Event & Send Email Invitation</button> 
		
	</form>
</body>
</html>
