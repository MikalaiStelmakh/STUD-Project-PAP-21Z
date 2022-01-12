package z11.libraryapp.model;

import java.sql.Date;

public class HistoryNode {
    private int id;
    private int bookInstanceId;
    private int userId;
    private Date dateBorrowed;
    private Date dateReturned;

    public HistoryNode(int id, int bookInstanceId, int userId, Date dateBorrowed, Date dateReturned){
        this.id = id;
        this.bookInstanceId = bookInstanceId;
        this.userId = userId;
        this.dateBorrowed = dateBorrowed;
        this.dateReturned = dateReturned;
    }

    public HistoryNode(int id, int bookInstanceId, int userId, Date dateBorrowed){
        this.id = id;
        this.bookInstanceId = bookInstanceId;
        this.userId = userId;
        this.dateBorrowed = dateBorrowed;
        this.dateReturned = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookInstanceId() {
        return bookInstanceId;
    }

    public void setBookInstanceId(int bookInstanceId) {
        this.bookInstanceId = bookInstanceId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getDateBorrowed() {
        return dateBorrowed;
    }

    public void setDateBorrowed(Date dateBorrowed) {
        this.dateBorrowed = dateBorrowed;
    }

    public Date getDateReturned() {
        return dateReturned;
    }

    public void setDateReturned(Date dateReturned) {
        this.dateReturned = dateReturned;
    }
}
