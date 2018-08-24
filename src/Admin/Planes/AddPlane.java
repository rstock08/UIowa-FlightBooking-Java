package Admin.Planes;

import General.AccountFunctions;
import General.AirplaneFunctions;

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
@WebServlet(name = "AddPlane", value="/Admin.Planes.AddPlane")
public class AddPlane extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String isAdded = (attemptAddPlane(request.getParameter("modelID")))?"Added":"not Added";
        response.setContentType("text/plain");
        response.getWriter().print(isAdded);
    }

    private static boolean attemptAddPlane(String modelID){
        Connection con = AccountFunctions.OpenDatabase();
        boolean isAdded = AirplaneFunctions.addAirplane(con,modelID);
        AccountFunctions.closeConnection(con);
        return isAdded;
    }
}
