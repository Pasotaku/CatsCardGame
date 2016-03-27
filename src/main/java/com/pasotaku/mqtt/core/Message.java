package com.pasotaku.mqtt.core;

import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Message {
    private MqttMessage mqttMessage;
    private String topic;
    private String user;

    Message() {
        this.mqttMessage = new MqttMessage();
        this.topic = "";
        this.user = "";
    }

    Message(String topic, MqttMessage mqttMessage) {
        this.mqttMessage = mqttMessage;
        this.topic = topic;
        String[] topicTree = topic.split("/");
        this.user = topicTree[0];
    }

    public String getTopic() {
        return this.topic;
    }

    public String getUser(){
        return this.user;
    }

    public String toString() {
        return this.mqttMessage.toString();
    }
}
