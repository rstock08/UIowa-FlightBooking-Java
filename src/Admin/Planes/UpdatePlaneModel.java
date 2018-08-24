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
@WebServlet(name = "UpdatePlaneModel" , value="/Admin.Planes.UpdatePlaneModel")
public class UpdatePlaneModel extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String modelID = request.getParameter("modelModalID");
        String planeModel = request.getParameter("modelModal");
        String modelCapacity = request.getParameter("modelModalCapacity");
        String modelFuel = request.getParameter("modelModalFuel");
        String modelBurn = request.getParameter("modelModalBurn");
        String modelVelocity = request.getParameter("modelModalVelocity");

        String economy = request.getParameter("hasEconomyModal");
        String business = request.getParameter("hasBusinessModal");
        String firstclass = request.getParameter("hasFirstModal");
        String seatsEconomy = request.getParameter("seatsEconomyRangeModal");
        String seatsBusiness = request.getParameter("seatsBusinessRangeModal");
        String seatsFirst = request.getParameter("seatsFirstRangeModal");
        String wasUpdated = "false";

        if(planeModel==null){
            wasUpdated = "false";
        }else{
            planeModel = planeModel.toUpperCase();
            wasUpdated = "true";
        }

        if(economy==null){
            economy = "0";
            seatsEconomy = "0";
        }else{economy="1";}

        if(business==null){
            business = "0";
            seatsBusiness = "0";
        }else{business="1";}
        if(firstclass==null){
            firstclass = "0";
            seatsFirst = "0";
        }else{firstclass="1";}

        System.out.println("-----Updating----------");
        System.out.println("ID: " +modelID);
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


        if(wasUpdated.equals("true")) {
            wasUpdated = (attemptUpdatePlaneModel(modelID,planeModel, modelCapacity, modelFuel, modelBurn, modelVelocity,
                    economy, business, firstclass, seatsEconomy, seatsBusiness, seatsFirst)) ? "true" : "false";
        }
        response.setContentType("text/plain");
        response.getWriter().print(wasUpdated);


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private static boolean attemptUpdatePlaneModel(String modelID,String planeModel, String modelCapacity, String modelFuel,
                                                String modelBurn, String modelVelocity, String hasEconomy,
                                                String hasBusiness, String hasFirst, String seatsEconomy,
                                                String seatsBusiness, String seatsFirst){
        //---//
        Connection con = AccountFunctions.OpenDatabase();
        boolean modelUpdated = AirplaneFunctions.updatePlaneModel(con,modelID,planeModel,modelCapacity,modelFuel, modelBurn,
                modelVelocity, hasEconomy, hasBusiness, hasFirst, seatsEconomy, seatsBusiness, seatsFirst,
                Calculations.calcBasePrice(modelFuel,modelCapacity));
        AccountFunctions.closeConnection(con);
        return modelUpdated;
    }
}
