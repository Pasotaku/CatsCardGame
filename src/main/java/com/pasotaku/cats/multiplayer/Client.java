package com.pasotaku.cats.multiplayer;

import com.pasotaku.mqtt.Message;
import com.pasotaku.mqtt.MessageBroker;

public class Client {

    private MessageBroker connection;

    public static void main (String[] args) {
        Client testClient = new Client("myUser");
        System.out.println(testClient.createGame());
        testClient.disconnect();
    }

    public Client(String user){
        this.connection = new MessageBroker(user, true);
    }

    public String listGames(){
        return "";
    }

    public String createGame(){
        this.connection.setRoom("lobby", false);
        this.connection.send("create");
        Message roomName = this.connection.recieve();
        while (roomName.toString().equals("create")) {
            roomName = this.connection.recieve();
        }
        return roomName.getMqttMessage().toString();
    }

    public void joinGame(){

    }

    public void disconnect(){
        this.connection.disconnect();
    }

}
