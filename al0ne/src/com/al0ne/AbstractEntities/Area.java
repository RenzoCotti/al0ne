package com.al0ne.AbstractEntities;

import com.al0ne.AbstractEntities.Enums.TechLevel;
import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.Quests.Quest;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by BMW on 05/04/2017.
 */
public class Area implements Serializable{

    //TODO a world is a superclass of areas?

    private String areaName;
    private String startingRoom;
    private HashMap<String, Room> rooms;
    private World parentWorld;
//    protected LootTable lootTable;



    public Area(String areaName, World w) {
        this.areaName = areaName;
        this.rooms = new HashMap<>();
        this.parentWorld = w;
//        this.lootTable = new LootTable();
    }

    public Room getStartingRoom() {
        return rooms.get(startingRoom);
    }

    public void setStartingRoom(Room startingRoom) {
        this.startingRoom = startingRoom.getID();
    }

    public Player getPlayer() {
        return parentWorld.getPlayer();
    }

    public HashMap<String, Room> getRooms() {
        return rooms;
    }

    public void addRoom(Room room){
        rooms.put(room.getID(), room);
    }

    public String getAreaName() {
        return areaName;
    }

    public TechLevel getTechLevel() {
        return parentWorld.getTechLevel();
    }

    public World getParentWorld() {
        return parentWorld;
    }

    public void addQuest(Quest q){
        parentWorld.addQuest(q);
    }
}
