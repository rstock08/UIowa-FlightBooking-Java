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
import java.util.ArrayList;

/**
 * Created by johnn on 4/16/2017.
 */
@WebServlet(name = "PlaneList", value="/Admin.Planes.PlaneList")
public class PlaneList extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String planeList = attemptGetPlaneList();
        System.out.println(planeList);
        response.setContentType("text/plain");
        response.getWriter().print(planeList);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private static String attemptGetPlaneList(){
        Connection con = AccountFunctions.OpenDatabase();
        String planeList = planesListToJSON(AirplaneFunctions.getPlaneList(con));
        AccountFunctions.closeConnection(con);
        return planeList;
    }

    private static String planesListToJSON(ArrayList<String[]> planeList){
        System.out.println("parsing model list to JSON...");
        String[] list = {"pID","pModID"};
        String jsonPlanes = "{\"planes\":[ ";
        for (int i = 0; i < planeList.size(); i++) {
            jsonPlanes += "{\""+list[0]+"\":"+planeList.get(i)[0]+",";
            jsonPlanes += "\""+list[1]+"\":"+planeList.get(i)[1]+"},";
        }
        System.out.println("finished parsing plane list to JSON");
        return jsonPlanes.substring(0,jsonPlanes.length()-1)+"]}";
    }
}
