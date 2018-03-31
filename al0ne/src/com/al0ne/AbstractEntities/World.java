package com.al0ne.AbstractEntities;

import com.al0ne.AbstractEntities.Enums.TechLevel;
import com.al0ne.AbstractEntities.Player.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class World implements Serializable{
    private String worldName;
    private ArrayList<Area> areas;
    protected Player player;
    private TechLevel techLevel;
    private Area startingArea;

    public World(String name, TechLevel tl, Player p) {
        this.worldName = name;
        this.areas = new ArrayList<>();
        this.techLevel = tl;
        this.player = p;
    }

    public String getWorldName() {
        return worldName;
    }

    public ArrayList<Area> getAreas() {
        return areas;
    }

    public void addArea(Area a) {
        this.areas.add(a);
    }

    public Area getStartingArea() {
        return startingArea;
    }

    public void setStartingArea(Area startingArea) {
        this.startingArea = startingArea;
    }

    public TechLevel getTechLevel() {
        return techLevel;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
