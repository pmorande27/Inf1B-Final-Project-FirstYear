import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/** 
 * Class responsible for loading
 * book data from file.
 */
public class LibraryFileLoader {

    private static final String NO_CONTENT_LOADED = "ERROR: No content loaded before parsing.";
    public static final String ARGUMENT_SEPARATOR = ",";
    public static final String AUTHOR_SEPARATOR = "-";
    /**
     * Contains all lines read from a book data file using
     * the loadFileContent method.
     * 
     * This field can be null if loadFileContent was not called
     * for a valid Path yet.
     * 
     * NOTE: Individual line entries do not include line breaks at the 
     * end of each line.
     */
    private List<String> fileContent;

    /** Create a new loader. No file content has been loaded yet. */
    public LibraryFileLoader() { 
        fileContent = null;
    }

    /**
     * Load all lines from the specified book data file and
     * save them for later parsing with the parseFileContent method.
     * 
     * This method has to be called before the parseFileContent method
     * can be executed successfully.
     * 
     * @param fileName file path with book data
     * @return true if book data could be loaded successfully, false otherwise
     * @throws NullPointerException if the given file name is null
     */
    public boolean loadFileContent(Path fileName) {
        Objects.requireNonNull(fileName, "Given filename must not be null.");
        boolean success = false;

        try {
            fileContent = Files.readAllLines(fileName);
            success = true;
        } catch (IOException | SecurityException e) {
            System.err.println("ERROR: Reading file content failed: " + e);
        }

        return success;
    }

    /**
     * Has file content been loaded already?
     * @return true if file content has been loaded already.
     */
    public boolean contentLoaded() {
        return fileContent != null;
    }

    /**
     * Parse file content loaded previously with the loadFileContent method.
     * 
     * @return books parsed from the previously loaded book data or an empty list
     * if no book data has been loaded yet.
     */
    public List<BookEntry> parseFileContent() {
        int lineCount = 1;
        if (!contentLoaded()){
            System.err.println(NO_CONTENT_LOADED);
            return Collections.emptyList();
        }
        List<BookEntry> books = new ArrayList<>();
        boolean isFirst = true;
        for (String line:  fileContent) {
            try {
                if (isFirst) {
                    isFirst = false;
                } else {

                    addNewBook(line, books);
                    lineCount++;
                }
            }
            catch (IllegalArgumentException | NullPointerException e){
                lineCount++;
                System.err.println("Error: Could not Load the book in line: "+ lineCount + " possible error in the parameters of the book" );
            }
        }


        return books;
    }

    /**
     * Private method used to create a new book from the received line, it also adds this to a given list of books.
     *
     * @param line given String that contains all the information required to create a BookEntry, separated by commas.
     * @param books given List that contains BookEntries.
     */

    private void addNewBook(String line, List<BookEntry> books) {
        String[] lineComponents  = line.split(ARGUMENT_SEPARATOR);
        String title= lineComponents[0];
        String[] authors = lineComponents[1].split(AUTHOR_SEPARATOR);
        float rating = Float.parseFloat(lineComponents[2]);
        String ISBN = lineComponents[3];
        int pages = Integer.parseInt(lineComponents[4]);
        books.add(new BookEntry(title,authors,rating,ISBN,pages));
    }
}
