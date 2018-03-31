package com.al0ne.Engine.Game;

import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.Room;
import com.al0ne.AbstractEntities.Enums.CommandMap;
import com.al0ne.AbstractEntities.World;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by BMW on 28/01/2017.
 */
public class Game implements Serializable {

    //used to discriminate games in the editor
    private String gameName;

    //this is the starting world
    protected int startingWorld;
    protected int currentWorld;

    private ArrayList<World> worlds;


    private int turnCounter;
    private boolean debugMode;
    private CommandMap commands;
    private String notes;


    public Game(String name) {
        this.gameName = name;
        this.worlds = new ArrayList<>();
        this.turnCounter = 0;
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
        World w = worlds.get(currentWorld);
        w.setPlayer(p);
    }

    public void setRoom(Room r) {
        World w = worlds.get(currentWorld);
        Player p = getPlayer();
        p.setCurrentRoom(r);
        w.setPlayer(p);
    }

    public ArrayList<World> getWorlds() {
        return worlds;
    }

    public World getWorld(int i) {
        return worlds.get(i);
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

    public void addWorld(World w) {
        this.worlds.add(w);
    }

    public void setNotes(String s) {
        this.notes = s;
    }

    public String getNotes() {
        return notes;
    }

    public World getCurrentWorld() {
        return worlds.get(currentWorld);
    }

    public int getCurrentWorldIndex(){
        return  currentWorld;
    }

    public void setCurrentWorld(int i) {
        this.currentWorld = i;
    }

    public int getStartingWorld() {
        return startingWorld;
    }

    public int getWorldCount() {
        return worlds.size();
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
