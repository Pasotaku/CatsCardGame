package com.pasotaku.cats.multiplayer;

import java.util.UUID;

import com.pasotaku.mqtt.Message;
import com.pasotaku.mqtt.MessageBroker;

public class Server {

    // Entry-point
    public static void main (String[] args) {
        MessageBroker connection = new MessageBroker("server", true);
        Message currentMessage;
        connection.setRoom("lobby", true);
        while (connection.getState().equals("Connected")){
            currentMessage = connection.recieve();
            if (currentMessage.getMqttMessage().toString().equals("create")){
                connection.send(UUID.randomUUID().toString(), currentMessage.getTopic(), false);
            }
        }

    }

    public void createGame() {
        // Create threaded instance of game logic
    }

}
