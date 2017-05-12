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

    public World(String worldName, String startingRoom) {
        this.worldName = worldName;
        this.startingRoom=startingRoom;
        this.rooms = new HashMap<>();
        this.lootTable = new LootTable();
    }

    public Room getStartingRoom() {
        return rooms.get(startingRoom);
    }

    public void setStartingRoom(String startingRoom) {
        this.startingRoom = startingRoom;
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
