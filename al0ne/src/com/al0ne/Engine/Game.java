package com.al0ne.Engine;

import com.al0ne.Player;
import com.al0ne.Room;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by BMW on 28/01/2017.
 * a Game is:
 * a Player
 * an allRooms
 * ...
 */
public class Game {

    private Player player;
    private HashMap<String, Room> allRooms;

    public Game(Player player, HashMap<String, Room> allRooms) {
        this.player = player;
        this.allRooms = new HashMap<>();
    }

    public Player getPlayer() {
        return player;
    }


}
