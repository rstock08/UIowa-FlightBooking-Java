<%--
  Created by IntelliJ IDEA.
  User: johnn
  Date: 3/26/2017
  Time: 5:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="General.CityFunctions" %>
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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="js/Admin/Locations.js" ></script>
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
                        <button class="account-dropbutton selected">Locations</button>
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
                        <button class="account-dropbutton">Flights</button>
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

<div id="adminLocations">
    <h1>Locations</h1>
    <section id="sidebar" class="locationsidebar">
        <form id="adminAddLocationForm">
            <ul>
                <li>
                    <br>
                    <div class="locationFormInputTitle"><b>State: </b></div>
                    <select id="stateName" name="stateName" style="width: 20ch" required>
                    </select>
                </li>
                <li>
                    <br>
                    <div class="locationFormInputTitle"><b>City: </b></div>
                    <select id="cityName" name="cityName" style="width: 20ch" required>
                    </select>
                </li>
            </ul>
        </form>
        <br>
        <button id="addLocationButton" name="addLocationButton">Add Location</button>
        <br>
    </section>

    <section id="googleAdminLocationsContainer">
        <div id="googleAdminLocations" style="width:95%;height:95%;">

        </div>
    </section>
    <br>


    <div id="adminLocationTableNavigation">
        <button id="adminLocationPreviousPage">Previous</button>
        <button id="adminLocationNextPage">Next</button>
    </div>
    <section id="adminLocationTablesContainer">
        <table class="admin_man_table">
            <th><b>State</b></th>
            <th><b>City</b></th>
            <th><b>Action</b></th>
        </table>
        <table id="adminActiveCityList" class="admin_man_table">

        </table>
    </section>
</div>


<footer>
</footer>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAmOyQqedQY902x2uHDZ80Xr6c2mW-JtwQ&callback=adminLocMap"></script>

</body>

</html>