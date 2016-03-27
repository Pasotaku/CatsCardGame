package com.pasotaku.cats.multiplayer;

import com.pasotaku.mqtt.games.BaseClient;

import java.util.concurrent.TimeUnit;

public class Client extends BaseClient{

    public static void main (String[] args) {
        try {
            // Can use multiple clients
            Client testClient = new Client("myUser");
            Client secondClient = new Client("yourUser");

            // Example Flow
            testClient.createGame("my2");
            testClient.startGame();
            secondClient.createGame("your3");
            secondClient.sendMessage("mine is great");
            testClient.sendMessage("testing game send.");
            testClient.sendMessage("/end");
            testClient.joinGame("your3");
            secondClient.startGame();
            secondClient.sendMessage("yours is dumb");
            testClient.sendMessage("maybe.");
            testClient.sendMessage("/end");

            // Cleanup
            TimeUnit.SECONDS.sleep(5);
            testClient.disconnect();
            secondClient.shutdownServer();
            secondClient.disconnect();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private Client(String user){
        super("tcp://rhitgaming.com:1883", user);
    }

}
