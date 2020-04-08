import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

public class GroupCmdBasicTest extends GroupCmdTest {

    // ------------------------- test helpers ----------------------------

    private void checkOutputHeader(String expectedHeader) {
        String[] executeStdOutLines = CommandTestUtils.captureExecuteStdOutputLines(testCommand, testLibrary);
        assertEquals("Unexpected group output header.", expectedHeader, executeStdOutLines[0]);
    }

    // ------------------------- parseArguments tests --------------------

    @Test
    public void testParseArgumentsIllegalArgument() {
        String blankArg = "";
        CommandTestUtils.checkArgumentInput(testCommand, false, blankArg);
        CommandTestUtils.checkArgumentInput(testCommand, false, "nonsense");
    }

    @Test
    public void testParseArgumentsLegalArgument() {
        CommandTestUtils.checkArgumentInput(testCommand, true, TITLE_ARGUMENT);
        CommandTestUtils.checkArgumentInput(testCommand, true, AUTHOR_ARGUMENT);
    }
    @Test
    public void testParseArgumentsSpaces(){
        boolean result = testCommand.parseArguments("     ");
        assertFalse("Given Argument should not be accepted",result);

    }
    @Test

    public void testParseArgumentsSpaces2(){
        boolean result = testCommand.parseArguments("              TITLE");
        assertTrue("Given Argument should be accepted",result);

    }
    @Test

    public void testParseArgumentsSpaces3(){
        boolean result = testCommand.parseArguments("                   TITLE     ");
        assertTrue("Given Argument should be accepted",result);

    }
    @Test

    public void testParseArgumentsSpaces4(){
        boolean result = testCommand.parseArguments("AUTHOR    ");
        assertTrue("Given Argument should be accepted",result);

    }
    @Test

    public void testParseArgumentsSpaces5(){
        boolean result = testCommand.parseArguments("AUTHOR                   ");
        assertTrue("Given Argument should be accepted",result);

    }
    @Test

    public void testParseArgumentsSpaces6(){
        boolean result = testCommand.parseArguments("AUTHOR      Word2tabs");
        assertFalse("Given Argument should not be accepted",result);

    }
    @Test

    public void testParseArgumentsSpaces7(){
        boolean result = testCommand.parseArguments("AUTHOR\n"+"Word2");
        assertFalse("Given Argument should not be accepted",result);

    }
    @Test

    public void testParseArgumentsSpaces8(){
        boolean result = testCommand.parseArguments("AUTHOR TITLE     ");
        assertFalse("Given Argument should not be accepted",result);

    }
    @Test

    public void testParseArgumentsSpaces9(){
        boolean result = testCommand.parseArguments("             AUTHOR          ");
        assertTrue("Given Argument should  be accepted",result);

    }


    // ------------------------- execute tests --------------------

    @Test
    public void testExecuteEmptyBookData() {
        testLibrary = new LibraryData();
        List<BookEntry> bookData = Collections.<BookEntry>emptyList();
        FieldTestUtils.setPrivateField(testLibrary, testLibrary.getClass(), "books", bookData);

        String expectedConsoleOutput = "The library has no book entries.";
        CommandTestUtils.checkExecuteConsoleOutput(testCommand, testLibrary, expectedConsoleOutput);
    }

    @Test
    public void testExecuteGroupByTitleLines() {
        checkOutputLineCount(16); // one for the header, one for each group and one for each title entry
    }

    @Test
    public void testExecuteGroupByAuthorLines() {
        testCommand = new GroupCmd(AUTHOR_ARGUMENT);
        checkOutputLineCount(16); // one for the header, one for each group and one for each title entry
    }

    @Test
    public void testExecuteGroupByTitleHeader() {
        checkOutputHeader(String.format(GROUP_HEADER_OUTPUT, TITLE_ARGUMENT));
    }

    @Test
    public void testExecuteGroupByAuthorHeader() {
        testCommand = new GroupCmd(AUTHOR_ARGUMENT);
        checkOutputHeader(String.format(GROUP_HEADER_OUTPUT, AUTHOR_ARGUMENT));
    }

    @Test
    public void testExecuteGroupByTitleGroups() {
        String[] executeStdOutLines = CommandTestUtils.captureExecuteStdOutputLines(testCommand, testLibrary);
        List<String> expectedGroups = List.of("A", "B", "C", "D", "E", "F");
        checkGroupOutputOrder(executeStdOutLines, expectedGroups);
    }

    @Test
    public void testExecuteGroupByAuthorGroups() {
        testCommand = new GroupCmd(AUTHOR_ARGUMENT);
        String[] executeStdOutLines = CommandTestUtils.captureExecuteStdOutputLines(testCommand, testLibrary);
        List<String> expectedGroups = List.of("A Author", "B Author", "C Author", "D Author", "E Author", "F Author");
        checkGroupOutputOrder(executeStdOutLines, expectedGroups);
    }
}