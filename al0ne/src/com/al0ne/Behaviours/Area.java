package com.al0ne.Behaviours;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by BMW on 05/04/2017.
 */
public class Area implements Serializable{


    protected String areaName;
    protected String startingRoom;
    protected HashMap<String, Room> rooms;
    protected Player player;
//    protected LootTable lootTable;
    //todo add random events with chance, for both rooms and areas



    public Area(String worldName) {
        this.areaName = worldName;
        this.rooms = new HashMap<>();
//        this.lootTable = new LootTable();
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

    public String getAreaName() {
        return areaName;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
        this.startingRoom = player.getCurrentRoom().getID();
    }


}
