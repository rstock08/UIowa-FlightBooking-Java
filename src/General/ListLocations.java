package General;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

/**
 * Created by johnn on 4/17/2017.
 */
@WebServlet(name = "ListLocations", value="/General.ListLocations")
public class ListLocations extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String activity = request.getParameter("activity");
        boolean allList = ((activity == null) || activity.equals("1"));
        String activeList = attemptGetLocations(allList);
        System.out.println(activeList);
        response.setContentType("text/plain");
        response.getWriter().print(activeList);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private static String attemptGetLocations(boolean activity){
        Connection con = AccountFunctions.OpenDatabase();
        String activeList = locationsToJSON(CityFunctions.getLocationList(con, activity));
        AccountFunctions.closeConnection(con);
        return activeList;
    }

    private static String locationsToJSON(ArrayList<ArrayList<String[]>> scList){
        String[] list = {"ID","NM","LAT","LONG"}; // states[city[]] thus if you want chicago. states[#].cities[#] then .cID or .cName
        String jsonList= "{\"states\":[";

        for(int cState=0; cState<scList.size(); cState++){
            jsonList += "{\"ID\":"+cState+",\"NM\":\""+scList.get(cState).get(0)[0]+"\",";
            jsonList += "\"cities\":[";
            for(int cCity=1; cCity<scList.get(cState).size(); cCity++){
                jsonList += "{\"" + list[0] + "\":" + scList.get(cState).get(cCity)[0] + ",";
                jsonList += "\"" + list[1] + "\":\"" + scList.get(cState).get(cCity)[1] + "\",";
                jsonList += "\"" + list[2] + "\":" + scList.get(cState).get(cCity)[2] + ",";
                jsonList += "\"" + list[3] + "\":" + scList.get(cState).get(cCity)[3] + "},";

            }
            jsonList = jsonList.substring(0,jsonList.length()-1)+"]},";
        }

        return jsonList.substring(0,jsonList.length()-1)+"]}";
    }


}


/*


{
    flight: name,
    address: 245 cheesecake,


    residents:[
                {
                    name: billy
                    age: 23
                },
                {
                    name: charlie
                    age: 24
                }
            ]

}

list = JSON.parse(msg)

list.flight -> name
list.address -> 245 cheesecake
list.residents[0].name -> billy







 */
