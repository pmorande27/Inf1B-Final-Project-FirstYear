import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class of the command SEARCH that is used to search for a  key value (given by the user) in the title of all the books of the given library data,
 * if that value is found the title of the BookEntry wll be printed.
 */
public class SearchCmd extends LibraryCommand{
    /**
     * Private String used to print a message when no books are found for the input given by the user.
     */
    private static final String NOT_FOUND = "No hits found for search term: ";
    /**
     * Private String used to print a new BlankLine
     */
    private static final String BLANK_LINE = "\n";
    /**
     * Public String used to initialize an empty String
     */
    public static final String EMPTY_STRING = "";
    /**
     * Private Field that once initialised it will contain the information about the input given by the user
     * if and only if that value is allowed.
     */
    private String parsedArgument;

    /**
     * Constructor of the class that calls the SuperClass Constructor giving as parameters the CommandType Search and the given argument Input.
     * The super constructor will call the overridden version of the method parseArgument to check the validity of the given ArgumentInput
     * (and therefore to try to initialize the field parsedArgument).
     *
     * @param argumentInput (String) argument provided by the User
     */
    public SearchCmd(String argumentInput){
        super(CommandType.SEARCH,argumentInput);
    }

    /**
     * Overridden method from the class LibraryCommand,
     * in this particular case it is used to search and print all the books whose titles contain a given String (provided by the user)
     *
     * @param data book data to be considered for command execution.
     * @throws NullPointerException if the given LibraryData is null or if the parsedArgument is null, the second case means that there has been a problem during parsing.
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, ExceptionsMessages.NUll_DATA_EXCEPTION_MESSAGE);
        Objects.requireNonNull(parsedArgument, ExceptionsMessages.ERROR_IN_PARSING_MESSAGE);
        searchExecute(data.getBookData());

    }

    /**
     * Private method that is used as a helper Method of execute.
     * It is used to iterate through all the books contained in a given Library.
     * If a BookEntry's title contains the key value (Non-case sensitive) the title will be printed by the method.
     *
     * @param books list of all the books contained in a given Library.
     */
    private void searchExecute( List<BookEntry> books) {
        StringBuilder result = new StringBuilder(EMPTY_STRING);
        Iterator<BookEntry> bookIter = books.iterator();
        while (bookIter.hasNext()){
            BookEntry book = bookIter.next();
            if(containsNotCaseSensitive(book.getTitle(),parsedArgument)){
                result.append(book.getTitle()).append(BLANK_LINE);
            }
        }
        if (result.length() == 0){
            System.out.println(NOT_FOUND + parsedArgument );
        }
        else{
            System.out.print(result);
        }
    }

    /**
     * Private method that is used to check if a given String (parsedArgument) is contained in the other not taking into account the Upper and Lower Case,
     * just the letters itself.
     *
     * @param title String that contains the information of the title of the book, is the String that might or might not contain the second String.
     * @param parsedArgument String that contains the information about the key value that is going to be searched in the title.
     * The method will check if this String is contained in the first one.
     * @return true if the title contains the parsedArguments(not case-sensitive) and false otherwise.
     */

    private boolean containsNotCaseSensitive(String title, String parsedArgument) {
        return (title.toLowerCase().contains(parsedArgument.toLowerCase()));
    }

    /**
     * Overridden method from the class LibraryCommand that in this case is used to check the validity of the given Argument.
     * The given Argument must be a single Word, if this is the case this method will initialize/update the value of the field parsedArgument.
     *
     * @param argumentInput argument input for this command
     * @return true if the given Argument passes the validity checks and false otherwise,
     * in this case the validity check consists in the method singleWord (helper method).
     * @throws NullPointerException if the given argumentInput is null.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        super.parseArguments(argumentInput);
        String stripArgument =argumentInput.strip();
        if(singleWord(stripArgument)){
            this.parsedArgument = stripArgument;
            return true;
        }
        return false;
    }

    /**
     * Private method that is used to check if a given String is formed just by a single word,
     * the regular expression "\\s" is used to check  if the given input is formed by a single word, with the find method of the matcher it will search in all the string for
     * a space-like character (tab, spaces, \n...), if it found it means that the given input is not formed by a single word.
     *
     * @param argumentInput String that corresponds to the input given by the user
     * @return true the given String is formed by just one word and false otherwise.
     */
    private boolean singleWord(String argumentInput) {
        if (argumentInput.isBlank()){
            return false;
        }
        Pattern spaceSearch = Pattern.compile("\\s");
        Matcher matches = spaceSearch.matcher(argumentInput);
        return !matches.find();
    }
}
