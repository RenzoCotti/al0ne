package com.al0ne.Engine;

import com.al0ne.Entities.Behaviours.Player;
import com.al0ne.Entities.Behaviours.Room;
import com.al0ne.Entities.Behaviours.World;

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
    private World world;
    private int turnCounter;

    public Game(Player player, World world, int turnCounter) {
        this.player = player;
        this.world = world;
        this.turnCounter = turnCounter;
    }

    public Player getPlayer() {
        return player;
    }

    public Room getRoom(){
        return player.getCurrentRoom();
    }

    public World getWorld(){
        return world;
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
                .append(this.player).append(" world : ")
                .append(this.world).append(" turnCounter : ")
                .append(this.turnCounter).toString();
    }

    public void setWorld(World world) {
        this.world = world;
    }


}
