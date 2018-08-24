package Admin.Locations;

import General.AccountFunctions;
import General.CityFunctions;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by johnn on 4/16/2017.
 */
@WebServlet(name = "AddLocation", value="/Admin.Locations.AddLocation")
public class AddLocation extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String state = request.getParameter("stateName");
        String city = request.getParameter("cityName");

        System.out.println("State="+state);
        System.out.println("City="+city);
        String attempt = "false";

        if(state!=null && city!=null){
            attempt = attemptAddLocation(state,city)?"true":"false";
        }

        response.setContentType("text/plain");
        response.getWriter().print(attempt);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private static boolean attemptAddLocation(String state, String city){
        Connection con = AccountFunctions.OpenDatabase();
        boolean attempt = CityFunctions.setActivityForCity(con,city,1);
        AccountFunctions.closeConnection(con);
        return attempt;
    }
}
