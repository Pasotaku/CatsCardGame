package com.pasotaku.cats.multiplayer;

import com.pasotaku.mqtt.games.BaseServer;

public class Server extends BaseServer{

    // Entry-point
    public static void main (String[] args) {
        Server main_server = new Server("tcp://rhitgaming.com:1883", new GameMaster());
        main_server.start();
        System.out.println("End of main");
    }

    private Server(String serverAddress, GameMaster gameMaster){
        super(serverAddress, "server", gameMaster, 1000);
    }
}
