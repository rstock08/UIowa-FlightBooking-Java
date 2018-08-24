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
 * Created by johnn on 4/18/2017.
 */
@WebServlet(name = "BasicPlaneModels", value="/General.BasicPlaneModels")
public class BasicPlaneModels extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String basicModels = attemptBasicPlaneModels();
        System.out.println(basicModels);
        response.setContentType("text/plain");
        response.getWriter().print(basicModels);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // returns only model name and model ID to use in search
    }

    private static String attemptBasicPlaneModels(){
        Connection con = AccountFunctions.OpenDatabase();
        String basicModelList = basicModelListToJSON(AirplaneFunctions.getBasicModelList(con));
        AccountFunctions.closeConnection(con);
        return basicModelList;
    }

    private static String basicModelListToJSON(ArrayList<String[]> basicList){
        String[] list = {"ID","NM","BP"};
        String jsonBasicList = "{\"models\":[";
        for(int count=0; count<basicList.size(); count++){
            jsonBasicList += "{\""+list[0]+"\":"+basicList.get(count)[0]+",";
            jsonBasicList += "\""+list[1]+"\":\""+basicList.get(count)[1]+"\",";
            jsonBasicList += "\""+list[2]+"\":"+basicList.get(count)[2]+"},";

        }
        return jsonBasicList.substring(0,jsonBasicList.length()-1)+"]}";
    }
}
