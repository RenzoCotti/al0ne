package com.al0ne.Engine.Game;

import com.al0ne.AbstractEntities.Area;
import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.Room;
import com.al0ne.AbstractEntities.Enums.CommandMap;

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

    private String gameName;
    protected String startingWorld;
    protected String currentWorld;
    private HashMap<String, Area> worlds;
    private int turnCounter;
    private int worldCount;
    private boolean debugMode;
    private CommandMap commands;
    private String notes;


    public Game(String name) {
        this.gameName = name;
        this.worlds = new HashMap<>();
        this.turnCounter = 0;
        this.worldCount = 0;
        this.debugMode=false;
        this.commands = new CommandMap();
        this.notes = "";
    }

    public Player getPlayer() {
        return worlds.get(currentWorld).getPlayer();
    }

    public Room getRoom() {
        return worlds.get(currentWorld).getPlayer().getCurrentRoom();
    }

    public void setPlayer(Player p) {
        Area pw = worlds.get(currentWorld);
        pw.setPlayer(p);
//        worlds.put(currentWorld, pw);
    }

    public void setRoom(Room r) {
        Area pw = worlds.get(currentWorld);
        Player p = getPlayer();
        p.setCurrentRoom(r);
        pw.setPlayer(p);
//        worlds.put(currentWorld, pw);
    }

    public HashMap<String, Area> getWorlds() {
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
        return " worlds : " +
                this.worlds + " turnCounter : " +
                this.turnCounter + " notes: " +
                this.notes;
    }

    public void addWorld(Area area) {
        this.worldCount++;
        this.worlds.put(area.getAreaName(), area);
    }

    public void setNotes(String s) {
        this.notes = s;
    }

    public String getNotes() {
        return notes;
    }

    public String getCurrentWorldName() {
        return currentWorld;
    }

    public Area getCurrentWorld() {
        return worlds.get(currentWorld);
    }

    public void setCurrentWorld(String currentWorld) {
        this.currentWorld = currentWorld;
    }

    public Area getWorld(String worldID){
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

    public String getGameName() {
        return gameName;
    }
}
