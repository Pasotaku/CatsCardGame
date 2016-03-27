package com.pasotaku.mqtt.games;

import com.pasotaku.mqtt.core.Message;
import com.pasotaku.mqtt.core.MessageBroker;

public class BaseClient {

    private MessageBroker connection;
    private String user;

    public BaseClient(String serverAddress, String user){
        this.user = "UN_" + user.trim().replace(" ", "_");
        this.connection = new MessageBroker(serverAddress, this.user, true, true);
        this.connection.setRoom("lobby", false);
        this.connection.send("@lobby", this.user, true);
    }

    protected String listGames(){
        return "";
    }

    protected Boolean createGame(String roomName){
        this.connection.setRoom("lobby", false);
        this.connection.send("@lobby", this.user, true);
        this.connection.send("/create " + roomName.trim().replace(" ", "_"));
        Message roomResponse = this.connection.recieve();
        while (roomResponse.toString().startsWith("/")) {
            roomResponse = this.connection.recieve();
        }
        if(roomResponse.toString().startsWith("!")){
            return false;
        }
        this.joinGame(roomResponse.toString());
        return true;
    }

    protected void joinGame(String roomName){
        this.connection.setRoom(roomName, true);
        this.connection.send("@" + roomName, this.user, true);
    }

    protected void startGame(){
        this.connection.send("/start");
    }

    protected void leaveGame(){
        this.connection.setRoom("lobby", false);
        this.connection.send("@lobby", this.user, true);
    }

    protected void disconnect(){
        this.connection.disconnect();
    }

    protected void sendMessage(String message){
        this.connection.send(message);
    }

    protected void shutdownServer(){
        this.connection.send("/shutdown", this.user + "/lobby", false);
    }

}
