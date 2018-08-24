package General;// Imported packages
import General.AccountFunctions;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;

@WebServlet(name="Flight Query", value="/General.FlightQuery")
public class FlightQuery extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("Getting flights...");
        System.out.println("----------");
        String dState = request.getParameter("departState");
        System.out.println("dState="+dState);

        String dCity = request.getParameter("departCity");
        System.out.println("dCity="+dCity);

        String dDate = request.getParameter("departDate");
        System.out.println("dDate="+dDate);

        String aState = request.getParameter("arriveState");
        System.out.println("aState="+aState);

        String aCity = request.getParameter("arriveCity");
        System.out.println("aCity="+aCity);

        String model = request.getParameter("planeModel");
        System.out.println("model="+model);

        String tickets = request.getParameter("numberPassengers");
        System.out.println("tickets="+tickets);

        String pref = request.getParameter("preference");
        String travelType = request.getParameter("travelType");
        System.out.println("travelType="+travelType);

        String multiStop = request.getParameter("travelLayover");
        System.out.println("multiStop="+multiStop);

        String returnDate = request.getParameter("returnDate");

        String[] dateArray = dDate.split("/");
        dDate = dateArray[2] + "-"+dateArray[0]+"-"+dateArray[1];

        if(travelType==null){
            travelType="-1";
        }
        if(travelType.equals("-1") || travelType.equals("0")){
            returnDate = "0";
        }

        if(returnDate!=null && !returnDate.equals("0")) {
            String[] returnArray = returnDate.split("/");
            returnDate = returnArray[2] + "-" + returnArray[0] + "-" + returnArray[1];
        }
        System.out.println("returnDate="+returnDate);

        System.out.println("----------");


        String queryList = attemptFlightQuery(dCity,aCity,dDate,model,tickets,pref,travelType,multiStop, returnDate);
        System.out.println(queryList);

        response.setContentType("text/plain");
        response.getWriter().print(queryList);

    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ArrayList<String> allFlights = new ArrayList<>();

        for(int id=1; id<7; id++){
            String flight = request.getParameter("flight"+id);
            if(!flight.equals("null")){
                allFlights.add(flight);
            }
        }

        System.out.println(allFlights);

        String bookingInfo = attemptBookingQuery(allFlights);

        System.out.println(bookingInfo);

        response.setContentType("text/plain");
        response.getWriter().print(bookingInfo);
    }

    public static String getDateAndTimeSrc(){
        String htmlCode = "";
        htmlCode += "<link rel=\"stylesheet\" href=\"https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css\">";
        htmlCode += "<script src=\"https://code.jquery.com/jquery-1.12.4.js\"></script>";
        htmlCode += "<script src=\"https://code.jquery.com/ui/1.12.1/jquery-ui.js\"></script>";
        htmlCode += "<link rel=\"stylesheet\" href=\"//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.css\">";
        htmlCode += "<script src=\"//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.js\"></script>";
        return htmlCode;
    }

    public static String addPickers(String type){
        String htmlCode = "";
        htmlCode += "<script> $( function() { $( \"#"+type+"datepicker\" ).datepicker();} ); </script>";
        htmlCode += "<script> $( function() { $(\"#"+type+"timepicker\").timepicker();}) </script>";
        return htmlCode;
    }

    private static String attemptFlightQuery(String dCity, String aCity, String dDate, String model, String tickets, String pref, String travelType, String multiStop, String returnDate){
        Connection con = AccountFunctions.OpenDatabase();
        String queryList = queryToJSON(FlightsFunctions.getSuperFlightQuery(con,dCity,aCity,dDate,model,tickets,pref,travelType, multiStop, returnDate), dCity);
        AccountFunctions.closeConnection(con);
        return queryList;
    }

    private static String attemptBookingQuery(ArrayList<String> allFlights){
        Connection con = AccountFunctions.OpenDatabase();
        String allFlightsInfo = bookingToJSON(FlightsFunctions.getBookingQuery(con,allFlights));
        AccountFunctions.closeConnection(con);
        return allFlightsInfo;
    }

    private static String queryToJSON(ArrayList<ArrayList<ArrayList<String>>> queryList, String dCity){
        String[] list = {"fID","pID","mID","dLoc","aLoc","aEcon","aBus","aFirst","Dem","DP","dDate","dTime","aDate","aTime"};

        String jsonQuery = "{\"trips\":[  ";

        for(int trip=0; trip<queryList.size(); trip++){

            jsonQuery += "{\"flights\":[  ";

            for(int flight=0; flight<queryList.get(trip).size(); flight++){

                jsonQuery +="{";
                for(int infoNumbers=0; infoNumbers<queryList.get(trip).get(flight).size()-4;infoNumbers++){
                    jsonQuery += "\""+list[infoNumbers]+"\":"+queryList.get(trip).get(flight).get(infoNumbers)+",";
                }
                for(int infoStrings=queryList.get(trip).get(flight).size()-4;infoStrings<queryList.get(trip).get(flight).size();infoStrings++){
                    jsonQuery += "\""+list[infoStrings]+"\":\""+queryList.get(trip).get(flight).get(infoStrings)+"\",";
                }
                jsonQuery = jsonQuery.substring(0,jsonQuery.length()-1)+"},";

            }

            jsonQuery = jsonQuery.substring(0,jsonQuery.length()-1)+"]},";
        }

        return jsonQuery = jsonQuery.substring(0,jsonQuery.length()-1)+"]}";
    }


    private static String bookingToJSON(ArrayList<ArrayList<String>> bookingList){
        String[] list = {"fID","pID","mID","dLoc","aLoc","aEcon","aBus","aFirst","Dem","DP","dDate","dTime","aDate","aTime"};
        String jsonBooking = "{\"flights\":[  ";
        for(int i=0; i<bookingList.size(); i++){
            jsonBooking +="{";
            for(int j=0; j<bookingList.get(i).size()-4;j++){
                jsonBooking += "\""+list[j]+"\":"+bookingList.get(i).get(j)+",";
            }
            for(int k=bookingList.get(i).size()-4;k<bookingList.get(i).size();k++){
                jsonBooking += "\""+list[k]+"\":\""+bookingList.get(i).get(k)+"\",";
            }
            jsonBooking = jsonBooking.substring(0,jsonBooking.length()-1)+"},";
        }
        return jsonBooking.substring(0,jsonBooking.length()-1)+"]}";
    }
}

