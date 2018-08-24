package General;

import junit.framework.TestResult;

/**
 * Created by crisi_000 on 4/18/2017.
 */
public class TestingMethods {

    public static void main(String[] args){
        AccountFunctionsTest aft = new AccountFunctionsTest();
        try{
            aft.setUp();
        } catch (Exception e){
            e.printStackTrace();
        }


    }
}
