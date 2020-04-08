import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SearchCmdBasicTest extends SearchCmdTest {

    // ------------------------- parseArguments tests --------------------

    @Test
    public void testParseArgumentsIllegalArgument() {
        String blankArg = "";
        CommandTestUtils.checkArgumentInput(testCommand, false, blankArg);

        String argWithSpaces = "invalid search query";
        CommandTestUtils.checkArgumentInput(testCommand, false, argWithSpaces);
    }

    @Test
    public void testParseArgumentsLegalArgument() {
        CommandTestUtils.checkArgumentInput(testCommand, true, SINGLE_WORD_TITLE);

        String argWithHyphen = "Hundred-Dollar";
        CommandTestUtils.checkArgumentInput(testCommand, true, argWithHyphen);
    }

    @Test
    public void testParseArgumentsSpaces(){
        boolean result = testCommand.parseArguments("     ");
        assertFalse("Given Argument should not be accepted",result);

    }
    @Test

    public void testParseArgumentsSpaces2(){
        boolean result = testCommand.parseArguments("              word");
        assertTrue("Given Argument should be accepted",result);

    }
    @Test

    public void testParseArgumentsSpaces3(){
        boolean result = testCommand.parseArguments("                   WORD     ");
        assertTrue("Given Argument should be accepted",result);

    }
    @Test

    public void testParseArgumentsSpaces4(){
        boolean result = testCommand.parseArguments("WORD    ");
        assertTrue("Given Argument should be accepted",result);

    }
    @Test

    public void testParseArgumentsSpaces5(){
        boolean result = testCommand.parseArguments("WORD                   ");
        assertTrue("Given Argument should be accepted",result);

    }
    @Test

    public void testParseArgumentsSpaces6(){
        boolean result = testCommand.parseArguments("Word      Word2tabs");
        assertFalse("Given Argument should not be accepted",result);

    }
    @Test

    public void testParseArgumentsSpaces7(){
        boolean result = testCommand.parseArguments("Word\n"+"Word2");
        assertFalse("Given Argument should not be accepted",result);

    }
    @Test

    public void testParseArgumentsSpaces8(){
        boolean result = testCommand.parseArguments("Word1 Word2     ");
        assertFalse("Given Argument should not be accepted",result);

    }
    @Test

    public void testParseArgumentsSpaces9(){
        boolean result = testCommand.parseArguments("Word1\n                                 word2               ");
        assertFalse("Given Argument should not be accepted",result);

    }


    // ------------------------- execute tests --------------------

    @Test
    public void testExecuteFindExactMatch() {
        String expectedConsoleOutput = SINGLE_WORD_TITLE;
        CommandTestUtils.checkExecuteConsoleOutput(testCommand, testLibrary, expectedConsoleOutput);
    }

    @Test
    public void testExecuteFindWordMatch() {
        String expectedConsoleOutput = MULTI_WORD_TITLE_A;
        testCommand = new SearchCmd(MULTI_WORD_SEARCH_TERM_SINGLE_HIT);
        CommandTestUtils.checkExecuteConsoleOutput(testCommand, testLibrary, expectedConsoleOutput);
    }

    @Test
    public void testExecuteFindNoMatch() {
        String searchTerm = "Unknown";
        String expectedConsoleOutput = NO_HITS_FOUND_MESSAGE + searchTerm;
        testCommand = new SearchCmd(searchTerm);
        CommandTestUtils.checkExecuteConsoleOutput(testCommand, testLibrary, expectedConsoleOutput);
    }
}
