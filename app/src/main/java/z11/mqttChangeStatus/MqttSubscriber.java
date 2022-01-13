package z11.mqttChangeStatus;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.eclipse.paho.mqttv5.client.*;
import org.eclipse.paho.mqttv5.client.persist.MemoryPersistence;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.MqttSubscription;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;
import java.time.LocalTime;

import java.util.Random;
import java.util.Scanner;

public class MqttSubscriber implements MqttCallback{
    private MqttAsyncClient client;
    private IMqttToken token;
    private TextView errorLabel;
    private TextView successLabel;

    public MqttSubscriber(TextView errorLabel, TextView successLabel) {
        this.errorLabel = errorLabel;
        this.successLabel = successLabel;
    }

    @Override
    public void disconnected(MqttDisconnectResponse disconnectResponse) {
        System.out.println("Disconnected");
    }

    @Override
    public void mqttErrorOccurred(MqttException exception) {
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        String msg = message.toString();
        String[] data =  msg.split(":");
        if(data[2].equals("lent")){
            successLabel.setText("LENT");
        } else if(data[2].equals("returned")){
            successLabel.setText("RETURNED");
        } else if(data[2].equals("noReservation")){
            errorLabel.setText("NO RESERVATION");
        } else if(data[2].equals("notReturned")){
            errorLabel.setText("FAILED TO RETURN");
        }
    }

    @Override
    public void deliveryComplete(IMqttToken token) {
    }

    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        System.out.println("Connected");
    }

    @Override
    public void authPacketArrived(int reasonCode, MqttProperties properties) {
    }


    @Override
    protected void finalize(){
    }
}
