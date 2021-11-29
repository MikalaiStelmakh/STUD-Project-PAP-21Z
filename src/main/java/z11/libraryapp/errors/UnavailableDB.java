package z11.libraryapp.errors;

public class UnavailableDB extends Exception{
    public UnavailableDB(Exception error) {
        super(error);
    }
}
