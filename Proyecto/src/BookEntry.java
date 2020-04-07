import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

/**
 * Immutable class encapsulating data for a single book entry.
 */
public final class BookEntry {
    public static final int MIN_RATING = 0;
    public static final int MAX_RATING = 5;
    public static final String AUTHORS_SEPARATOR = ", ";
    public static final String BLANK_LINE = "\n";
    public static final String PRINT_RATING = "Rating: ";
    public static final String PRINT_ISBN = "ISBN: ";
    public static final String PRINT_PAGES = " pages";
    public static final String BY_AUTHORS = "by ";
    public static final String FORMAT_NUMBER_OF_DECIMALS = "%.2f";
    /**
     * Private field that is used to store the information about the number of pages of a Book
     */
    private final int pages;
    /**
     * Private field (String) used to store the information about the title of the Book.
     */
    private final String title;
    /**
     * Private field (Array of Strings) used to store the information regarding the author(s) of the Book.
     */
    private final String[] authors;
    /**
     * Private field (float) used to store the information regarding the rating of the Book, must be between 0 and 5.
     */
    private final float rating;
    /**
     * Private field (String) used to store the information regarding the ISBN number of the Book.
     */
    private final String ISBN;

    /**
     * Constructor of the class BookEntry that is used to Initialize all the private fields of the class.
     *
     * @param name String that contains the title of the book.
     * @param authors Array of Strings that contains all the information regarding the author(s) of the book.
     * @param rating Float that describes the rating of the book, must be within the accepted range (between 0 and 5).
     * @param ISBN String that describes the ISBN number of the book.
     * @param pages int that describes the number of pages of the book.
     * @throws NullPointerException if a member of the parameter authors is null or if the parameters name or ISBN are null.
     * @throws IllegalArgumentException if the rating is not within the accepted range, uses the method checkRating for it.
     */

    public BookEntry(String name, String[] authors, float rating , String ISBN, int pages){
        isNullConstructorParameter(name,authors,ISBN);
        isMemberNull(authors);
        checkRating(rating);
        checkPages(pages);
        this.pages = pages;
        this.title = name;
        this.authors = authors.clone();
        this.rating = rating;
        this.ISBN = ISBN;
    }

    /**
     * Public Method used to get the rating of a  book.
     *
     * @return this method should return the field rating (Float) of the object.
     */

    public float getRating() {
        return rating;
    }
    /**
     * Public Method used to get the authors of a  book.
     *
     * @return this method should return the field author (Array of Strings) of the object.
     */

    public String[] getAuthors() {
        return authors.clone();
    }
    /**
     * Public Method used to get the Title of a  book.
     *
     * @return this method should return the field title (String) of the object.
     */

    public String getTitle() {

        return title;
    }

    /**
     * Public Method used to get the pages of a  book.
     *
     * @return this method should return the field pages (int) of the object.
     */

    public int getPages() {
        return pages;
    }

    /**
     * Public Method used to get the ISBN number of a class instance.
     *
     * @return this method should return the field ISBN (String) of the object.
     */

    public String getISBN() {
        return ISBN;
    }

