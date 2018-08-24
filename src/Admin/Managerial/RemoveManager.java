package Admin.Managerial;

import General.AccountFunctions;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

/**
 * Created by johnn on 4/15/2017.
 */
@WebServlet(name = "RemoveManager", value="/Admin.Managerial.RemoveManager")
public class RemoveManager extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String managerID = request.getParameter("removeManager");
        String isRemoved = (removeManager(managerID))?"Removed":"Not Removed";
        System.out.println("Entry was "+isRemoved);
        response.setContentType("text/plain");
        response.getWriter().print(isRemoved);
    }

    private static boolean removeManager( String managerID){
        Connection con = AccountFunctions.OpenDatabase();
        boolean isRemoved = AccountFunctions.deleteAccount(con,managerID);
        AccountFunctions.closeConnection(con);
        return isRemoved;
    }

}
