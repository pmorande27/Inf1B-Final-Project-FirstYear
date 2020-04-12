import org.junit.Test;

import static org.junit.Assert.*;

public class ListCmdBasicTest extends ListCmdTest {

    // ------------------------- parseArguments tests --------------------
    @Test
    public void testParseArgumentValidCommand1(){
        boolean result = testCommand.parseArguments("SHORT");
        assertFalse("Given Argument should not be accepted",result);
    }
    @Test
    public void testParseArgumentValidCommand2(){
        boolean result = testCommand.parseArguments("LONG");
        assertFalse("Given Argument should not be accepted",result);
    }
    @Test
    public void testParseArgumentValidCommand3(){
        boolean result = testCommand.parseArguments("LoNG");
        assertFalse("Given Argument should not be accepted",result);
    }
    @Test
    public void testParseArgumentValidCommand4(){
        boolean result = testCommand.parseArguments("ShORT");
        assertFalse("Given Argument should not be accepted",result);
    }
  
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
    public void testExecuteBlankList() {
        String expectedConsoleOutput = "3 books in library:\nTitleA\nTitleB\nTitleC";
        testCommand.parseArguments("");
        CommandTestUtils.checkExecuteConsoleOutput(testCommand, testLibrary, expectedConsoleOutput);
    }
    @Test
    public void testNoBookInLibrary(){
        String expectedConsoleOutput = "The library has no book entries.";
        LibraryData data = new LibraryData();
        CommandTestUtils.checkExecuteConsoleOutput(testCommand,data,expectedConsoleOutput);
    }
    @Test
    public void testExecuteBlankListAndShort() {
        testCommand.parseArguments("short");
        StdStreamIntercept intercept = new StdStreamIntercept();
        intercept.stdCaptureStart();
        testCommand.execute(testLibrary);
        String resultShort = intercept.getCapturedStdOut();
        StdStreamIntercept intercept2 = new StdStreamIntercept();
        intercept2.stdCaptureStart();
        testCommand.parseArguments("");
        testCommand.execute(testLibrary);
        String resultBlank = intercept2.getCapturedStdOut();
        intercept.stdCaptureStop();
        intercept2.stdCaptureStop();
        assertEquals(resultBlank,resultShort);
    }
    @Test
    public void testHeaderShort(){
    String[] executeStdOutLines = CommandTestUtils.captureExecuteStdOutputLines(testCommand, testLibrary);
    int books = testLibrary.getBookData().size();

    assertEquals("Unexpected group output header.", books+" books in library:", executeStdOutLines[0]);
}
    @Test
    public void testHeaderLong(){
        testCommand.parseArguments("long");
        String[] executeStdOutLines = CommandTestUtils.captureExecuteStdOutputLines(testCommand, testLibrary);
        int books = testLibrary.getBookData().size();

        assertEquals("Unexpected group output header.", books+" books in library:", executeStdOutLines[0]);
    }
    @Test
    public void testHeaderBlank(){
        testCommand.parseArguments("");
        String[] executeStdOutLines = CommandTestUtils.captureExecuteStdOutputLines(testCommand, testLibrary);
        int books = testLibrary.getBookData().size();

        assertEquals("Unexpected group output header.", books+" books in library:", executeStdOutLines[0]);
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
