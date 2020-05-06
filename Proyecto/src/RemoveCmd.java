import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class of the command Remove. It is used to remove a book from the Library.
 * It has two modalities, it can remove a book searching by the title of the book, or it can remove book(s) by searching one of the authors of the book(s).
 */
public class RemoveCmd extends LibraryCommand {

    /**
     * Private (String) constant used to print a message to the user,
     * in this case the message informs the user that the searched book (by title) has been removed from the Library
     */
    private static final String REMOVED_SUCCESSFULLY = ": removed successfully.";
    /**
     * private String constant used to print a message to the user,
     * in this case the message informs the user that the book that he/she is trying to remove is not in the library.
     */
    private static final String NOT_FOUND_MESSAGE = ": not found.";
    /**
     * Private (String) constant used to print a message to the user,
     * in this case th message informs the user that the remove command has been evoked in the AUTHOR form.
     */
    private static final String REMOVED_BY_AUTHOR = " books removed for author: ";
    /**
     * Private (String) constant used to print a message to the user,
     * in this case the message informs the user that the given library is empty.
     */
    private static final String NO_BOOK_ENTRIES = "The library has no book entries.";
    /**
     * Field  (enum CommandOptions) that can take the values AUTHOR or TITLE,
     * it will determine the executing modality of the command.
     */
    private CommandOptions command;
    /**
     * String that contains the value that is going to be searched for in all the book of the library to delete the ones that match that value.
     * (it can be either the Title of the book or the one of the Authors).
     */
    private String value;

    /**
     * Constructor of the class that calls the constructor of the superClass. It creates a Remove Command
     * In addition the SuperClass will call the overridden method ParseArguments, and therefore it will try to initialize the values of the fields of the Command Remove.
     *
     * @param argumentInput is the String provided by the user. It is expected to contain as first word AUTHOR or TITLE.
     * @throws IllegalArgumentException if the given argument input is not accepted by the overridden method parseArgument (look to super contructor call).
     * @throws NullPointerException if the given argument input is null.
     */
    public RemoveCmd(String argumentInput){
        super(CommandType.REMOVE,argumentInput);
    }

    /**
     * Overridden method from the superClass (LibraryCommand it is an Abstract method). Is used to execute the command Remove.
     * The command Remove is used to delete a book from a given LibraryData. THe method will check which of the two modalities has been evoked by the user,
     * and it will either remove a book searching by author or remove a book searching by title.
     *
     * @param data book data to be considered for command execution.
     * @throws NullPointerException if the given LibraryData is null or if the command or value are null, the last one  should not happen
     * if the logic of the programme is maintained. However, this exception makes the code more safe from reflection..
     * @throws IllegalArgumentException if the command is not valid, this should not happen if the logic of the programme is maintained.However,
     * this exception makes the code more safe from reflection.
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, ExceptionsMessages.NUll_DATA_EXCEPTION_MESSAGE);
        Objects.requireNonNull(command, ExceptionsMessages.ERROR_IN_PARSING_MESSAGE);
        Objects.requireNonNull(value, ExceptionsMessages.ERROR_IN_PARSING_MESSAGE);
        List<BookEntry> books = data.getBookData();
        if (books.isEmpty()) {
            System.out.println(NO_BOOK_ENTRIES);
        }
        switch (this.command){
            case TITLE:
                executeRemoveTitle(books);
                break;
            case AUTHOR:
                executeRemoveAuthor(books);
                break;
            default:
                throw new IllegalArgumentException(ExceptionsMessages.ERROR_IN_PARSING_MESSAGE);
        }
    }

    /**
     * Helper Method used in the execute method, this method is only evoked when command is equal to TITLE (CommandOptions).
     * Therefore this method will iterate through all the books contained in the given Library until it finds one whose title matches the value(title) given by the user.
     * That book will be removed and the loop will be exited (there are no repeated books).
     * If there is no book that matches the given title a message informing the user will be printed.
     *
     * @param books is the list of bookEntries contained in the given Library. It must not be empty to enter to this method.
     */
    private void executeRemoveTitle(List<BookEntry> books) {

        boolean success = false;
        Iterator<BookEntry>bookIterator= books.iterator();
        while (bookIterator.hasNext()){
            BookEntry book = bookIterator.next();
            if (book.getTitle().equals(value)){
                System.out.println(value + REMOVED_SUCCESSFULLY);
                bookIterator.remove();
                success = true;
                break;
            }
        }
        if (!success){
            System.out.println(value+NOT_FOUND_MESSAGE);
        }
    }
    /**
     * Helper Method used in the method execute, this method only is evoked when command is equal to AUTHOR (CommandOptions)-
     * Therefore this method will iterate through all the books contained in the given Library until it finds one whose one of its authors matches the value
     * (stored in the field author)
     * That book will be removed and the loop continue to remove all the books of that author.
     * there will be a count of how many books are being removed that will be printed at the end of the execution.
     *
     * @param books is the list of bookEntries contained in the given Library. It must not be empty to enter to this method.
     */

