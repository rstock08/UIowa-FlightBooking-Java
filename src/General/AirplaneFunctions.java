package General;

import General.AccountFunctions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Created by crisi_000 on 3/30/2017.
 */

public class AirplaneFunctions {

    public static void main( String[]  args){
        Connection connection = null;
        try {
            boolean working = false;
            connection = AccountFunctions.OpenDatabase();
            //createPlanesTable(connection);
            //addAirplane(connection, "Boeing",250,3);
            //createPlaneModelTable(connection);
            //working = checkLogin(connection,"test@gmail.com","test");
            //System.out.println(working);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    public static void createPlanesTable(Connection con)
    {
        Connection c = con;
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            /*String sql = "CREATE TABLE PLANES " +
                    "(ID INT PRIMARY KEY     NOT NULL AUTO_INCREMENT," +
                    " PLANE_TYPE           TEXT    NOT NULL, " +
                    " CAPACITY        INT     NOT NULL," +
                    " CLASSES         INT    NOT NULL );";*/

            //String sql = "ALTER TABLE PLANES DROP COLUMN CLASSES;";


            //stmt.executeUpdate(sql);

            /*sql = "ALTER TABLE PLANES ADD COLUMN " +
                    "(maxFIRST          INT NOT NULL, " +
                    " maxBUSINESS       INT NOT NULL, " +
                    " maxECONOMY        INT NOT NULL, " +
                    " takenFIRST        INT NOT NULL, " +
                    " takenBUSINESS     INT NOT NULL, " +
                    " takenECONOMY      INT NOT NULL, " +
                    " availableFIRST    INT AS (maxFIRST - takenFIRST), " +
                    " availableBUSINESS INT AS (maxBUSINESS - takenBUSInESS), " +
                    " availableECONOMY  INT AS (maxECONOMY - takenECONOMY), " +
                    " BASE_PRICE        INT NOT NULL, " +
                    " multipleFIRST     INT NOT NULL, " +
                    " multipleBUSINESS  INT NOT NULL, " +
                    " multipleECONOMY   INT NOT NULL);";*/
            //stmt.executeUpdate(sql);

            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }

    public static void createPlaneModelTable(Connection con){
        Connection c = con;
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            String sql = "CREATE TABLE PLANEMODELS " +
                    "(ID INT PRIMARY KEY    NOT NULL    AUTO_INCREMENT," +
                    " PLANE_MODEL            TEXT        NOT NULL, " +
                    " CAPACITY              INT         NOT NULL," +
                    " hasECONOMY            BOOLEAN         NOT NULL," +
                    " hasBUSINESS           BOOLEAN         NOT NULL," +
                    " hasFIRST              BOOLEAN         NOT NULL,"+
                    " FUEL_CAPACITY         INT             NOT NULL,"+
                    " FUEL_BURN_RATE        DECIMAL(5,4)    NOT NULL,"+
                    " AVERAGE_VELOCITY      INT             NOT NULL);";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");

    }

    public static boolean addPlaneModel(Connection con, String model, String capacity, String fuel, String burn,
                                        String velocity, String hasEconomy, String hasBusiness, String hasFirst,
                                        String seatsEconomy, String seatsBusiness, String seatsFirst, String basePrice ){

        Statement stmt = null;
        boolean planeModelAdded = false;
        try {

            con.setAutoCommit(false);
            stmt = con.createStatement();

            String sql = "SELECT * FROM planemodels WHERE plane_model='"+model+"';";
            ResultSet rs = stmt.executeQuery(sql);

            if(!rs.next()){
                sql = "INSERT INTO planemodels (PLANE_MODEL, CAPACITY, hasECONOMY, hasBUSINESS, hasFIRST, seatsECONOMY, seatsBUSINESS, seatsFIRST, FUEL_CAPACITY, FUEL_BURN_RATE, AVERAGE_VELOCITY, BASE_PRICE) " +
                        "VALUES ( '" + model + "' , " + capacity + " , "+ hasEconomy +", "+ hasBusiness+", "+ hasFirst + ", "+seatsEconomy+","+seatsBusiness+","+seatsFirst+","+fuel+","+burn+","+velocity+","+basePrice+");";
                stmt.executeUpdate(sql);
                stmt.close();
                planeModelAdded = true;
            }

            con.commit();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Records updated successfully");

        return planeModelAdded;
    }

    public static boolean updatePlaneModel(Connection con, String modelID, String planeModel, String capacity,
                                        String modelFuel, String modelBurn, String modelVelocity, String hasEconomy,
                                        String hasBusiness, String hasFirst , String seatsEcon, String seatsBus,
                                           String seatsFirst, String basePrice){
        boolean modelUpdated = false;
        Statement stmt = null;
        try {
            con.setAutoCommit(false);

            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM planemodels WHERE PLANE_MODEL='"+planeModel+"';");
            rs.next();

            if(rs.getString("count(*)").equals("1")){

                String sql = "UPDATE planemodels SET plane_model='"+planeModel+"', Capacity=" + capacity + "," +
                        " FUEL_CAPACITY=" + modelFuel + ",FUEL_BURN_RATE="+modelBurn+", " +
                        "AVERAGE_VELOCITY=" + modelVelocity + ", hasECONOMY=" + hasEconomy + "," +
                        "hasBUSINESS="+hasBusiness+", hasFIRST=" + hasFirst + ", seatsECONOMY=" + seatsEcon + "," +
                        "seatsBusiness="+seatsBus+", seatsFirst="+seatsFirst+", base_price="+basePrice+" WHERE ID="+modelID+";";
                stmt.executeUpdate(sql);
                stmt.close();
                modelUpdated = true;
            }

            con.commit();
        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return modelUpdated;
    }

    public static void deletePlaneModel(Connection c, String planeModelID){
        Statement stmt = null;
        try {
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "DELETE FROM planemodels WHERE ID = "+planeModelID+";";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public static ArrayList<String[]> getPlaneModelsList(Connection con){
        ArrayList<String[]> modelSpecs = new ArrayList<>();
        Statement stmt = null;
        try {

            con.setAutoCommit(false);
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM planemodels ORDER BY PLANE_MODEL;");

            while(rs.next()){
                String[] temp = {rs.getString("plane_model"), rs.getString("id"),
                rs.getString("capacity"), rs.getString("fuel_capacity"),
                rs.getString("fuel_burn_rate"), rs.getString("Average_velocity"),
                rs.getString("hasEconomy"), rs.getString("hasBusiness"),
                rs.getString("hasFirst"), rs.getString("seatsEconomy"),
                rs.getString("seatsBusiness"), rs.getString("seatsFirst"),
                rs.getString("base_price")};
                modelSpecs.add(temp);
                System.out.println("retrieving...");
            }

            con.commit();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Records planemodels obtained");

        return modelSpecs;
    }

    public static ArrayList<String[]> getBasicModelList(Connection con){
        ArrayList<String[]> basicModelInfo = new ArrayList<String[]>();
        Statement stmt = null;
        try{
            con.setAutoCommit(false);
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("Select ID, PLANE_MODEL, BASE_PRICE From planemodels ORDER BY PLANE_MODEL;");
            while(rs.next()){
                String[] tempInfo = new String[3];
                tempInfo[0] = rs.getString("ID");
                tempInfo[1] = rs.getString("plane_model");
                tempInfo[2] = rs.getString("Base_price");
                basicModelInfo.add(tempInfo);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return basicModelInfo;
    }

    //i assume that if a plane has first class, it also has business and coach. If it has business it also has coach
    public static boolean addAirplane(Connection con, String modelID) {
        Statement stmt = null;
        boolean airplaneAdded = false;
        System.out.println("Adding new airplane");

        try {

            con.setAutoCommit(false);
            stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM planemodels WHERE id="+modelID+";");

            if(rs.next()) {
                String planeType = rs.getString("ID");

                String sql = "INSERT INTO PLANES (MODEL_ID) VALUES ("+planeType+");";
                stmt.executeUpdate(sql);
                airplaneAdded = true;
            }

            stmt.close();
            con.commit();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return airplaneAdded;
    }

    public static boolean updateAirplane(Connection con, String planeID,String modelID) {
        Statement stmt = null;
        boolean airplaneAdded = false;
        System.out.println("Airplane updating... ");

        try {

            con.setAutoCommit(false);
            stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM planes WHERE id="+planeID+";");

            if(rs.next()) {
                String sql = "UPDATE INTO PLANES (Model_ID) VALUES ("+ modelID +") WHERE id="+planeID+";";
                stmt.executeUpdate(sql);

                rs = stmt.executeQuery("Select count(*) from planes where id="+planeID+" AND model_ID="+modelID+";");
                rs.next();
                if(rs.getString("count(*)").equals("1")){
                    airplaneAdded = true;
                }
            }

            stmt.close();
            con.commit();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return airplaneAdded;
    }

    public static boolean deleteAirplane(Connection con, String planeID){

        boolean isDeleted = false;
        Statement stmt = null;
        try {
            con.setAutoCommit(false);
            stmt = con.createStatement();
            String sql = "DELETE FROM planes WHERE ID = "+planeID+";";
            stmt.executeUpdate(sql);
            ResultSet rs = stmt.executeQuery("Select count(*) from planes where ID="+planeID);
            rs.next();
            if(rs.getString("count(*)").equals("0")){
                isDeleted = true;
            }
            stmt.close();
            con.commit();
        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Airplaine "+planeID+" deleted");
        return isDeleted;
    }

    public static ArrayList<String[]> getPlaneList(Connection con){
        ArrayList<String[]> planes = new ArrayList<>();
        Statement stmt = null;
        try {

            con.setAutoCommit(false);
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM planes;");

            while(rs.next()){
                String[] temp = {rs.getString("ID"), rs.getString("model_ID")};
                planes.add(temp);
                System.out.println("retrieving planes...");
            }

            con.commit();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Records planes obtained");

        return planes;
    }

    public static String[] planeModelSpecifics(Connection con, String modelID){
        String[] specificDetail = new String[4];
        Statement stmt = null;
        try {

            con.setAutoCommit(false);
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM planemodels WHERE id="+modelID+";");

            if(rs.next()){
                specificDetail[0] = rs.getString("capacity");
                specificDetail[1] = rs.getString("fuel_capacity");
                specificDetail[2] = rs.getString("fuel_burn_rate");
                specificDetail[3] = rs.getString("average_velocity");
            }

            con.commit();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Obtained plane model specifics");

        return specificDetail;
    }

    public static ArrayList<String[]> airplanesCurrentlyAvailable(Connection con, String modelID, String departDate,
                                                          String departTime, String arriveDate, String arriveTime){
        ArrayList<String[]> availablePlanesList = new ArrayList<>();

        boolean isAvailable;
        try {
            Statement stmt = null;
            Statement stmtInner = null;
            con.setAutoCommit(false);
            stmt = con.createStatement();
            stmtInner = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT * FROM planes WHERE MODEL_ID="+modelID+" ORDER BY id;");

            while(rs.next()){
                ResultSet innerRS = stmtInner.executeQuery("Select * FROM flights WHERE plane_id="+rs.getString("id")+";");

                isAvailable = true;
                if (innerRS.next()) {
                    do {
                        if (!checkDates(innerRS.getString("Departure_Date"),
                                innerRS.getString("Arrival_Date"), departDate, arriveDate)) {

                            isAvailable = checkTimes(innerRS.getString("Departure_time"),
                                    innerRS.getString("Arrival_time"), departTime, arriveTime);
                        }
                    } while (innerRS.next());
                } else {
                    isAvailable = true;
                }

                if (isAvailable) {
                    String[] availablePlane = new String[2];
                    availablePlane[0] = rs.getString("ID");
                    availablePlane[1] = rs.getString("model_ID");
                    System.out.println("plane ID="+availablePlane[0]);
                    System.out.println("model ID ="+availablePlane[1]);
                    availablePlanesList.add(availablePlane);
                }
            }
            stmt.close();
            stmtInner.close();
            con.commit();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Finished Making Available AdminPlanes Array");

        return availablePlanesList;

    }

    public static boolean checkDates(String qDepart, String qArrive, String cDepart, String cArrive) {
        return LocalDate.parse(qDepart).isAfter(LocalDate.parse(cArrive)) ||
                LocalDate.parse(qArrive).isBefore(LocalDate.parse(cDepart));
    }

    public static boolean checkTimes(String qDTime, String qATime, String cDTime, String cATime){
        return LocalTime.parse(qDTime).isAfter(LocalTime.parse(cATime))
                || LocalTime.parse(cDTime).isAfter(LocalTime.parse(qATime));
    }

}
