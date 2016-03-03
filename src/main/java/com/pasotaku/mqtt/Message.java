package com.pasotaku.mqtt;

import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Message {
    private MqttMessage mqttMessage;
    private String topic;

    public Message() {
        this.mqttMessage = new MqttMessage();
        this.topic = "";
    }

    public Message(String topic, MqttMessage mqttMessage) {
        this.mqttMessage = mqttMessage;
        this.topic = topic;
    }

    public MqttMessage getMqttMessage() {
        return this.mqttMessage;
    }

    public String getTopic() {
        return this.topic;
    }

    public String toString() {
        return this.mqttMessage.toString();
    }
}
