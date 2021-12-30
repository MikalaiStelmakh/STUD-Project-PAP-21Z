package z11.mqttSub.errors;

public class TransactionError extends Exception{
  public TransactionError(Exception error) {
    super(error);
  }
}
