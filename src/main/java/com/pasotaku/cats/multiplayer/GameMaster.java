package com.pasotaku.cats.multiplayer;

import com.pasotaku.mqtt.core.MessageBroker;
import com.pasotaku.mqtt.games.BaseGameMaster;

class GameMaster implements BaseGameMaster {

    @Override
    public void runGame(MessageBroker connection) {
        int i = 0;
        String message;
        do {
            i++;
            message = connection.recieve().toString();
            System.out.println(Integer.toString(i) + ": '" + message + "'");

        } while (!message.startsWith("/end"));
        System.out.println("End of game");
    }
}
