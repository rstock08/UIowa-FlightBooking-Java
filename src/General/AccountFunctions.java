package General; /**
 * Created by crisi_000 on 3/21/2017.
 */

import java.sql.*;
import java.util.ArrayList;

public class AccountFunctions
{

    public static void main( String[]  args){
        Connection connection = null;
        try {
            boolean working = false;
            connection = OpenDatabase();
            addAdmin(connection,"admin@gmail.com","testing123");
            //CreateAccountsTable(connection);
            //AddCustomer(connection, 2,"test@gmail.com","test");
            //working = checkLogin(connection,"test@gmail.com","test");
            System.out.println(working);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    public AccountFunctions(){}

    public static Connection OpenDatabase()
    {

        Connection c = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/accounts?autoReconnect=true&useSSL=false","root","charlie1");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");
        return c;
    }

    //probably wont be needed again
    public static void CreateAccountsTable(Connection con)
    {
        Connection c = con;
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            String sql = "CREATE TABLE ACCOUNTS " +
                    "(ID INT PRIMARY KEY     NOT NULL," +
                    " EMAIL           TEXT    NOT NULL, " +
                    " PASSWORD        TEXT     NOT NULL)";
            stmt.executeUpdate(sql);

            sql = "ALTER TABLE ACCOUNTS ADD COLUMN ROLE TEXT NOT NULL";
            stmt.executeUpdate(sql);
            System.out.println("ID MODIFIED");
            //sql = "ALTER TABLE ACCOUNTS MODIFY COLUMN EMAIL TEXT NOT NULL UNIQUE";
           // stmt.executeUpdate(sql);

            stmt.close();
            //c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }

    public static boolean checkLogin(Connection con, String user, String pass)
    {
        Connection c = con;
        Statement stmt = null;
        boolean found = false;
        try {

            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM ACCOUNTS;" );
            while ( rs.next() && !found ) {
                int id = rs.getInt("id");
                String  username = rs.getString("email");
                String  password = rs.getString("password");
                if ((username.equals(user))&&(pass.equals(password))){
                    found = true;
                }
            }
            rs.close();
            stmt.close();
            //c.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
        return found;
    }

    public static void closeConnection(Connection c){
        try {
            c.close();
            System.out.println("Database connection closed");
        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public static void addAdmin(Connection c, String email, String password){
        Statement stmt = null;
        try {
            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = "INSERT INTO ACCOUNTS (EMAIL,PASSWORD,ROLE) " +
                    "VALUES ('" + email + "' , '" + password + "' , 'ADMIN' );";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public static boolean addAccount(Connection c, String fullname, String email, String password, String role){
        Statement stmt = null;
        boolean accountAdded = false;
        try {
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM accounts WHERE email='"+email+"';");

            if(!rs.next()){
                String sql = "INSERT INTO ACCOUNTS (FULL_NAME,EMAIL,PASSWORD,ROLE) " +
                        "VALUES ('"+fullname+"' , '" + email + "' , '" + password + "' , '"+role+"' );";
                stmt.executeUpdate(sql);
                stmt.close();
                accountAdded = true;
            }

            System.out.println(accountAdded);

            c.commit();
        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return accountAdded;
    }

    public static boolean deleteAccount(Connection con, String accountID){
        Statement stmt = null;
        boolean isDeleted = false;
        try {
            con.setAutoCommit(false);

            stmt = con.createStatement();
            String sql = "DELETE FROM accounts WHERE ID = "+accountID+";";
            stmt.executeUpdate(sql);
            sql = "Select Count(*) FROM accounts WHERE ID = "+accountID+";";
            ResultSet rs = stmt.executeQuery(sql);
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
        return isDeleted;
    }

    public static Character checkRole(Connection c, String email){
        Statement stmt = null;
        String role = null;
        Character result = 'C';
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ROLE FROM ACCOUNTS WHERE EMAIL = '" + email + "';");
            rs.next();
            role = rs.getString("role");
        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        if (role.equals("CUSTOMER")){
            result = 'C';
        }
        else if (role.equals("MANAGER")){
            result = 'M';
        }
        else if (role.equals("ADMIN")){
            result = 'A';
        }
        return result;
    }

    public static boolean updateAccount(Connection con, String id, String name, String email, String password, String role){
        Statement stmt = null;
        boolean isUpdated = false;
        try {

            con.setAutoCommit(false);
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM accounts WHERE email='"+email+"';");
            rs.next();
            if(rs.getString("count(*)").equals("1")){
                String sql = "UPDATE ACCOUNTS SET FULL_NAME='"+name+"', EMAIL='" + email + "', PASSWORD='" + password + "' WHERE ID="+id+";";
                stmt.executeUpdate(sql);
                stmt.close();
                isUpdated = true;
            }
            con.commit();
        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return isUpdated;
    }

    public static ArrayList<String[]> getManagerList(Connection con){
        Statement stmt = null;
        ArrayList<String[]> managerList = new ArrayList<>();

        try{
            con.setAutoCommit(false);

            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM accounts WHERE role='MANAGER' ORDER BY id;");

            while(rs.next()){
                String[] info = {rs.getString("id"),rs.getString("full_name"),
                        rs.getString("email"),rs.getString("password")};
                managerList.add(info);
            }

            con.close();

        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);

        }

        return managerList;
    }

    public static int getID(Connection con, String email){
        Connection c = con;
        Statement stmt = null;
        int id = 0;
        try {

            c.setAutoCommit(false);

            stmt = c.createStatement();
            System.out.println(email);
            ResultSet rs = stmt.executeQuery( "SELECT * FROM ACCOUNTS WHERE EMAIL = '" + email + "';" );
            if(rs.next()) {
                id = rs.getInt("ID");
            }
            rs.close();
            stmt.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return id;
    }
}