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
		Name:<input type="text" name="eventName"/><br/>
		Description:<input type="text" name="description"/><br/>
		Min Participants:<input type="text" name="minParticipants"/><br/>
		Invitation List:<input type="text" name="invitationList"/><br/>
		<button type="submit">Create Event</button>
<!-- 		<button type="submit">Create Event & Send Email Invitation</button>  -->
		
	</form>
</body>
</html>