package z11.mqttSub.errors;

public class UnavailableDB extends Exception{
    public UnavailableDB(Exception error) {
        super(error);
    }
}
