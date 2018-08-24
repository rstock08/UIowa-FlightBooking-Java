package Admin.Flights;

import General.AccountFunctions;
import General.AirplaneFunctions;
import General.Calculations;
import General.CityFunctions;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

/**
 * Created by johnn on 4/15/2017.
 */
@WebServlet(name = "GetAvailablePlanes",value="/Admin.Flights.GetAvailablePlanes")
public class GetAvailablePlanes extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String departDate = request.getParameter("flightDepartureDate");
            String departTime = request.getParameter("flightDepartureTime");
            String arrivalDate = request.getParameter("flightArrivalDate");
            String arrivalTime = request.getParameter("flightArrivalTime");
            String planeModel = request.getParameter("flightPlaneModelSelect");

            System.out.println("-----------------------");
            System.out.println("departDate: " + departDate);
            System.out.println("departTime: " + departTime);
            System.out.println("arrivalDate: " + arrivalDate);
            System.out.println("arrivalTime: " + arrivalTime);
            System.out.println("planeModel: " + planeModel);
            System.out.println("-----------------------");

            String json = compileJSON(getAvailableAirPlanes(planeModel,departDate,
                    departTime,arrivalDate,arrivalTime));

            System.out.println(json);
            response.setContentType("text/plain");
            response.getWriter().print(json);

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    private static ArrayList<String[]> getAvailableAirPlanes(String planeModel, String departDate, String departTime,
                                                             String arriveDate, String arriveTime){

        Connection con = AccountFunctions.OpenDatabase();
        ArrayList<String[]> planes = AirplaneFunctions.airplanesCurrentlyAvailable(con, planeModel, departDate, departTime,
                arriveDate, arriveTime);
        AccountFunctions.closeConnection(con);
        return planes;
    }

    private static String compileJSON(ArrayList<String[]> planeInfo){
        String[] list = {"pID","mID"};

        /*
        for(int j=0; j<planeInfo.size();j++){
            System.out.println("pID="+planeInfo.get(j)[0]+"     mID="+planeInfo.get(j)[1]);
        }
        */
        String jsonPlanes = " ";
        for (int i = 0; i < planeInfo.size(); i++) {
            jsonPlanes += "{\""+list[0]+"\":"+planeInfo.get(i)[0] +",";
            jsonPlanes += "\""+list[1]+"\":"+planeInfo.get(i)[1] + "},";
        }
        return "{\"numberOfPlanes\":"+planeInfo.size()+",\"planes\":["+jsonPlanes.substring(0,jsonPlanes.length()-1)+"]}";
    }

}

