package z11.mqttSub;

import java.sql.*;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

import z11.mqttSub.model.*;
import z11.mqttSub.errors.*;

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
        try {
            ResultSet rs = con.createStatement().executeQuery(query);
            return rs;
        } catch (SQLException e){
            throw new DdlQueryError(e);
        }
    }

    public void dmlQuery(String query) throws DmlQueryError, UnavailableDB{
        try {
            con.createStatement().executeUpdate(query);
        } catch (SQLException e){
            throw new DmlQueryError(e);
        }
    }

    public void dmlTransaction(String[] queries) throws  SQLException, TransactionError, UnavailableDB{
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

    public void lendBook(int userId, int bookInstanceId) throws UnavailableDB, DmlQueryError{
        String query = "UPDATE book_instance "
                     + "SET user_id = ?, lend_date = ?, return_date = ?, is_available = 0 "
                     + "WHERE book_instance_id = ? ";
        PreparedStatement stmt = null;
        try {
            LocalDate currDate = LocalDate.now();
            LocalDate returnDate = currDate.plusMonths(1);
            stmt = con.prepareStatement(query);
            stmt.setInt(1, userId);
            stmt.setDate(2, java.sql.Date.valueOf(currDate));
            stmt.setDate(3, java.sql.Date.valueOf(returnDate));
            stmt.setInt(4, bookInstanceId);

        } catch (SQLException e){
            e.printStackTrace();
            throw new UnavailableDB(e);
        }
        try {
            stmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
            throw new DmlQueryError(e);
        }
    }

    public void returnBook(int bookInstanceId) throws UnavailableDB, DmlQueryError{
        String query = "UPDATE book_instance "
                     + "SET user_id = ?, lend_date = ?, return_date = ?, is_available = 1 "
                     + "WHERE book_instance_id = ? ";
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(query);
            stmt.setNull(1, Types.NUMERIC);
            stmt.setNull(2, Types.DATE);
            stmt.setNull(3, Types.DATE);
            stmt.setInt(4, bookInstanceId);

        } catch (SQLException e){
            e.printStackTrace();
            throw new UnavailableDB(e);
        }
        try {
            stmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
            throw new DmlQueryError(e);
        }
    }

    public ArrayList<Book> getBooks() throws UnavailableDB, DdlQueryError{
        ArrayList<Book> books = new ArrayList<Book>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT book.book_id, book.title, book.summary, book.publication_year, book.pages, "
                + "book.cover, country.name country, series.name series, language.name language "
                + "FROM book "
                + "JOIN country ON (book.country_id = country.country_id) "
                + "JOIN series ON (book.series_id = series.series_id) "
                + "JOIN language ON (book.language_id = language.language_id)";
        getConnection();
        try{
            stmt = con.prepareStatement(query);
        } catch (SQLException e){
            e.printStackTrace();
            throw new UnavailableDB(e);
        }
        try {
            rs = stmt.executeQuery();
        } catch (SQLException e){
            e.printStackTrace();
            throw new DdlQueryError(e);
        }
        try{
            while(rs.next()){
                try{
                    int id = rs.getInt(1);
                    String title = rs.getString(2);
                    String summary = rs.getString(3);
                    int publicationYear = rs.getInt(4);
                    int pages = rs.getInt(5);
                    String coverSrc = rs.getString(6);
                    String country = rs.getString(7);
                    String series = rs.getString(8);
                    String language = rs.getString(9);

                    ArrayList<Author> authors = getBookAuthors(id);
                    ArrayList<String> genres = getBookGenres(id);

                    books.add(new Book(id, title, summary, publicationYear, pages, coverSrc, country,
                            series, language, authors, genres));
                }
                catch(SQLException e){
                    continue;
                }
            }
        } catch(SQLException e){
            e.printStackTrace();
            throw new UnavailableDB(e);
        }
        return books;
    }

    public Book getBook(int bookId) throws UnavailableDB, DdlQueryError{
        Book book = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT book.book_id, book.title, book.summary, book.publication_year, book.pages, "
                + "book.cover, country.name country, series.name series, language.name language "
                + "FROM book "
                + "JOIN country ON (book.country_id = country.country_id) "
                + "JOIN series ON (book.series_id = series.series_id) "
                + "JOIN language ON (book.language_id = language.language_id)"
                + "WHERE book_book.id = ?";
        try{
            stmt = con.prepareStatement(query);
            stmt.setInt(1, bookId);
        } catch (SQLException e){
            e.printStackTrace();
            throw new UnavailableDB(e);
        }
        try {
            rs = stmt.executeQuery();
        } catch (SQLException e){
            e.printStackTrace();
            throw new DdlQueryError(e);
        }
        try{
            rs = stmt.executeQuery();
            if(rs.next()){
                int id = rs.getInt(1);
                String title = rs.getString(2);
                String summary = rs.getString(3);
                int publicationYear = rs.getInt(4);
                int pages = rs.getInt(5);
                String coverSrc = rs.getString(6);
                String country = rs.getString(7);
                String series = rs.getString(8);
                String language = rs.getString(9);

                ArrayList<Author> authors = getBookAuthors(id);
                ArrayList<String> genres = getBookGenres(id);

                book = new Book(id, title, summary, publicationYear, pages, coverSrc, country,
                        series, language, authors, genres);
            } else{
                return null;
            }
        } catch(SQLException e){
            e.printStackTrace();
            throw new UnavailableDB(e);
        }
        return book;
    }

    public Author getAuthor(int authorId) throws UnavailableDB, DdlQueryError{
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Author author = null;
        String query = "SELECT genre.name FROM author WHERE genre.id = ?" ;

        try{
            stmt = con.prepareStatement(query);
            stmt.setInt(1, authorId);
        } catch (SQLException e){
            e.printStackTrace();
            throw new UnavailableDB(e);
        }
        try {
            rs = stmt.executeQuery();
        } catch (SQLException e){
            e.printStackTrace();
            throw new DdlQueryError(e);
        }
        try{
            rs = stmt.executeQuery();
            if(rs.next()){
                int id = rs.getInt(1);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
                int birthYear = rs.getInt(4);
                int deathYear = rs.getInt(5);
                author = new Author(id, firstName, lastName, birthYear, deathYear);
            } else{
                return null;
            }
        } catch(SQLException e){
            e.printStackTrace();
            throw new UnavailableDB(e);
        }
        return author;
    }

    private String getGenre(int genreId) throws UnavailableDB, DdlQueryError{
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String genre = null;
        String query = "SELECT genre.name FROM genre WHERE genre.id = ?";

        try{
            stmt = con.prepareStatement(query);
            stmt.setInt(1, genreId);
        } catch (SQLException e){
            e.printStackTrace();
            throw new UnavailableDB(e);
        }
        try {
            rs = stmt.executeQuery();
        } catch (SQLException e){
            e.printStackTrace();
            throw new DdlQueryError(e);
        }
        try{
            genre = rs.getString(1);
        } catch(SQLException e){
            e.printStackTrace();
            throw new UnavailableDB(e);
        }
        return genre;
    }

    public ArrayList<String> getBookGenres(int bookId) throws UnavailableDB, DdlQueryError{
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<String> genres = new ArrayList<String>();
        String query = "SELECT genre.genre_id, genre.name "
                + "FROM genre "
                + "JOIN book_genre ON (genre.genre_id = book_genre.genre_id) "
                + "JOIN book ON (book.book_id = book_genre.book_id) "
                + "WHERE book.book_id = ?";
        try{
            stmt = con.prepareStatement(query);
            stmt.setInt(1, bookId);
        } catch (SQLException e){
            e.printStackTrace();
            throw new UnavailableDB(e);
        }
        try {
            rs = stmt.executeQuery();
        } catch (SQLException e){
            e.printStackTrace();
            throw new DdlQueryError(e);
        }
        try{
            while(rs.next()){
                genres.add(rs.getString(1));
            }
        } catch(SQLException e){
            e.printStackTrace();
            throw new UnavailableDB(e);
        }
        return genres;
    }

    public ArrayList<Author> getBookAuthors(int bookId) throws UnavailableDB, DdlQueryError{
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Author> authors = new ArrayList<>();
        String query = "SELECT author.author_id, author.first_name, author.last_name, author.birth_year, "
                + "author.death_year "
                + "FROM  author "
                + "JOIN book_author ON (author.author_id = book_author.author_id) "
                + "JOIN book ON (book.book_id = book_author.book_id) "
                + "WHERE book.book_id = ?";

        try{
            stmt = con.prepareStatement(query);
            stmt.setInt(1, bookId);
        } catch (SQLException e){
            e.printStackTrace();
            throw new UnavailableDB(e);
        }
        try {
            rs = stmt.executeQuery();
        } catch (SQLException e){
            e.printStackTrace();
            throw new DdlQueryError(e);
        }
        try{
            while(rs.next()){
                int id = rs.getInt(1);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
                int birthYear = rs.getInt(4);
                int deathYear = rs.getInt(5);
                authors.add(new Author(id, firstName, lastName, birthYear, deathYear));
            }
        } catch(SQLException e){
            e.printStackTrace();
            throw new UnavailableDB(e);
        }
        return authors;
    }

    protected void finalize () {
        closeConnetion();
    }
}
