<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Welcome to LPO</title>
    <link href="css/reset.css" rel="stylesheet" type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Loved+by+the+King' rel='stylesheet' type='text/css'>
    <link href="css/style.css" rel="stylesheet" type='text/css'>
    <link href="css/zocial.css" rel="stylesheet" type='text/css'>
</head>

<body>
<% 
	String parm = "";
	if (request.getParameter("k") != null && !request.getParameter("k").isEmpty())
		parm = "?k="+request.getParameter("k");
%>
	<div id="welcome_logo">Let's Play Out!</div>
  <div id="welcome_body">
    <div class="left"> 
    <p>
    Welcome! The LPO system is used to connect participants to events. 
    </p>
    <br />
    <p>
    In order to use the system, you must have a valid Google Account. 
    No personal information will be available to the system!
    </p>
    </div>
    <div class="divider"></div>
    <div class="right">
      <form action="Authorization" method="post" >
      <button type="submit" class="zocial googleplus">Sign in with Google</button>
      </form>
    </div> 
  </div>
</body>
</html>
