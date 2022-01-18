package z11.libraryapp;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import  java.sql.PreparedStatement;
import java.sql.Types;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import z11.libraryapp.model.*;
import z11.libraryapp.errors.*;

public class DbHandler {

    private String login = "dsavytsk";
    private String password = "dsavytsk";
    private Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(16, 32, 1, 65536, 10);
    private Connection con = null;
    private int transCount = 0;

    public DbHandler() throws  UnavailableDB{
        getConnection();
    }

    public void getConnection() throws  UnavailableDB{
        try {
            if(!isConnected()){
                Class.forName("oracle.jdbc.driver.OracleDriver");
                this.con = DriverManager.getConnection(
                        "jdbc:oracle:thin:@ora4.ii.pw.edu.pl:1521/pdb1.ii.pw.edu.pl",
                        login, password);
            }
        } catch (ClassNotFoundException e){
            e.printStackTrace();
            System.exit(1);
        } catch (SQLException e){
            throw new UnavailableDB(e);
        }

    }

    public void closeConnetion() {
        try{
            con.close();
        } catch(SQLException ingored) {}
        finally{
            con = null;
        }
    }

    public boolean isConnected() {
        try{
            return this.con != null && !this.con.isClosed();
        } catch (SQLException ignored) {}
        return false;
    }

    public void begin() throws SQLException {
        con.setAutoCommit(false);
        transCount++;
    }

