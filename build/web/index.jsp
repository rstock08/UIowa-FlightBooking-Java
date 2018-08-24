<%--
  Created by IntelliJ IDEA.
  User: crisi_000
  Date: 3/23/2017
  Time: 1:49 PM
  To change this template use File | Settings | File Templates.

--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="General.FlightQuery" %>
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
    <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.0.min.js"></script>
    <%=FlightQuery.getDateAndTimeSrc()%>

    <script src="js/General/FlightQuery.js"></script>

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
                        <button class="account-dropbutton selected">Search</button>
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


<div id="vwrapper">

    <section id="sidebar">
        <form id="flightQueryForm" method="post" onsubmit="return attemptFlightQuery()">
            <p><b>Departure Location:</b></p>
            <select id="flightQueryDepartState" name="departState" required>
            </select>
            <select id="flightQueryDepartCity" name="departCity" required>
            </select>
            <br>
            <p><b>Departure Date:</b></p>
            <input type="text" name="departDate" placeholder="Select Date" id="flightQueryDepartDate" required>
            <br>
            <p><b>Arrival Location:</b></p>
            <select id="flightQueryArrivalState" name="arriveState" required>
            </select>
            <select id="flightQueryArrivalCity" name="arriveCity" required>
            </select>
            <br>
            <p><b>Travel Type:</b></p>
            <select id="flightQueryTravelType" name="travelType">
                <option disabled selected>--Select Ticket Type--</option>
                <option value="0" onclick="disableReturnDatePicker()">One-Way</option>
                <option value="1" onclick="enableReturnDatePicker()">Round</option>
            </select>
            <br>
            <p><b>Return Date:</b></p>
            <input type="text" name="returnDate" placeholder="Select Date" id="flightQueryReturnDate" disabled>
            <br>
            <p><b>Plane Model:</b></p>
            <select id="flightQueryPlaneModel" name="planeModel">
            </select>
            <br>
            <p><b>Number of Tickets:</b></p>
            <select id="flightQueryNumberOfPassengers" name="numberPassengers">
                <option disabled selected>--Select Amount--</option>
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
                <option value="6">6</option>
                <option value="7">7</option>
                <option value="8">8</option>
                <option value="9">9</option>
                <option value="10">10</option>
            </select>
            <p><b>Seating Preference:</b></p>
            <select id="flightQueryPreferredSeating" name="preference">
                <option disabled selected>--Select Preference--</option>
                <option value="0">Economy</option>
                <option value="1">Business</option>
                <option value="2">First</option>
            </select>
            <br>
            <p><b>Layover Preference:</b></p>
            <select id="flightQueryLayover" name="travelLayover">
                <option disabled selected>--Select Layover Pref--</option>
                <option value="0">Direct Flight</option>
                <option value="1">One Stop</option>
                <option value="1">Two Stop</option>
            </select>
            <br>
            <br>
            <div>
                <button type="submit" id="flightQueryButton">Search</button>
            </div>
        </form>
    </section>

    <section id="googlemapContainer">
        <div id="googlemap" style="width:95%;height:95%;">
        </div>
    </section>

    <section id="flightQueryControls">
        <div>
            <div>
                <button id="flightQueryPrevious" >Previous</button>
                <button id="flightQueryNext" >Next</button>
                <button id="showGoogleMap" >Toggle Map View</button>
            </div>
            <div>
                <div>
                    <div>
                        Economy
                    </div>
                    <div>
                        Business
                    </div>
                    <div>
                        First
                    </div>
                </div>
            </div>
        </div>
    </section>

    <section id="flightQueryView">
        <%-- Flight stuff is being populated from javascript file --%>
    </section>


</div>

<footer>

</footer>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAmOyQqedQY902x2uHDZ80Xr6c2mW-JtwQ&callback=myMap"></script>

</body>
</html>