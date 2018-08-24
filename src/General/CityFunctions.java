package General;

import General.AccountFunctions;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by crisi_000 on 4/6/2017.
 */
public class CityFunctions {


    public static void main( String[]  args){
        Connection connection = null;
        try {
            boolean working = false;
            connection = AccountFunctions.OpenDatabase();
            //createCityTable(connection);
            //addStatesAndCities(connection);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void createCityTable(Connection c) {
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            //stmt.executeUpdate(sql);
            String sql = "CREATE TABLE CITIES " +
                    "(ID INT PRIMARY KEY     NOT NULL AUTO_INCREMENT," +
                    " CITYNAME           TEXT    NOT NULL, " +
                    " STATE        TEXT     NOT NULL);";
                    /*" AIRPORT       TEXT    NOT NULL)";*/
            //String sql = "ALTER TABLE cities DROP COLUMN AIRPORT;";
            //String sql = "ALTER TABLE cities ADD COLUMN ACTIVE BOOLEAN NOT NULL, latitude DECIMAL(14,10) NOT NULL, longitude DECIMAL(14,10);";

            stmt.executeUpdate(sql);

            stmt.close();
            //c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }

    public static String getIndex(Connection con, String state, String city){
        Statement stmt = null;
        String id = "0";
        try {
            stmt = con.createStatement();
            String sql = "SELECT * FROM cities WHERE state='"+state+"' AND cityname='"+city+"';";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                id = rs.getString("id");
            }

            rs.close();
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return id;
    }

    public static boolean setActivityForCity(Connection con, String index, int active){

        boolean attemptSuccess = false;
        Statement stmt = null;
        try {
            con.setAutoCommit(false);
            stmt = con.createStatement();
            stmt.executeUpdate("UPDATE cities SET active="+active+" WHERE id="+index+";");
            con.commit();

            ResultSet rs = stmt.executeQuery("Select Count(*) from cities WHERE id="+index+" AND active="+active+";");
            rs.next();
            if(active==1){
                attemptSuccess = rs.getInt("count(*)")==1;
            }else{
                attemptSuccess = rs.getInt("count(*)")==1;
            }
            con.commit();
        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return attemptSuccess;
    }

    public static String getActiveCities(Connection con){
        Statement stmt = null;
        String htmlCode = "";

        try{
            con.setAutoCommit(false);

            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM cities WHERE active=TRUE ORDER BY state, cityName;");
            htmlCode += "<table class=\"admin_man_table\"><tr>";
            htmlCode += "<th><b>State</b></th>";
            htmlCode += "<th><b>City</b></th>";
            htmlCode += "<th><b>Action</b></th>";
            htmlCode += "</tr>";

            while(rs.next()){
                htmlCode += "<form action=\"General.Locations\">";
                htmlCode += "<input type=\"hidden\" name=\"cityID\" value=\""+ rs.getString("ID") + "\" >";
                htmlCode += "<tr>";
                htmlCode += "<td>" + rs.getString("state") + "</td>";
                htmlCode += "<td>" + rs.getString("cityname") + "</td>";
                htmlCode += "<td> <button type=\"submit\" name=\"removeLocationButton\">Remove</button></td>";
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

    public static String getActiveCitiesList(){
        return cityList(true);
    }

    public static String getActiveStatesList(String call){
        return stateList(call,true);
    }

    public static String getStates(){
        return stateList( "alterCityMenu",false);
    }

    public static String getCities(){
        return cityList(false);
    }

    private static String cityList(boolean activeList){
        Statement stmt = null;
        String cityCode = "";
        String sql = "SELECT * FROM cities ";
        if(activeList){
            sql += " WHERE active=true ";
        }
        sql += "ORDER BY state, cityname;";
        try{
             Connection con = AccountFunctions.OpenDatabase();

            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                String currentState = rs.getString("state");
                String currentCity = rs.getString("cityName");
                String classState = currentState.replace(" ","-").toLowerCase();

                cityCode += "<option class=\""+classState+" adminAllCities\" value=\"" + currentCity + "\">";
                cityCode += currentCity;
                cityCode += "</option>\n";

            }

            con.close();

        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);

        }
        return cityCode;
    }

    private static String stateList(String call, boolean activeList){
        Statement stmt = null;
        String stateCode = "";
        String sql = "SELECT DISTINCT state FROM cities ";
        if(activeList){
            sql += " WHERE active=true ";
        }
        sql += " ORDER BY state;";

        try{

            Connection con = AccountFunctions.OpenDatabase();
            con.setAutoCommit(false);

            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                String currentState = rs.getString("state");
                String classState = currentState.replace(" ","-").toLowerCase();

                stateCode += "<option value=\""+currentState+"\" onclick=\""+call+"(&quot;" + classState + "&quot;)\">";
                stateCode += currentState;
                stateCode += "</option>\n";
            }

            con.close();

        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);

        }
        return stateCode;
    }

    public static String[] getLatAndLong(Connection con, String state, String city){
        String[] latandlong = new String[2];
        Statement stmt = null;

        try{
            con.setAutoCommit(false);

            String sql = "SELECT * FROM cities WHERE state='"+state+"' AND cityname='"+city+"';";
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if(rs.next()){
                latandlong[0] = rs.getString("latitude");
                latandlong[1] = rs.getString("longitude");
            }
            stmt.close();
            rs.close();
        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);

        }
        return latandlong;
    }

    public static ArrayList<ArrayList<String[]>> getLocationList(Connection con, boolean activity){
        ArrayList<ArrayList<String[]>> activeLocations = new ArrayList<ArrayList<String[]>>();
        Statement stmt = null;
        String wantActive = activity ? "WHERE ACTIVE=1":"";

        System.out.println("wantActive = "+wantActive);

        try{
            con.setAutoCommit(false);
            stmt = con.createStatement();
            String sql = "SELECT DISTINCT state FROM cities " + wantActive + " ORDER BY STATE";
            ResultSet states = stmt.executeQuery(sql);
            //String[] stateList = new String[50];
            while(states.next()){
                activeLocations.add(new ArrayList<>());
                String[] stateName = new String[1];
                stateName[0] = states.getString("state");
                activeLocations.get(activeLocations.size()-1).add(stateName);
            }
            sql = "SELECT * FROM cities "+wantActive+" ORDER BY STATE, ID"; // aka true
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                for(int i=0; i<activeLocations.size();i++){
                    if(activeLocations.get(i).get(0)[0].equals(rs.getString("state"))){
                        String[] info = new String[4];
                        info[0] = rs.getString("ID");
                        info[1] = rs.getString("cityName");
                        info[2] = rs.getString("latitude");
                        info[3] = rs.getString("longitude");
                        //activeLocations.get(i).add(rs.getString("ID"));
                        //activeLocations.get(i).add(rs.getString("cityName"));
                        activeLocations.get(i).add(info);
                        i = activeLocations.size()+1;
                    }
                }
            }
        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);

        }
        return activeLocations;
    }


    public static String[] getIndexName(Connection con, String index){

        Statement stmt = null;
        String[] cityState = new String[2];

        try{
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("Select state,CityName from cities Where id="+index+";");
            if(rs.next()) {
                cityState[0] = rs.getString("cityName");
                cityState[1] = rs.getString("state");
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return cityState;

    }


}
