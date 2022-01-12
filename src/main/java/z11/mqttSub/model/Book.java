package z11.libraryapp.model;

import java.util.ArrayList;

public class Book {
    private int id;
    private String title;
    private String summary;
    private int publicationYear;
    private int pages;
    private String coverSrc;
    private String country;
    private String series;
    private String language;
    private ArrayList<Author> authors;
    private ArrayList<String> genres;

    public Book(int id, String title, String summary, int publicationYear, int pages, String coverSrc,
                String country, String series, String language, ArrayList<Author> authors, ArrayList<String> genres){
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.publicationYear = publicationYear;
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

    public ArrayList<Author> getAuthor() {
        return authors;
    }

    public void setAuthor(Author author) {
        this.authors.add(author);
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(String genre) {
        this.genres.add(genre);
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
