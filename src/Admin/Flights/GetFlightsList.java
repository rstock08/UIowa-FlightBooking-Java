package Admin.Flights;

import General.AccountFunctions;
import General.FlightsFunctions;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

/**
 * Created by johnn on 5/5/2017.
 */
@WebServlet(name = "GetFlightList", value="/Admin.Flights.GetFlightsList")
public class GetFlightsList extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String flightList = attemptGetFlightList();
        System.out.println(flightList);
        response.setContentType("text/plain");
        response.getWriter().print(flightList);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    static private String attemptGetFlightList(){
        Connection con = AccountFunctions.OpenDatabase();
        String flightList = flightListToJSON(FlightsFunctions.getFlightList(con));
        AccountFunctions.closeConnection(con);
        return flightList;
    }

    static private String flightListToJSON(ArrayList<ArrayList<String>> flightList){
        String[] list = {"fID","pID","dDate","dTime","dCity","dState","aDate","aTime","aCity","aState","aEcon","aBus","aFirst"};

        String jsonFlights = "{\"flightList\":[ ";

        for(int i=0; i<flightList.size(); i++){
            jsonFlights += "{";
            for(int j=0; j<flightList.get(i).size(); j++){
                jsonFlights += "\"" + list[j] + "\":\"" + flightList.get(i).get(j) + "\",";
            }
            jsonFlights = jsonFlights.substring(0,jsonFlights.length()-1)+"},";
        }

        return jsonFlights.substring(0,jsonFlights.length()-1)+"]}";

    }



}
