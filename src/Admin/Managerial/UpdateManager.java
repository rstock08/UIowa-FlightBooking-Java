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
@WebServlet(name="UpdateManager", value="/Admin.Managerial.UpdateManager")
public class UpdateManager extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("managerModalName");
        String id = request.getParameter("managerModalID");
        String email = request.getParameter("managerModalEmail");
        String password = request.getParameter("managerModalPassword");
        System.out.println("no error");

        String isUpdated = (updateManager(name,id,email,password))?"Updated":"Not Updated";
        System.out.println("Entry has: "+isUpdated);
        response.setContentType("text/plain");
        response.getWriter().print(isUpdated);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private static boolean updateManager(String id,String name, String email, String password){
        Connection con = AccountFunctions.OpenDatabase();
        boolean isUpdated =AccountFunctions.updateAccount( con, name, id, email, password, "MANAGER");
        AccountFunctions.closeConnection(con);
        return isUpdated;
    }

}