    /**
     * Public Method that is used to verify if a given object is equal to the object being used (the one that invokes this method)
     * For that it will check individually if all the private fields of both objects are the same after some initial checks
     * (like verifying if they have the same reference of if they are of the same Class).
     *
     * @param object Object that is going to be compared with the object that invokes the method.
     * @return true if all the private fields are equal (or if they share the same reference and if they pass the first initial checks) and false otherwise.
     */

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof BookEntry)){
            return false;
        }
        BookEntry bookEntry = (BookEntry) object;
        return pages == bookEntry.pages &&
                Float.compare(bookEntry.rating, rating) == 0 &&
                title.equals(bookEntry.title) &&
                Arrays.equals(authors, bookEntry.authors) &&
                ISBN.equals(bookEntry.ISBN);
    }

    /**
     * Overridden Public Method that is used to produce the has Code of an instance of the class (object of class BookEntry).
     * For that it Will be taken into account the value of all of its fields. (Which describe the object).
     *
     * @return integer that represents the has code of the object.
     */
    
    @Override
    public int hashCode() {
        int result = Objects.hash(pages, title, rating, ISBN);
        result = 31 * result + Arrays.hashCode(authors);
        return result;
    }

    /**
     * Overridden Public Method that is used to transform an instance of this class into a String,
     * being able to use the method System.out.println with the objects of this class.
     *
     * @return It must return a String that contains all the information about the object. (all the values of the fields that describe an object of this class).
     */
    @Override
    public String toString() {
        return  title
                + BLANK_LINE + BY_AUTHORS + authorsToString(authors)
                + BLANK_LINE +PRINT_RATING + String.format(Locale.ROOT, FORMAT_NUMBER_OF_DECIMALS, rating)
                + BLANK_LINE + PRINT_ISBN + ISBN
                + BLANK_LINE + pages + PRINT_PAGES;
    }

    /**
     * Private Method that is used as a Helper Method of toString in the Class BookEntry.
     * It is used to create an String from the array of Strings that describe the authors of a book.
     *
     * @param authors Array of Strings that contains the information about the author(s) of the book.
     * @return a single String that has the same information contained in the Array authors but in the correct format.
     */

    private String authorsToString(String[] authors) {
        StringBuilder result = new StringBuilder();
        for (int i = 0 ; i<authors.length;i++){
            result.append(authors[i]);
            if (i != authors.length-1){
                result.append(AUTHORS_SEPARATOR);
            }
        }
        return result.toString();
    }
    /**
     * Private method that is used in the constructor of BookEntry.
     * It is used to determine if the parameter rating is within the accepted range of values. (0.0 -5.0).
     *
     * @param rating Float that describes the rating of a given book
     * @throws IllegalArgumentException if the rating is not within the accepted range.
     */
    private  void checkRating(float rating) {
        if (rating < MIN_RATING || rating > MAX_RATING){
            throw new IllegalArgumentException(ExceptionsMessages.INVALID_RATING_MESSAGE);
        }
    }
    /**
     * Private method that is used in the constructor of BookEntry.
     * It is used to determine if one of the elements of an Array of Strings is null (if one of the elements in the Array of authors is null)
     *
     * @param authors Array of Strings that contains the information regarding
     * @throws NullPointerException if one of the members of authors is null.
     */

    public static void isMemberNull(String[] authors) {
        for (String author : authors){
            Objects.requireNonNull(author, ExceptionsMessages.NULL_MEMBERS_MESSAGE);
        }
    }
    /**
     * Private method that is in the constructor of BookEntry
     * It is used to determine if any of the given parameters is null
     *
     * @param name String that describes the title of the book.
     * @param authors Array of Strings that contains all the information regarding the author(s) of the book.
     * @param ISBN String that describes the ISBN number of the book.
     * @throws NullPointerException if any of the given parameters is null
     */
    public static void isNullConstructorParameter(String name, String[] authors, String ISBN) {
        Objects.requireNonNull(name, ExceptionsMessages.NUll_EXCEPTION_MESSAGE);
        Objects.requireNonNull(authors, ExceptionsMessages.NUll_EXCEPTION_MESSAGE);
        Objects.requireNonNull(ISBN, ExceptionsMessages.NUll_EXCEPTION_MESSAGE);
    }

    /**
     * Private method that is used in the constructor of BookEntry.
     * It is used to check if the given number of pages of a Book is not a negative number
     *
     * @param pages Number of pages of a Book
     * @throws IllegalArgumentException if the given number of pages is negative.
     */
    public static void checkPages(int pages){
        if (pages<0){
            throw new IllegalArgumentException(ExceptionsMessages.ERROR_MESSAGE_PAGES);
        }
    }
}
