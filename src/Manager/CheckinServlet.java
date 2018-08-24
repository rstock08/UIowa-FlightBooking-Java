package Manager;

import General.AccountFunctions;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

/**
 * Created by ReedS on 4/25/2017.
 */
@WebServlet(urlPatterns = { "/CheckinServlet" })
public class CheckinServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    // Get info from checkin form "manager.jsp"
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Assign parameters to values
        String referenceID = request.getParameter("referenceID");
        String bags = request.getParameter("bags");


        boolean result;

        // Open connection to database
        Connection con = AccountFunctions.OpenDatabase();

        // Checkin
        result = ManagerCheckIn.managerCheckIn(con, Integer.parseInt(referenceID));

        System.out.println("Here 2"+bags);

        // Add bag
        //ManagerCheckIn.numberOfBags(con, Integer.parseInt(referenceID), Integer.parseInt(bags));

        // Check if user was properly submitted
        if(result == true){
            response.sendRedirect("success.jsp");
        }
        else
        {
            response.sendRedirect("failure.jsp");
        }
    }
}
