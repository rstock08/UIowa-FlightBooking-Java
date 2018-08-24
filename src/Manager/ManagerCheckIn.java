package Manager;

import General.AccountFunctions;

import java.sql.Connection;
import java.sql.Statement;

/**
 * Created by Charlie on 4/25/2017.
 */
public class ManagerCheckIn {

    public static boolean managerCheckIn(Connection con, int referenceID){
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate("UPDATE BOOKEDFLIGHTS SET checkedIn = 1 WHERE ID = " + referenceID +";");
            stmt.close();
            System.out.println("Made it");
            return true;
        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            System.out.println("Didn't make it");
            return false;
        }
    }

    public static void managerCancelFlight(Connection con, int referenceID){
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate("DELETE FROM BOOKEDFLIGHTS WHERE ID = " + referenceID +";");
            stmt.close();
        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

   public static void numberOfBags(Connection con, int referenceID, int bag){
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate("UPDATE BOOKEDFLIGHTS SET bags = bag WHERE ID = " + referenceID +";");
            stmt.close();
        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
}
