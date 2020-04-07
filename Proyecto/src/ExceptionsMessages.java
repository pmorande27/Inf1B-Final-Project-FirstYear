import java.util.Objects;

public class ExceptionsMessages extends Exception {
    /**
     * Public constant that is used to print a message to the user informing that one of the members of an array is null.
     */
    public static final String NULL_MEMBERS_MESSAGE = "Members of authors must not be null";
    /**
     * Public constant that is used to print a message to the user informing that the rating is not in the accepted range.
     */
    public static final String INVALID_RATING_MESSAGE = "Rating Must be between 0 and 5";
    /**
     * Public constant used to inform the user that the pages of a book are negative
     */
    public static final String ERROR_MESSAGE_PAGES = "The Number of pages must not be negative!";
    /**
     * Public constant used to inform the user that a given object must not be null."
     */

    public static final String NUll_EXCEPTION_MESSAGE = "Given object must not be null";
    /**
     * Public constant used in the cmd classes, used to inform that an error has occurred during parsing.
     */
    public static final String ERROR_IN_PARSING_MESSAGE = "Argument Input parsed Incorrectly";

    /**
     * Public constant used in the cmd classes, used to inform the user that a given Library data is null.
     */
    public static final String NUll_DATA_EXCEPTION_MESSAGE ="Given Library data must not be null";

    private ExceptionsMessages() {
    }


}
