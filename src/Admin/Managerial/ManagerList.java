package Admin.Managerial;

import General.AccountFunctions;
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
@WebServlet("/Admin.Managerial.ManagerList")
public class ManagerList extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("At GetManagerList Servlet");
        response.setContentType("text/plain");
        response.getWriter().print(formManagerTable());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private static String formManagerTable(){
        Connection con = AccountFunctions.OpenDatabase();
        String table = managerListToJSON(AccountFunctions.getManagerList(con));
        AccountFunctions.closeConnection(con);
        return table;

    }

    private static String managerListToJSON(ArrayList<String[]> managerList){
        String[] list = {"manID","manName","manEmail","manPass"};
        String jsonManagers = "{\"manager\":[";
        for (int i = 0; i < managerList.size(); i++) {
            jsonManagers += "{\"" + list[0] + "\":" + managerList.get(i)[0] + "";
            for (int j = 1; j < managerList.get(i).length; j++) {
                jsonManagers += ",\"" + list[j] + "\":\"" + managerList.get(i)[j]+"\"";
            }
            jsonManagers += "},";

        }
        return jsonManagers.substring(0,jsonManagers.length()-1)+"]}";
    }



}