    public void rollback() {
        try{
            con.rollback();
            if(--transCount == 0) {
                con.setAutoCommit(true);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void commit() {
        try{
            if(--transCount == 0){
                con.commit();
                con.setAutoCommit(true);
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    private ResultSet ddlQuery(String query, Object... params) throws  DdlQueryError, UnavailableDB{
        getConnection();
        try{
            PreparedStatement stmt = con.prepareStatement(query);
            for(int i = 0; i < params.length; i++)
                stmt.setObject(i + 1, params[i]);

            ResultSet rs = stmt.executeQuery();
            return rs;
        } catch (SQLException e){
            throw new DdlQueryError(e);
        }
    }

    private void dmlQuery(String query, Object... params) throws DmlQueryError, UnavailableDB{
        getConnection();
        try {
            PreparedStatement stmt = con.prepareStatement(query);
            for(int i = 0; i < params.length; i++)
                stmt.setObject(i + 1, params[i]);
            stmt.executeQuery();
        } catch (SQLException e){
            throw new DmlQueryError(e);
        }
    }

    private void dmlTramsaction(String[] queries, Object... params) throws TransactionError, UnavailableDB{
        if(queries.length != params.length) throw new Error("invalid param number");
        getConnection();
        try{
            begin();
            for(int i = 0; i < queries.length; i++){
                dmlQuery(queries[i], params[i]);
            }
            commit();
        } catch (SQLException | DmlQueryError e){
            rollback();
            throw new TransactionError(e);
        }
    }

    public ArrayList<Book> getBooks() throws UnavailableDB{
        ArrayList<Book> books = new ArrayList<Book>();
        String query = "SELECT book.book_id, book.title, book.summary, book.publication_year, book.date_added, "
                + "book.pages, book.cover, country.name country, series.name series, language.name language "
                + "FROM book "
                + "JOIN country ON (book.country_id = country.country_id) "
                + "JOIN series ON (book.series_id = series.series_id) "
                + "JOIN language ON (book.language_id = language.language_id)"
                + "ORDER BY book.date_added DESC";
        try(ResultSet rs = ddlQuery(query)){
            while(rs.next()){
                try{
                    int id = rs.getInt(1);
                    String title = rs.getString(2);
                    String summary = rs.getString(3);
                    int publicationYear = rs.getInt(4);
                    Date dateAdded = rs.getDate(5);
                    int pages = rs.getInt(6);
                    String coverSrc = rs.getString(7);
                    String country = rs.getString(8);
                    String series = rs.getString(9);
                    String language = rs.getString(10);

                    ArrayList<Author> authors = getBookAuthors(id);
                    ArrayList<Genre> genres = getBookGenres(id);

                    books.add(new Book(id, title, summary, publicationYear, dateAdded, pages, coverSrc, country,
                            series, language, authors, genres));
                }
                catch(SQLException e){
                    continue;
                }
            }
        } catch(DdlQueryError | SQLException e){
            e.printStackTrace();
            System.exit(1);
        }
        return books;
    }

    public Book getBook(int bookId) throws UnavailableDB{
        String query = "SELECT book.book_id, book.title, book.summary, book.publication_year, book.date_added, "
                + "book.pages, book.cover, country.name country, series.name series, language.name language "
                + "FROM book "
                + "LEFT JOIN country ON (book.country_id = country.country_id) "
                + "LEFT JOIN series ON (book.series_id = series.series_id) "
                + "LEFT JOIN language ON (book.language_id = language.language_id) "
                + "where book.book_id = ?";
        Book book = null;
        try(ResultSet rs = ddlQuery(query, bookId)){
            if(!rs.next()) return book;
            int id = rs.getInt(1);
            String title = rs.getString(2);
            String summary = rs.getString(3);
            int publicationYear = rs.getInt(4);
            Date dateAdded = rs.getDate(5);
            int pages = rs.getInt(6);
            String coverSrc = rs.getString(7);
            String country = rs.getString(8);
            String series = rs.getString(9);
            String language = rs.getString(10);

            ArrayList<Author> authors = getBookAuthors(id);
            ArrayList<Genre> genres = getBookGenres(id);
            book = new Book(id, title, summary, publicationYear, dateAdded, pages, coverSrc,
                    country, series, language, authors, genres);
        } catch (SQLException | DdlQueryError e){
            e.printStackTrace();
        }
        return book;
    }

    public Book getBook(String title) throws UnavailableDB{
        String query = "SELECT book.book_id, book.title, book.summary, book.publication_year, book.date_added, "
                + "book.pages, book.cover, country.name country, series.name series, language.name language "
                + "FROM book "
                + "LEFT JOIN country ON (book.country_id = country.country_id) "
                + "LEFT JOIN series ON (book.series_id = series.series_id) "
                + "LEFT JOIN language ON (book.language_id = language.language_id) "
                + "where book.title = ?";
        Book book = null;
        try(ResultSet rs = ddlQuery(query, title)){
            if(!rs.next()) return book;
            int id = rs.getInt(1);
            String summary = rs.getString(3);
            int publicationYear = rs.getInt(4);
            Date dateAdded = rs.getDate(5);
            int pages = rs.getInt(6);
            String coverSrc = rs.getString(7);
            String country = rs.getString(8);
            String series = rs.getString(9);
            String language = rs.getString(10);

            ArrayList<Author> authors = getBookAuthors(id);
            ArrayList<Genre> genres = getBookGenres(id);
            book = new Book(id, title, summary, publicationYear, dateAdded, pages, coverSrc,
                    country, series, language, authors, genres);
        } catch (SQLException | DdlQueryError e){
            e.printStackTrace();
        }
        return book;
    }

    public ArrayList<Genre> getGenres() throws UnavailableDB {
        ArrayList<Genre> genres = new ArrayList<Genre>();
        String query = "select * from genre order by name asc";
        try(ResultSet rs = ddlQuery(query)){
            while(rs.next()){
                try{
                    int id = rs.getInt(1);
                    String name = rs.getString(2);
                    genres.add(new Genre(id, name));
                }
                catch(SQLException e){
                    continue;
                }
            }
        } catch(DdlQueryError | SQLException e){
            e.printStackTrace();
        }
        return genres;
    }

    public String getGenre(int genreId) throws UnavailableDB{
        String query = "SELECT genre.name FROM genre WHERE genre_id = ?";
        String genre = null;
        try(ResultSet rs = ddlQuery(query, genreId)){
            if(!rs.next()) return genre;
            genre = rs.getString(1);
        } catch(DdlQueryError | SQLException e){
            e.printStackTrace();
        }
        return genre;
    }

    public String getGenre(String genreName) throws UnavailableDB{
        String query = "SELECT genre.name FROM genre WHERE name = ?";
        String genre = null;
        try(ResultSet rs = ddlQuery(query, genreName)){
            if(!rs.next()) return genre;
            genre = rs.getString(1);
        } catch(DdlQueryError | SQLException e){
            e.printStackTrace();
        }
        return genre;
    }

    public ArrayList<Genre> getBookGenres(int bookId) throws UnavailableDB{
        String query = "SELECT genre.genre_id, genre.name "
                + "FROM genre "
                + "JOIN book_genre ON (genre.genre_id = book_genre.genre_id) "
                + "JOIN book ON (book.book_id = book_genre.book_id) "
                + "WHERE book.book_id = ?";
        ArrayList<Genre> genres = new ArrayList<Genre>();
        try(ResultSet rs = ddlQuery(query, bookId)){
            while (rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                genres.add(new Genre(id, name));
            }
        } catch(DdlQueryError | SQLException e){
            e.printStackTrace();
        }
        return genres;
    }

    public ArrayList<Book> getBooksByGenre(int genreId) throws UnavailableDB{
        ArrayList<Book> books = new ArrayList<Book>();
        String query = "select book_id, title, summary, publication_year, "
                + "date_added, pages, cover, c.name, s.name, l.name "
                + "from book join country c using(country_id) "
                + "join series s using(series_id) join language l using(language_id) "
                + "join book_genre using (book_id) join genre using(genre_id) where genre_id = ?";
        try(ResultSet rs = ddlQuery(query, genreId)){
            while(rs.next()){
                try{
                    int id = rs.getInt(1);
                    String title = rs.getString(2);
                    String summary = rs.getString(3);
                    int publicationYear = rs.getInt(4);
                    Date dateAdded = rs.getDate(5);
                    int pages = rs.getInt(6);
                    String coverSrc = rs.getString(7);
                    String country = rs.getString(8);
                    String series = rs.getString(9);
                    String language = rs.getString(10);

                    ArrayList<Author> authors = getBookAuthors(id);
                    ArrayList<Genre> genres = getBookGenres(id);

                    books.add(new Book(id, title, summary, publicationYear, dateAdded, pages, coverSrc, country,
                            series, language, authors, genres));
                }
                catch(SQLException e){
                    continue;
                }
            }
        } catch(DdlQueryError | SQLException e){
            e.printStackTrace();
        }
        return books;
    }

    public ArrayList<BookInstance> getBookInstances(int bookId) throws UnavailableDB{
        ArrayList<BookInstance> bookInstances = new ArrayList<BookInstance>();
        String query = "select book_instance_id, book_id, user_id, s.name "
                + "from book_instance bk join status s using(status_id) "
                + "where book_id = ?";
        try(ResultSet rs = ddlQuery(query, bookId)){
            while (rs.next()){
                int id = rs.getInt(1);
                int userId = rs.getInt(3);
                String status = rs.getString(4);
                bookInstances.add(new BookInstance(id, bookId, userId, status));
            }
        }
        catch (DdlQueryError | SQLException e){
            e.printStackTrace();
        }
        return bookInstances;
    }

    public BookInstance getBookInstance(int book_instance_id) throws UnavailableDB{
        String query = "select * from book_instance where book_instance_id = ?";
        BookInstance bookInstance = null;
        try(ResultSet rs = ddlQuery(query, book_instance_id)){
            if(!rs.next()) return bookInstance;
            int id = rs.getInt(1);
            int bookId = rs.getInt(2);
            int userId = rs.getInt(3);
            String status = rs.getString(4);
            bookInstance = new BookInstance(id, bookId, userId, status);
        } catch (SQLException | DdlQueryError e){
            e.printStackTrace();
        }
        return bookInstance;
    }

    public ArrayList<Book> getBooksInSameSeries(int bookId) throws UnavailableDB {
        ArrayList<Book> books = new ArrayList<Book>();
        String query = "SELECT b2.book_id, b2.title, b2.summary, b2.publication_year, b2.date_added, "
                + "b2.pages, b2.cover, c.name country, s.name series, l.name language "
                + "from book b1 join book b2 on(b1.series_id = b2.series_id) "
                + "join country c on (b2.country_id = c.country_id) "
                + "join series s on (b2.series_id = s.series_id) "
                + "join language l on (b2.language_id = l.language_id) "
                + "where (b1.book_id = ?)";
        try(ResultSet rs = ddlQuery(query, bookId)){
            while(rs.next()){
                try{
                    int id = rs.getInt(1);
                    String title = rs.getString(2);
                    String summary = rs.getString(3);
                    int publicationYear = rs.getInt(4);
                    Date dateAdded = rs.getDate(5);
                    int pages = rs.getInt(6);
                    String coverSrc = rs.getString(7);
                    String country = rs.getString(8);
                    String series = rs.getString(9);
                    String language = rs.getString(10);

                    ArrayList<Author> authors = getBookAuthors(id);
                    ArrayList<Genre> genres = getBookGenres(id);

                    books.add(new Book(id, title, summary, publicationYear, dateAdded, pages, coverSrc, country,
                            series, language, authors, genres));
                }
                catch(SQLException e){
                    continue;
                }
            }
        } catch(DdlQueryError | SQLException e){
            e.printStackTrace();
        }
        return books;
    }

    public ArrayList<Author> getAuthors() throws UnavailableDB{
        ArrayList<Author> authors = new ArrayList<Author>();
        String query = "select author_id, first_name, last_name, birth_year, "
                + "nvl(death_year, 0), biography, photo "
                + "from author order by last_name asc";
        try (ResultSet rs = ddlQuery(query)) {
            while(rs.next()){
                try{
                    int id = rs.getInt(1);
                    String first_name = rs.getString(2);
                    String last_name = rs.getString(3);
                    int birth_year = rs.getInt(4);
                    int death_year = rs.getInt(5);
                    String biography = rs.getString(6);
                    String photo_src = rs.getString(7);
                    authors.add(new Author(id, first_name, last_name, birth_year, death_year, biography, photo_src));
                }
                catch(SQLException e){
                    continue;
                }
            }
        } catch (DdlQueryError | SQLException e) {
            e.printStackTrace();
        }
        return authors;
    }

    public Author getAuthor(int authorId) throws UnavailableDB{
        String query = "select * from author where author_id = ?";
        Author author = null;
        try(ResultSet rs = ddlQuery(query, authorId)){
            if(rs.next()){
                int id = rs.getInt(1);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
                int birthYear = rs.getInt(4);
                int deathYear = rs.getInt(5);
                String biography = rs.getString(6);
                String photoSrc = rs.getString(7);
                author = new Author(id, firstName, lastName, birthYear, deathYear, biography, photoSrc);
            } else{
                return null;
            }
        } catch(DdlQueryError | SQLException e){
            e.printStackTrace();
        }
        return author;
    }

    public ArrayList<Author> getBookAuthors(int bookId) throws UnavailableDB{
        String query = "SELECT author.*"
                + "FROM  author "
                + "JOIN book_author ON (author.author_id = book_author.author_id) "
                + "JOIN book ON (book.book_id = book_author.book_id) "
                + "WHERE book.book_id = ?";
        ArrayList<Author> authors = new ArrayList<Author>();
        try(ResultSet rs = ddlQuery(query, bookId)){
            while(rs.next()){
                int id = rs.getInt(1);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
                int birthYear = rs.getInt(4);
                int deathYear = rs.getInt(5);
                String biography = rs.getString(6);
                String photoSrc = rs.getString(7);
                authors.add(new Author(id, firstName, lastName, birthYear, deathYear, biography, photoSrc));
            }
        } catch(DdlQueryError | SQLException e){
            e.printStackTrace();
        }
        return authors;
    }

    public ArrayList<Book> getAuthorBooks(int authorId) throws UnavailableDB{
        String query = "select book_id, title, summary, publication_year, "
                + "date_added, pages, cover, c.name, s.name, l.name "
                + "from book join country c using(country_id) "
                + "join series s using(series_id) join language l using(language_id) "
                + "join book_author using (book_id) "
                + "join author using (author_id) where author_id = ?";
        ArrayList<Book> books = new ArrayList<Book>();
        try(ResultSet rs = ddlQuery(query, authorId)){
            while(rs.next()){
                int id = rs.getInt(1);
                String title = rs.getString(2);
                String summary = rs.getString(3);
                int publicationYear = rs.getInt(4);
                Date dateAdded = rs.getDate(5);
                int pages = rs.getInt(6);
                String coverSrc = rs.getString(7);
                String country = rs.getString(8);
                String series = rs.getString(9);
                String language = rs.getString(10);

                ArrayList<Author> authors = getBookAuthors(id);
                ArrayList<Genre> genres = getBookGenres(id);

                books.add(new Book(id, title, summary, publicationYear, dateAdded, pages, coverSrc, country,
                        series, language, authors, genres));
            }
        }
        catch (DdlQueryError | SQLException e){
            e.printStackTrace();
        }
        return books;
    }

    public ArrayList<Genre> getAuthorGenres(int authorId) throws UnavailableDB{
        ArrayList<Genre> genres = new ArrayList<Genre>();
        String query = "select distinct genre_id, g.name "
                + "from genre g join book_genre using (genre_id) "
                + "join book_author using (book_id) "
                + "join author using(author_id) "
                + "where author_id = ?";
        try(ResultSet rs = ddlQuery(query, authorId)){
            while(rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                genres.add(new Genre(id, name));
            }
        }
        catch (DdlQueryError | SQLException e){
            e.printStackTrace();
        }
        return genres;
    }

    public boolean validateUser(String login, String password) throws UnavailableDB{
        login = login.toLowerCase();

        String query = "select password from users where login = ?";
        try(ResultSet rs = ddlQuery(query, login)){
            if(!rs.next()) return false;
            String hashedPassword = rs.getString(1);
            return encoder.matches(password, hashedPassword);
        } catch (DdlQueryError e) {
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<User> getUsers() throws UnavailableDB {
        ArrayList<User> users = new ArrayList<User>();
        String query = "select user_id, u.name, surname, login, password, "
                + "p.name from users u join permissions p using(permission_id)";
        try(ResultSet rs = ddlQuery(query)){
            while(rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String surname = rs.getString(3);
                String login = rs.getString(4);
                String password = rs.getString(5);
                String permission = rs.getString(6);
                users.add(new User(id, name, surname, login, password, permission));
            }
        } catch (SQLException | DdlQueryError e) {
            e.printStackTrace();
        }
        return users;
    }

    public void delUser(int userId) throws UnavailableDB {
        String query = "DELETE FROM users WHERE user_id = ?";
        try {
            dmlQuery(query, userId);
        } catch (DmlQueryError e){
            e.printStackTrace();
        }
    }

    public User getUserByLogin(String login) throws UnavailableDB{
        User user = null;
        login = login.toLowerCase();
        String query = "select user_id, u.name, surname, login, password, p.name "
                + "from users u join permissions p using(permission_id) where u.login = ?";
        try(ResultSet rs = ddlQuery(query, login)){
            if(!rs.next()) return user;
            user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), login, rs.getString(5), rs.getString(6));
        } catch (SQLException | DdlQueryError e){
            e.printStackTrace();
        }
        return user;
    }

    public User createUser(String name, String surname, String login, String password) throws UnavailableDB{
        login = login.toLowerCase();
        String hashedPassword = encoder.encode(password);
        String query = "insert into users values(null, ?, ?, ?, ?, 2)";
        User user = null;
        try{
            dmlQuery(query, name, surname, login, hashedPassword);
            user = getUserByLogin(login);
        } catch (DmlQueryError e){
            e.printStackTrace();
        }
        return user;
    }

    public boolean isUniqueLogin(String login) throws UnavailableDB{
        login = login.toLowerCase();
        String query = "SELECT count(*) FROM users WHERE login = ?";
        try(ResultSet rs = ddlQuery(query, login)){
            if(!rs.next()) return false;
            return rs.getInt(1) == 0;
        } catch (SQLException | DdlQueryError e){
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<HistoryNode> getHistoryNodes() throws UnavailableDB{
        ArrayList<HistoryNode> historyNodes = new ArrayList<HistoryNode>();
        String query = "SELECT bih_id, book_instance_id, user_id, borrow_date, "
                + "nvl(return_date, TO_DATE('2000-01-01', 'yyyy-mm-dd')) FROM book_instance_history";
        try(ResultSet rs = ddlQuery(query)){
            while(rs.next()){
                try{
                    int id = rs.getInt(1);
                    int bookInstanceId = rs.getInt(2);
                    int userId = rs.getInt(3);
                    Date dateBorrowed = rs.getDate(4);
                    Date dateReturned = rs.getDate(5);
                    if (dateReturned.toString().equals("2000-01-01")){
                        historyNodes.add(new HistoryNode(id, bookInstanceId, userId, dateBorrowed));
                    }
                    else{
                        historyNodes.add(new HistoryNode(id, bookInstanceId, userId, dateBorrowed, dateReturned));
                    }
                }
                catch(SQLException e){
                    continue;
                }
            }
        } catch(DdlQueryError | SQLException e){
            e.printStackTrace();
        }
        return historyNodes;
    }

    public Series getSeries(int seriesId) throws UnavailableDB{
        Series series = null;
        String query = "select * from series where series_id = ?";
        try(ResultSet rs = ddlQuery(query, seriesId)) {
            while (rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                series = new Series(id, name);
            }
        } catch (DdlQueryError | SQLException e) {
            e.printStackTrace();
        }
        return series;
    }

    public ArrayList<Series> getSeries() throws UnavailableDB{
        ArrayList<Series> series = new ArrayList<Series>();
        String query = "select * from series";
        try(ResultSet rs = ddlQuery(query)) {
            while (rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                series.add(new Series(id, name));
            }
        } catch (DdlQueryError | SQLException e) {
            e.printStackTrace();
        }
        return series;
    }

    public void lendBook(int userId, int bookInstanceId) throws UnavailableDB{
        String query = "UPDATE book_instance "
                + "SET user_id = ?, status_id = 2 "
                + "WHERE book_instance_id = ? ";
        try {
            dmlQuery(query, userId, bookInstanceId);
        } catch (DmlQueryError e){
            e.printStackTrace();
        }
    }

    public void returnBook(int bookInstanceId) throws UnavailableDB{
        String query = "UPDATE book_instance "
                + "SET user_id = null, status_id = 0 "
                + "WHERE book_instance_id = ? ";
        try {
            dmlQuery(query, bookInstanceId);
        } catch (DmlQueryError e){
            e.printStackTrace();
        }
    }

    public void reserveBook(int bookInstanceId, int userId) throws UnavailableDB{
        String query = "UPDATE book_instance "
                + "SET user_id = ?, status_id = 1 "
                + "WHERE book_instance_id = ? ";
        try {
            dmlQuery(query, userId, bookInstanceId);
        } catch (DmlQueryError e){
            e.printStackTrace();
        }
    }

    public String getBookInstanceStatus(int bookInstanceId) throws UnavailableDB {
        String status = null;
        String query = "SELECT status.name FROM book_instance "
                + "JOIN status using(status_id) "
                + "WHERE book_instance.book_instance_id = ?";
        try(ResultSet rs = ddlQuery(query, bookInstanceId)){
            if(!rs.next()) return status;
            status = rs.getString(1);
            return  rs.getString(1);
        } catch (SQLException | DdlQueryError e){
            e.printStackTrace();
        }
        return status;
    }

    public boolean checkReservation(int userId, int bookInstanceId) throws UnavailableDB {
        return checkStatus(userId, bookInstanceId, "RESERVED");
    }

    public boolean checkLent(int userId, int bookInstanceId) throws UnavailableDB {
        return checkStatus(userId, bookInstanceId, "LENT");
    }

    public boolean checkStatus(int userId, int bookInstanceId, String status) throws UnavailableDB {
        String query = "SELECT status.name FROM book_instance "
                + "JOIN status using(status_id) "
                + "WHERE book_instance.book_instance_id = ? AND user_id = ?";
        try(ResultSet rs = ddlQuery(query, bookInstanceId, userId)) {
            if (!rs.next()) return false;
            if(rs.getString(1).equals(status)){
                return true;
            }
            return false;
        } catch (SQLException | DdlQueryError e){
            e.printStackTrace();
        }
        return false;
    }

    public void delGenre(int genreId) throws UnavailableDB{
        String query = "DELETE FROM genre WHERE genre_id = ?";
        PreparedStatement stmt = null;
        try{
            dmlQuery(query, genreId);
        } catch (DmlQueryError e){
            e.printStackTrace();
        }
    }

    public void delGenre(String  genreName) throws UnavailableDB{
        String query = "DELETE FROM genre WHERE name = ?";
        PreparedStatement stmt = null;
        try{
            dmlQuery(query, genreName);
        } catch (DmlQueryError e){
            e.printStackTrace();
        }
    }

    public void addGenre(String name) throws UnavailableDB {
        String query = "INSERT INTO genre VALUES(null, ?)";
        try {
            dmlQuery(query, name);
        } catch (DmlQueryError e){
            e.printStackTrace();
        }
    }

    public String getCountry(int countyId) throws  UnavailableDB {
        String query = "SELECT country_id from country WHERE country_id = ?";
        String counry = null;
        try(ResultSet rs = ddlQuery(query, countyId)){
            if(!rs.next()) return counry;
            counry =  rs.getString(1);
        } catch (SQLException | DdlQueryError e){
            e.printStackTrace();
        }
        return counry;
    }

    public int getCountryId(String county) throws  UnavailableDB {
        int countryId = -1;
        String query = "SELECT country_id from country WHERE name = ?";
        try(ResultSet rs = ddlQuery(query, county)){
            if(!rs.next()) return countryId;
            countryId =  rs.getInt(1);
        } catch (SQLException | DdlQueryError e){
            e.printStackTrace();
        }
        return countryId;
    }

    public String getCountryName(int id) throws  UnavailableDB {
        String name = null;
        String query = "SELECT name from country WHERE country_id = ?";
        try(ResultSet rs = ddlQuery(query, id)){
            rs.next();
            name = rs.getString(1);
            return rs.getString(1);
        } catch (SQLException | DdlQueryError e){
            e.printStackTrace();
        }
        return name;
    }

    public void delSeries(int seriesId) throws UnavailableDB{
        String query = "DELETE FROM series WHERE series_id = ?";
        try {
            dmlQuery(query, seriesId);
        } catch (DmlQueryError e){
            e.printStackTrace();
        }
    }

    public void addSeries(String name) throws UnavailableDB {
        String query = "INSERT INTO SERIES VALUES(null, ?)";
        try {
            dmlQuery(query, name);
        } catch (DmlQueryError e){
            e.printStackTrace();
        }
    }


    public int getLanguageId(String language) throws  UnavailableDB {
        int languageId = -1;
        String query = "SELECT language_id from language WHERE name = ?";
        try(ResultSet rs = ddlQuery(query, language)){
            if(!rs.next()) return languageId;
            languageId = rs.getInt(1);
        } catch (SQLException | DdlQueryError e){
            e.printStackTrace();
        }
        return languageId;
    }

    public String getLanguage(int id) throws  UnavailableDB {
        String name = null;
        String query = "SELECT name from country WHERE language_id = ?";
        try(ResultSet rs = ddlQuery(query, id)){
            rs.next();
            name = rs.getString(1);
            return rs.getString(1);
        } catch (SQLException | DdlQueryError e){
            e.printStackTrace();
        }
        return name;
    }

    public int getSeriesId(String series) throws  UnavailableDB {
        int seriesId = -1;
        String query = "SELECT series_id from series WHERE name = ?";
        try(ResultSet rs = ddlQuery(query, series)){
            if(!rs.next()) return seriesId;
            seriesId = rs.getInt(1);
        } catch (SQLException | DdlQueryError e){
            e.printStackTrace();
        }
        return seriesId;
    }

    public String getSeriesName(int id) throws  UnavailableDB {
        String name = null;
        String query = "SELECT name from country WHERE series_id = ?";
        try(ResultSet rs = ddlQuery(query, id)){
            rs.next();
            name = rs.getString(1);
            return rs.getString(1);
        } catch (SQLException | DdlQueryError e){
            e.printStackTrace();
        }
        return name;
    }

    public void addBook(int id, String title, String summary, int publicationYear, int pages,
                        String coverSrc, String country, String series, String language, ArrayList<Author> authors,
                        ArrayList<Genre> genres) throws UnavailableDB, TransactionError{
        getConnection();
        String insertBook = "INSERT INTO BOOK(BOOK_ID, TITLE,SUMMARY,PUBLICATION_YEAR,PAGES,COVER,COUNTRY_ID,SERIES_ID,LANGUAGE_ID)"
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String getBookId = "SELECT max(book_id) FROM book";
        String insertBookAuthors = "INSERT INTO BOOK_AUTHOR VALUES(?, ?)";
        String insertBookGenres = "INSERT INTO BOOK_GENRE VALUES(?, ?)";
        try{
            begin();

            int countryId = getCountryId(country);
            int languageId = getLanguageId(language);
            if(series.equals("null")){
                dmlQuery(insertBook, id, title, summary, publicationYear, pages,
                        coverSrc, countryId, null, languageId);
            } else {
                int seriesId = getSeriesId(series);
                dmlQuery(insertBook, id, title, summary, publicationYear, pages,
                        coverSrc, countryId, seriesId, languageId);
            }
            for(Author author : authors){
                dmlQuery(insertBookAuthors, id, author.getId());
            }

            for(Genre genre: genres){
                dmlQuery(insertBookGenres, id, genre.getId());
            }
            commit();
        } catch (SQLException | DmlQueryError e){
            rollback();
            throw new TransactionError(e);
        }
    }

    public void addBook(Book book) throws UnavailableDB, TransactionError{
        getConnection();
        String insertBook = "INSERT INTO BOOK(TITLE,SUMMARY,PUBLICATION_YEAR,PAGES,COVER,COUNTRY_ID,SERIES_ID,LANGUAGE_ID)"
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        String getBookId = "SELECT max(book_id) FROM book";
        String insertBookAuthors = "INSERT INTO BOOK_AUTHOR VALUES(?, ?)";
        String insertBookGenres = "INSERT INTO BOOK_GENRE VALUES(?, ?)";
        try{
            begin();

            int countryId = getCountryId(book.getCountry());
            int languageId = getLanguageId(book.getLanguage());
            if(book.getSeries().equals("null")){
                dmlQuery(insertBook, book.getTitle(), book.getSummary(), book.getPublicationYear(), book.getPages(),
                        book.getCoverSrc(), countryId, null, languageId);
            } else {
                int seriesId = getSeriesId(book.getSeries());
                dmlQuery(insertBook, book.getTitle(), book.getSummary(), book.getPublicationYear(), book.getPages(),
                        book.getCoverSrc(), countryId, seriesId, languageId);
            }
            ResultSet rs = ddlQuery(getBookId);
            rs.next();
            int bookId = rs.getInt(1);

            for(Author author : book.getAuthors()){
                dmlQuery(insertBookAuthors, bookId, author.getId());
            }

            for(Genre genre: book.getGenres()){
                dmlQuery(insertBookGenres, bookId, genre.getId());
            }
            commit();
        } catch (SQLException | DdlQueryError | DmlQueryError e){
            rollback();
            throw new TransactionError(e);
        }
    }

    public void delBook(int bookId) throws TransactionError, UnavailableDB {
        String delBook = "DELETE FROM book WHERE book_id = ?";
        String delBookGenres = "DELETE FROM book_genre WHERE book_id = ?";
        String delBookAuthors = "DELETE FROM book_author WHERE book_id = ?";
        try{
            begin();
            dmlQuery(delBook, bookId);
            dmlQuery(delBookGenres, bookId);
            dmlQuery(delBookAuthors, bookId);

            commit();

        } catch (SQLException | DmlQueryError e){
            rollback();
            throw new TransactionError(e);
        }
    }

    public void addAuthor(Author author) throws UnavailableDB {
        String query = "INSERT INTO author VALUES(null, ?, ?, ?, ?, ?, ?)";
        try {
            if(author.getDeathYear() == -1){
                dmlQuery(query, author.getFirstName(), author.getLastName(), author.getBirthYear(), null,
                        author.getBiography(), author.getPhotoSrc());
            }
            else{
                dmlQuery(query, author.getFirstName(), author.getLastName(), author.getBirthYear(),
                        author.getDeathYear(), author.getBiography(), author.getPhotoSrc());
            }

        } catch (DmlQueryError e){
            e.printStackTrace();
        }
    }

    public void delAuthor(int authorId) throws UnavailableDB{
        String query = "DELETE FROM author WHERE author_id = ?";
        try {
            dmlQuery(query, authorId);
        } catch (DmlQueryError e){
            e.printStackTrace();
        }
    }

    public void addLanguage(String language) throws UnavailableDB {
        String query = "INSERT INTO language VALUES(null, ?)";
        try {
            dmlQuery(query, language);
        } catch (DmlQueryError e){
            e.printStackTrace();
        }
    }


    public void delLanguage(int languageId) throws UnavailableDB {
        String query = "DELETE FROM language WHERE language_id = ?";
        try {
            dmlQuery(query, languageId);
        } catch (DmlQueryError e){
            e.printStackTrace();
        }
    }

    public void delLanguage(String language) throws UnavailableDB {
        String query = "DELETE FROM language WHERE name = ?";
        try {
            dmlQuery(query, language);
        } catch (DmlQueryError e){
            e.printStackTrace();
        }
    }

    public void addCountry(String country) throws UnavailableDB {
        String query = "INSERT INTO country VALUES(null, ?)";
        try {
            dmlQuery(query, country);
        } catch (DmlQueryError e){
            e.printStackTrace();
        }
    }

    public void delCountry(int countryId) throws UnavailableDB {
        String query = "DELETE FROM country WHERE country_id = ?";
        try {
            dmlQuery(query, countryId);
        } catch (DmlQueryError e){
            e.printStackTrace();
        }
    }

    public void delCountry(String country) throws UnavailableDB {
        String query = "DELETE FROM country WHERE name = ?";
        try {
            dmlQuery(query, country);
        } catch (DmlQueryError e){
            e.printStackTrace();
        }
    }

    public void addBookInstance(int bookId) throws UnavailableDB {
        String query = "INSERT INTO book_instance VALUES(null, ?, 0)";
        try {
            dmlQuery(query, bookId);
        } catch (DmlQueryError e){
            e.printStackTrace();
        }
    }

    public void addBookInstance(int bookInstanceId, int bookId) throws UnavailableDB {
        String query = "INSERT INTO book_instance VALUES(?, ?, null, 0)";
        try {
            dmlQuery(query, bookInstanceId, bookId);
        } catch (DmlQueryError e){
            e.printStackTrace();
        }
    }

    public void delBookInstance(int bookInstanceId) throws UnavailableDB {
        String query = "DELETE FROM book_instance WHERE book_instance_id = ?";
        try {
            dmlQuery(query, bookInstanceId);
        } catch (DmlQueryError e){
            e.printStackTrace();
        }
    }

    protected void finalize () {
        closeConnetion();
    }

}
