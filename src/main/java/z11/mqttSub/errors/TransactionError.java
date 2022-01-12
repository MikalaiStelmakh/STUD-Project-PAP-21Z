package z11.libraryapp.errors;

public class TransactionError extends Exception{
  public TransactionError(Exception error) {
    super(error);
  }
}
