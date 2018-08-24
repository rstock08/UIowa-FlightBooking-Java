<%--
  Created by IntelliJ IDEA.
  User: johnn
  Date: 3/26/2017
  Time: 5:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
    <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.0.min.js"></script>
    <script src="js/planeModals.js" ></script>
    <script src="js/adminPlanes.js" ></script>
    <script src="js/Admin/Aeronautics.js" ></script>


    <style>

        /* The Modal (background) */
        #planeModelModal , #planeModal{
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
        #planeModelModalContent , #planeModalContent {
            background-color: #fefefe;
            margin: auto;
            padding: 20px;
            border: 1px solid #888;
            width: 50%;
        }

        /* The Close Button */
        #planeModelModalClose , #planeModalClose {
            color: #aaaaaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
        }

        #planeModelModalClose:hover, #planeModelModalClose:focus,
        #planeModalClose:hover, #planeModalClose:focus {
            color: #000;
            text-decoration: none;
            cursor: pointer;
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
                        <button class="account-dropbutton selected">Planes</button>
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

<div id="adminPlanes">
    <h1>New Plane Model</h1>
    <form id="planeModelForm" onsubmit="return addPlaneModel()" method="post">
        <ul class="modelForm">
            <li><div class="planeFormInputTitle"><b>Plane Model: </b></div>
                <input type="text" id="planeModel" name="planeModel" placeholder="Type" maxlength="40" required>
            </li>
            <li><div class="planeFormInputTitle"><b>Carrying Capacity: </b></div>
                <input type="range" id="modelCapacity" name="modelCapacity" placeholder="Capacity" min="1" max="400" maxlength = "3" step="1"  onchange="alterText()" required>
                <input type="text" id="modelCapacityText" name="modelCapacityText" value="0" disabled required>
            </li>
            <li><div class="planeFormInputTitle"><b>Fuel Capacity: </b></div>
                <input type="range" id="modelFuel" name="modelFuel" placeholder="tonnes" min="1" max="250" maxlength = "4" step="1"  onchange="alterText()" required>
                <input type="text" id="modelFuelText" name="modelFuelText" value="0" disabled required>
            </li>
            <li><div class="planeFormInputTitle"><b>Fuel Burn Rate: </b></div>
                <input type="range" id="modelBurn" name="modelBurn" placeholder="kg/km" min="0.4" max="10" maxlength = "4" step="0.2"  onchange="alterText()" required>
                <input type="text" id="modelBurnText" name="modelBurnText" value="0" disabled required>
            </li>
            <li><div class="planeFormInputTitle"><b>Average Velocity: </b></div>
                <input type="range" id="modelVelocity" name="modelVelocity" min="250" max="2000" maxlength = "4" step="25" onchange="alterText()" required>
                <input type="text" id="modelVelocityText" name="modelVelocity" value="0" disabled required>
            </li>
            <li>
                <br>
                <div id="adminPlanesCheckBoxLabels" class="classesCheckboxLabel">
                    <div class="planeFormInputTitle"><b>Classes: </b>
                        <ul>
                            <li style="height:32px;">
                                <input type="checkbox" name="hasEconomyClass" value="true" title="Economy" onclick="alterSlider(0)" ><b>Economy</b>
                            </li>
                            <li style="height:32px;">
                                <input type="checkbox" name="hasBusinessClass" value="true" title="Business" onclick="alterSlider(1)"><b>Business</b>
                            </li>
                            <li style="height:32px;">
                                <input type="checkbox" name="hasFirstClass" value="true" title="First" onclick="alterSlider(2)" ><b>First</b>
                            </li>
                        </ul>
                    </div>
                    <div style="display:inline-flex;width:500px;">
                        <b>&nbsp;</b>
                        <ul class="classesCheckbox" >
                            <li>
                                <input type="range" id="seatsEconomyRange" name="seatsEconomy" value="0" min="0" max="400" step="1" onchange="alterText()" disabled required>
                                <input type="text" id="seatsEconomy" name="seatsEconomy" value="0" disabled required>
                            </li>
                            <li>
                                <input type="range" id="seatsBusinessRange" name="seatsBusiness" value="0" min="0" max="400" step="1" onchange="alterText()" disabled required>
                                <input type="text" id="seatsBusiness" name="seatsBusiness" value="0" disabled required>
                            </li>
                            <li>
                                <input type="range" id="seatsFirstRange" name="seatsFirst" value="0" min="0" max="400" step="1"  onchange="alterText()" disabled required>
                                <input type="text" id="seatsFirst" name="seatsFirst" value="0" disabled required>
                            </li>
                        </ul>
                    </div>

                </div>
            </li>
        </ul>
        <button type="submit" id="addPlaneModelButton" name="addPlaneModelButton">Add Model</button>
    </form>
    <br>
    <div id="adminPlaneModelTableNavigation">
        <button id="adminPlaneModelPreviousPage">Previous</button>
        <button id="adminPlaneModelNextPage">Next</button>
    </div>
    <section id="adminPlaneModelsTablesContainer" style="height:600px">
        <table class="admin_man_table admin_planeModel_table" >
            <tr>
                <th><b>Model</b></th>
                <th><b>Capacity</b></th>
                <th><b>Economy</b></th>
                <th><b>Business</b></th>
                <th><b>First</b></th>
                <th><b>Economy</b></th>
                <th><b>Business</b></th>
                <th><b>First</b></th>
                <th><b>Action</b></th>
            </tr>
            <tr>
                <th></th>
                <th>(persons)</th>
                <th></th>
                <th></th>
                <th></th>
                <th>(persons)</th>
                <th>(persons)</th>
                <th>(persons)</th>
            </tr>
        </table>
        <table id="planeModelListTable" class="admin_man_table admin_planeModel_table" >
            <%-- Table generated in javascript --%>
        </table>
    </section>
    <br>
    <br>
    <h1>New Plane</h1>
    <form id="planeForm" method="post" onsubmit="return attemptAddAirplane()">
        <ul class="planeForm">
            <li><div class="planeFormInputTitle"><b>Plane Type: </b></div>
                <select id="planeModelSelect" name="planeSelect" onchange="enableAddModelButton()">
                    <option disabled selected>Select Plane Model</option>
                </select>
            </li>
            <li><div class="planeFormInputTitle"><b>Capacity: </b></div>
                <input type="text" id="planeCapacity" name="planeCapacity" disabled>
            </li>
            <li><div class="planeFormInputTitle"><b>Economy Seats: </b></div>
                <input type="text" id="planeEconomySeats" name="planeEconomySeats" disabled >
            </li>
            <li><div class="planeFormInputTitle"><b>Business Seats: </b></div>
                <input type="text" id="planeBusinessSeats" name="planeBusinessSeats" disabled >
            </li>
            <li><div class="planeFormInputTitle"><b>First Seats: </b></div>
                <input type="text" id="planeFirstSeats" name="planeFirstSeats" disabled >
            </li>
            <li>
                <br>
            </li>
        </ul>
        <button type="submit" id="addPlaneButton" name="addPlaneButton" disabled>Add Plane</button>
    </form>
    <br>
    <div id="adminPlaneTableNavigation">
        <button id="adminPlanePreviousPage">Previous</button>
        <button id="adminPlaneNextPage">Next</button>
    </div>

    <section id="adminPlaneTablesContainer" style="height:820px">
        <table class="admin_man_table admin_planeModel_table" >
            <tr>
                <th><b>ID</b></th>
                <th><b>Model</b></th>
                <th><b>Action</b></th>
            </tr>
            <tr>
                <th></th>
                <th></th>
                <th></th>
            </tr>
        </table>
        <table id="planeTable" class="admin_man_table admin_planeModel_table" >
            <%-- Table generated in javascript --%>
        </table>
    </section>
    <br>
