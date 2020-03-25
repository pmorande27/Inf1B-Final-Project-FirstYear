import java.util.ArrayList;
import java.util.Collections;

public class SearchAuthorCmd extends LibraryCommand {
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
        Exceptions.isNull(data);
        Exceptions.isNull(author);
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
            System.out.println("No book found for the author: "+ author);
        }
        else{
            System.out.println("found " +result.size()+ " book(s) for the author: "+ author);
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
        Exceptions.isNull(argumentInput);
        if(argumentInput.isBlank()){
            return false;

        }
        this.author = argumentInput;
        return true;
    }
}
