import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class ListCmdBasicTest extends ListCmdTest {

    // ------------------------- parseArguments tests --------------------
  
    @Test
    public void testParseArgumentsIllegalArgument() {
        CommandTestUtils.checkArgumentInput(testCommand, false, "nonsense");
    }

    @Test
    public void testParseArgumentsLegalArgument() {
        CommandTestUtils.checkArgumentInput(testCommand, true, SHORT_ARGUMENT);
        CommandTestUtils.checkArgumentInput(testCommand, true, LONG_ARGUMENT);
        CommandTestUtils.checkArgumentInput(testCommand, true, BLANK_ARGUMENT);
    }
    @Test
    public void testParseArgumentsSpaces(){
        boolean result = testCommand.parseArguments("     ");
        assertTrue("Given Argument should be accepted",result);

    }
    @Test

    public void testParseArgumentsSpaces2(){
        boolean result = testCommand.parseArguments("     long");
        assertTrue("Given Argument should be accepted",result);

    }
    @Test

    public void testParseArgumentsSpaces3(){
        boolean result = testCommand.parseArguments("     short");
        assertTrue("Given Argument should be accepted",result);

    }
    @Test

    public void testParseArgumentsSpaces4(){
        boolean result = testCommand.parseArguments("     long    ");
        assertTrue("Given Argument should be accepted",result);

    }
    @Test

    public void testParseArgumentsSpaces5(){
        boolean result = testCommand.parseArguments("     short    ");
        assertTrue("Given Argument should be accepted",result);

    }
    @Test

    public void testParseArgumentsSpaces6(){
        boolean result = testCommand.parseArguments("    short fas  ");
        assertFalse("Given Argument should not be accepted",result);

    }
    @Test

    public void testParseArgumentsSpaces7(){
        boolean result = testCommand.parseArguments("    long fas  ");
        assertFalse("Given Argument should not be accepted",result);

    }
    @Test

    public void testParseArgumentsSpaces8(){
        boolean result = testCommand.parseArguments("        fas  ");
        assertFalse("Given Argument should not be accepted",result);

    }
    @Test

    public void testParseArgumentsSpaces9(){
        boolean result = testCommand.parseArguments("   ");
        assertTrue("Given Argument should be accepted",result);

    }

    // ------------------------- execute tests --------------------

    @Test
    public void testExecuteShortList() {
        String expectedConsoleOutput = "3 books in library:\nTitleA\nTitleB\nTitleC";
        CommandTestUtils.checkExecuteConsoleOutput(testCommand, testLibrary, expectedConsoleOutput);
    }

    @Test
    public void testExecuteLongList() {
        testCommand = new ListCmd(LONG_ARGUMENT);

        String expectedConsoleOutput = 
        "3 books in library:\n" +
        "TitleA\n" +
        "by AuthorA\n" +
        "Rating: 3.20\n" +
        "ISBN: ISBNA\n" +
        "500 pages\n\n" +
        "TitleB\n" +
        "by AuthorB\n" +
        "Rating: 4.30\n" +
        "ISBN: ISBNB\n" +
        "400 pages\n\n" +
        "TitleC\n" +
        "by AuthorC\n" +
        "Rating: 1.30\n" +
        "ISBN: ISBNC\n" +
        "300 pages";

        CommandTestUtils.checkExecuteConsoleOutput(testCommand, testLibrary, expectedConsoleOutput);
    }

}
