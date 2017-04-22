package com.al0ne.Engine;

import com.al0ne.Behaviours.Pairs.PairWorld;
import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Room;
import com.al0ne.Behaviours.World;
import com.al0ne.Entities.Worlds.CreateAlpha;
import com.al0ne.Entities.Worlds.CreateSmallCave;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by BMW on 28/01/2017.
 * a Game is:
 * a Player
 * an allRooms
 * ...
 */
public class Game implements Serializable {

    private String startingWorld;
    private String currentWorld;
    private HashMap<String, PairWorld> worlds;
    private int turnCounter;

    private String notes;

    public Game(int turnCounter, boolean needs) {
        this.worlds = new HashMap<>();
        this.turnCounter = turnCounter;

        World startingWorld = new CreateAlpha();
        World caveWorld = new CreateSmallCave();
        if (needs) {
            addWorld(startingWorld, new Player(startingWorld.getStartingRoom(), true, "You are a knight."));
            addWorld(caveWorld, new Player(caveWorld.getStartingRoom(), true, "You are a caveman.") );
        } else{
            addWorld(startingWorld, new Player(startingWorld.getStartingRoom(), false, "You are a knight."));
            addWorld(caveWorld, new Player(caveWorld.getStartingRoom(), false, "You are a caveman."));
        }

        this.currentWorld = startingWorld.getWorldName();
        this.startingWorld = startingWorld.getWorldName();

    }

    public Player getPlayer() {
        return worlds.get(currentWorld).getPlayer();
    }

    public Room getRoom() {
        return worlds.get(currentWorld).getPlayer().getCurrentRoom();
    }

    public HashMap<String, PairWorld> getWorlds() {
        return worlds;
    }

    public void addTurn() {
        this.turnCounter++;
    }

    public int getTurnCount() {
        return turnCounter;
    }

    @Override
    public String toString() {
        return new StringBuffer(" worlds : ")
                .append(this.worlds).append(" turnCounter : ")
                .append(this.turnCounter).append(" notes: ")
                .append(this.notes).toString();
    }

    public void addWorld(World world, Player player) {
        this.worlds.put(world.getWorldName(), new PairWorld(player, world));
    }

    public void setNotes(String s) {
        this.notes = s;
    }

    public String getNotes() {
        return notes;
    }

    public String getCurrentWorld() {
        return currentWorld;
    }

    public void setCurrentWorld(String currentWorld) {
        this.currentWorld = currentWorld;
    }

    public PairWorld getWorld(String worldID){
        return worlds.get(worldID);
    }

    public HashMap<String, Room> getRooms(){
        return worlds.get(currentWorld).getWorld().getRooms();
    }

    public String getStartingWorld() {
        return startingWorld;
    }

}
