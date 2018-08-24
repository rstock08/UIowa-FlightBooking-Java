<%--
  Created by IntelliJ IDEA.
  User: ReedS
  Date: 3/25/2017
  Time: 8:34 AM
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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="js/General/LoginCreate.js"></script>
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
                        <a href="<%=logPage%>" class="selected"><%=logSet%>
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

    <section id="loginSection">
        <div id="loginOptions">
            <div>
                <button id="loginButton" class="selected">Login</button>
            </div>
            <div>
                <button id="createButton">Create Account</button>
            </div>
        </div>
        <div id="signInContentArea">
            <div id="loginDiv">
                <form id="loginAccountForm" method="post" onsubmit="return userExists()">
                    <br>
                    <p><b>Email</b></p>
                    <input type="email" name="userEmail" maxlength="40" align="middle" placeholder="Email" required>
                    <p><b>Password</b></p>
                    <input type="password" name="userPassword" maxlength="16" align="middle" placeholder="Password" required>
                    <br>
                    <div id="loginErrorMessage">
                        <p>Incorrect information</p>
                        <p>Please check username and password</p>
                    </div>
                    <button class="loginCreateButtons" type="submit">Login</button>
                </form>
            </div>
            <div id="createDiv">
                <form id="createAccountForm" onsubmit="return validateForm()" method="post" accept-charset="UTF-8">
                    <br>
                    <p><b>Name</b></p>
                    <input type="text" name="userName" maxlength="16" align="middle" placeholder="Name" required>
                    <p><b>Email</b></p>
                    <input type="email" name="userEmail" maxlength="40" align="middle" placeholder="Email" required>
                    <p><b>Password</b></p>
                    <input type="password" name="userPassword" maxlength="16" align="middle" placeholder="Password" required>
                    <br>
                    <div id="passwordErrorMessage" >
                        <p>Password must contain:</p>
                        <p>8 to 16 characters</p>
                        <p>1 uppercase</p>
                        <p>1 lowercase</p>
                        <p>1 number</p>
                    </div>
                    <div id="youExistDude">
                        <p>This email is already in use</p>
                    </div>
                    <button class="loginCreateButtons" type="submit">Create Account</button>
                </form>
            </div>
        </div>
    </section>

</div>

<footer>

</footer>

</body>

</html>