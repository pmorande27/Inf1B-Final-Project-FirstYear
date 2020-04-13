import java.util.*;

/**
 * Class for Command Group, this command is used to print all the books of a Library grouped by author or title, depending on the modality chosen by the user.
 */
public class GroupCmd extends LibraryCommand {
    /**
     * Private constant (String) that is used to print a message, informing the user of the type of groups that the command is going to print.
     */
    private static final String DATA_BY = "Grouped data by ";
    /**
     * Private constant (String) used to turn a character into a String.
     */
    private static final String STRING_CONVERTER = "";
    /**
     * Private Constant (String) that is used as the header of each group in this command.
     */
    private static final String GROUP_HEADER = "## ";
    /**
     * Private Constant (String) that is used to identify the number group when printing the books by title.
     */
    private static final String NUMBER_GROUP = "[0-9]";
    /**
     * Private field that is used to store the information about the command given by the user, as it is a CommandOptions it can take the values AUTHOR or TITLE.
     */
    private CommandOptions command;

    /**
     * Constructor of the class, it creates a Group Command. It calls the super Class constructor.
     * This SuperClass Constructor will call the overridden method parseArguments to try to initialize the fields of the created Group Command.
     *
     * @param ArgumentInput String given by the user. It is expected to take the value of either AUTHOR or TITLE.
     * @throws IllegalArgumentException if the given ArgumentInput is not valid.
     * @throws NullPointerException if the given ArgumentInput is null.
     */
    public GroupCmd(String ArgumentInput){
        super(CommandType.GROUP,ArgumentInput);
    }

    /**
     * Overridden method that is used to execute the command Group.
     * if the given Library is not empty it will try to order (and print) the books in groups using a helper method.
     * If it is empty it will print a message to the user.
     *
     * @param data book data to be considered for command execution.
     * @throws NullPointerException if the given LibraryData is null or if the command is null, the last case should not happen if the logic of the programme is maintained.However,
     * this exception makes the code more safe from reflection.
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, ExceptionsMessages.NUll_DATA_EXCEPTION_MESSAGE);
        Objects.requireNonNull(this.command, ExceptionsMessages.ERROR_IN_PARSING_MESSAGE);
        List<BookEntry> books = data.getBookData();
        if (books.isEmpty()) {
            System.out.println(ListCmd.NO_BOOK_ENTRIES);
        } else {
            groupData(books);
        }
    }

    /**
     * Private Method that is used as a helper Method in execute.
     * This method is used to iterate over the books and to create a hashMap that will held the information about the groups,
     * each key of the hashMap will be linked to a different group
     * This method uses two helper methods in function of what type of group has been chosen by the user.
     *
     * @param books is the list of books contained in the given Library, to enter this method it must not be empty.
     * @throws IllegalArgumentException if the command is not valid, this should not happen if the logic of the programme is maintained.However,
     * this exception makes the code more safe from reflection.
     */
    private void groupData(List<BookEntry> books) {
        HashMap<String, List<String>> groupedData = new HashMap<>();
        Iterator<BookEntry> bookIter;
        bookIter = books.iterator();
        while (bookIter.hasNext()) {
            BookEntry book = bookIter.next();
            switch (this.command) {
                case AUTHOR:
                    executeGroupByAuthor(groupedData, book);
                    break;
                case TITLE:
                    executeGroupByTitle(groupedData, book);
                    break;
                default:
                    throw new IllegalArgumentException(ExceptionsMessages.ERROR_IN_PARSING_MESSAGE);
            }
        }
            executePrinting(groupedData);
    }

    /**
     * Private method used as a helper method of groupData, this method is only evoked when the command is equal to TITLE,
     * in this case the method will try to organise all the books in groups by title in a hashMap
     * for that the key will be the first letter of the title or the numeric group if the first character is a number. (The group should be case-insensitive).
     *
     * @param groupedData HashMap that contains all the different groups of books.
     * @param book BookEntry that is going to be analysed and added to the hashMap.
     */
    private void executeGroupByTitle( HashMap<String, List<String>> groupedData ,BookEntry book) {
        String key;
        if (Character.isDigit(book.getTitle().charAt(0))){
            key = NUMBER_GROUP;
        }
        else{
            key =STRING_CONVERTER+ Character.toUpperCase(book.getTitle().charAt(0)) ;
        }
        addBookToHashMapAndToKeys( key,  groupedData, book);
    }

    /**
     * This method is used as a Helper method of GroupData, it is only evoked when the command is equal to AUTHOR.
     * In this case the method will create a key for each author that has written the book, so a book can appear in more than one group
     *
     * @param groupedData HashMap that contains all the different groups of book`s titles.
     * @param book BookEntry that is going to be analysed and added to the hashMap.
     */
    private void executeGroupByAuthor( HashMap<String, List<String>> groupedData ,BookEntry book) {
        for (String key : book.getAuthors()){
                addBookToHashMapAndToKeys(key,groupedData,book);
        }
    }

    /**
     * Private Method that is used in both executeGroupByAuthor and executeGroupByTitle,
     * This method given a key and a hashMap it will look if the key is already in the hashMap and if it is not it will add the key and the value,
     * if the key is already there it will add the value to the list of values(titles) that is linked to such key.
     * If the key is not in the hashMap it will update the list keys.
     *
     * @param key group were the title of the book is going to be added.
     * @param GroupedData  HashMap that contains all the different groups of book`s titles.
     * @param book BookEntry that is going to be analysed and added to the hashMap.
     */
    private void addBookToHashMapAndToKeys(String key, HashMap<String,List<String>> GroupedData, BookEntry book){
        if (!GroupedData.containsKey(key)){
            List<String> list = new ArrayList<>();
            list.add(book.getTitle());
            GroupedData.put(key,list);
        }
        else{
            GroupedData.get(key).add(book.getTitle());
        }

    }
    /**
     * Private Method, used in the method groupData.
     * This method is used to print all the titles contained as values in of a HashMap, taking into account the lexicographical order.
     *
     * @param groupedData  HashMap that contains all the different groups of book titles.
     */
    private void executePrinting(HashMap<String, List<String>> groupedData) {
        List<String> keys = new ArrayList<>(groupedData.keySet());
        Collections.sort(keys);
        System.out.println(DATA_BY + command.toString());
        for (String key : keys) {
            System.out.println( GROUP_HEADER + key);
            Collections.sort(groupedData.get(key));
            for (String title:groupedData.get(key)){
                System.out.println("\t"+title);
            }
        }
    }


    /**
     * Overridden Method used to determine if the user's input is valid.
     * For that the first condition is that the argument is not null, it should also take into account if it is one of the accepted commands(AUTHOR or TITLE).
     *
     * @param argumentInput argument input for this command
     * @return true if the Argument has passed all the tests and false otherwise.
     * @throws NullPointerException if the given Argument is null.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        super.parseArguments(argumentInput);
        return parseCommandOption(argumentInput.strip());
    }

    /**
     * Private Method uses as a Helper method in parseArguments.
     * This method is used to determine if the given argument matches one of the two accepted command values that are of the type CommandOptions,
     * and can have the values of AUTHOR or TITLE
     *
     * @param option argument input given by the user.
     * @return true if the input matches one of the two command options and false otherwise.
     */
    private boolean parseCommandOption(String option) {
        for (CommandOptions options : CommandOptions.values()) {
            if (options.name().equals(option)) {
                this.command = options;
                return true;
            }
        }
        return false;
    }
}
