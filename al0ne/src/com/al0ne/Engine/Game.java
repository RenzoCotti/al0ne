package com.al0ne.Engine;

import com.al0ne.Behaviours.Pairs.PairWorld;
import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Room;
import com.al0ne.Behaviours.World;
import com.al0ne.Behaviours.Enums.CommandMap;

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

    protected String startingWorld;
    protected String currentWorld;
    protected HashMap<String, World> worlds;
    protected int turnCounter;
    protected int worldCount;
    protected boolean debugMode;
    protected CommandMap commands;


    private String notes;

    public Game() {
        this.worlds = new HashMap<>();
        this.turnCounter = 0;
        this.worldCount = 0;
        this.debugMode=false;
        this.commands = new CommandMap();

    }

    public Player getPlayer() {
        return worlds.get(currentWorld).getPlayer();
    }

    public Room getRoom() {
        return worlds.get(currentWorld).getPlayer().getCurrentRoom();
    }

    public void setPlayer(Player p) {
        World pw = worlds.get(currentWorld);
        pw.setPlayer(p);
//        worlds.put(currentWorld, pw);
    }

    public void setRoom(Room r) {
        World pw = worlds.get(currentWorld);
        Player p = getPlayer();
        p.setCurrentRoom(r);
        pw.setPlayer(p);
//        worlds.put(currentWorld, pw);
    }

    public HashMap<String, World> getWorlds() {
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

    public void addWorld(World world) {
        this.worldCount++;
        this.worlds.put(world.getWorldName(), world);
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

    public World getWorld(String worldID){
        return worlds.get(worldID);
    }

    public HashMap<String, Room> getRooms(){
        return worlds.get(currentWorld).getRooms();
    }

    public String getStartingWorld() {
        return startingWorld;
    }

    public int getWorldCount() {
        return worldCount;
    }

    public void toggleDebugMode(){
        getPlayer().setMaxHealth(99);
        getPlayer().setMaxWeight(180);
        getPlayer().setArmor(20);
        getPlayer().setDamage(20);
        this.debugMode = !this.debugMode;
    }

    public boolean isInDebugMode(){
        return this.debugMode;
    }

    public CommandMap getCommands(){
        return this.commands;
    }


}
