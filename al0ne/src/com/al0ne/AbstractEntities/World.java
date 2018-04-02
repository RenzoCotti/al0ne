package com.al0ne.AbstractEntities;

import com.al0ne.AbstractEntities.Enums.TechLevel;
import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.Quests.Quest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class World implements Serializable{
    private String worldName;
    private ArrayList<Area> areas;
    private HashMap<String, Quest> quests;
    protected Player player;
    private TechLevel techLevel;
    private Area startingArea;

    public World(String name, TechLevel tl, Player p) {
        this.worldName = name;
        this.areas = new ArrayList<>();
        this.techLevel = tl;
        this.player = p;
        this.quests = new HashMap<>();
    }

    public String getWorldName() {
        return worldName;
    }

    public ArrayList<Area> getAreas() {
        return areas;
    }

    public void addArea(Area a) {
        if(startingArea == null){
            startingArea = a;
        }
        this.areas.add(a);
    }

    public Area getStartingArea() {
        return startingArea;
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

    public HashMap<String, Quest> getQuests() {
        return quests;
    }

    public void addQuest(Quest q) {
        this.quests.put(q.getQuestID(), q);
    }

    public boolean completeQuest(String questID){
        Quest q = quests.get(questID);
        if(q != null && !q.isCompleted()){
            q.setCompleted();
            q.questReward(player);
            return true;
        } else {
            return false;
        }
    }
}
