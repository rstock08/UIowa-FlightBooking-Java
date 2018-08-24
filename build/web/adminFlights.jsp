<%--
  Created by IntelliJ IDEA.
  User: johnn
  Date: 3/26/2017
  Time: 5:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="General.AdminFunctions" %>
<%@ page import="General.FlightQuery" %>
<%@ page import="General.CityFunctions" %>
<%@ page import="Admin.Flights.AddFlight" %>
<%@ page import="Admin.Planes.PlaneModelsList" %>

<%
    String accountText = "";
    if(session.getAttribute("role").toString().equals("ADMIN")){
        accountText = session.getAttribute("userEmail").toString();
    } else {
        response.sendRedirect("logout.jsp");
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


    <%=FlightQuery.getDateAndTimeSrc()%>
    <%//=FlightQuery.addPickers("flightDeparture")%>
    <%//=FlightQuery.addPickers("flightArrival")%>
    <script src="js/Admin/Flights.js"></script>
    <style>
        /* The Modal (background) */
        .adminAddFlightModal{
            display: none; /* Hidden by default */
            position: fixed; /* Stay in place */
            z-index: 1; /* Sit on top */
            padding-top: 100px; /* Location of the box */
            left: 0;
            top: 0;
            width: 100%; /* Full width */
            height: 100%; /* Full height */
            overflow: auto; /* Enable scroll if needed */
            background-color: rgb(0,0,0); /* Fallback color */
            background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
        }

        /* Modal Content */
        .adminAddFlightModalContents {
            background-color: #fefefe;
            margin: auto;
            padding: 20px;
            border: 1px solid #888;
            width: 50%;
        }

        /* The Close Button */
        .adminAddFlightModalClose {
            color: #aaaaaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
        }

        .adminAddFlightModalClose:hover, .adminAddFlightModalClose:focus{
            color: #000;
            text-decoration: none;
            cursor: pointer;
        }

        #adminAddFlightModalForm{
            background: transparent;
            width: 90%;
            margin: 0 5%;
        }

        #adminAddFlightModalForm div{
            width: 100%;
            text-align: left;
            display: block;
            margin: auto;
            background-color: transparent;
        }

        #adminAddFlightModalForm button{
            display: block;
            margin: 0 0 0 auto;
        }

        #adminAddFlightModalForm div div{
            width: 100%;
            alignment: center;
            display: inline-block;
            text-align :center;
            vertical-align: center;
            margin: auto;
            padding: 0 0 5px 0;
            background-color: transparent;
        }

        #adminAddFlightModalForm div div p{
            width: 20%;
            display: inline-block;
            vertical-align: center;
            margin: auto;
            padding: 0;
            background-color: transparent;
        }

        #adminAddFlightModalForm div div div{
            width: 10%;
            display: inline-block;
            alignment: center;
            margin: auto;
            background-color: transparent;
        }

        #adminAddFlightModalForm div a {
            width: 33%;
            margin: auto;
            background-color: transparent;
        }

        #adminAddFlightModalUL{
            background-color: #e6e6e6;
        }

        #adminAddFlightModalUL li{
            border: solid black 1px;
        }

        #adminAddFlightModalUL li div {
            background-color:transparent;
            width: 25%;
            display: inline-block;
            text-align: center;
            margin: auto;
        }

        #adminAddFlightModalUL li div p{
            background-color:transparent;
            display: inline-block;
            margin: auto;
        }

        #adminAddFlightModalUL li div input{
            background-color:transparent;
            alignment: center;
            display: inline-block;
            margin: auto;
        }

        #adminAddFlightModalUL li:hover, #adminAddFlightModalForm div a{
            background-color: yellow;
            border: solid black 1px;
        }

    </style>

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
                    <a href="adminLocations.jsp">
                        <button class="account-dropbutton">Locations</button>
                    </a>
                </div>
            </li>

            <li>
                <div class="account-dropdown">
                    <a href="adminPlanes.jsp">
                        <button class="account-dropbutton">Planes</button>
                    </a>
                </div>
            </li>

            <li>
                <div class="account-dropdown">
                    <a href="adminFlights.jsp">
                        <button class="account-dropbutton selected">Flights</button>
                    </a>
                </div>
            </li>

            <li>
                <div class="account-dropdown">
                    <a href="adminManagers.jsp">
                        <button class="account-dropbutton">Managers</button>
                    </a>
                </div>
            </li>

            <li>
                <div class="account-dropdown">
                    <a href="admin.jsp">
                        <button class="account-dropbutton"><%=accountText%>
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

