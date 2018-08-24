package General;

import General.AccountFunctions;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import javax.servlet.http.HttpSession;


/**
 * Created by ReedS on 3/25/2017.
 */
@WebServlet(name="Create Account", value="/General.CreateAccount")
public class CreateAccount extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String userName = request.getParameter("userName");
        String userEmail = request.getParameter("userEmail");
        String userPassword = request.getParameter("userPassword");
        String userAlreadyExists = "false";

        Connection con = AccountFunctions.OpenDatabase();
        if (AccountFunctions.checkLogin(con,userEmail,userPassword)){
            userAlreadyExists = "alreadyExists";
        } else {
            userAlreadyExists = (AccountFunctions.addAccount(con, userName, userEmail, userPassword, "CUSTOMER"))?"true":"false";// add customer to database

        }
        AccountFunctions.closeConnection(con); // close connection
        response.setContentType("text/plain");
        response.getWriter().print(userAlreadyExists);
    }


    // Takes input from html and checks if user gave valid password and email
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }
}
