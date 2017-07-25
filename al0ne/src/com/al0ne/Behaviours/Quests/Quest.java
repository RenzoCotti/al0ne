package com.al0ne.Behaviours.Quests;

import com.al0ne.Behaviours.Pairs.Pair;
import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.abstractEntities.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A Quest consists of 1 of:
 * - give x to y
 * - kill x
 * - talk to x
 * - use x
 * - use x on y
 * - go to x
 * - take x
 *
 * A Quest can, on completion:
 * - unlock something
 * - teleport somewhere
 * - add entities somewhere (add item?)
 * - add another quest
 * - change world
 * - remove entity
 * - ...
 */
public abstract class Quest implements Serializable{
    protected String questID;
    protected boolean completed;
    protected ArrayList<Pair> toAdd;
    protected String requiredQuest;
    protected HashMap<Integer, Object> rewards;

    public Quest(String questID){
        this.questID = questID;
        this.completed = false;
        this.toAdd = new ArrayList<>();
        this.rewards = new HashMap<>();
    }

    //to implement in different kind of quests
    public abstract void checkCompletion(Player player);

    public abstract void questReward();

    public String getQuestID() {
        return questID;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public ArrayList<Pair> getToAdd() {
        return toAdd;
    }

    public void addEntity(Entity entity, int count){
        toAdd.add(new Pair(entity, count));
    }

    public String getRequiredQuest() {
        return requiredQuest;
    }

    public void setRequiredQuest(String requiredQuest) {
        this.requiredQuest = requiredQuest;
    }

    public HashMap<Integer, Object> getRewards() {
        return rewards;
    }

    public void addRewards(int number, Object object) {
        this.rewards.put(number, object);
    }
}
