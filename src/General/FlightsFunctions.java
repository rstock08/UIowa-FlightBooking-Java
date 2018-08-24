package General;

import General.AccountFunctions;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by crisi_000 on 3/30/2017.
 */
public class FlightsFunctions {
    public static void main( String[]  args){
        Connection connection = null;
        try {
            boolean working = false;
            connection = AccountFunctions.OpenDatabase();
            //CreateFlightsTable(connection);
            // add flight will not work until the city references (1 and 2) are defined in the city table
            //AddFlight(connection, 1,new Date(2005,04,15), new Time(5),1, new Date(04,04,2017), new Time(12), 2, 250);

            //deleteFlight(connection, 1);
            //working = checkLogin(connection,"test@gmail.com","test");
            //System.out.println(working);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }




    public static void createFlightsTable(Connection con)
    {
        Connection c = con;
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            String sql = "DROP TABLE flights;";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE `accounts`.`flights` (\n" +
                    "  `Flight_ID` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `Plane_ID` INT NOT NULL,\n" +
                    "  `DEPARTURE_DATE` DATE NOT NULL,\n" +
                    "  `DEPARTURE_TIME` TIME NOT NULL,\n" +
                    "  `DEPARTURE_LOCATION` INT NOT NULL,\n" +
                    "  `ARRIVAL_DATE` DATE NOT NULL,\n" +
                    "  `ARRIVAL_TIME` TIME NOT NULL,\n" +
                    "  `ARRIVAL_LOCATION` INT NOT NULL,\n" +
                    "  `PRICE` INT NOT NULL,\n" +
                    "  PRIMARY KEY (`Flight_ID`),\n" +
                    "  INDEX `PLANE_idx` (`Plane_ID` ASC),\n" +
                    "  INDEX `DEPARTURE_idx` (`DEPARTURE_LOCATION` ASC),\n" +
                    "  INDEX `ARRIVAL_idx` (`ARRIVAL_LOCATION` ASC),\n" +
                    "  CONSTRAINT `PLANE`\n" +
                    "    FOREIGN KEY (`Plane_ID`)\n" +
                    "    REFERENCES `accounts`.`planes` (`ID`)\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION,\n" +
                    "  CONSTRAINT `DEPARTURE`\n" +
                    "    FOREIGN KEY (`DEPARTURE_LOCATION`)\n" +
                    "    REFERENCES `accounts`.`cities` (`ID`)\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION,\n" +
                    "  CONSTRAINT `ARRIVAL`\n" +
                    "    FOREIGN KEY (`ARRIVAL_LOCATION`)\n" +
                    "    REFERENCES `accounts`.`cities` (`ID`)\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION);";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }




    public static boolean addFlight(Connection con, String planeID, String deptDate, String deptTime, String deptLocation,
                                 String arrivalDate, String arrivalTime, String arrivalLocation, String demand, String distancePrice, String timePeriod, String occurrences)
    //i assume that if a plane has first class, it also has business and coach. If it has business it also has coach
    {
        boolean isFlightAdded = false;
        Statement stmt = null;
        try {
            con.setAutoCommit(false);
            stmt = con.createStatement();
            System.out.println("About to get result set.");
            ResultSet rs = stmt.executeQuery("Select seatsEconomy, seatsBusiness, seatsFirst from planes JOIN planemodels on planes.model_id=planemodels.ID Where planes.ID="+planeID+";");
            if(rs.next()){
                String aEcon = rs.getString("seatsEconomy");
                String aBus = rs.getString("seatsBusiness");
                String aFirst = rs.getString("seatsFirst");
                System.out.println("Error about to happen");

                int tPeriod = Integer.parseInt(timePeriod);
                int occur = Integer.parseInt(occurrences);

                LocalDate currentDepart = LocalDate.parse(deptDate);
                LocalDate currentArrival = LocalDate.parse(arrivalDate);
                for(int count=0; count<tPeriod; count+=occur) {
                    LocalDate newDepart = currentDepart.plusDays(count);
                    LocalDate newArrival = currentArrival.plusDays(count);
                    // calc new date


                    Statement stmtInner = null;
                    stmtInner = con.createStatement();
                    boolean isAvailable = false;
                    ResultSet innerRS = stmtInner.executeQuery("Select * FROM flights WHERE plane_id="+planeID+";");
                    isAvailable = true;
                    if (innerRS.next()) {
                        do {
                            if (!AirplaneFunctions.checkDates(innerRS.getString("Departure_Date"),
                                    innerRS.getString("Arrival_Date"), newDepart.toString(), newArrival.toString())) {

                                isAvailable = AirplaneFunctions.checkTimes(innerRS.getString("Departure_time"),
                                        innerRS.getString("Arrival_time"), deptTime, arrivalTime);
                            }
                        } while (innerRS.next());
                    } else {
                        isAvailable = true;
                    }

                    if(isAvailable){
                        String sql = "INSERT INTO FLIGHTS (PLANE_ID, DEPARTURE_DATE, DEPARTURE_TIME, DEPARTURE_LOCATION," +
                                "ARRIVAL_DATE, ARRIVAL_TIME, ARRIVAL_LOCATION, availableECONOMY, availableBUSINESS, availableFIRST," +
                                "takenECONOMY, takenBUSINESS, takenFIRST, DEMAND, DISTANCE_PRICE,IS_ACTIVE) " +
                                "VALUES ( " + planeID + " , '" + newDepart.toString() + "' , '" + deptTime + "' , " + deptLocation + " , '" + newArrival.toString() + "','" +
                                arrivalTime + "'," + arrivalLocation + "," + aEcon + "," + aBus + "," + aFirst + ",0,0,0," + demand + ", " + distancePrice + ",1);";
                        stmt.executeUpdate(sql);
                        isFlightAdded = true;
                    }

                }

                System.out.println("error is actually here");
            }

            stmt.close();
            con.commit();
            //c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Flight created ");
        return isFlightAdded;
    }

    public static void deleteFlight(Connection con, int flightID){
        boolean found = false;
        try{
            Statement stmt = con.createStatement();
            String sql = "";
            ResultSet rs = stmt.executeQuery( "SELECT * FROM FLIGHTS WHERE Flight_ID = "+  flightID + ";");
            if(rs.next()) {
                stmt.execute("DELETE FROM FLIGHTS WHERE Flight_ID = " + flightID + ";");
            }
            rs.close();
            stmt.close();


        } catch(Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Flight deleted");

    }

    public static String[][] getUpcomingFlights(Connection con, int userID){ //gathers upcoming flights
        boolean found = false;
        String[][] display = new String[10][7];
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM BOOKEDFLIGHTS WHERE USERID = "+  userID + "AND Completed=0;");
            int i = 0;
            while (rs.next()){
                display[i][0] = rs.getString("FlightID");
                display[i][1] = rs.getString("TotalTickets");
                display[i][2] = rs.getString("ticketsEconomy");
                display[i][3] = rs.getString("ticketsBusiness");
                display[i][4] = rs.getString("ticketsFirst");
                display[i][5] = rs.getString("priceEconomy");
                display[i][6] = rs.getString("priceBusiness");
                display[i][7] = rs.getString("priceFirst");
                i++;
            }
            rs.close();
            stmt.close();


        } catch(Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return display;
    }

    public static String[][] getCompletedFlights(Connection con, int userID){ //gathers upcoming flights
        boolean found = false;
        String[][] display = new String[10][7];
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM BOOKEDFLIGHTS WHERE USERID = "+  userID + "AND Completed=1;");
            int i = 0;
            while (rs.next()){
                display[i][0] = rs.getString("FlightID");
                display[i][1] = rs.getString("TotalTickets");
                display[i][2] = rs.getString("ticketsEconomy");
                display[i][3] = rs.getString("ticketsBusiness");
                display[i][4] = rs.getString("ticketsFirst");
                display[i][5] = rs.getString("priceEconomy");
                display[i][6] = rs.getString("priceBusiness");
                display[i][7] = rs.getString("priceFirst");
                i++;
            }
            rs.close();
            stmt.close();


        } catch(Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return display;
    }

    public static void setFlightCompleted(Connection con, int flightID){
        try{
            Statement stmt = con.createStatement();
            stmt.executeQuery( "UPDATE BOOKEDFLIGHTS SET completed = 1 WHERE flightID = " + flightID +";");
            stmt.close();

        } catch(Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public static ArrayList<ArrayList<String>> getFlightQuery(Connection con, String dCity, String aCity, String model, String tickets, String pref, String travelType){
        String criteria="";
        criteria += (dCity!=null) ? "DEPARTURE_LOCATION="+ dCity + " AND ":"";
        criteria += (aCity!=null) ? "ARRIVAL_LOCATION="+ aCity + " AND ":"";
        criteria += (model!=null && !model.equals("0"))?"MODEL_ID="+model+" AND ":"";
        criteria += (tickets!=null)? "(availableEconomy+availableBusiness+availableFirst) >="+tickets+" AND ":"";
        criteria += (pref!=null && pref.equals("0"))? "availableEconomy>0 AND ":"";
        criteria += (pref!=null && pref.equals("1"))? "availableBusiness>0 AND ":"";
        criteria += (pref!=null && pref.equals("2"))? "availableFirst>0 AND ":"";
        //criteria += (travelType!=null && travelType.equals("0"))? "availableEconomy>0 AND ":"";
        //criteria += (travelType!=null && travelType.equals("1"))? "availableEconomy>0 AND ":"";

        Statement stmt = null;

        ArrayList<ArrayList<String>> flightInfo = new ArrayList<>();

        try{
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("Select * FROM FLIGHTS JOIN PLANES ON flights.plane_ID=planes.ID WHERE "+criteria+" IS_ACTIVE=1 ORDER BY departure_date, departure_time;");
            while(rs.next()){
                flightInfo.add(new ArrayList<String>());
                flightInfo.get(flightInfo.size()-1).add(rs.getString("Flight_ID"));
                flightInfo.get(flightInfo.size()-1).add(rs.getString("Plane_ID"));
                flightInfo.get(flightInfo.size()-1).add(rs.getString("Model_ID"));
                flightInfo.get(flightInfo.size()-1).add(rs.getString("Departure_Location"));
                flightInfo.get(flightInfo.size()-1).add(rs.getString("Arrival_Location"));
                flightInfo.get(flightInfo.size()-1).add(rs.getString("availableEconomy"));
                flightInfo.get(flightInfo.size()-1).add(rs.getString("availableBusiness"));
                flightInfo.get(flightInfo.size()-1).add(rs.getString("availableFirst"));
                flightInfo.get(flightInfo.size()-1).add(rs.getString("Demand"));
                flightInfo.get(flightInfo.size()-1).add(rs.getString("Distance_Price"));
                flightInfo.get(flightInfo.size()-1).add(rs.getString("Departure_date")); // these and below are
                flightInfo.get(flightInfo.size()-1).add(rs.getString("Departure_Time")); // only on bottom
                flightInfo.get(flightInfo.size()-1).add(rs.getString("Arrival_Date")); // because of how JSON
                flightInfo.get(flightInfo.size()-1).add(rs.getString("Arrival_Time")); // parse is set up
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return flightInfo;
    }



    public static ArrayList<ArrayList<ArrayList<String>>> getSuperFlightQuery(Connection con, String dCity, String aCity, String dDate, String model, String tickets, String pref, String travelType, String multiStop, String returnDate){
        String criteria="";
        criteria += (model!=null && !model.equals("0"))?"MODEL_ID="+model+" AND ":"";
        criteria += (tickets!=null)? "(availableEconomy+availableBusiness+availableFirst) >="+tickets+" AND ":"";
        criteria += (pref!=null && pref.equals("0"))? "availableEconomy>0 AND ":"";
        criteria += (pref!=null && pref.equals("1"))? "availableBusiness>0 AND ":"";
        criteria += (pref!=null && pref.equals("2"))? "availableFirst>0 AND ":"";

        System.out.println("Criteria="+criteria);

        Statement stmt = null;
        Statement oneStmt = null;
        Statement twoStmt = null;

        ArrayList<ArrayList<ArrayList<String>>> flightInfo = new ArrayList<>();

        try{
            stmt = con.createStatement();
            ResultSet allFlights = stmt.executeQuery("Select * FROM FLIGHTS JOIN PLANES ON flights.plane_ID=planes.ID WHERE Departure_Location="+dCity+" AND Departure_date='"+dDate+"' AND "+criteria+" IS_ACTIVE=1 ORDER BY departure_date, departure_time;");
            while(allFlights.next()) {
                System.out.println("I have a flight!!!");

                if(allFlights.getString("Arrival_Location").equals(aCity)){
                    if(travelType.equals("1")){

                        ArrayList<ArrayList<ArrayList<String>>> returnDirectFlights = getSuperFlightQuery(con,aCity,dCity,returnDate,model,tickets,pref,"0",multiStop,"9999-09-09");

                        for(int returns=0; returns<returnDirectFlights.size(); returns++){
                            ArrayList<ArrayList<String>> tempDF = new ArrayList<>();
                            tempDF.add(addFlightToList(allFlights));
                            for(int flight=0; flight<returnDirectFlights.get(returns).size();flight++){
                                tempDF.add(returnDirectFlights.get(returns).get(flight));
                            }
                            flightInfo.add(tempDF);
                            System.out.println("Direct flight added with return flight");
                        }

                    }else {
                        ArrayList<ArrayList<String>> tempDF = new ArrayList<>();
                        tempDF.add(addFlightToList(allFlights));
                        flightInfo.add(tempDF);
                        System.out.println("Direct flight added");
                    }
                }else if(multiStop==null || multiStop.equals("1") || multiStop.equals("2")){
                    String oneStopDepart = allFlights.getString("Arrival_Location");
                    String oneStopDate = allFlights.getString("Arrival_Date");
                    String oneStopTime = allFlights.getString("Arrival_Time");
                    oneStmt = con.createStatement();
                    ResultSet oneStop = oneStmt.executeQuery("Select * From Flights Join Planes on Flights.plane_ID=planes.ID where  Departure_Location="+oneStopDepart+" AND "+criteria+" Departure_Date>='"+oneStopDate+"' AND Departure_Date<Date_ADD('"+oneStopDate+"', INTERVAL 2 DAY) AND Departure_Time>'"+oneStopTime+"' AND IS_ACTIVE=1 ORDER BY departure_date, departure_time;");

                    while(oneStop.next()) {
                        if (oneStop.getString("Arrival_Location").equals(aCity)) {

                            if(travelType.equals("1")){

                                ArrayList<ArrayList<ArrayList<String>>> returnOneFlights = getSuperFlightQuery(con,aCity,dCity,returnDate,model,tickets,pref,"0",multiStop,"9999-09-09");

                                for(int oneReturn=0; oneReturn<returnOneFlights.size(); oneReturn++){
                                    ArrayList<ArrayList<String>> tempOne = new ArrayList<>();
                                    tempOne.add(addFlightToList(allFlights));
                                    tempOne.add(addFlightToList(oneStop));
                                    for(int flight=0; flight<returnOneFlights.get(oneReturn).size();flight++){
                                        tempOne.add(returnOneFlights.get(oneReturn).get(flight));
                                    }
                                    flightInfo.add(tempOne);
                                    System.out.println("One Stop Flight Added with return flights");
                                }

                            }else {
                                ArrayList<ArrayList<String>> tempOne = new ArrayList<>();
                                tempOne.add(addFlightToList(allFlights));
                                tempOne.add(addFlightToList(oneStop));
                                flightInfo.add(tempOne);
                                System.out.println("One Stop Flight Added");
                            }
                        } else if(multiStop==null || multiStop.equals("2")){

                            String twoStopDepart = oneStop.getString("Arrival_Location");
                            String twoStopDate = oneStop.getString("Arrival_Date");
                            String twoStopTime = oneStop.getString("Arrival_Time");
                            twoStmt = con.createStatement();

                            ResultSet twoStop = twoStmt.executeQuery("Select * From Flights Join Planes on Flights.plane_ID=planes.ID where Departure_Location="+twoStopDepart+" AND "+criteria+" Departure_Date>='"+twoStopDate+"' AND Departure_Date<Date_ADD('"+twoStopDate+"', INTERVAL 2 DAY) AND Departure_Time>'"+twoStopTime+"' AND IS_ACTIVE=1 ORDER BY departure_date, departure_time;");

                            while(twoStop.next()){
                                if(twoStop.getString("Arrival_Location").equals(aCity)){

                                    if(travelType.equals("1")){

                                        ArrayList<ArrayList<ArrayList<String>>> returnTwoFlights = getSuperFlightQuery(con,aCity,dCity,returnDate,model,tickets,pref,"0",multiStop,"9999-09-09");

                                        for(int twoReturn=0; twoReturn<returnTwoFlights.size(); twoReturn++){
                                            ArrayList<ArrayList<String>> tempTwo = new ArrayList<>();
                                            tempTwo.add(addFlightToList(allFlights));
                                            tempTwo.add(addFlightToList(oneStop));
                                            tempTwo.add(addFlightToList(twoStop));
                                            for(int flight=0; flight<returnTwoFlights.get(twoReturn).size();flight++){
                                                tempTwo.add(returnTwoFlights.get(twoReturn).get(flight));
                                            }
                                            flightInfo.add(tempTwo);
                                            flightInfo.add(returnTwoFlights.get(twoReturn));
                                            System.out.println("Two Stop Flight Added");
                                        }

                                    }else {
                                        ArrayList<ArrayList<String>> tempTwo = new ArrayList<>();
                                        tempTwo.add(addFlightToList(allFlights));
                                        tempTwo.add(addFlightToList(oneStop));
                                        tempTwo.add(addFlightToList(twoStop));
                                        flightInfo.add(tempTwo);
                                        System.out.println("Two Stop Flight Added");
                                    }
                                }
                            }

                        }

                    }

                }

            }
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return flightInfo;

    }


    public static ArrayList<ArrayList<String>> getBookingQuery(Connection con, ArrayList<String> allFlights){
        ArrayList<ArrayList<String>> flightInfo = new ArrayList<>();

        Statement stmt = null;

        try{
            stmt = con.createStatement();

            for(int i=0; i<allFlights.size(); i++) {
                System.out.println("getting flight ifno for:"+ allFlights.get(i));
                ResultSet rs = stmt.executeQuery("Select * FROM FLIGHTS JOIN PLANES ON flights.plane_ID=planes.ID WHERE flight_id=" + allFlights.get(i) + ";");
                while (rs.next()) {
                    flightInfo.add(addFlightToList(rs));
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return flightInfo;
    }


    private static ArrayList<String> addFlightToList(ResultSet rs){

        ArrayList<String> currentFlight = new ArrayList<>();

        String[] itemList = {"Flight_ID","Plane_ID","Model_ID","Departure_Location","Arrival_Location","availableEconomy","availableBusiness","availableFirst",
        "Demand","Distance_Price","Departure_Date","Departure_Time","Arrival_Date","Arrival_Time"};
        try {
            for (int i = 0; i < itemList.length; i++) {
                currentFlight.add(rs.getString(itemList[i]));
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return currentFlight;
    }


    public static ArrayList<ArrayList<String>> getFlightList(Connection con){

        Statement stmt = null;
        ArrayList<ArrayList<String>> flightList = new ArrayList<>();
        String[] itemList = {"flight_id","plane_id","departure_date","departure_time","departure_location","arrival_date","arrival_time","arrival_location","availableEconomy","availableBusiness","availableFirst"};

        try{
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("Select * From flights ORDER BY DEPARTURE_DATE, DEPARTURE_TIME;");

            while(rs.next()){
                flightList.add(new ArrayList<>());
                for(int i=0; i<itemList.length; i++){
                    if(!itemList[i].equals("departure_location") && !itemList[i].equals("arrival_location")) {
                        flightList.get(flightList.size() - 1).add(rs.getString(itemList[i]));
                    }else{
                        String[] cityState = CityFunctions.getIndexName(con,rs.getString(itemList[i]));
                        flightList.get(flightList.size()-1).add(cityState[0]);
                        flightList.get(flightList.size()-1).add(cityState[1]);
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return flightList;
    }




}
