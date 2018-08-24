<%--
  Created by IntelliJ IDEA.
  User: ReedS
  Date: 4/30/2017
  Time: 1:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String accountText = "" ;
    if(session.getAttribute("role").toString().equals("MANAGER")){
        accountText = session.getAttribute("userEmail").toString();
    } else {
        response.sendRedirect("logout.jsp");
    }
%>

<html>
<head>
    <link rel="stylesheet" href="css/reset.css">
    <link rel="stylesheet" href="css/normalize.css">
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/manager.css">
    <title>User Lookup</title>
</head>
<header>

    <a href="index.jsp" id="logo">
        <h1>Iowa Air</h1>
        <h2>Travel Like A Hawkeye</h2>
    </a>
    <a href="index.jsp" id="logo">
        <h1>Iowa Air</h1>
        <h2>Travel Like A Hawkeye</h2>
    </a>
    <nav>
        <ul>
            <li>
                <div class="account-dropdown">
                    <a href="index.jsp">
                        <button class="account-dropbutton">Search Flight</button>
                    </a>
                </div>
            </li>

            <li>
                <div class="account-dropdown">
                    <a href="ManagerUserLookup.jsp">
                        <button class="account-dropbutton">User Lookup</button>
                    </a>
                </div>
            </li>

            <li>
                <div class="account-dropdown">
                    <a href="manager.jsp">
                        <button class="account-dropbutton">Checkin Customer</button>
                    </a>
                </div>
            </li>

            <li>
                <div class="account-dropdown">
                    <a href="manager.jsp">
                        <button class="account-dropbutton selected"><%=accountText%>
                        </button>
                    </a>
                    <div class="account-dropdown-content">
                        <a href="logout.jsp">Log Out</a>
                    </div>
                </div>
            </li>
        </ul>
    </nav>

</header>
<body>

<div class = checkin>
    <form id = "UserHistoryServlet">
        Enter User Email:<br>
        <input type="text" required placeholder="User Email" name="userLookup">
        <input type="submit" value="Search">
    </form>
</div>

<div class = checkin>
    <form id = "LookupUserHistory">
        <input type="text" placeholder="Flight ID" name="flightToAdd">
        <input type="button" onclick="" value="Add Flight">
    </form>
</div>

</body>
</html>
