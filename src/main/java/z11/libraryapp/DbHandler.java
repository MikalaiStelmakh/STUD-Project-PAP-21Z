package z11.libraryapp;

import java.sql.DriverManager;
import java.sql.Connection;
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
        String query = "SELECT book.book_id, book.title, book.summary, book.publication_year, book.pages, "
                     + "book.cover, country.name country, series.name series, language.name language "
                     + "FROM book "
                     + "JOIN country ON (book.country_id = country.country_id) "
                     + "JOIN series ON (book.series_id = series.series_id) "
                     + "JOIN language ON (book.language_id = language.language_id)";
        try{
            ResultSet rs = ddlQuery(query);
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
        } catch(DdlQueryError | SQLException e){
            e.printStackTrace();
            System.exit(1);
        }

        return books;
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

    public ArrayList<String> getBookGenres(int bookId) throws UnavailableDB{
        String query = "SELECT genre.genre_id, genre.name "
                     + "FROM genre "
                     + "JOIN book_genre ON (genre.genre_id = book_genre.genre_id) "
                     + "JOIN book ON (book.book_id = book_genre.book_id) "
                     + "WHERE book.book_id = " + bookId;
        ArrayList<String> genres = new ArrayList<String>();
        try{
            ResultSet rs = ddlQuery(query);
            while(rs.next()){
                genres.add(rs.getString(1));
            }
        } catch(DdlQueryError | SQLException e){
            e.printStackTrace();
            System.exit(1);
        }
        return genres;
    }

    public Author getAuthor(int authorId) throws UnavailableDB{
        String query = "SELECT genre.name FROM author WHERE genre.id = " + authorId;
        Author author = null;
        try{
            ResultSet rs = ddlQuery(query);
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
        } catch(DdlQueryError | SQLException e){
            e.printStackTrace();
            System.exit(1);
        }
        return author;
    }

    public ArrayList<Author> getBookAuthors(int bookId) throws UnavailableDB{
        String query = "SELECT author.author_id, author.first_name, author.last_name, author.birth_year, "
                     + "author.death_year "
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
                authors.add(new Author(id, firstName, lastName, birthYear, deathYear));
            }
        } catch(DdlQueryError | SQLException e){
            e.printStackTrace();
            System.exit(1);
        }
        return authors;
    }

    public void finalize () {
        closeConnetion();
    }
}
