import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;

public abstract class CommandTest {

    protected static final String TITLE_ARGUMENT = "TITLE";
    protected static final String AUTHOR_ARGUMENT = "AUTHOR";

    protected static final String BLANK_ARGUMENT = "";

    protected LibraryCommand testCommand;
    protected LibraryData testLibrary;

    public CommandTest() {
        testCommand = null;
        testLibrary = null;
    }

    protected abstract CommandType getCmdType();

    // ------------------------- initialisation tests --------------------

    @Test
    public void testClassCommandExtension() {
        assertEquals(testCommand.getClass() + " has unexpected superclass.", LibraryCommand.class,
                testCommand.getClass().getSuperclass());
    }

    @Test
    public void testCtorSuperclassCall() {
        CommandTestUtils.checkCtorSuperclassCall(testCommand, getCmdType());
    }

    // ------------------------- parseArguments tests --------------------

    @Test
    public void testIsParseArgumentsOverridden() {
        CommandTestUtils.checkIfParseArgumentsIsOverridden(testCommand);
    }

    //-------------------------Exception tests--------------------------

    @Test(expected = NullPointerException.class)
    public void testExecuteLibraryDataNull() {
        testCommand.execute(null);
    }
    @Test (expected = NullPointerException.class)
    public void testNullArgument(){
        String Argument = null;
        testCommand.parseArguments(Argument);
    }
    @Test (expected = IllegalArgumentException.class)
    public void testConstructorIllegalArgument(){
        String Argument = "illegal";
        AddCmd FailCommand =new AddCmd(Argument) {
        };
    }
    @Test(expected = NullPointerException.class)
    public void NullLibraryData(){

        testCommand.execute(null);
    }
    @Test(expected = NullPointerException.class)
    public void testNullValue() throws IllegalAccessException {

        Field[] a = testCommand.getClass().getDeclaredFields();
        for (Field b : a) {
            if (!java.lang.reflect.Modifier.isStatic(b.getModifiers())) {
                b.setAccessible(true);
                b.set(testCommand, null);
            }

        }
        testCommand.execute(testLibrary);
    }
}