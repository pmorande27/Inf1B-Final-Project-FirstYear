import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class newTestsSearchCmd {
    SearchCmd testCommand;
    LibraryData data;
    AddCmd addBase;

    @Before
    public void setup() {
        data = new LibraryData();
        testCommand=  new SearchCmd("harry");
        addBase = new AddCmd(database);
        addBase.execute(data);

    }
    @Parameterized.Parameters
    public static Object[] parameters(){
        return new Object[]{"books01.csv", "books02.csv", "books03.csv", "books04.csv", "books05.csv"};
    }
    @Parameterized.Parameter(0)
    public String database;
    @Test
    public void testSearchCase(){

        String argument ="harry";
        CheckArgument(argument);
    }
    @Test
    public void testSearchCase2(){
        String argument ="ice";
        CheckArgument(argument);
    }
    @Test
    public void testSearchCase3(){
        String argument ="a";
        CheckArgument(argument);
    }

    private void CheckArgument(String argument) {
        testCommand.parseArguments(argument);
        StdStreamIntercept intercept = new StdStreamIntercept();
        intercept.stdCaptureStart();
        try {
            try {
                testCommand.execute(data);
            } catch (Exception e) {
                System.err.println("ERROR: executing execute for console output check:" + e);
            }
            // ignore leading and trailing white spaces
            String lowcase =intercept.getCapturedStdOut().replaceAll("\r", "").trim();
            StdStreamIntercept intercept2 = new StdStreamIntercept();
            intercept2.stdCaptureStart();
            testCommand.parseArguments(argument.toUpperCase());
            testCommand.execute(data);
            String Uppercase =intercept2.getCapturedStdOut().replaceAll("\r", "").trim();
            if ((!Uppercase.contains("No hits found for search term:") | !lowcase.contains("No hits found for search term:"))){
                assertEquals(lowcase,Uppercase);
                intercept2.stdCaptureStop();
            }


        } finally {
            intercept.stdCaptureStop();
        }
    }


}
