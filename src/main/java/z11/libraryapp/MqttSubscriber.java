package z11.libraryapp;

import org.eclipse.paho.mqttv5.client.*;
import org.eclipse.paho.mqttv5.client.persist.MemoryPersistence;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.MqttSubscription;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;

import java.sql.SQLException;
import java.time.LocalTime;

import z11.libraryapp.errors.*;

import java.util.Random;
import java.util.Scanner;

public class MqttSubscriber implements MqttCallback{
    private MqttAsyncClient client;
    private IMqttToken token;
    private DbHandler dbManager;

    public MqttSubscriber() {}

    public void run(){
        try{
            dbManager = new DbHandler();

            String clientId = String.valueOf(System.currentTimeMillis());
            MemoryPersistence persistence = new MemoryPersistence();
            client=new MqttAsyncClient("tcp://test.mosquitto.org:1883", clientId, persistence);
            client.setCallback(this);
            MqttConnectionOptions connOpts = new MqttConnectionOptions();
            connOpts.setCleanStart(false);
            connOpts.setSessionExpiryInterval((long)10000);
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
            if(dbManager.checkReservation(userId, bookInstanceId)){
                dbManager.lendBook(userId, bookInstanceId);
                publishMQTT(userId, bookInstanceId, "lent");
            } else {
                publishMQTT(userId, bookInstanceId, "noReservation");
            }
        }
        if(data[2].equals("return")){
            if(dbManager.checkLent(userId, bookInstanceId)){
                dbManager.returnBook(bookInstanceId);
                publishMQTT(userId, bookInstanceId, "returned");
            } else {
                publishMQTT(userId, bookInstanceId, "notReturned");
            }


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
        }
    }

    @Override
    protected void finalize(){
//        stop();
    }
}
