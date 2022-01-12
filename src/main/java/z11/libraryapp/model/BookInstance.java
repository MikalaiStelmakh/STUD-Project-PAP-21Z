package z11.libraryapp.model;

public class BookInstance {
    private int id;
    private int book_id;
    private int user_id;
    private String status;

    public BookInstance(int id, int  book_id, int user_id, String status){
        this.id = id;
        this.book_id = book_id;
        this.user_id = user_id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
