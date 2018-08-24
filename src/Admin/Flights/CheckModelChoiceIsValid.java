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

/**
 * Created by johnn on 4/16/2017.
 */
@WebServlet(name = "CheckModelChoiceIsValid", value="/Admin.Flights.CheckModelChoiceIsValid")
public class CheckModelChoiceIsValid extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try{
            String departState = request.getParameter("flightDepartureLocationState");
            String departCity = request.getParameter("flightDepartureLocationCity");
            String arrivalState = request.getParameter("flightArrivalLocationState");
            String arrivalCity = request.getParameter("flightArrivalLocationCity");
            String planeModel = request.getParameter("flightPlaneModelSelect");

            int canTravel = 1;
            int time = 0;
            int distancePrice=0;
            if(departState!=null && departCity!=null && arrivalState!=null && arrivalCity!=null) {

                System.out.println("-----------------------");
                System.out.println("departState: " + departState);
                System.out.println("departCity: " + departCity);
                System.out.println("arrivalState: " + arrivalState);
                System.out.println("arrivalCity: " + arrivalCity);
                System.out.println("modelID: " + planeModel);

                System.out.println(request);
                System.out.println(request.getContextPath());
                System.out.println(request.getQueryString());

                double[] departLatAndLong = getLatAndLong(departState, departCity);
                double[] arrivalLatAndLong = getLatAndLong(arrivalState, arrivalCity);
                double distance = Calculations.getDistanceBetweenCitiesInKilometers(
                        departLatAndLong[0], departLatAndLong[1],
                        arrivalLatAndLong[0], arrivalLatAndLong[1]);

                String[] modelSpecifics = getModelSpecifics(planeModel);
                int fuelCap = Integer.parseInt(modelSpecifics[1]);
                double fuelBurnRate = Double.parseDouble(modelSpecifics[2]);
                int averageVelocity = Integer.parseInt(modelSpecifics[3]);


                canTravel = (Calculations.hasEnoughFuel(fuelCap, fuelBurnRate, distance)) ? 1 : 0;
                time = (int) (distance * 60 / averageVelocity);
                distancePrice = Calculations.getDistancePrice(distance);
            }

            String responseText = "{\"canTravel\":" + canTravel + ",\"timed\":" + time + ",\"distancePrice\":" + distancePrice + "}";

            System.out.println(responseText);

            response.setContentType("text/plain");
            response.getWriter().print(responseText);
            System.out.println("-----------------------");

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private static double[] getLatAndLong(String state, String city) {
        double[] value = {0.0, 0.0};
        Connection con = AccountFunctions.OpenDatabase();
        String[] valueString = CityFunctions.getLatAndLong(con, state, city);
        value[0] = Double.parseDouble(valueString[0]);
        value[1] = Double.parseDouble(valueString[1]);
        AccountFunctions.closeConnection(con);

        return value;
    }

    private static String[] getModelSpecifics(String planeModel){
        Connection con = AccountFunctions.OpenDatabase();
        String[] value = AirplaneFunctions.planeModelSpecifics(con, planeModel);
        AccountFunctions.closeConnection(con);
        return value;
    }
}
