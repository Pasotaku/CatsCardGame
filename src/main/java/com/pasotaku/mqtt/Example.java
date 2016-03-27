package com.pasotaku.mqtt;

import com.pasotaku.mqtt.core.Message;
import com.pasotaku.mqtt.core.MessageBroker;

public class Example {

    public static void main (String[] args){
        System.out.println("Start");
        // Creates a new connection to the server
        // Requires the mqtt server and a username.
        // Setting the third parameter to true will forget the previous room
        MessageBroker multiplayer = new MessageBroker("tcp://rhitgaming.com:1883", "newuser", true, true);

        // Sends a message to the current room. By default, clients join the "general" room.
        multiplayer.send("Hello");

        // Determines if the connection is still alive.
        System.out.println(multiplayer.getState());

        // Gets the first message in the message queue.
        // To get the payload as a string, use the toString function.
        System.out.println(multiplayer.recieve().toString());

        // Games are instanced by specific "rooms" (topics).
        // Message queue is cleared when switching rooms.
        // Second parameter determines if you want to listen to all other clients in the room.
        multiplayer.setRoom("game", true);

        // Verify what room you are in and if you are listening to all clients.
        System.out.println(multiplayer.getRoom());
        System.out.println(multiplayer.getAllRoomScope());

        // Recieve blocks until the next message if the queue is empty.
        // In this example case, use an external client or 'mosquitto_pub -t "user/game" -m "test"' on the server.
        Message newestMessage = multiplayer.recieve();
        System.out.println(newestMessage.getUser());
        System.out.println(newestMessage.toString());

        // Always disconnect cleanly.
        // Subscribing to the root "user/" or "#" topic will show if the client disconnected cleanly.
        multiplayer.disconnect();
        System.out.println("Finish");
    }

}
