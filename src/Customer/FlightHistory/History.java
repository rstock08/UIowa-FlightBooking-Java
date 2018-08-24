package Customer.FlightHistory;

import General.AccountFunctions;
import General.AirplaneFunctions;
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
 * Created by johnn on 4/28/2017.
 */
@WebServlet(name = "History", value="/Customer.FlightHistory.History")
public class History extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String userEmail = request.getSession().getAttribute("userEmail").toString();
        String basicList = attemptFlightsList(userEmail);
        response.setContentType("text/plain");
        response.getWriter().print(basicList);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private static String attemptFlightsList(String userEmail){
        Connection con = AccountFunctions.OpenDatabase();
        String basicFlightList = flightListToJSON(FlightHistory.getFlightList(con, userEmail));
        AccountFunctions.closeConnection(con);
        return basicFlightList;
    }

    public static String flightListToJSON(ArrayList<String[]> basicList){
        String[] list = {"flightID","totalTickets","ticketsEconomy","ticketsBusiness","ticketsFirst","departureDate",
        "departureTime","departureLocation","arrivalDate","arrivalTime","arrivalLocation"};
        String jsonBasicList = "{\"history\":[";
        for(int count=0; count<basicList.size(); count++){
            jsonBasicList += "{\""+list[0]+"\":"+basicList.get(count)[0]+",";
            jsonBasicList += "\""+list[1]+"\":\""+basicList.get(count)[1]+"\",";
            jsonBasicList += "\""+list[2]+"\":\""+basicList.get(count)[2]+"\",";
            jsonBasicList += "\""+list[3]+"\":\""+basicList.get(count)[3]+"\",";
            jsonBasicList += "\""+list[4]+"\":\""+basicList.get(count)[4]+"\",";
            jsonBasicList += "\""+list[5]+"\":\""+basicList.get(count)[5]+"\",";
            jsonBasicList += "\""+list[6]+"\":\""+basicList.get(count)[6]+"\",";
            jsonBasicList += "\""+list[7]+"\":\""+basicList.get(count)[7]+"\",";
            jsonBasicList += "\""+list[8]+"\":\""+basicList.get(count)[8]+"\",";
            jsonBasicList += "\""+list[9]+"\":\""+basicList.get(count)[9]+"\",";
            jsonBasicList += "\""+list[10]+"\":\""+basicList.get(count)[10]+"\"},";

        }
        jsonBasicList = jsonBasicList.substring(0,jsonBasicList.length()-1)+"]}";
        System.out.println(jsonBasicList);
        return jsonBasicList;
    }

}