</div>





<div id="planeModelModal">
    <div id="planeModelModalContent">
        <span id="planeModelModalClose">&times;</span>

        <form id="planeModelModalForm" >
            <ul class="modelForm">
                <li><div class="planeFormInputTitle"><b>Model: </b></div>
                    <input type="text" id="modelModalID" name="modelModalID" placeholder="Type" maxlength="40" required readonly>
                </li>
                <li><div class="planeFormInputTitle"><b>Model Name: </b></div>
                    <input type="text" id="modelModal" name="modelModal" placeholder="Type" maxlength="40" required>
                </li>
                <li><div class="planeFormInputTitle"><b>Carrying Capacity: </b></div>
                    <input type="range" id="modelModalCapacity" name="modelModalCapacity" placeholder="Capacity" min="1" max="400" maxlength = "3" step="1"  onchange="alterModalText()" required>
                    <input type="text" id="modelModalCapacityText" name="modelModalCapacityText" value="0" disabled required>
                </li>
                <li><div class="planeFormInputTitle"><b>Fuel Capacity: </b></div>
                    <input type="range" id="modelModalFuel" name="modelModalFuel" placeholder="tonnes" min="1" max="250" maxlength = "4" step="1"  onchange="alterModalText()" required>
                    <input type="text" id="modelModalFuelText" name="modelModalFuelText" value="0" disabled required>
                </li>
                <li><div class="planeFormInputTitle"><b>Fuel Burn Rate: </b></div>
                    <input type="range" id="modelModalBurn" name="modelModalBurn" placeholder="kg/km" min="0.4" max="10" maxlength = "4" step="0.2"  onchange="alterModalText()" required>
                    <input type="text" id="modelModalBurnText" name="modelModalBurnText" value="0" disabled required>
                </li>
                <li><div class="planeFormInputTitle"><b>Average Velocity: </b></div>
                    <input type="range" id="modelModalVelocity" name="modelModalVelocity" min="250" max="2000" maxlength = "4" step="25" onchange="alterModalText()" required>
                    <input type="text" id="modelModalVelocityText" name="modelModalVelocity" value="0" disabled required>
                </li>

                <li>
                    <div class="classesCheckboxLabel">
                        <div class="planeFormInputTitle"><b>Available Classes: </b></div>
                        <ul class="classesCheckbox" >
                            <li><div class="planeFormInputTitle"></div>
                                <input type="checkbox" id="hasEconomyModal" name="hasEconomyModal" value="true" title="Economy" onclick="alterModalSlider(0)" ><b>Economy</b>
                                <input type="range" id="seatsEconomyRangeModal" name="seatsEconomyRangeModal" value="0" min="0" max="400" step="1" onchange="alterModalText()"  required>
                                <input type="text" id="seatsEconomyModalText" name="seatsEconomyModalText" value="0" disabled required>
                            </li>
                            <li><div class="planeFormInputTitle"></div>
                                <input type="checkbox" id="hasBusinessModal" name="hasBusinessModal" value="true" title="Business" onclick="alterModalSlider(1)"><b>Business</b>
                                <input type="range" id="seatsBusinessRangeModal" name="seatsBusinessRangeModal" value="0" min="0" max="400" step="1" onchange="alterModalText()"  required>
                                <input type="text" id="seatsBusinessModalText" name="seatsBusinessModalText" value="0" disabled required>

                            </li>
                            <li><div class="planeFormInputTitle"></div>
                                <input type="checkbox" id="hasFirstModal" name="hasFirstModal" value="true" title="First" onclick="alterModalSlider(2)" ><b>First</b>
                                <input type="range" id="seatsFirstRangeModal" name="seatsFirstRangeModal" value="0" min="0" max="400" step="1"  onchange="alterModalText()"  required>
                                <input type="text" id="seatsFirstModalText" name="seatsFirstModalText" value="0" disabled required>

                            </li>
                        </ul>
                    </div>
                </li>
            </ul>
        </form>
        <button id="updatePlaneModelModalButton" name="updatePlaneModelModalButton">Update Model</button>
        <button id="removePlaneModelModalButton" name="removePlaneModelModalButton">Remove Model</button>
    </div>
</div>

<div id="planeModal">
    <div id="planeModalContent">
        <span id="planeModalClose">&times;</span>

        <form id="planeModalForm" >
            <ul class="modelForm">
                <li><div class="planeFormInputTitle"><b>Plane ID: </b></div>
                    <input type="text" id="planeModalID" name="planeModalID" placeholder="Type" maxlength="40" required readonly>
                </li>
                <li><div class="planeFormInputTitle"><b>Plane Model: </b></div>
                    <input type="text" id="planeModalModelID" name="planeModalModelID" placeholder="Type" maxlength="40" required>
                </li>
            </ul>
        </form>
        <button id="updatePlaneModalButton" name="updatePlaneModalButton">Update Model</button>
        <button id="removePlaneModalButton" name="removePlaneModalButton">Remove Model</button>
    </div>
</div>




<footer>

</footer>

</body>

</html>