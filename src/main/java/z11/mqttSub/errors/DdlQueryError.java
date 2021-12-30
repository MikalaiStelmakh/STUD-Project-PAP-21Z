package z11.mqttSub.errors;

public class DdlQueryError extends Exception{
    public DdlQueryError(Exception error) {
        super(error);
    }
}
