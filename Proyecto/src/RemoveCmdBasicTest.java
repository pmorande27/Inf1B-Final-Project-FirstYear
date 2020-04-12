import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RemoveCmdBasicTest extends RemoveCmdTest {
    RemoveCmd testCommandByAuthors;
    LibraryData data2;
    AddCmd addDatabase1;
    AddCmd addDatabase5;


    @Before
    public void setup() {
        testCommandByAuthors=  new RemoveCmd("AUTHOR J.K. Rowling");
        data2 =new LibraryData();
        addDatabase1 = new AddCmd("books01.csv");
        addDatabase5 = new AddCmd("books05.csv");

        testCommand = new RemoveCmd(TITLE_ARGUMENT + " " + TITLE_VALUE_ARGUMENT);

        testLibrary = new LibraryData();
        List<BookEntry> bookData = new ArrayList<>();
        bookData.add(new BookEntry("TitleA", new String[] { "AuthorA" }, 3.2f, "ISBNA", 500));
        bookData.add(new BookEntry(TITLE_VALUE_ARGUMENT, new String[] { AUTHOR_VALUE_ARGUMENT }, 4.3f, "ISBNB", 400));
        bookData.add(new BookEntry("TitleC", new String[] { "AuthorC" }, 1.3f, "ISBNC", 300));
        FieldTestUtils.setPrivateField(testLibrary, testLibrary.getClass(), "books", bookData);
    }

    // ------------------------- parseArguments tests --------------------

    @Test
    public void testParseArgumentsIllegalArgument() {
        String blankArg = "";
        CommandTestUtils.checkArgumentInput(testCommand, false, blankArg);
        CommandTestUtils.checkArgumentInput(testCommand, false, TITLE_ARGUMENT + " " + blankArg);
        CommandTestUtils.checkArgumentInput(testCommand, false, AUTHOR_ARGUMENT + " " + blankArg);
        CommandTestUtils.checkArgumentInput(testCommand, false, "nonsense");
    }

    @Test
    public void testParseArgumentsLegalArgument() {
        String[] valueArgs = new String[] { TITLE_VALUE_ARGUMENT, AUTHOR_VALUE_ARGUMENT, GENERIC_VALUE_ARGUMENT };
        String[] typeArgs = new String[] { TITLE_ARGUMENT, AUTHOR_ARGUMENT };

        for (String typeArg : typeArgs) {
            for (String valueArg : valueArgs) {
                CommandTestUtils.checkArgumentInput(testCommand, true, typeArg + " " + valueArg);
            }
        }
    }
    @Test
    public void testParseArgumentsSpaces(){
        boolean result = testCommand.parseArguments("     ");
        assertFalse("Given Argument should not be accepted",result);

    }
    @Test

    public void testParseArgumentsSpaces2(){
        boolean result = testCommand.parseArguments("    TITLE          word");
        assertTrue("Given Argument should be accepted",result);

    }
    @Test

    public void testParseArgumentsSpaces3(){
        boolean result = testCommand.parseArguments("     AUTHOR              WORD     ");
        assertTrue("Given Argument should be accepted",result);

    }
    @Test

    public void testParseArgumentsSpaces4(){
        boolean result = testCommand.parseArguments("AUTHOR    WORD    ");
        assertTrue("Given Argument should be accepted",result);

    }
    @Test

    public void testParseArgumentsSpaces5(){
        boolean result = testCommand.parseArguments("TITLE     WORD    ");
        assertTrue("Given Argument should be accepted",result);

    }
    @Test

    public void testParseArgumentsSpaces6(){
        boolean result = testCommand.parseArguments("TITLE");
        assertFalse("Given Argument should not be accepted",result);

    }
    @Test

    public void testParseArgumentsSpaces7(){
        boolean result = testCommand.parseArguments("TITLE     ");
        assertFalse("Given Argument should not be accepted",result);

    }
    @Test

    public void testParseArgumentsSpaces8(){
        boolean result = testCommand.parseArguments("AUTHOR     ");
        assertFalse("Given Argument should not be accepted",result);

    }
    @Test

    public void testParseArgumentsSpaces9(){
        boolean result = testCommand.parseArguments("AUTHOR");
        assertFalse("Given Argument should not be accepted",result);

    }
    @Test

    public void testParseArgumentsSpaces10(){
        boolean result = testCommand.parseArguments("AUTHOR\tword1");
        assertTrue("Given Argument should  be accepted",result);

    }
    @Test

    public void testParseArgumentsSpaces11(){
        boolean result = testCommand.parseArguments("AUTHOR\nword1");
        assertTrue("Given Argument should not be accepted",result);

    }

    // ------------------------- execute tests --------------------

    @Test
    public void testExecuteRemoveTitle() {
        checkRemoveTitleExecute(testCommand, testLibrary, TITLE_VALUE_ARGUMENT);
    }

    @Test
    public void testExecuteRemoveTitleConsoleOut() {
        String expectedConsoleOutput = String.format(TITLE_REMOVE_MESSAGE, TITLE_VALUE_ARGUMENT);
        CommandTestUtils.checkExecuteConsoleOutput(testCommand, testLibrary, expectedConsoleOutput);
    }

    @Test
    public void testExecuteRemoveAuthor() {
        testCommand = new RemoveCmd(AUTHOR_ARGUMENT + " " + AUTHOR_VALUE_ARGUMENT);
        checkRemoveAuthorExecute(testCommand, testLibrary, AUTHOR_VALUE_ARGUMENT);
    }
    @Test
    public void testEffectiveRemove(){

        testCommand.parseArguments("TITLE 1984");
        LibraryData data =new LibraryData();

        AddCmd add = new AddCmd("books05.csv");
        add.execute(data);
        int initialCount =data.getBookData().size();

        testCommand.execute(data);
        int final_count = data.getBookData().size();
        Assert.assertEquals(initialCount-1,final_count);
    }
    @Test
    public void testNotEffectiveRemove(){

        testCommand.parseArguments("TITLE Paths to God: Living the Bhagavad Gita");
        LibraryData data =new LibraryData();

        AddCmd add = new AddCmd("books05.csv");
        add.execute(data);
        int initalCount = data.getBookData().size();

        testCommand.execute(data);
        int final_count_find = data.getBookData().size();
        add.execute(data);
        testCommand.parseArguments("TITLE Paths to GOD: Living the Bhagavad Gita");
        testCommand.execute(data);
        int notfoundCunt = data.getBookData().size();
        Assert.assertEquals(initalCount-1,final_count_find);
        Assert.assertEquals(initalCount,notfoundCunt);

    }
    @Test
    public void testRemoveByAuthor(){
        addDatabase1.execute(data2);
        int initialCount = data2.getBookData().size();
        testCommandByAuthors.execute(data2);
        int finalCount = data2.getBookData().size();
        assertEquals(initialCount-3,finalCount);
    }
    @Test
    public void testRemoveByAuthor2(){
        addDatabase1.execute(data2);
        String expectedOutput = "3 books removed for author: J.K. Rowling";
        CommandTestUtils.checkExecuteConsoleOutput(testCommandByAuthors,data2,expectedOutput);
    }
    @Test
    public void testRemoveByAuthor3(){
        addDatabase1.execute(data2);
        String expectedOutput = "0 books removed for author: nonsense";
        testCommandByAuthors.parseArguments("AUTHOR nonsense");
        CommandTestUtils.checkExecuteConsoleOutput(testCommandByAuthors,data2,expectedOutput);
    }
    @Test
    public void testRemoveByAuthor4(){
        addDatabase1.execute(data2);
        String expectedOutput = "0 books removed for author: nonsense";
        testCommandByAuthors.parseArguments("AUTHOR nonsense");
        CommandTestUtils.checkExecuteConsoleOutput(testCommandByAuthors,data2,expectedOutput);
    }
    @Test
    public void testRemoveByAuthor5(){
        addDatabase5.execute(data2);
        testCommandByAuthors.execute(data2);
        SearchCmd search = new SearchCmd("HARRY");
        String expectedOutput="No hits found for search term: HARRY";
        CommandTestUtils.checkExecuteConsoleOutput(search,data2,expectedOutput);
    }
    @Test
    public void testRemoveByAuthor6CaseSensitive(){
        addDatabase5.execute(data2);
        testCommandByAuthors.parseArguments("AUTHOR J.k. ROWLING");
        String expectedOutput = "0 books removed for author: J.k. ROWLING";
        CommandTestUtils.checkExecuteConsoleOutput(testCommandByAuthors,data2,expectedOutput);
    }



    @Test
    public void testExecuteRemoveAuthorConsoleOut() {
        testCommand = new RemoveCmd(AUTHOR_ARGUMENT + " " + AUTHOR_VALUE_ARGUMENT);

        int removedAuthors = 1;
        String expectedConsoleOutput = String.format(AUTHOR_REMOVE_MESSAGE, removedAuthors, AUTHOR_VALUE_ARGUMENT);
        CommandTestUtils.checkExecuteConsoleOutput(testCommand, testLibrary, expectedConsoleOutput);
    }


    @Test
    public void testExecuteNotFound() {
        checkEntryNotFound();
    }
}
