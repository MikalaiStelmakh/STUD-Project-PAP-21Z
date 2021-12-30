package z11.mqttSub.errors;

public class DmlQueryError extends Exception{
    public DmlQueryError(Exception error) {
        super(error);
    }
}
