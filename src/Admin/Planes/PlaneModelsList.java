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
@WebServlet(name = "PlaneModelsList", value="/Admin.Planes.PlaneModelsList")
public class PlaneModelsList extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String modelList = attemptRetrievePlaneModelList();
        System.out.println(modelList);
        response.setContentType("text/plain");
        response.getWriter().print(modelList);
    }

    private static String attemptRetrievePlaneModelList(){
        System.out.println("attempting retrieval");
        Connection con = AccountFunctions.OpenDatabase();
        String modelList = modelListToJSON(AirplaneFunctions.getPlaneModelsList(con));
        AccountFunctions.closeConnection(con);
        System.out.println("finished retrieval");
        return modelList;
    }

    private static String modelListToJSON(ArrayList<String[]> modelList){
        System.out.println("parsing model list to JSON...");
        String[] list = {"mModel","mID","mCap","mFuel","mBurn","mAvgV","hEcon","hBus","hFirst","sEcon","sBus","sFirst","mBP"};
        String jsonModels = "{\"models\":[ ";
        for (int i = 0; i < modelList.size(); i++) {
            jsonModels += "{\"" + list[0] + "\":\"" + modelList.get(i)[0] + "\"";
            for (int j = 1; j < modelList.get(i).length; j++) {
                jsonModels += ",\"" + list[j] + "\":"+ modelList.get(i)[j];
            }
            jsonModels += "},";

        }
        System.out.println("finished parsing model list to JSON");
        return jsonModels.substring(0,jsonModels.length()-1)+"]}";
    }

}
