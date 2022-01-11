package z11.libraryapp;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

import z11.libraryapp.model.*;
import z11.libraryapp.errors.*;

public class DbHandler {

    private String login = "dsavytsk";
    private String password = "dsavytsk";

    private Connection con = null;

    public DbHandler() throws  UnavailableDB{
        getConnection();
    }

    public void getConnection() throws  UnavailableDB{
        try {
            if(con == null || con.isClosed()){
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

    public ResultSet ddlQuery(String query) throws  DdlQueryError, UnavailableDB{
        getConnection();
        try {
            ResultSet rs = con.createStatement().executeQuery(query);
            return rs;
        } catch (SQLException e){
            throw new DdlQueryError(e);
        }
    }

    public void dmlQuery(String query) throws DmlQueryError, UnavailableDB{
        getConnection();
        try {
            con.createStatement().executeUpdate(query);
        } catch (SQLException e){
            throw new DmlQueryError(e);
        }
    }

    public void dmlTramsaction(String[] queries) throws  SQLException, TransactionError, UnavailableDB{
        getConnection();
        con.setAutoCommit(false);
        try{
            for(String query : queries){
                con.createStatement().executeUpdate(query);
            }
            con.commit();
        } catch (Exception e){
            con.rollback();
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
        try{
            ResultSet rs = ddlQuery(query);
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

    public ArrayList<Genre> getGenres() throws UnavailableDB {
        ArrayList<Genre> genres = new ArrayList<Genre>();
        String query = "select * from genre order by name asc";
        try{
            ResultSet rs = ddlQuery(query);
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
            System.exit(1);
        }

        return genres;
    }

    public String getGenre(int genreId) throws UnavailableDB{
        String query = "SELECT genre.name FROM genre WHERE genre.id = " + genreId;
        String genre = null;
        try{
            ResultSet rs = ddlQuery(query);
            genre = rs.getString(1);
        } catch(DdlQueryError | SQLException e){
            e.printStackTrace();
            System.exit(1);
        }
        return genre;
    }

    public ArrayList<Genre> getBookGenres(int bookId) throws UnavailableDB{
        String query = "SELECT genre.genre_id, genre.name "
                     + "FROM genre "
                     + "JOIN book_genre ON (genre.genre_id = book_genre.genre_id) "
                     + "JOIN book ON (book.book_id = book_genre.book_id) "
                     + "WHERE book.book_id = " + bookId;
        ArrayList<Genre> genres = new ArrayList<Genre>();
        try{
            ResultSet rs = ddlQuery(query);
            while (rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                genres.add(new Genre(id, name));
            }
        } catch(DdlQueryError | SQLException e){
            e.printStackTrace();
            System.exit(1);
        }
        return genres;
    }

    public ArrayList<Book> getBooksByGenre(int genre_id) throws UnavailableDB{
        ArrayList<Book> books = new ArrayList<Book>();
        String query = "select book_id, title, summary, publication_year, "
                     + "date_added, pages, cover, c.name, s.name, l.name "
                     + "from book join country c using(country_id) "
                     + "join series s using(series_id) join language l using(language_id) "
                     + "join book_genre using (book_id) join genre using(genre_id) where genre_id = " + genre_id;
        try{
            ResultSet rs = ddlQuery(query);
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

    public int getNumOfAvailableInstances(int bookId) throws UnavailableDB {
        int n=0;
        String query = "select count(*) from book_instance where (book_id = %d and is_available = 1)";
        query = String.format(query, bookId);
        try {
            ResultSet rs = ddlQuery(query);
            rs.next();
            n = rs.getInt(1);
        } catch (DdlQueryError e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n;
    }

    public ArrayList<Book> getBooksInSameSeries(int bookId) throws UnavailableDB {
        ArrayList<Book> books = new ArrayList<Book>();
        String query = "SELECT b2.book_id, b2.title, b2.summary, b2.publication_year, b2.date_added, "
                     + "b2.pages, b2.cover, c.name country, s.name series, l.name language "
                     + "from book b1 join book b2 on(b1.series_id = b2.series_id) "
                     + "join country c on (b2.country_id = c.country_id) "
                     + "join series s on (b2.series_id = s.series_id) "
                     + "join language l on (b2.language_id = l.language_id) "
                     + "where (b1.book_id = %d)";
        query = String.format(query, bookId);
        try{
            ResultSet rs = ddlQuery(query);
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

    public ArrayList<Author> getAuthors() throws UnavailableDB{
        ArrayList<Author> authors = new ArrayList<Author>();
        String query = "select * from author order by last_name asc";
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
        String query = "select * from author where author_id = " + authorId;
        Author author = null;
        try{
            ResultSet rs = ddlQuery(query);
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
            System.exit(1);
        }
        return author;
    }

    public ArrayList<Author> getBookAuthors(int bookId) throws UnavailableDB{
        String query = "SELECT author.*"
                     + "FROM  author "
                     + "JOIN book_author ON (author.author_id = book_author.author_id) "
                     + "JOIN book ON (book.book_id = book_author.book_id) "
                     + "WHERE book.book_id = " + bookId;
        ArrayList<Author> authors = new ArrayList<Author>();
        try{
            ResultSet rs = ddlQuery(query);
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
            System.exit(1);
        }
        return authors;
    }

    public ArrayList<Book> getAuthorBooks(int author_id) throws UnavailableDB{
        String query = "select book_id, title, summary, publication_year, "
                     + "date_added, pages, cover, c.name, s.name, l.name "
                     + "from book join country c using(country_id) "
                     + "join series s using(series_id) join language l using(language_id) "
                     + "join book_author using (book_id) "
                     + "join author using (author_id) where author_id = " + author_id;
        ArrayList<Book> books = new ArrayList<Book>();
        try{
            ResultSet rs = ddlQuery(query);
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
            System.exit(1);
        }
        return books;
    }

    public ArrayList<Genre> getAuthorGenres(int author_id) throws UnavailableDB{
        ArrayList<Genre> genres = new ArrayList<Genre>();
        String query = "select distinct genre_id, g.name "
                     + "from genre g join book_genre using (genre_id) "
                     + "join book_author using (book_id) "
                     + "join author using(author_id) "
                     + "where author_id = " + author_id;
        try{
            ResultSet rs = ddlQuery(query);
            while(rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                genres.add(new Genre(id, name));
            }
        }
        catch (DdlQueryError | SQLException e){
            e.printStackTrace();
            System.exit(1);
        }
        return genres;
    }

    public boolean validateUser(String login, String password) throws UnavailableDB{
        login = login.toLowerCase();
        String query = "select count(*) from users where (login='%s' and password='%s')";
        query = String.format(query, login, password);
        ResultSet rs;
        try {
            rs = ddlQuery(query);
            rs.next();
            boolean result;
            result = rs.getInt(1) == 1;
            return result;
        } catch (DdlQueryError e) {
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User getUserByLogin(String login) throws SQLException, DdlQueryError, UnavailableDB{
        User user;
        login = login.toLowerCase();
        String query = "select user_id, u.name, surname, login, password, p.name "
                     + "from users u join permissions p using(permission_id) where u.login='%s'";
        query = String.format(query, login);
        ResultSet rs = ddlQuery(query);
        rs.next();
        user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), login, rs.getString(5), rs.getString(6));
        return user;
    }

    public User createUser(String name, String surname, String login, String password) throws DmlQueryError, UnavailableDB, SQLException, DdlQueryError{
        login = login.toLowerCase();
        String query = "insert into users values(null, '%s', '%s', '%s', '%s', 2)";
        query = String.format(query, name, surname, login, password);
        dmlQuery(query);
        return getUserByLogin(login);
    }

    public boolean isUniqueLogin(String login) throws SQLException, DdlQueryError, UnavailableDB{
        login = login.toLowerCase();
        String query = "select count(*) from users where login = '" + login + "'";
        ResultSet rs = ddlQuery(query);
        rs.next();
        return rs.getInt(1) == 0;
    }



    public void finalize () {
        closeConnetion();
    }
}