    private void executeRemoveAuthor(List<BookEntry> books) {
        int bookCount = 0;
        Iterator<BookEntry>bookIterator = books.iterator();
        while (bookIterator.hasNext()) {
            BookEntry book = bookIterator.next();
            if (Arrays.asList(book.getAuthors()).contains(value)) {
                bookIterator.remove();
                bookCount++;
            }
        }
        System.out.println( bookCount +  REMOVED_BY_AUTHOR+ value );
    }


    /**
     * Overridden Method from the super class (LibraryCommand), this method is used to check the validity of the Arguments given by the user.
     * This method is also used to update the parameters of the command.
     * In this case a valid argument is that where the input's first word is the word AUTHOR or TITLE.
     * If the argument is accepted then the fields of the class will be updated.
     *
     * @param argumentInput argument input for this command
     * @return true if the argument is allowed and false otherwise.
     * @throws NullPointerException if the given argumentInput is null.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        super.parseArguments(argumentInput);
        String[] twoArguments = getCommandAndValue(argumentInput);
        return checkValidity(twoArguments);
    }

    /**
     * Private method that is used as a helper method in ParseArguments, it is used to separate in two the given ArgumentInput,
     * it will be divided into the command (TITLE or AUTHOR) and the value that is going to be removed.
     * To do this it will use a regular expression to assure that it the given input will be divided by any white-space character that it contains,
     * it could be a space, a tab or a intro (\n)
     * Therefore
     * "Command\tValue"
     * "Command\nValue"
     * "Command Value"
     * are all accepted as two words.
     *
     * @param argumentInput given input by the user
     * @return the array that contains the two words
     */
    private String[] getCommandAndValue(String argumentInput) {
        Pattern whitespace = Pattern.compile("\\s+");
        Matcher matcher = whitespace.matcher(argumentInput.strip());
        String finalArgument = matcher.replaceAll(" ");
        return finalArgument.split(" ",2);
    }

    /**
     * Private Method that is used as a helper Method o parseArguments.
     * It is used to determine the validity of the given input, in this case the method checks that there are at least two words,
     * and  that  the second one is not blank.
     *
     * @param twoArguments is the split version of the User's input, the first element is the first word and the second element is the rest of it.
     * @return false if it is not an acceptable input and if it passes the first initial checks,
     * it will call another method (parseCommandOption) to check if the first word is an allowed command.
     * In that case  it will return the sentence of that method.
     */

    private boolean checkValidity(String[] twoArguments) {
        if (twoArguments.length == 2 && !twoArguments[1].isBlank()){
            value = twoArguments[1];
            return parseCommandOption(twoArguments[0]);

        }
            return false;
    }

    /**
     * Private method that is used as a Helper Method of checkValidity.
     * It is used to check if the first word of the user input corresponds to the value of one of the accepted Allowed commands,
     * which are contained in the enum class CommandOptions.
     *
     * @param Command String that corresponds with the first word of the user's input.
     * @return true if it matches one of the accepted command options (which are stored in the enum class CommandOptions) and false otherwise.
     */
    private boolean parseCommandOption(String Command) {
        for (CommandOptions options : CommandOptions.values()) {
            if (options.name().equals(Command)) {
                this.command = options;
                return true;
            }
        }
        return false;
    }

}
