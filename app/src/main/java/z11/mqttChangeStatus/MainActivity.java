package z11.mqttChangeStatus;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.eclipse.paho.mqttv5.client.MqttAsyncClient;
import org.eclipse.paho.mqttv5.client.IMqttToken;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.eclipse.paho.mqttv5.client.persist.MemoryPersistence;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;


public class MainActivity extends AppCompatActivity {
    private MqttAsyncClient client;
    private IMqttToken token;
    private TextView errorLabel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        errorLabel = findViewById(R.id.errorLabel);
        try{
            connectMqttBroker();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

public void connectMqttBroker() throws MqttException{
        MemoryPersistence persistence = new MemoryPersistence();
        String clienId = String.valueOf(System.currentTimeMillis());
        client = new MqttAsyncClient("tcp://test.mosquitto.org:1883", clienId, persistence);
        MqttConnectionOptions connOpts = new MqttConnectionOptions();
        connOpts.setCleanStart(false);
        connOpts.setSessionExpiryInterval((long)10000);
        token = client.connect(connOpts);
        token.waitForCompletion();


    }

    public void lent(View view) {
        EditText bookInstanceET = findViewById(R.id.bookInstanceId);
        EditText userIdET = findViewById(R.id.userId);
        errorLabel.setText("");

        String bookInstanceTxt = bookInstanceET.getText().toString().trim();
        String userTxt = userIdET.getText().toString().trim();
        if(bookInstanceTxt.length() == 0){
            errorLabel.setText("ENTER bookInstanceId");
            return;
        }
        if(userTxt.length() == 0){
            errorLabel.setText("ENTER userId");
            return;
        }
        int bookInstanceId = Integer.parseInt(bookInstanceTxt);
        int userId = Integer.parseInt(userTxt);
        publishMQTT(bookInstanceId, userId, "lend");
    }

    public void returnBack(View view) {
        EditText bookInstanceET = findViewById(R.id.bookInstanceId);
        errorLabel.setText("");

        String bookInstanceTxt = bookInstanceET.getText().toString().trim();
        if(bookInstanceTxt.length() == 0){
            errorLabel.setText("ENTER bookInstanceId");
            return;
        }
        int bookInstanceId = Integer.parseInt(bookInstanceTxt);
        publishMQTT(bookInstanceId, -1, "return");
    }

    public void publishMQTT(int bookInstanceId, int userId, String msg) {
        try {
            if(!client.isConnected()) connectMqttBroker();
            MqttMessage message = new MqttMessage();
            String content = String.format("%d:%d:%s", bookInstanceId, userId, msg);
            message.setPayload(content.getBytes());
            message.setQos(1);
            token = client.publish("z11_library", message);
            token.waitForCompletion();
        } catch (Exception e){
            e.printStackTrace();
            errorLabel.setText("ERROR");
        }
    }
}
