package Admin.Flights;

import General.AccountFunctions;
import General.CityFunctions;
import General.FlightsFunctions;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

/**
 * Created by johnn on 4/14/2017.
 */
@WebServlet(name="AddFlight",value="/Admin.Flights.AddFlight")
public class AddFlight extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //flightDepartureDate=2017-03-18&flightDepartureTime=01:30:00&flightArrivalDate=2017-03-18&flightArrivalTime=08:39:00&flightPlaneModelSelect=BOEING%20777&viewPage=0&flightDepartureLocationState=Alabama&flightDepartureLocationCity=Birmingham&flightArrivalLocationState=California&flightArrivalLocationCity=Los+Angeles&value=9;
        String departState = request.getParameter("flightDepartureLocationState");
        String departCity = request.getParameter("flightDepartureLocationCity");
        String arrivalState = request.getParameter("flightArrivalLocationState");
        String arrivalCity = request.getParameter("flightArrivalLocationCity");
        String departDate = request.getParameter("flightDepartureDate");
        String departTime = request.getParameter("flightDepartureTime");
        String arrivalDate = request.getParameter("flightArrivalDate");
        String arrivalTime = request.getParameter("flightArrivalTime");
        String planeID = request.getParameter("value");
        String distancePrice = request.getParameter("distancePrice");
        String demand = request.getParameter("flightDemandSlider");
        String timePeriod = request.getParameter("timePeriod");
        String occurrences = request.getParameter("occurrences");


        System.out.println("-----------------------------");
        System.out.println(departState);
        System.out.println(departCity);
        System.out.println(arrivalState);
        System.out.println(arrivalCity);
        System.out.println(departDate);
        System.out.println(departTime);
        System.out.println(arrivalDate);
        System.out.println(arrivalTime);
        System.out.println(planeID);
        System.out.println(distancePrice);
        System.out.println(demand);
        System.out.println("-----------------------------");

        response.setContentType("text/plain");

        String isAddedStringBoolean = (attemptAddFlight(planeID,departDate,
                departTime,departState,departCity,arrivalDate,arrivalTime,
                arrivalState,arrivalCity,distancePrice,demand, timePeriod, occurrences)) ? "1":"0";

        System.out.println(isAddedStringBoolean);
        response.getWriter().print(isAddedStringBoolean);
    }

    private static boolean attemptAddFlight(String planeID, String departDate, String departTime,
                                                 String departState, String departCity, String arrivalDate,
                                                 String arrivalTime, String arrivalState, String arrivalCity,
                                                 String distancePrice, String demand, String timePeriod, String occurrences){
        // ---- ///
        Connection con = AccountFunctions.OpenDatabase();
        boolean isPlaneAdded = FlightsFunctions.addFlight(con,planeID,departDate,departTime,
                CityFunctions.getIndex(con, departState,departCity),arrivalDate,arrivalTime,
                CityFunctions.getIndex(con,arrivalState,arrivalCity),demand,distancePrice, timePeriod, occurrences);
        AccountFunctions.closeConnection(con);
        return isPlaneAdded;

    }

}
