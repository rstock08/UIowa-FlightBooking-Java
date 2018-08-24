package General;// Imported packages
import General.AccountFunctions;

import java.sql.Connection;
import java.sql.Statement;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.sql.*;

@WebServlet("/General.AdminFunctions")
public class AdminFunctions extends HttpServlet {

    public static String getFlights(){
        Connection con = AccountFunctions.OpenDatabase();
        Statement stmt = null;
        String htmlCode = "";

        try{
            con.setAutoCommit(false);

            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM flights ORDER BY departure_date , departure_location, departure_time, arrival_date, arrival_location,  arrival_time;");
            htmlCode += "<table class=\"admin_flight_table\"><tr>";
            htmlCode += "<th><b>Depart</b></th>";
            htmlCode += "<th></th>";
            htmlCode += "<th></th>";
            htmlCode += "<th><b>Arrive</b></th>";
            htmlCode += "<th></th>";
            htmlCode += "<th></th>";
            htmlCode += "<th><b>Action</b></th>";
            htmlCode += "</tr>";

            while(rs.next()){
                htmlCode += "<form action=\"General.AdminFunctions\">";
                htmlCode += "<input type=\"hidden\" name=\"flightID\" value=\""+rs.getString("Flight_ID") + "\" >";
                htmlCode += "<tr>";
                htmlCode += "<td>" + rs.getString("Departure_Date") + "</td>";
                htmlCode += "<td>" + rs.getString("Departure_Location") + "</td>";
                htmlCode += "<td>" + rs.getString("Departure_Time") + "</td>";
                htmlCode += "<td>" + rs.getString("Arrival_Date") + "</td>";
                htmlCode += "<td>" + rs.getString("Arrival_Location") + "</td>";
                htmlCode += "<td>" + rs.getString("Arrival_Time") + "</td>";
                htmlCode += "<td> <button type=\"submit\" name=\"editFlightButton\">Edit</button></td>";
                htmlCode += "</tr>";
                htmlCode += "</form>";
            }

            htmlCode += "</table>";

            con.close();

        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);

        }

        return htmlCode;
    }

    public static String getPlanes(){
        Connection con = AccountFunctions.OpenDatabase();
        Statement stmt = null;
        String htmlCode = "";

        try{
            con.setAutoCommit(false);

            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM planes ORDER BY plane_type, capacity, classes;");
            htmlCode += "<table class=\"admin_planes_table\"><tr>";
            htmlCode += "<th><b>Plane Type</b></th>";
            htmlCode += "<th><b>Capacity</b></th>";
            htmlCode += "<th><b>Classes</b></th>";
            htmlCode += "<th><b>Action</b></th>";
            htmlCode += "</tr>";

            while(rs.next()){
                htmlCode += "<form action=\"General.AdminFunctions\">";
                htmlCode += "<input type=\"hidden\" name=\"planeID\" value=\""+rs.getString("id") + "\" >";
                htmlCode += "<tr>";
                htmlCode += "<td>" + rs.getString("Plane_Type") + "</td>";
                htmlCode += "<td>" + rs.getString("Capacity") + "</td>";
                htmlCode += "<td>" + rs.getString("Classes") + "</td>";
                htmlCode += "<td> <button type=\"submit\" name=\"editPlaneButton\">Edit</button></td>";
                htmlCode += "</tr>";
                htmlCode += "</form>";
            }

            htmlCode += "</table>";

            con.close();

        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);

        }

        return htmlCode;
    }

}

