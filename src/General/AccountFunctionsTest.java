package General;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.*;

/**
 * Created by crisi_000 on 4/18/2017.
 */
public class AccountFunctionsTest {
    @Before
    public void setUp() throws Exception {
        String email = "test.iowa.air@gmail.com";
        String password = "testtesttest123";
        Connection con = AccountFunctions.OpenDatabase();
        checkLogin(con,email,password);
        password = "wrong";
        checkLogin(con,email,password);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void checkLogin(Connection con, String email, String password) throws Exception {
        try{
            assertTrue(AccountFunctions.checkLogin(con,email,password));
            System.out.println("Log in succesful");
        } catch(AssertionError e) {
            System.out.println("Log in failed");
        }
    }

}