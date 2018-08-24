package General;// Imported packages
import General.AccountFunctions;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// Servlet class General.Login
@WebServlet("/General.Login")
public class Login extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userEmail = request.getParameter("userEmail");
        String userPassword = request.getParameter("userPassword");
        String userExists = "false";

        // I get an error locating com.mysql.jdc.Driver
        //General.AccountFunctions AF = new General.AccountFunctions();
        Connection con = AccountFunctions.OpenDatabase();
        // Checks if account is in system then redirects if it is a valid account
        if(AccountFunctions.checkLogin(con,userEmail,userPassword)){
            System.out.println("i am currently right here!");
            HttpSession mySession = request.getSession();
            mySession.setAttribute("userEmail",userEmail);

            // Checks the role of the user
            switch(AccountFunctions.checkRole(con, userEmail)){
                // Checks if role is admin
                case 'A':
                    mySession.setAttribute("role","ADMIN");
                    userExists = "admin.jsp";
                    break;
                // Checks if role is manager
                case 'M':
                    mySession.setAttribute("role","MANAGER");
                    userExists = "manager.jsp";
                    break;
                // Customers
                default:
                    mySession.setAttribute("role","CUSTOMER");
                    userExists = "index.jsp";
            }

        } else {
            userExists = "false";
        }

        AccountFunctions.closeConnection(con);

        response.setContentType("text/plain");
        response.getWriter().print(userExists);

    }
}

