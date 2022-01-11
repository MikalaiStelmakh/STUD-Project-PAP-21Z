package z11.libraryapp.model;

import java.sql.Date;
import java.util.ArrayList;

import javafx.scene.chart.XYChart.Series;

public class Book {
    private int id;
    private String title;
    private String summary;
    private int publicationYear;
    private Date dateAdded;
    private int pages;
    private String coverSrc;
    private String country;
    private String series;
    private String language;
    private ArrayList<Author> authors;
    private ArrayList<Genre> genres;

    public Book(int id, String title, String summary, int publicationYear, int pages, String coverSrc,
                String country, String series, String language) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.publicationYear = publicationYear;

        this.pages = pages;
        this.coverSrc = coverSrc;
        this.country = country;
        this.series = series;
        this.language = language;
    }

    public Book(int id, String title, String summary, int publicationYear, Date dateAdded, int pages, String coverSrc,
                String country, String series, String language, ArrayList<Author> authors, ArrayList<Genre> genres){
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.publicationYear = publicationYear;
        this.dateAdded = dateAdded;
        this.pages = pages;
        this.coverSrc = coverSrc;
        this.country = country;
        this.series = series;
        this.language = language;
        this.authors = authors;
        this.genres = genres;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getPublicationYear(){
        return this.publicationYear;
    }

    public void setPublicationYear(int publicationYear){
        this.publicationYear = publicationYear;
    }

    public Date getDateAdded(){
        return this.dateAdded;
    }

    public void setDateAdded(Date dateAdded){
        this.dateAdded = dateAdded;
    }

    public int getPages(){
        return this.pages;
    }

    public void setPages(int pages){
        this.pages = pages;
    }

    public String getCoverSrc() {
        return coverSrc;
    }

    public void setCoverSrc(String coverSrc) {
        this.coverSrc = coverSrc;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public ArrayList<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Author author) {
        this.authors.add(author);
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }

    public String getAuthorsNames(){
        String res = "";
        String delimiter = ", ";

        for (int i = 0; i<authors.size(); i++){
            res += authors.get(i);
            if (i+1 < authors.size()){
                res += delimiter;
            }
        }
        return res;
    }
}
