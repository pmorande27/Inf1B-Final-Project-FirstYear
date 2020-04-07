import java.util.Objects;

public class Exceptions extends Exception{
    public static final String NULL_MEMBERS_MESSAGE = "Members of authors must not be null";
    public static final String INVALID_RATING_MESSAGE = "Rating Must be between 0 and 5";
    private static final String NUll_EXCEPTION_MESSAGE= "Given object must not be null";
    public static final String ERROR_IN_PARSING_MESSAGE = "Argument Input parsed Incorrectly";
    private static final String ERROR_MESSAGE_PAGES = "The Number of pages must not be negative!";
    public static final int MIN_RATING = 0;
    public static final int MAX_RATING = 5;

    public Exceptions(){
        super();

    }

    /**
     * public method that is used in the constructor of BookEntry.
     * It is used to determine if the parameter rating is within the accepted range of values. (0.0 -5.0).
     *
     * @param rating Float that describes the rating of a given book
     * @throws IllegalArgumentException if the rating is not within the accepted range.
     */
    public static void checkRating(float rating) {
        if (rating < MIN_RATING || rating > MAX_RATING){
            throw new IllegalArgumentException(INVALID_RATING_MESSAGE);
        }
    }
    /**
     * Public method that is in the constructor of BookEntry.
     * It is used to determine if one of the elements of an Array of Strings is null (if one of the elements in the Array of authors is null)
     *
     * @param authors Array of Strings that contains the information regarding
     *
     */

    public static void isMemberNull(String[] authors) {
        for (String author : authors){
            Objects.requireNonNull(author, NULL_MEMBERS_MESSAGE);
        }
    }
    /**
     * Public method that is in the constructor of BookEntry
     * It is used to determine if any of the given parameters is null
     *
     * @param name String that describes the title of the book.
     * @param authors Array of Strings that contains all the information regarding the author(s) of the book.
     * @param ISBN String that describes the ISBN number of the book.
     */
    public static void isNullConstructorParameter(String name, String[] authors, String ISBN) {
        Objects.requireNonNull(name,NUll_EXCEPTION_MESSAGE);
        Objects.requireNonNull(authors,NUll_EXCEPTION_MESSAGE);
        Objects.requireNonNull(ISBN,NUll_EXCEPTION_MESSAGE);
    }
    public static void isNull(Object e){
        Objects.requireNonNull(e,NUll_EXCEPTION_MESSAGE);
    }
    public static void checkPages(int pages){
        if (pages<0){
            throw new IllegalArgumentException(ERROR_MESSAGE_PAGES);
        }
    }
}
