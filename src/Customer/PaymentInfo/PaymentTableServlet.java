package Customer.PaymentInfo;

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
 * Created by Charlie on 5/1/2017.
 */

@WebServlet(name = "PaymentTableServlet", value = "/Customer.PaymentInfo.PaymentTableServlet")
public class PaymentTableServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userEmail = request.getSession().getAttribute("userEmail").toString();
        String basicList = attemptPaymentList(userEmail);
        response.setContentType("text/plain");
        response.getWriter().print(basicList);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private static String attemptPaymentList(String userEmail){
        Connection con = AccountFunctions.OpenDatabase();
        String paymentList = paymentListToJSON(PaymentFunctions.displayPaymentInfo(con,AccountFunctions.getID(con,userEmail)));
        AccountFunctions.closeConnection(con);
        return paymentList;
    }

    public static String paymentListToJSON(ArrayList<String[]> basicList){
        String[] list = {"CardName","CardNumber","ExpDate","SecurityCode","Address","State",
                "City","ZipCode","Phonenumber"};
        String jsonBasicList = "{\"payment\":[";
        for(int count=0; count<basicList.size(); count++){
            jsonBasicList += "{\""+list[0]+"\":\""+basicList.get(count)[0]+"\",";
            jsonBasicList += "\""+list[1]+"\":\""+basicList.get(count)[1]+"\",";
            jsonBasicList += "\""+list[2]+"\":\""+basicList.get(count)[2]+"\",";
            jsonBasicList += "\""+list[3]+"\":\""+basicList.get(count)[3]+"\",";
            jsonBasicList += "\""+list[4]+"\":\""+basicList.get(count)[4]+"\",";
            jsonBasicList += "\""+list[5]+"\":\""+basicList.get(count)[5]+"\",";
            jsonBasicList += "\""+list[6]+"\":\""+basicList.get(count)[6]+"\",";
            jsonBasicList += "\""+list[7]+"\":\""+basicList.get(count)[7]+"\",";
            jsonBasicList += "\""+list[8]+"\":\""+basicList.get(count)[8]+"\"},";

        }
        jsonBasicList = jsonBasicList.substring(0,jsonBasicList.length()-1)+"]}";
        System.out.println(jsonBasicList);
        return jsonBasicList;
    }
}
