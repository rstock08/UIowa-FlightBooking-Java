package Customer.FlightHistory;

import General.AccountFunctions;
import General.FlightHistory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

/**
 * Created by Charlie on 4/30/2017.
 */
@WebServlet(name = "ActiveFlightsServlet", value = "/Customer.FlightHistory.ActiveFlightsServlet")
public class ActiveFlightsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userEmail = request.getSession().getAttribute("userEmail").toString();
        String basicList = attemptActiveFlightsList(userEmail);
        response.setContentType("text/plain");
        response.getWriter().print(basicList);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private static String attemptActiveFlightsList(String userEmail){
        Connection con = AccountFunctions.OpenDatabase();
        String basicFlightList = History.flightListToJSON(FlightHistory.getActiveFlightList(con, userEmail));
        AccountFunctions.closeConnection(con);
        return basicFlightList;
    }


}
