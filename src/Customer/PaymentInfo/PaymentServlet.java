package Customer.PaymentInfo;


import General.AccountFunctions;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Charlie on 4/25/2017.
 */

@WebServlet(name = "PaymentServlet", value = "/Customer.PaymentInfo.PaymentServlet")
public class PaymentServlet extends HttpServlet{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cardName = request.getParameter("cardName");
        String cardNumber = request.getParameter("cardNumber");
        String securityCode = request.getParameter("securityCode");
        String address = request.getParameter("address");
        String state = request.getParameter("state");
        String city = request.getParameter("city");
        String zipCode = request.getParameter("zipCode");
        String month = request.getParameter("expMonth");
        String year = request.getParameter("expYear");
        String phoneNumber = request.getParameter("phoneNumber");
        String userEmail = request.getSession().getAttribute("userEmail").toString();

        String expDate = year + "-" + month + "-01";

        Connection con = AccountFunctions.OpenDatabase();
        PaymentFunctions.addPayment(con, AccountFunctions.getID(con, userEmail), cardName, cardNumber, expDate, securityCode, address, city, state, zipCode, phoneNumber);
        AccountFunctions.closeConnection(con);



        response.sendRedirect("payment.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }


}
