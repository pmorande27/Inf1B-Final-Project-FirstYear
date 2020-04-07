import java.util.Objects;

public class Exceptions extends Exception {
    public static final String NULL_MEMBERS_MESSAGE = "Members of authors must not be null";
    public static final String INVALID_RATING_MESSAGE = "Rating Must be between 0 and 5";
    public static final String NUll_EXCEPTION_MESSAGE = "Given object must not be null";
    public static final String ERROR_IN_PARSING_MESSAGE = "Argument Input parsed Incorrectly";
    public static final String ERROR_MESSAGE_PAGES = "The Number of pages must not be negative!";
    public static final String NUll_DATA_EXCEPTION_MESSAGE ="Given Library data must not be null";

    private Exceptions() {
    }


}
