package Admin.Planes;

import General.AccountFunctions;
import General.AirplaneFunctions;
import General.Calculations;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

/**
 * Created by johnn on 4/16/2017.
 */
@WebServlet(name = "AddPlaneModel", value="/Admin.Planes.AddPlaneModel")
public class AddPlaneModel extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String planeModel = request.getParameter("planeModel");
        String modelCapacity = request.getParameter("modelCapacity");
        String modelFuel = request.getParameter("modelFuel");
        String modelBurn = request.getParameter("modelBurn");
        String modelVelocity = request.getParameter("modelVelocity");

        String economy = request.getParameter("hasEconomyClass");
        String business = request.getParameter("hasBusinessClass");
        String firstclass = request.getParameter("hasFirstClass");
        String seatsEconomy = request.getParameter("seatsEconomy");
        String seatsBusiness = request.getParameter("seatsBusiness");
        String seatsFirst = request.getParameter("seatsFirst");
        String wasAdded = "false";

        if(planeModel==null){
            wasAdded = "false";
        }else{
            planeModel = planeModel.toUpperCase();
            wasAdded = "true";
        }

        if(economy==null){
            economy = "false";
            seatsEconomy = "0";
        }
        if(business==null){
            business = "false";
            seatsBusiness = "0";
        }
        if(firstclass==null){
            firstclass = "false";
            seatsFirst = "0";
        }

        System.out.println("--------Adding---------");
        System.out.println("model: "+planeModel);
        System.out.println("capacity: "+modelCapacity);
        System.out.println("Fuel: "+modelFuel);
        System.out.println("burn: "+modelBurn);
        System.out.println("velocity: "+modelVelocity);
        System.out.println("hEcon: "+economy);
        System.out.println("hBus: "+business);
        System.out.println("hFirst: "+firstclass);
        System.out.println("sEcon: "+seatsEconomy);
        System.out.println("sBus: "+seatsBusiness);
        System.out.println("sFirst: "+seatsFirst);
        System.out.println("------------------------");


        if(wasAdded.equals("true")) {
            wasAdded = (attemptAddPlaneModel(planeModel, modelCapacity, modelFuel, modelBurn, modelVelocity,
                    economy, business, firstclass, seatsEconomy, seatsBusiness, seatsFirst)) ? "true" : "false";
        }
        response.setContentType("text/plain");
        response.getWriter().print(wasAdded);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private static boolean attemptAddPlaneModel(String planeModel, String modelCapacity, String modelFuel,
                                                String modelBurn, String modelVelocity, String hasEconomy,
                                                String hasBusiness, String hasFirst, String seatsEconomy,
                                                String seatsBusiness, String seatsFirst){
        //---//
        Connection con = AccountFunctions.OpenDatabase();
        boolean modelAdded = AirplaneFunctions.addPlaneModel(con,planeModel,modelCapacity,modelFuel, modelBurn,
                modelVelocity, hasEconomy, hasBusiness, hasFirst, seatsEconomy, seatsBusiness, seatsFirst,
                Calculations.calcBasePrice(modelFuel,modelCapacity));
        AccountFunctions.closeConnection(con);
        return modelAdded;
    }

}
