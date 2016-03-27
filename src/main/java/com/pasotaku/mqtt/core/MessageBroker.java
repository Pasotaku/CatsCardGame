package com.pasotaku.mqtt.core;

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

    private String state;
    private String prevTopic;
    private String topic;

    private Callback callback;
    private MqttClient client;
    private String user;
    private String room;
    private Boolean allRoomScope;

    private final Boolean isClient;

    public MessageBroker(String server, String user, boolean reset, boolean isClient){
        this.isClient = isClient;
        // Default Values
        this.prevTopic = "";
        this.topic = "";
        // Set connection options
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(reset);
        this.user = user;
        try {
            options.setWill(this.user, "Lost Connection".getBytes("UTF-8"), 2, this.isClient);
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
            this.allRoomScope = true;
            prefix = "+";
        }
        else {
            this.allRoomScope = false;
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
        if (!room.equals("")){
            this.topic = prefix + "/" + room;
        } else {
            this.topic = prefix;
        }
        this.room = room;
        try {
            this.client.subscribe(this.topic, 2);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public String getRoom(){
        return this.room;
    }

    public Boolean getAllRoomScope(){
        return this.allRoomScope;
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
            this.client.publish(this.user, "Logged In".getBytes("UTF-8"), 2, this.isClient);
        } catch (MqttException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void checkOut(){
        try {
            this.client.publish(this.user, "Logged Out".getBytes("UTF-8"), 2, this.isClient);
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

        Message getMessage(){
            Message newMessage = new Message();
            try {
                newMessage = this.messageQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return newMessage;
        }

        boolean isValid() {
            return this.valid;
        }

        void clear(){
            this.messageQueue.clear();
        }

    }

}
