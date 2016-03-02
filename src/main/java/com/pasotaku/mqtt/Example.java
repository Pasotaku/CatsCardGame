package com.pasotaku.mqtt;

public class Example {

    public static void main (String[] args){
        System.out.println("Start");
        // Creates a new connection to the server
        MessageBroker multiplayer = new MessageBroker("newuser", true);

        // Sends a message to the current room. By default, clients join the "general" room.
        multiplayer.send("Hello");

        // Determines if the connection is still alive.
        System.out.println(multiplayer.getState());

        // Gets the first message in the message queue.
        System.out.println(multiplayer.recieve());

        // Games are instanced by specific "rooms" (topics).
        // Message queue is cleared when switching rooms.
        multiplayer.setRoom("game");

        // Recieve blocks until the next message if the queue is empty.
        // In this example case, use an external client or 'mosquitto_pub -t "user/game" -m "test"' on the server.
        System.out.println(multiplayer.recieve());

        // Always disconnect cleanly.
        // Subscribing to the root "user/" or "#" topic will show if the client disconnected cleanly.
        multiplayer.disconnect();
        System.out.println("Finish");
    }

}
