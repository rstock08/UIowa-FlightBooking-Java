<%--
  Created by IntelliJ IDEA.
  User: johnn
  Date: 4/28/2017
  Time: 4:59 PM
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

<html>
<head>
    <meta charset="UTF-8">
    <title>Iowa Air: Booking</title>
    <link rel="stylesheet" href="css/reset.css">
    <link rel="stylesheet" href="css/normalize.css">
    <link rel="stylesheet" href="css/main.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.0.min.js"></script>

    <script src="js/General/Booking.js"></script>

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
                        <a href="<%=historyPage%>">Flight History</a>
                        <a href="<%=paymentPage%>">Payment Info</a>
                    </div>
                </div>
            </li>
        </ul>
    </nav>


</header>

    <section id="bookingContent" >
        <table class="bookingTables">
            <tr>
                <th>Flight ID</th>
                <th>Depart Date</th>
                <th>Depart Time</th>
                <th>Arrival Date</th>
                <th>Arrival Time</th>
                <th>Plane Model</th>
                <th>Plane ID</th>
                <th>Tickets</th>
                <th>Class</th>
            </tr>
        </table>
        <table id="bookingTable" class="bookingTables bottomTable">

        </table>
        <br><br><br>
        <p id="totalCostDisplay">

        </p>
        <br><br><br>
        <form id="bookingForm" method="post" onsubmit="return checkCVC()">
            <input id="flight1" type="hidden" name="flight1">
            <input id="flight2" type="hidden" name="flight2">
            <input id="flight3" type="hidden" name="flight3">
            <input id="flight4" type="hidden" name="flight4">
            <input id="flight5" type="hidden" name="flight5">
            <input id="flight6" type="hidden" name="flight6">

            <input id="ticketsPurchased" type="hidden" name="ticketsPurchased">
            <input id="classPurchased" type="hidden" name="classPurchased">
            <input id="totalCost" type="hidden" name="totalCost">
            <select id="bookingPaymentInfo" name="bookingPaymentInfo">
            </select>
            <input type="text" maxlength="3" name="bookingCVC" class="cvcInput">
            <br><br>
            <button type="submit">Purchase Now</button>
        </form>
    </section>



</body>
</html>
