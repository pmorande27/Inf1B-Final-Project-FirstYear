import java.nio.file.Path;
import java.util.Objects;

/**
 * Class for the Command ADD. It is used to add a database (of ending csv) containing information about BookEntries to a LibraryData.
 * This LibraryData contains a field that it is a List of book Entries and it will be updated with the information of the database.
 */

public class AddCmd extends LibraryCommand {
    /**
     * Private String Constant used to indicate the end of the name of the allowed database (csv).
     */
    private static final String CSV_EXTENSION = ".csv";
    /**
     * Private Field that when initialized will contain the path of the Database.
     */
    private Path parsedArgument;

    /**
     * Constructor of the class that will call the constructor of the super class.
     * This will call the method parseArguments to initialize the field parseArgument.
     *
     * @param argumentInput this is the input given by the user (String) that will contain information about where the file is located (path). To be valid it
     */

    public AddCmd(String argumentInput) {
        super(CommandType.ADD, argumentInput);
    }

    /**
     * Overridden method from the super class (LibraryCommand) where this is an abstract method.
     * This method is used to execute the command ADD. Therefore it will load the data of the given csv file to a LibraryData
     *
     * @param data book data to be considered for command execution.
     */
    @Override
    public void execute(LibraryData data) {
       Objects.requireNonNull(data, ExceptionsMessages.NUll_DATA_EXCEPTION_MESSAGE);
       Objects.requireNonNull(parsedArgument, ExceptionsMessages.ERROR_IN_PARSING_MESSAGE);
       data.loadData(parsedArgument);
    }

    /**
     * Overridden method form the super class (Library Command).
     * In this class the method parseArguments is used to check if the given input is valid.
     * for that it will check if the ending of the input is ".csv".
     *
     * @param argumentInput argument input for this command
     * @return it returns true if the Argument Input is valid and false otherwise.
     */

    @Override
    protected boolean parseArguments(String argumentInput) {
        super.parseArguments(argumentInput);
        if (argumentInput.strip().endsWith(CSV_EXTENSION)) {
            parsedArgument = Path.of(argumentInput.strip());
            return true;
        }
        return false;


    }
}



