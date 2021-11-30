package z11.libraryapp.errors;

public class DmlQueryError extends Exception{
    public DmlQueryError(Exception error) {
        super(error);
    }
}
