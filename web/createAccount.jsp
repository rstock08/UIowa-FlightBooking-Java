<%--
  Created by IntelliJ IDEA.
  User: ReedS
  Date: 3/25/2017
  Time: 8:37 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%
    String logPage, logSet, accountText, paymentPage, activePage, historyPage;

    if (session.getAttribute("userEmail") != null) {
        accountText = session.getAttribute("userEmail").toString();
        logPage = "logout.jsp";
        logSet = "Log Out";
        paymentPage = "payment.jsp";
        activePage = "activeFlights.jsp";
        historyPage = "flightHistory.jsp";
    } else {
        accountText = "My Account";
        logPage = "login.jsp";
        logSet = "Log In";
        paymentPage = "login.jsp";
        activePage = "login.jsp";
        historyPage = "login.jsp";
    }

%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Iowa Air</title>
    <link rel="stylesheet" href="css/reset.css">
    <link rel="stylesheet" href="css/normalize.css">
    <link rel="stylesheet" href="css/main.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

</head>

<body onload=document.getElementById("loginForm").reset()>

<header>
    <a href="index.jsp" id="logo">
        <h1>Iowa Air</h1>
        <h2>Travel Like A Hawkeye</h2>
    </a>
    <nav>
        <ul>
            <li>
                <div class="account-dropdown">
                    <a href="index.jsp">
                        <button class="account-dropbutton">Search</button>
                    </a>
                </div>
            </li>

            <li>
                <div class="account-dropdown">
                    <a href="contact.jsp">
                        <button class="account-dropbutton">Contact Us</button>
                    </a>
                </div>
            </li>

            <li>
                <div class="account-dropdown">
                    <a href="myAccount.jsp">
                        <button class="account-dropbutton"><%=accountText%>
                        </button>
                    </a>
                    <div class="account-dropdown-content">
                        <a href="<%=logPage%>"><%=logSet%>
                        </a>
                        <a href="<%=activePage%>">Active Flights</a>
                        <a href="<%=historyPage%>">Flight History</a>
                        <a href="<%=paymentPage%>">Payment Info</a>
                    </div>
                </div>
            </li>
        </ul>
    </nav>
</header>


<div id="viewwrapper">

    <section id="sidebar" class="login">
        <p>
            My Account
        </p>
        <p>
            Active Flights
        </p>
        <p>
            Flight History
        </p>
        <p>
            Payment Info
        </p>
    </section>

    <section id="main">
        <form action="General.CreateAccount">
            Email: <input type="text" name="userEmail" maxlength="40" align="middle" placeholder="Email" required><br>
            Password: <input type="password" name="userPassword" maxlength="16" align="middle" placeholder="Password"
                             required><br>
            Confirm Password: <input type="password" name="confirmUserPassword" maxlength="16" align="middle">
            <input type="submit" value="Create Account">
        </form>
    </section>

</div>

<footer>

</footer>

</body>

</html>
<!---
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>General.Login</title>
</head>
<style>
body {
margin:0;
background: url("img/airplane.jpg");
background-size: 100% 170%;
background-repeat: no-repeat;
}
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

<ul>
<li><a href="home.jsp">Home</a></li>
<li><a href="#news">News</a></li>
<li><a href="#contact">Contact</a></li>
</ul>

<div
style="padding:20px;
margin-top:30px;
height:20px;
width:0%;">
</div>


<body style = "text-align:center;">

<form action="General.CreateAccount">
<br>
<br>
<br>
<br>
User email:
<br>
<input type="text" name="userEmail" maxlength="40" align="middle" placeholder="Email" required>
<br>
User password:
<br>
<input type="password" name="userPassword" maxlength="10" align="middle" placeholder="Password" required>
<br>
Confirm user password:
<br>
<input type="password" name="userPassword" maxlength="10" align="middle">
<br>
<br>
<input type="button" value="Create Account">
</form>

</body>

</body>
</html>
-->
