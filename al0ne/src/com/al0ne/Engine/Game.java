package com.al0ne.Engine;

import com.al0ne.Entities.Player;
import com.al0ne.Room;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by BMW on 28/01/2017.
 * a Game is:
 * a Player
 * an allRooms
 * ...
 */
public class Game implements Serializable{

    private Player player;
    private HashMap<String, Room> allRooms;
    private int turnCounter;

    public Game(Player player, HashMap<String, Room> allRooms, int turnCounter) {
        this.player = player;
        this.allRooms = allRooms;
        this.turnCounter = turnCounter;
    }

    public Player getPlayer() {
        return player;
    }

    public Room getRoom(){
        return player.getCurrentRoom();
    }

    public HashMap<String, Room> getAllRooms(){
        return allRooms;
    }

    public void addTurn(){
        this.turnCounter++;
    }

    public int getTurnCount(){
        return turnCounter;
    }

    @Override
    public String toString() {
        return new StringBuffer(" Player : ")
                .append(this.player).append(" allRooms : ")
                .append(this.allRooms).append(" turnCounter : ")
                .append(this.turnCounter).toString();
    }


}
