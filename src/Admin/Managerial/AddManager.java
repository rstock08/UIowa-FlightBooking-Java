package Admin.Managerial;

import AirFunctions.Email.SendEmail;
import General.AccountFunctions;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.Random;

/**
 * Created by johnn on 4/15/2017.
 */
@WebServlet("/Admin.Managerial.AddManager")
public class AddManager extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String manName = request.getParameter("managerName");
        String manEmail = request.getParameter("managerEmail");
        System.out.println(manName+manEmail);
        String addedStatus = (addManager(manName,manEmail))?"Added":"NotAdded";

        response.setContentType("text/plain");
        System.out.println(addedStatus);
        response.getWriter().print(addedStatus);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private static boolean addManager(String managerName, String managerEmail){
        Connection con = AccountFunctions.OpenDatabase();

        // Added a string to hold the random pass for a short period of time
        String managerPass = randomPassword();
        boolean managerAdded = AccountFunctions.addAccount(con,managerName, managerEmail, managerPass,"MANAGER");
        AccountFunctions.closeConnection(con);

        // Added email functionality
        SendEmail mailer = new SendEmail();
        /*
        try {
            mailer.sendEmail(managerEmail, managerPass);
            System.out.println("Email sent.");
        } catch (Exception ex) {
            System.out.println("Failed to sent email.");
            ex.printStackTrace();
        }
        // End of added email functionality
*/
        return managerAdded;
    }

    private static String randomPassword(){
        String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String beta = "abcdefghijklmnopqrstuvwxyz";
        String numeric = "0123456789";

        Random rand = new Random(System.nanoTime());

        String password = "";

        for(int i=0; i<4; i++){

            password += alpha.charAt(rand.nextInt(alpha.length()));
            password += beta.charAt(rand.nextInt(beta.length()));
            password += beta.charAt(rand.nextInt(beta.length()));
            password += numeric.charAt(rand.nextInt(numeric.length()));

        }

        return password;
    }
}
