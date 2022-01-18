package z11.tests;

import org.junit.jupiter.api.*;
import z11.libraryapp.DbHandler;
import z11.libraryapp.errors.DmlQueryError;
import z11.libraryapp.errors.TransactionError;
import z11.libraryapp.errors.UnavailableDB;
import z11.libraryapp.model.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DbHandlerTest {
    public DbHandler dbManager;
    public int testInt = 999999;
    public String testString = "test";

    @BeforeAll
    public void init() throws UnavailableDB {
        dbManager = new DbHandler();
    }

    @AfterAll
    public void close() throws UnavailableDB{
        dbManager.closeConnetion();
    }

    @BeforeEach
    public void setUp() throws SQLException {
        dbManager.begin();
    }

    @AfterEach
    public void tearDown(){
        dbManager.rollback();
    }

    @org.junit.jupiter.api.Test
    void getBooks() throws UnavailableDB {
        ArrayList<Book> books = dbManager.getBooks();
        assert(books.size() != 0);
        for(Book book : books){
            assertNotNull(book);
        }
    }

    @org.junit.jupiter.api.Test
    void getBook() throws UnavailableDB {
        assertNull(dbManager.getBook(testInt));
        Book book = dbManager.getBook(0);
        assertNotNull(book);
        ArrayList<Author> authors = dbManager.getBookAuthors(0);
        ArrayList<Genre> genres = dbManager.getBookGenres(0);
        for(int i = 0; i < authors.size(); i++){
            assertEquals(book.getAuthors().get(i).getName(), authors.get(i).getName());
        }
        for(int i = 0; i < genres.size(); i++){
            assertEquals(book.getGenres().get(i).getName(), genres.get(i).getName());
        }
    }

    @org.junit.jupiter.api.Test
    void getGenres() throws UnavailableDB {
        ArrayList<Genre> genres = dbManager.getGenres();
        assert(genres.size() != 0);
        for(Genre genre : genres){
            assert(genre != null);
        }
    }

    @org.junit.jupiter.api.Test
    void getGenre() throws UnavailableDB {
        assertNotNull(dbManager.getGenre(0));
    }

    @org.junit.jupiter.api.Test
    void getBookGenres() throws UnavailableDB {
        ArrayList<Genre> genres = dbManager.getGenres();
        assert(genres.size() != 0);
        for(Genre genre : genres){
            assert(genre != null);
        }
    }

    @org.junit.jupiter.api.Test
    void getBooksByGenre() throws UnavailableDB {
        ArrayList<Book> books = dbManager.getBooksByGenre(0);
        assert(books.size() != 0);
        for(Book book : books){
            assertNotNull(book);
        }
    }

    @org.junit.jupiter.api.Test
    void getBookInstances() throws UnavailableDB {
        ArrayList<BookInstance> books = dbManager.getBookInstances(0);
        assert(books.size() != 0);
        for(BookInstance bookInstance : books){
            assertNotNull(bookInstance);
        }
    }

    @org.junit.jupiter.api.Test
    void getBookInstance() throws UnavailableDB {
        assertNotNull(dbManager.getBookInstance(0));
    }

    @org.junit.jupiter.api.Test
    void getAuthors() throws UnavailableDB {
        ArrayList<Author> authors = dbManager.getAuthors();
        assert(authors.size() != 0);
        for(Author author : authors){
            assertNotNull(author);
        }
    }

    @org.junit.jupiter.api.Test
    void getAuthor() throws UnavailableDB {
        assertNotNull(dbManager.getAuthor(0));
    }

    @org.junit.jupiter.api.Test
    void getBookAuthors() throws UnavailableDB {
        ArrayList<Author> authors = dbManager.getBookAuthors(0);
        assert(authors.size() != 0);
        for(Author author : authors){
            assertNotNull(author);
        }
    }

    @org.junit.jupiter.api.Test
    void getAuthorBooks() throws UnavailableDB {
        ArrayList<Book> books = dbManager.getAuthorBooks(0);
        assert(books.size() != 0);
        for(Book book : books){
            assertNotNull(book);
        }
    }

    @org.junit.jupiter.api.Test
    void getAuthorGenres() throws UnavailableDB {
        ArrayList<Genre> genres = dbManager.getAuthorGenres(0);
        assert(genres.size() != 0);
        for(Genre genre : genres){
            assertNotNull(genre);
        }
    }

    @org.junit.jupiter.api.Test
    void getUsers() throws UnavailableDB {
        ArrayList<User> users = dbManager.getUsers();
        assert(users.size() != 0);
        for(User user : users){
            assertNotNull(user);
        }
    }

    @org.junit.jupiter.api.Test
    void getUserByLogin() throws UnavailableDB {
        assertNotNull(dbManager.getUserByLogin("admin"));
    }

    @org.junit.jupiter.api.Test
    void createUser() throws UnavailableDB {
        assertNull(dbManager.createUser(testString, testString, "admin", testString));
    }

    @org.junit.jupiter.api.Test
    void isUniqueLogin() throws UnavailableDB {
        assertFalse(dbManager.isUniqueLogin("admin"));
    }

    @org.junit.jupiter.api.Test
    void getHistoryNodes() throws UnavailableDB {
        ArrayList<HistoryNode> hNodes = dbManager.getHistoryNodes();
        assert(hNodes.size() != 0);
        for(HistoryNode node : hNodes){
            assertNotNull(node);
        }
    }

    @org.junit.jupiter.api.Test
    void getSeries() throws UnavailableDB {
        Series series = dbManager.getSeries(0);
        assertNotNull(series);

        ArrayList<Series> seriesArr = dbManager.getSeries();
        assert(seriesArr.size() != 0);
        for(Series tmp : seriesArr){
            assertNotNull(tmp);
        }
    }

    @org.junit.jupiter.api.Test
    void lendBook() throws TransactionError, UnavailableDB {
        addTestBook();
        addTestBookInstance();
        dbManager.reserveBook(testInt, 2);
        dbManager.lendBook(2, testInt);
        String a = dbManager.getBookInstanceStatus(testInt);
        dbManager.checkStatus(2, testInt, "LENT");
        assertTrue(dbManager.checkStatus(2, testInt, "LENT"));
    }

    @org.junit.jupiter.api.Test
    void returnBook() throws TransactionError, UnavailableDB {
        addTestBook();
        dbManager.reserveBook(testInt, 2);
        dbManager.lendBook(2, testInt);
        dbManager.checkStatus(2, testInt, "LENT");
    }

    @org.junit.jupiter.api.Test
    void reserveBook() throws UnavailableDB, TransactionError {
        addTestBook();
        addTestBookInstance();
        dbManager.reserveBook(testInt, 2);
        assertTrue(dbManager.checkStatus(2, testInt, "RESERVED"));
    }

    @org.junit.jupiter.api.Test
    void getBookInstanceStatus() throws TransactionError, UnavailableDB {
        addTestBook();
        addTestBookInstance();
        assertEquals(dbManager.getBookInstanceStatus(testInt), "AVAILABLE");
    }

    @org.junit.jupiter.api.Test
    void checkReservation() throws TransactionError, UnavailableDB {
        addTestBook();
        addTestBookInstance();
        assertFalse(dbManager.checkReservation(2, testInt));
        dbManager.reserveBook(testInt, 2);
        assertTrue(dbManager.checkReservation(2, testInt));
    }

    @org.junit.jupiter.api.Test
    void checkLent() throws UnavailableDB, TransactionError {
        addTestBook();
        addTestBookInstance();
        dbManager.reserveBook(testInt, 2);
        dbManager.lendBook(2, testInt);
        assertTrue(dbManager.checkStatus(2, testInt, "LENT"));
    }

    @org.junit.jupiter.api.Test
    void delGenre() throws UnavailableDB {
        dbManager.addGenre(testString);
        dbManager.delGenre(testString);
        assertNull(dbManager.getGenre(testString));
    }

    @org.junit.jupiter.api.Test
    void addGenre() throws UnavailableDB {
        assertNull(dbManager.getGenre(testString));
        dbManager.addGenre(testString);
        assertNotNull(dbManager.getGenre(testString));
    }

    @org.junit.jupiter.api.Test
    void delSeries() throws UnavailableDB {
        dbManager.addSeries(testString);
        dbManager.delSeries(dbManager.getSeriesId(testString));
        assertEquals(-1, dbManager.getSeriesId(testString));
    }

    @org.junit.jupiter.api.Test
    void addSeries() throws UnavailableDB {
        dbManager.addSeries(testString);
        assertNotEquals(-1, dbManager.getSeriesId(testString));
    }

    @org.junit.jupiter.api.Test
    void addBook() throws TransactionError, UnavailableDB {
        addTestCountry();
        addTestLanguage();
        dbManager.addBook(testInt, testString, testString, testInt, testInt, testString, testString,
                "null", testString, new ArrayList<Author>(), new ArrayList<Genre>());
        Book a = dbManager.getBook(testInt);
        assertNotNull(a);
    }

    @org.junit.jupiter.api.Test
    void delBook() throws TransactionError, UnavailableDB {
        assertNull(dbManager.getBook(testInt));
        addTestBook();
        dbManager.delBook(testInt);
        assertNull(dbManager.getBook(testInt));
    }

    @org.junit.jupiter.api.Test
    void addAuthor() throws UnavailableDB {
        assertNull(dbManager.getAuthor(testInt));
        Author author = new Author(testInt, testString, testString, testInt);
        dbManager.addAuthor(author);
    }

    @org.junit.jupiter.api.Test
    void delAuthor() throws UnavailableDB {
        assertNull(dbManager.getAuthor(testInt));
        addTestAuthor();
        dbManager.delAuthor(testInt);
        assertNull(dbManager.getBook(testInt));
    }

    @org.junit.jupiter.api.Test
    void addLanguage() throws UnavailableDB {
        assertEquals(-1, dbManager.getLanguageId(testString));
        dbManager.addLanguage(testString);
        assertNotEquals(-1, dbManager.getLanguageId(testString));
    }

    @org.junit.jupiter.api.Test
    void delLanguage() throws UnavailableDB {
        assertEquals(-1, dbManager.getLanguageId(testString));
        dbManager.addLanguage(testString);
        dbManager.delLanguage(dbManager.getLanguageId(testString));
        assertEquals(-1, dbManager.getLanguageId(testString));
        dbManager.addLanguage(testString);
        dbManager.delLanguage(testString);
        assertEquals(-1, dbManager.getLanguageId(testString));
    }

    @org.junit.jupiter.api.Test
    void addCountry() throws UnavailableDB {
        assertEquals(-1, dbManager.getCountryId(testString));
        dbManager.addCountry(testString);
        assertNotEquals(-1, dbManager.getCountryId(testString));
    }

    @org.junit.jupiter.api.Test
    void delCountry() throws UnavailableDB {
        assertEquals(-1, dbManager.getCountryId(testString));
        dbManager.addCountry(testString);
        dbManager.delCountry(dbManager.getCountryId(testString));
        assertEquals(-1, dbManager.getCountryId(testString));
        dbManager.addCountry(testString);
        dbManager.delCountry(testString);
        assertEquals(-1, dbManager.getCountryId(testString));

    }

    @org.junit.jupiter.api.Test
    void addBookInstance() throws TransactionError, UnavailableDB, SQLException {
        assertEquals(dbManager.getBookInstances(testInt).size(), 0);
        addTestBook();
        dbManager.addBookInstance(testInt, testInt);
        assertEquals(dbManager.getBookInstances(testInt).size(), 1);
    }

    @org.junit.jupiter.api.Test
    void delbookInstance() throws UnavailableDB, TransactionError, SQLException {
        addTestBook();
        dbManager.addBookInstance(testInt, testInt);
        assertEquals(dbManager.getBookInstances(testInt).size(), 1);
        dbManager.delBookInstance(testInt);
        assertEquals(dbManager.getBookInstances(testInt).size(), 0);
    }

    private void addTestBook() throws TransactionError, UnavailableDB {
        addTestCountry();
        addTestLanguage();
        dbManager.addBook(testInt, testString, testString, testInt, testInt, testString, testString,
                "null", testString, new ArrayList<Author>(), new ArrayList<Genre>());
    }

    private void addTestBookInstance() throws UnavailableDB {
        dbManager.addBookInstance(testInt, testInt);
    }

    private void delTestBookInstance() throws UnavailableDB {
        dbManager.delBookInstance(testInt);
    }

    private void delTestBook() throws UnavailableDB, TransactionError {
        dbManager.delBook(testInt);
    }

    private void addTestLanguage() throws UnavailableDB {
        dbManager.addLanguage(testString);
    }

    private void addTestCountry() throws UnavailableDB {
        dbManager.addCountry(testString);
    }

    private void addTestAuthor() throws UnavailableDB {
        Author author = new Author(testInt, testString, testString, testInt);
        dbManager.addAuthor(author);
    }

    private void addTestSeries() throws UnavailableDB {
        dbManager.addSeries(testString);
    }
}