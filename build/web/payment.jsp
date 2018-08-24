<%--
  Created by IntelliJ IDEA.
  User: johnn
  Date: 3/26/2017
  Time: 3:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Customer.PaymentInfo.PaymentServlet" %>
<%@ page import="Customer.PaymentInfo.PaymentFunctions" %>
<%@ page import="Customer.PaymentInfo.PaymentTableServlet" %>


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
                        <a href="<%=paymentPage%>" class="selected">Payment Info</a>

                        <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.0.min.js"></script>

                        <script src="js/General/payment.js"></script>
                    </div>
                </div>
            </li>
        </ul>
    </nav>
</header>


<div id="viewwrapper">



    <section id="sidebar">

            <div id="paymentDiv">
                <form id="addPaymentForm" action = "Customer.PaymentInfo.PaymentServlet" method="post">
                    <br>
                    <p><b>Name</b></p>
                    <input type="text" name="cardName" maxlength="24" align="middle" placeholder="Name" required>
                    <p><b>Card Number</b></p>
                    <input type="text" name="cardNumber" maxlength="16" align="middle" placeholder="CardNumber" required>
                    <p><b>Expiration Date</b></p>
                    <select id="paymentFormExpDay" name="expYear">
                        <option disabled selected>--Select Year--</option>
                        <option value="2017">2017</option>
                        <option value="2018">2018</option>
                        <option value="2019">2019</option>
                        <option value="2020">2020</option>
                        <option value="2021">2021</option>
                        <option value="2022">2022</option>
                        <option value="2023">2023</option>
                        <option value="2024">2024</option>
                        <option value="2025">2025</option>
                        <option value="2026">2026</option>
                        <option value="2027">2027</option>
                    </select>
                    <select id="paymentFormExpMonth" name="expMonth">
                        <option disabled selected>--Select Month--</option>
                        <option value="01">January</option>
                        <option value="02">February</option>
                        <option value="03">March</option>
                        <option value="04">April</option>
                        <option value="05">May</option>
                        <option value="06">June</option>
                        <option value="07">July</option>
                        <option value="08">August</option>
                        <option value="09">September</option>
                        <option value="10">October</option>
                        <option value="11">November</option>
                        <option value="12">December</option>
                    </select>
                    <p><b>Security Code</b></p>
                    <input type="text" name="securityCode" maxlength="16" align="middle" placeholder="Security Code" required>
                    <p><b>Street Address</b></p>
                    <input type="text" name="address" maxlength="24" align="middle" placeholder="Address" required>
                    <p><b>City</b></p>
                    <input type="text" name="city" maxlength="16" align="middle" placeholder="City" required>
                    <p><b>State</b></p>
                    <input type="text" name="state" maxlength="16" align="middle" placeholder="State" required>
                    <p><b>ZipCode</b></p>
                    <input type="text" name="zipCode" maxlength="16" align="middle" placeholder="ZipCode" required>
                    <p><b>Phone Number</b></p>
                    <input type="text" name="phoneNumber" maxlength="16" align="middle" placeholder="Phone Number" required>
                    <br>

                    <button class="paymentCreateButtons" type="submit">Create Payment</button>
                </form>
            </div>
    </section>

    <section id="paymentContent">
        <table class = "bookingTables">
            <tr>
                <th><b>Name</b></th>
                <th><b>Card #</b></th>
                <th><b>Exp</b></th>
                <th><b>Address</b></th>
                <th><b>City</b></th>
                <th><b>State</b></th>
                <th><b>ZipCode</b></th>
                <th><b>Phone #</b></th>
                <th><b>Action</b></th>

            </tr>
        </table>

        <table id="billingList" class="bookingTables bottomTable">

        </table>

    </section>

</div>

<footer>

</footer>

</body>

</html>
