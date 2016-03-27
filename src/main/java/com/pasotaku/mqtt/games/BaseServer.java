package com.pasotaku.mqtt.games;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.pasotaku.mqtt.core.Message;
import com.pasotaku.mqtt.core.MessageBroker;

public class BaseServer {

    private MessageBroker connection;
    private Set<String> roomList = ConcurrentHashMap.newKeySet();
    private String serverAddress;
    private BaseGameMaster gameMaster;

    private ExecutorService gameThreadPool;

    private final String id;
    private final Boolean toLog = false;

    public BaseServer(String serverAddress, String serverName, BaseGameMaster gameMaster, int maxThreads) {
        this.id = "SN_" + serverName;
        this.serverAddress = serverAddress;
        this.connection = new MessageBroker(serverAddress, this.id, true, false);
        this.connection.setRoom("lobby", true);
        this.gameMaster = gameMaster;
        this.gameThreadPool = Executors.newFixedThreadPool(maxThreads);

    }

    // Starts server
    protected void start(){
        Message currentMessage;
        this.log("Started server");
        while (this.connection.getState().equals("Connected")){
            currentMessage = this.connection.recieve();
            while (!currentMessage.toString().startsWith("/")){
                currentMessage = this.connection.recieve();
            }
            if (currentMessage.toString().startsWith("/create")){
                String roomName = currentMessage.toString().split(" ")[1];
                if (this.roomList.contains(roomName)){
                    this.log("Room already existed.");
                    this.connection.send("!Room already exists.", currentMessage.getTopic(), false);
                }
                else {
                    this.roomList.add(roomName);
                    // Create thread for watchdog and game master
                    this.log("Creating room");
                    this.gameThreadPool.submit(new GameThread(this.serverAddress, roomName, this.gameMaster));
                    this.gameThreadPool.submit(new WatchDog(this.serverAddress, roomName, this.roomList));
                    this.connection.send(roomName, currentMessage.getTopic(), false);
                }
            }
            else if (currentMessage.toString().startsWith("/shutdown")){
                this.log("Shutdown sent");
                this.connection.disconnect();
                this.log("Disconnected");
                this.gameThreadPool.shutdown();
                this.log("Thread pool shutdown.");
                break;
            }
        }
        this.log("Server finished.");
    }

    private void log (String message){
        if (this.toLog){
            System.out.println("[" + this.id + "] " + message);
        }
    }

    private class WatchDog implements Runnable{

        private final long timeoutTime = 300000;

        private String roomName;
        private Set<String> serverRoomList;

        private MessageBroker connection;
        private HashSet<String> userList = new HashSet<>();
        private long creationTime;

        private final String id;

        WatchDog(String serverAddress, String roomName, Set<String> serverRoomList){
            this.id = "WD_" + roomName;

            this.roomName = roomName;
            this.serverRoomList = serverRoomList;

            this.connection = new MessageBroker(serverAddress, this.id, true, false);
            this.connection.setRoom("", true);
            this.creationTime = System.currentTimeMillis();
        }

        @Override
        public void run() {
            this.log(this.serverRoomList.toString());
            Message currentMessage;
            this.log("Waiting for users.");
            while (this.userList.isEmpty()){
                // Wait for initial room creator.
                currentMessage = this.connection.recieve();
                this.log(">> " + currentMessage.getUser() + " " + currentMessage.toString());
                if (currentMessage.toString().equals("@" + this.roomName) &&
                        !currentMessage.getUser().startsWith("GM")){
                    this.userList.add(currentMessage.getUser());
                }
                else if (System.currentTimeMillis() - this.creationTime > timeoutTime){
                    // Time out if creator never shows.
                    this.serverRoomList.remove(this.roomName);
                    return;
                }
            }
            this.log("Room is no longer empty.");
            this.log(this.userList.toString());
            while (!this.userList.isEmpty()){
                // Wait until the room is empty
                currentMessage = this.connection.recieve();
                this.log(">> " + currentMessage.getUser() + " " + currentMessage.toString());
                if (this.userList.contains(currentMessage.getUser()) &&
                        (!currentMessage.toString().equals("@" + this.roomName) ||
                        currentMessage.toString().equals("Logged Out") ||
                        currentMessage.toString().equals("Lost Connection"))){
                    this.log(currentMessage.getUser() + " has left");
                    this.userList.remove(currentMessage.getUser());
                    this.log(this.userList.toString());
                }
                else if (currentMessage.toString().equals("@" + this.roomName) &&
                        !currentMessage.getUser().startsWith("GM")){
                    this.log(currentMessage.getUser() + " has joined");
                    this.userList.add(currentMessage.getUser());
                }
            }
            // Delete room
            this.log("Room deleted");
            this.serverRoomList.remove(this.roomName);
            this.connection.disconnect();
            this.log(this.serverRoomList.toString());
        }

        private void log (String message){
            if (toLog){
                System.out.println("[" + this.id + "] " + message);
            }
        }
    }

    private class GameThread implements Runnable {

        private MessageBroker connection;
        private BaseGameMaster gameMaster;

        private final String id;

        GameThread(String serverAddress, String roomName, BaseGameMaster gameMaster){
            this.id = "GM_" + roomName;
            this.connection = new MessageBroker(serverAddress, this.id, true, false);
            this.connection.setRoom(roomName, true);
            this.gameMaster = gameMaster;
        }

        @Override
        public void run() {
            Boolean startGame = false;
            String currentMessage;
            this.log("Game initialized");
            while (!startGame){
                currentMessage = this.connection.recieve().toString();
                if (currentMessage.startsWith("/start")){
                    startGame = true;
                }
            }
            this.log("Game started");
            this.gameMaster.runGame(this.connection);
            this.log("Game finished");
            this.connection.disconnect();
        }

        private void log (String message){
            if (toLog){
                System.out.println("[" + this.id + "] " + message);
            }
        }
    }
}
