package z11.libraryapp.errors;

public class DdlQueryError extends Exception{
    public DdlQueryError(Exception error) {
        super(error);
    }
}
