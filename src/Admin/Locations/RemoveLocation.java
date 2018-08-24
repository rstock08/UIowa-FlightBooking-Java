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
@WebServlet(name = "RemoveLocation", value="/Admin.Locations.RemoveLocation")
public class RemoveLocation extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String city = request.getParameter("cityID");
        String attempt = "false";

        if(city!=null){
            attempt = attemptRemoval(city)?"true":"false";
        }

        response.setContentType("text/plain");
        response.getWriter().print(attempt);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private static boolean attemptRemoval(String cityID){

        Connection con = AccountFunctions.OpenDatabase();
        boolean attempt = CityFunctions.setActivityForCity(con, cityID, 0);
        AccountFunctions.closeConnection(con);
        return attempt;
    }

}
