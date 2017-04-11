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

    public World(String worldName, String startingRoom) {
        this.worldName = worldName;
        this.startingRoom=startingRoom;
        this.rooms = new HashMap<>();
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

    public void setRooms(HashMap<String, Room> rooms) {
        this.rooms = rooms;
    }

    public void putRoom(Room room){
        rooms.put(room.getID(), room);
    }

    public String getWorldName() {
        return worldName;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }
}
