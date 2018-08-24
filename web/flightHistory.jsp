<%@ page import="General.FlightHistory" %><%--
  Created by IntelliJ IDEA.
  User: johnn
  Date: 3/26/2017
  Time: 3:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


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

    <%-- Needs jquery  and javascript file --%>
    <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.0.min.js"></script>

    <script src="js/General/FlightHistory.js"></script>




</head>

<body>

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
                        <a href="<%=historyPage%>" class="selected">Flight History</a>
                        <a href="<%=paymentPage%>">Payment Info</a>
                    </div>
                </div>
            </li>
        </ul>
    </nav>
</header>


<div id="viewwrapper">

    <section>
        <table class = "bookingTables">
            <tr>
                <th><b>Flight</b></th>
                <th><b>Total</b></th>
                <th><b>Economy</b></th>
                <th><b>Business</b></th>
                <th><b>First</b></th>
                <th><b>---------</b></th>
                <th><b>Departure</b></th>
                <th><b>---------</b></th>
                <th><b>---------</b></th>
                <th><b>Arrival</b></th>
                <th><b>---------</b></th>
            </tr>
            <tr>
                <th><b>Number</b></th>
                <th><b>Tickets</b></th>
                <th><b>Tickets</b></th>
                <th><b>Tickets</b></th>
                <th><b>Tickets</b></th>
                <th><b>Date</b></th>
                <th><b>Time</b></th>
                <th><b>Location</b></th>
                <th><b>Date</b></th>
                <th><b>Time</b></th>
                <th><b>Location</b></th>
            </tr>
        </table>

        <table id="flightHistoryList" class="bookingTables">

        </table>

    </section>




</div>

<footer>

</footer>

</body>

</html>