<section id="adminFlights">
    <h1>Flights</h1>
    <form id="adminFlightsForm" method="post" onsubmit="return viewAddFlightModal()">
        <div>
            <p class="admin_flight_type"><b>Departure</b></p>
            <p class="admin_flight_info">
                Date: <input type="text" name="flightDepartureDate" placeholder="Select Date" id="flightDeparturedatepicker" onclick="enableNextEntry(1)" required>
                Time: <input type="text" name="flightDepartureTime" placeholder="Select Time" id="flightDeparturetimepicker" disabled onclick="enableNextEntry(2)" required>
                Location:
                <select id="flightDepartureLocationState" name="flightDepartureLocationState" disabled onclick="enableNextEntry(3)" required>
                    <option disabled selected>---Select a state---</option>
                    <%=CityFunctions.getActiveStatesList("alterDepartureCitySelect")%>
                </select>
                <select id="flightDepartureLocationCity" name="flightDepartureLocationCity" disabled onclick="enableNextEntry(4)" required>
                    <option disabled selected>---Select a city---</option>
                    <%=CityFunctions.getActiveCitiesList()%>
                </select>
            </p>
            <p class="admin_flight_type"><b>Arrival</b></p>
            <p class="admin_flight_info">
                Date: <input type="text" name="flightArrivalDate" placeholder="Select Date" id="flightArrivaldatepicker"  disabled>
                Time: <input type="text" name="flightArrivalTime" placeholder="Select Time" id="flightArrivaltimepicker"  disabled>
                Location:
                <select id="flightArrivalLocationState" name="flightArrivalLocationState" onclick="enableNextEntry(5)" disabled>
                    <option id="flightArrivalLocationStateDefault" disabled selected>---Select a state---</option>
                    <%=CityFunctions.getActiveStatesList("alterArrivalCitySelect")%>
                </select>
                <select id="flightArrivalLocationCity" name="flightArrivalLocationCity" onclick="enableNextEntry(6)" disabled>
                    <option disabled selected>---Select a city---</option>
                    <%=CityFunctions.getActiveCitiesList()%>
                </select>
            </p>
            <p class="admin_flight_type"><b>Plane</b></p>
            <p class = "admin_flight_info">
                Type:
                <select id="flightPlaneModelSelect" name="flightPlaneModel" onchange="canModelMakeTheDistance()" onclick="enableNextEntry(7)" disabled >
                </select>
                Occurrence:
                <select id="flightOccurrenceSelect" name="occurrences">
                    <option disabled selected>--Select Occurrence--</option>
                    <option value="1">1 Time Only</option>
                    <option value="1">Daily</option>
                    <option value="7">Weekly</option>
                </select>
                Occurrence Period:
                <select id="flightOccurrencePeriodSelect" name="timePeriod">
                    <option disabled selected>--Select Period--</option>
                    <option value="1">1 Time Only</option>
                    <option value="7">1 Week</option>
                    <option value="30">1 Month</option>
                    <option value="90">3 Month</option>
                    <option value="180">6 Months</option>
                    <option value="365">1 Year</option>
                </select>
            </p>
            <br>
            <p class="admin_flight_info">
                Price Adjustment:<input type="text" id="flightDistancePrice" placeholder="$" value="" style="width:5ch;" disabled >
                Demand: <input type="range" id="flightDemandSlider" name="points" min="1" max="20" step="1" onchange="updateSliderText()">
                <input type="text" id="flightDemandValue" style="width:3ch;" disabled>
            </p>
        </div>
        <button type="submit" id="addFlightButton" name="addFlightButton" class="admin_flight_info_button"  disabled>Show Available Planes</button>
    </form>
</section>

<br>
<div id="adminFlightListTableContainerNavigation">
    <button id="adminFlightListTablePreviousPage">Previous</button>
    <button id="adminFlightListTableNextPage">Next</button>
</div>
<br>
<section id="adminFlightListTableContainer" style="height:800px;">
    <table class="adminFlightListTable">
        <tr>
            <th><b> </b></th>
            <th><b> </b></th>
            <th><b>| </b></th>
            <th><b>Depart</b></th>
            <th><b>Info</b></th>
            <th><b> </b></th>
            <th><b>| </b></th>
            <th><b>Arrival</b></th>
            <th><b>Info</b></th>
            <th><b> </b></th>
            <th><b>| </b></th>
            <th><b>Open</b></th>
            <th><b> </b></th>
        </tr>
        <tr>
            <th><b>Flight</b></th>
            <th><b>Plane</b></th>
            <th><b>Date</b></th>
            <th><b>Time</b></th>
            <th><b>City</b></th>
            <th><b>State</b></th>
            <th><b>Date</b></th>
            <th><b>Time</b></th>
            <th><b>City</b></th>
            <th><b>State</b></th>
            <th><b>Economy</b></th>
            <th><b>Business</b></th>
            <th><b>First</b></th>
        </tr>
    </table>

    <table id="adminFlightListTable" class="adminFlightListTable">

    </table>
</section>

















<div id="adminAddFlightModal" class="adminAddFlightModal">
    <div class="adminAddFlightModalContents">
        <span class="adminAddFlightModalClose" onclick="closeAddFlightModal()">&times;</span>
        <div id="adminAddFlightModalForm">
            <p><b id="adminAddFlightModalTitle"></b></p>
            <br>
            <div>
                <div>
                    <p><b>DEPART:</b></p>
                    <p id="adminFlightModalDepartLocation"></p>
                    <div><b> AT </b></div>
                    <div id="adminFlightModalDepartTime"></div>
                    <div><b> ON </b></div>
                    <p id="adminFlightModalDepartDate"></p>
                </div>
                <div>
                    <p><b>ARRIVE:</b></p>
                    <p id="adminFlightModalArriveLocation"></p>
                    <div><b> AT </b></div>
                    <div id="adminFlightModalArriveTime"></div>
                    <div><b> ON </b></div>
                    <p id="adminFlightModalArriveDate"></p>
                </div>
            </div>
            <ul id="adminAddFlightModalUL" class="planeForm">
            </ul>
            <br>
            <div>
                <div>
                    <a id="adminAddFlightModalPreviousPage" class="adminAddFlightModalButtons" >prev</a>
                    <p id="adminAddFlightModalCurrentPage">this</p>
                    <a id="adminAddFlightModalNextPage" class="adminAddFlightModalButtons">next</a>
                    <input id="adminAddFlightModalFormNumber" type="hidden" value="0">
                </div>
            </div>
            <br>
            <button id="adminAddFlightModalFormButton" onclick="submitToDatabase(); enableNextEntry(10);" value="">ADD FLIGHT</button>
        </div>
    </div>
</div>
<footer>

</footer>

</body>

</html>