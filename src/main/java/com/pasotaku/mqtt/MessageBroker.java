package com.pasotaku.mqtt;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.LinkedBlockingQueue;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MessageBroker {

    public String state;
    public String prevTopic;
    private String topic;

    private Callback callback;
    private MqttClient client;
    private String user;
    private String room;

    public MessageBroker(String user, boolean reset){
        // Default Values
        this.prevTopic = "";
        this.topic = "";
        // Set connection options
        String server = "tcp://rhitgaming.com:1883";
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(reset);
        this.user = user;
        try {
            options.setWill(this.user, "Lost Connection".getBytes("UTF-8"), 2, true);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // Create client
        try {
            this.client = new MqttClient(server, this.user, new MemoryPersistence());
            this.callback = new Callback();
            this.client.setCallback(this.callback);
            this.client.connect(options);
            this.state = this.client.isConnected() ? "Connected" : "Initial connection failed";
            this.setRoom("general", true);
            this.checkIn();
        } catch (MqttException e){
            e.printStackTrace();
        }
    }

    public void setRoom(String room, boolean joinAll){
        String prefix = this.user;
        if (joinAll) {
            prefix = "+";
        }
        if (!this.prevTopic.equals("")) {
            try {
                this.client.unsubscribe(this.prevTopic);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
        this.callback.clear();
        this.prevTopic = this.topic;
        this.topic = prefix + "/" + room;
        this.room = room;
        try {
            this.client.subscribe("+/" + room, 2);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public String getState(){
        this.state = this.callback.isValid() ? this.state : "Connection Lost";
        return this.state;
    }

    public void send(String message) {
        try {
            this.client.publish(this.user + "/" + this.room, message.getBytes("UTF-8"), 2, false);
        } catch (UnsupportedEncodingException | MqttException e) {
            e.printStackTrace();
        }
    }

    public void send(String message, String topic, boolean persist) {
        try {
            this.client.publish(topic, message.getBytes("UTF-8"), 2, persist);
        } catch (UnsupportedEncodingException | MqttException e) {
            e.printStackTrace();
        }
    }

    public Message recieve(){
        return this.callback.getMessage();
    }

    public void disconnect(){
        try{
            this.checkOut();
            this.client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void checkIn(){
        try {
            this.client.publish(this.user, "Logged In".getBytes("UTF-8"), 2, true);
        } catch (MqttException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void checkOut(){
        try {
            this.client.publish(this.user, "Logged Out".getBytes("UTF-8"), 2, true);
        } catch (MqttException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private class Callback implements MqttCallback{

        private boolean valid = true;
        private LinkedBlockingQueue<Message> messageQueue = new LinkedBlockingQueue<>();

        @Override
        public void connectionLost(Throwable cause){
            this.valid = false;
        }

        @Override
        public void messageArrived(String topic, MqttMessage mqttMessage){
            Message currentMessage = new Message(topic, mqttMessage);
            try {
                this.messageQueue.put(currentMessage);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token){}

        public Message getMessage(){
            Message newMessage = new Message();
            try {
                newMessage = this.messageQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return newMessage;
        }

        public boolean isValid() {
            return this.valid;
        }

        public void clear(){
            this.messageQueue.clear();
        }

    }

}
