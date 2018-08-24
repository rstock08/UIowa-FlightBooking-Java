package General;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by crisi_000 on 4/21/2017.
 */
public class FlightHistory {

    public static String getBookedFlights(Connection con, int userID){
        Statement stmt = null;
        String htmlCode = "";

        try{
            con.setAutoCommit(false);

            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM bookedFlights WHERE userID= '" + userID + "';");

            if (rs.next()) {
                htmlCode += "<table class=\"admin_man_table\"><tr>";
                htmlCode += "<th><b>Flight</b></th>";
                htmlCode += "<th><b>Total</b></th>";
                htmlCode += "<th><b>Economy</b></th>";
                htmlCode += "<th><b>Business</b></th>";
                htmlCode += "<th><b>First </b></th>";
                htmlCode += "<th><b>Departure</b></th>";
                htmlCode += "<th><b>Departure</b></th>";
                htmlCode += "<th><b>Arrival</b></th>";
                htmlCode += "<th><b>Arrival</b></th>";
                htmlCode += "</tr>";
                htmlCode += "<tr>";
                htmlCode += "<th><b>Number</b></th>";
                htmlCode += "<th><b>Tickets</b></th>";
                htmlCode += "<th><b>Tickets</b></th>";
                htmlCode += "<th><b>Tickets</b></th>";
                htmlCode += "<th><b>Tickets</b></th>";
                htmlCode += "<th><b>Date</b></th>";
                htmlCode += "<th><b>Time</b></th>";
                htmlCode += "<th><b>Date</b></th>";
                htmlCode += "<th><b>Time</b></th>";
                htmlCode += "</tr>";

                while (rs.next()) {
                    int flightnum = rs.getInt("flightID");
                    ResultSet rs2 = stmt.executeQuery("SELECT * FROM flights where flight_id = " + flightnum + ";");

                    if(rs2.next()) {
                        htmlCode += "<tr>";
                        htmlCode += "<td>" + flightnum + "</td>";
                        htmlCode += "<td>" + rs.getString("totalTickets") + "</td>";
                        htmlCode += "<td>" + rs.getString("ticketsEconomy") + "</td>";
                        htmlCode += "<td>" + rs.getString("ticketsBusiness") + "</td>";
                        htmlCode += "<td>" + rs.getString("ticketsFirst") + "</td>";
                        htmlCode += "<td>" + rs2.getString("departure_date") + "</td>";
                        htmlCode += "<td>" + rs2.getString("departure_time") + "</td>";
                        htmlCode += "<td>" + rs2.getString("arrival_date") + "</td>";
                        htmlCode += "<td>" + rs2.getString("arrival_time") + "</td>";
                        htmlCode += "</tr>";
                    }
                }

                htmlCode += "</table>";

                con.close();
            }

        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);

        }

        System.out.println(htmlCode);

        return htmlCode;
    }

    public static String getFlights(int userID){
        String htmlCode = "";
        try {
            Connection con = AccountFunctions.OpenDatabase();
            htmlCode = getBookedFlights(con, userID);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return htmlCode;
    }

    public static int getUserID(String email){
        int userId = 0;
        try {
            Connection con = AccountFunctions.OpenDatabase();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM accounts WHERE email = '" + email + "';");
            if (rs.next()) {
                userId = rs.getInt("ID");
            }
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return userId;
    }

    public static ArrayList<String[]> getFlightList(Connection con, String userID){
        ArrayList<String[]> basicModelInfo = new ArrayList<String[]>();
        Statement stmt = null;

        try{
            con.setAutoCommit(false);
            stmt = con.createStatement();
            int id =  AccountFunctions.getID(con,userID);
            ResultSet rs = stmt.executeQuery("SELECT * FROM bookedFlights WHERE userID= " + id + ";");
            while(rs.next()){
                int flightnum = rs.getInt("flightID");
                Statement stmt2 = con.prepareStatement("SELECT * FROM flights where flight_id = " + flightnum + ";");
                ResultSet rs2 = stmt2.executeQuery("SELECT * FROM flights where flight_id = " + flightnum + ";");

                String[] tempInfo = new String[11];
                tempInfo[0] = new String() + flightnum;
                tempInfo[1] = rs.getString("totalTickets");
                tempInfo[2] = rs.getString("ticketsEconomy");
                tempInfo[3] = rs.getString("ticketsBusiness");
                tempInfo[4] = rs.getString("ticketsFirst");
                rs2.next();
                tempInfo[5] = rs2.getString("departure_date");
                tempInfo[6] = rs2.getString("departure_Time");
                tempInfo[7] = getLocationName(con, rs2.getInt("departure_location"));
                tempInfo[8] = rs2.getString("arrival_date");
                tempInfo[9] = rs2.getString("arrival_time");
                tempInfo[10] = getLocationName(con,rs2.getInt("arrival_location"));
                rs2.close();
                stmt2.close();
                basicModelInfo.add(tempInfo);
            }
            rs.close();
            stmt.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return basicModelInfo;
    }

    public static String getLocationName(Connection con, int locID){
        String name = "";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM cities WHERE ID = " + locID + ";");
            if (rs.next()) {
                name = rs.getString("CITYNAME");
            } else {
                name = "Unknown";
            }
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return name;
    }

    public static ArrayList<String[]> getActiveFlightList(Connection con, String userID){
        ArrayList<String[]> basicModelInfo = new ArrayList<String[]>();
        Statement stmt = null;

        try{
            con.setAutoCommit(false);
            stmt = con.createStatement();
            int id =  AccountFunctions.getID(con,userID);
            ResultSet rs = stmt.executeQuery("SELECT * FROM bookedFlights WHERE userID= " + id + " AND Completed = 0;");
            while(rs.next()){
                int flightnum = rs.getInt("flightID");
                Statement stmt2 = con.prepareStatement("SELECT * FROM flights where flight_id = " + flightnum + ";");
                ResultSet rs2 = stmt2.executeQuery("SELECT * FROM flights where flight_id = " + flightnum + ";");
                String[] tempInfo = new String[11];
                tempInfo[0] = new String() + flightnum;
                tempInfo[1] = rs.getString("totalTickets");
                tempInfo[2] = rs.getString("ticketsEconomy");
                tempInfo[3] = rs.getString("ticketsBusiness");
                tempInfo[4] = rs.getString("ticketsFirst");
                rs2.next();
                tempInfo[5] = rs2.getString("departure_date");
                tempInfo[6] = rs2.getString("departure_Time");
                tempInfo[7] = getLocationName(con, rs2.getInt("departure_location"));
                tempInfo[8] = rs2.getString("arrival_date");
                tempInfo[9] = rs2.getString("arrival_time");
                tempInfo[10] = getLocationName(con,rs2.getInt("arrival_location"));
                rs2.close();
                stmt2.close();
                basicModelInfo.add(tempInfo);
            }
            rs.close();
            stmt.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return basicModelInfo;
    }
}
