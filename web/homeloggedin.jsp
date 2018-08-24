<%--
  Created by IntelliJ IDEA.
  User: ReedS
  Date: 3/25/2017
  Time: 8:37 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Home</title>
    <style>
        body {margin:0;}
        ul {
            list-style-type: none;
            margin: 0;
            padding: 0;
            overflow: hidden;
            background-color: #333;
            position: fixed;
            top: 0;
            width: 100%;
        }
        li {
            float: left;
        }
        li a {
            display: block;
            color: White;
            text-align: center;
            padding: 14px 16px;
            text-decoration: none;
        }
        li a:hover:not(.active) {
            background-color: #111;
        }
        .active {
            background-color: #ffe11e;
        }
    </style>
</head>
<body>

background-image: URL("airplane.png");
background-repeat: no-repeat;

<ul>
    <li><a href="index.jsp">Home</a></li>
    <li><a href="contact.jsp">Contact</a></li>
    <li style="float:right"><a class="active" href="index.jsp">Logout</a></li>
</ul>

<div style="padding:20px;margin-top:30px;height:1500px;">
    <h1>Flights</h1>

    <p>List of flights will go here...</p>

</div>

</body>
</html>
