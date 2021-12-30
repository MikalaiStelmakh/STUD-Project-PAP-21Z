package z11.mqttSub;

import org.eclipse.paho.mqttv5.client.MqttAsyncClient;
import org.eclipse.paho.mqttv5.client.MqttCallback;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.eclipse.paho.mqttv5.client.IMqttToken;
import org.eclipse.paho.mqttv5.client.persist.MemoryPersistence;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttSubscription;

import java.util.Scanner;

public class mqttSub {


    public static void main(String[] args) throws MqttException {
        MqttSubscriber sub = new MqttSubscriber();
        sub.run();

        while (true) {
            Scanner input = new Scanner(System.in);
            String i = input.nextLine();
            if (i.equalsIgnoreCase("q")) {
                System.exit(0);
                try {
                    sub.stop();
                } catch (MqttException ignored) {
                }
            }
        }
    }



}