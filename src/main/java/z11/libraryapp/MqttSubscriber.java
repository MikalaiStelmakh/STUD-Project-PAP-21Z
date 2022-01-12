package z11.libraryapp;

import org.eclipse.paho.mqttv5.client.*;
import org.eclipse.paho.mqttv5.client.persist.MemoryPersistence;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.MqttSubscription;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;

import z11.libraryapp.errors.*;

import java.util.Scanner;

public class MqttSubscriber implements MqttCallback{
    private MqttAsyncClient client;
    private IMqttToken token;
    private DbHandler dbManager;

    public MqttSubscriber() {}

    public void run(){
        try{
            dbManager = new DbHandler();

            MemoryPersistence persistence = new MemoryPersistence();
            client=new MqttAsyncClient("tcp://test.mosquitto.org:1883", "libraryApp", persistence);
            client.setCallback(this);
            MqttConnectionOptions connOpts = new MqttConnectionOptions();
            connOpts.setCleanStart(false);
            connOpts.setSessionExpiryInterval((long)3600);
            token = client.connect(connOpts);
            token.waitForCompletion();
            client.subscribe(new MqttSubscription("z11_library", 1));
        } catch (UnavailableDB ignored){}
        catch (MqttException e){
            e.printStackTrace();
        }


    }

    public void stop(){
        System.out.println("disc");
        try{
            client.disconnect();
        } catch (MqttException e){
            e.printStackTrace();
        }

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
        int userId = 0;
        int bookInstanceId = 0;
        String msg = message.toString();
        System.out.println(msg);
        String[] data =  msg.split(":");
        try {
            bookInstanceId = Integer.parseInt(data[0]);
            userId = Integer.parseInt(data[1]);

        } catch (NumberFormatException e){
            e.printStackTrace();
            return;
        }
        if(data[2].equals("lend")){
            dbManager.lendBook(userId, bookInstanceId);
        }
        if(data[2].equals("return")){
            dbManager.returnBook(bookInstanceId);
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
        stop();
    }
}
