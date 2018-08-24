<%--
  Created by IntelliJ IDEA.
  User: johnn
  Date: 3/26/2017
  Time: 5:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String accountText = "";
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
    <title>Managers</title>
</head>
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

<br>

<body>
    <br>


    <form action="CheckinServlet" method="get">
        <div class = checkin>
            Customer Information:<br>
            <input type="text" name="firstname" placeholder="First Name" required><br>
            <input type="text" name="lastname" placeholder="Last Name" required><br>
            <input type="text" name="customerEmail" placeholder="Customer Email" required><br>
            <input type="text" name="referenceID" placeholder="Reference ID Number" required><br><br>
        </div>

        <br>

        <div class = checkin>
            Baggage:<br>
            <div class="input_fields_wrap">
                <button class="add_field_button">Add Bag</button>
                <div></div>
            </div>
        </div>

        <div class = checkin>
            <input type = "text" name="price" placeholder="Price" readonly id=price />
            <input type = "text" id="hiddenbags" name="bags" readonly value="0"/>
        </div>

        <br>

        <input type="submit" value="Submit" class = "submitButton" onsubmit="return validate(this);">
        <input type="reset" value="Reset" class = "submitButton">
    </form>
</body>
</html>



<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
<script>
    $(document).ready(function() {
        var max_fields      = 4; //maximum input boxes allowed
        var wrapper         = $(".input_fields_wrap"); //Fields wrapper
        var add_button      = $(".add_field_button"); //Add button ID

        var x = 0; //initlal text box count
        $(add_button).click(function(e){ //on add input button click
            e.preventDefault();

            if(x < max_fields){ //max input box allowed
                x++; //text box increment
                $(wrapper).append('<div><input type="number" name="bagWeight" placeholder="bag lbs" max=100 min=0/>' +
                    '<a href="#" class="remove_field">Remove</a></div>'); //add input box

                document.getElementById("hiddenbags").value=x;
                document.getElementById("price").placeholder = "$ " + 50*x; //Incr// ement price
            }
        });

        $(wrapper).on("click",".remove_field", function(e){ //user click on remove text
            e.preventDefault(); $(this).parent('div').remove(); x--;

            document.getElementById("hiddenbags").value=x;
            document.getElementById("price").placeholder = "$ " + 50*x; //decrement price
        })
    });
</script>
