package z11.libraryapp.model;

public class EmailAdmin {
    private int id;
    private String email;

    public  EmailAdmin (int id, String email) {
        this.id = id;
        this.email = email;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }
}
