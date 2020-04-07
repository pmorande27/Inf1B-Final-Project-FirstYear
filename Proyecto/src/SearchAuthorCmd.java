import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class SearchAuthorCmd extends LibraryCommand {
    public static final String NO_BOOKS_FOUND = "No book found for the author: ";
    public static final String BOOKS_FOUND = "found ";
    public static final String NUMBER_OF_BOOKS_FOUND = " book(s) for the author: ";
    private String author;
    /**
     * Create the specified command and initialise it with
     * the given command argument.
     *
     * @param argumentInput argument input as expected by the extending subclass.
     * @throws IllegalArgumentException if given arguments are invalid
     * @throws NullPointerException     if any of the given parameters are null.
     */
    public SearchAuthorCmd( String argumentInput) {
        super(CommandType.SEARCHAUTHOR, argumentInput);

    }

    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data);
        Objects.requireNonNull(author);
        ArrayList<String> result = new ArrayList<>();
        for (BookEntry book: data.getBookData()){
            if (isAuthor(author,book)){
                result.add(book.getTitle());
            }
        }
        Collections.sort(result);
        printResult(result);

    }

    private void printResult(ArrayList<String> result) {
        if(result.isEmpty()){
            System.out.println(NO_BOOKS_FOUND + author);
        }
        else{
            System.out.println(BOOKS_FOUND +result.size()+ NUMBER_OF_BOOKS_FOUND + author);
            for(String title: result){
                System.out.println(title);
            }
        }
    }

    private boolean isAuthor(String author, BookEntry book) {
        for (String element : book.getAuthors()){
            if (element.toUpperCase().equals(author.toUpperCase())){
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean parseArguments(String argumentInput) {
        super.parseArguments(argumentInput);
        if(argumentInput.isBlank()){
            return false;

        }
        this.author = argumentInput;
        return true;
    }
}
