import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Class for the Command LIST. This command is used to print all the books contained in a LibraryData,
 * it has two formats: short, which includes only the titles of the books in the printing
 * and long that includes all the information about the books. The default format is short.
 */
public class ListCmd extends LibraryCommand {
    /**
     * Private String used as a message to show how many books are in a given Library (LibraryData).
     */
    private static final String BOOKS_FOUND = " books in library:";
    /**
     * Private String used as a message to tell the user that there are no book Entries in the given library.
     */
    public static final String NO_BOOK_ENTRIES = " The library has no book entries.";
    /**
     * Private String used to identify the given input with one of the possible commands.
     */
    private static final String COMMAND_OPTION = "Command";
    /**
     * Private String used to create new Blank Lines when printing.
     */
    private static final String BLANK_LINE="\n";
    /**
     * Private field that is used to store the information about the format of listing that can be short, long or blank (using then the default value)
     */
    private CommandOptionsList command;

    /**
     * Constructor of the class ListCmd. It calls the super-class Constructor using the CommandType List and the Argument input provided in the constructor.
     * The super constructor will check if the given input is null
     * it will call the method parseArgument to check if the given input is valid (the overridden version).
     *
     * @param argumentInput (String) Input given by the user, it should refer to the format of listing that the User wants (short or long).
     * Therefore it should only take 3 possible values:short, long or blank(""),
     */
    public ListCmd(String argumentInput){
        super(CommandType.LIST,argumentInput);
    }

    /**
     * Overridden Method from the abstract method of the superClass.
     * This method is used to print all the books contained in the given LibraryData according to the format given by the user.
     * It uses a helper method called listBooks.
     *
     * @param data book data to be considered for command execution.
     * @throws NullPointerException if data is null or if the command is null.
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data,Exceptions.NUll_DATA_EXCEPTION_MESSAGE);
        Objects.requireNonNull(command,Exceptions.ERROR_IN_PARSING_MESSAGE);
        List<BookEntry> books = data.getBookData();

        if (isNotEmptyBooks(books)){
            listBooks(books);
        }
    }

    /**
     * Private method uses as a helper method of execute.
     * This method is used to iterate over the List of BookEntries contained in a given libraryData and to print the content of the BookEntries,
     * according to the format given by the user.
     *
     * @param books is the list of bookEntries contained in the given Library. It must not be empty to enter to this method.
     * @throws IllegalArgumentException if the command is not equivalent to one of the accepted commands,
     * that would mean that an error has occurred during parsing
     */
    private void listBooks(List<BookEntry> books){
        Iterator<BookEntry> bookIterator = books.iterator();
        System.out.println(books.size()+BOOKS_FOUND);
        while (bookIterator.hasNext()) {
            BookEntry book = bookIterator.next();
            switch (this.command){
                case shortCommand:
                case blackCommand:
                    System.out.println(book.getTitle());
                    break;
                case longCommand:
                    System.out.println(book + BLANK_LINE);
                    break;
                default:
                    throw new IllegalArgumentException(Exceptions.ERROR_IN_PARSING_MESSAGE);
                }
            }
        }
    /**
     * private Method that is used as a helper method of listBooks.
     * It is used to check if a given List of BookEntries is not empty.
     *
     * @param books given List of BookEntries that it is going to be checked.
     * @return false if the List is  empty (printing a message saying so) and true if it is not.
     */
    private boolean isNotEmptyBooks(List<BookEntry> books){
        if (books.isEmpty()){
            System.out.println(NO_BOOK_ENTRIES);
            return false;
        }
        return true;
    }

    /**
     * Overridden Method used to check the validity of the given Argument Input (Supplied by the User)
     * In this case it will check that the given input is corresponds with one of the allowed formats.
     *
     * @param argumentInput argument input for this command
     * @return true if it is valid and false otherwise.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        super.parseArguments(argumentInput);
        return parseCommandOption(argumentInput.strip());
    }

    /**
     * Helper Method of parseArguments that is used to check if there is a correspondence between the given input and the allowed formats
     * If it is blank then the command will be equal to the CommandOptionsList shortCommand,
     * if the argument Input is short then the command will be equal to the CommandOptionList shortCommand and
     * if the argument input is equal to long the command will be equal to longCommand.
     * Other option will return false.
     *
     * @param ArgumentInput argument input for this command
     * @return true if the given ArgumentInput corresponds to an allowed format and false otherwise.
     */
    private boolean parseCommandOption(String ArgumentInput) {
        if (ArgumentInput.isBlank()){
            this.command = CommandOptionsList.blackCommand;
            return true;
        }
        for (CommandOptionsList options : CommandOptionsList.values()) {
            if (options.name().equals(ArgumentInput+ COMMAND_OPTION)) {
                this.command = options;
                return true;
            }
        }
        return false;
    }
}
