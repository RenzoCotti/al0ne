package com.al0ne.Behaviours;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by BMW on 05/04/2017.
 */
public class World implements Serializable{


    protected String worldName;
    protected String startingRoom;
    protected HashMap<String, Room> rooms;
    protected Player player;
    protected LootTable lootTable;

    //b: base
    //l: low
    //m: mid
    //h: high
    protected char techLevel;

    public World(String worldName, char techLevel) {
        this.worldName = worldName;
        this.rooms = new HashMap<>();
        this.lootTable = new LootTable();
        this.techLevel = techLevel;
    }

    public Room getStartingRoom() {
        return rooms.get(startingRoom);
    }

    public void setStartingRoom(Room startingRoom) {
        this.startingRoom = startingRoom.getID();
    }

    public HashMap<String, Room> getRooms() {
        return rooms;
    }

    public void putRoom(Room room){
        rooms.put(room.getID(), room);
    }

    public String getWorldName() {
        return worldName;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
