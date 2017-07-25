package com.al0ne.Behaviours;

import com.al0ne.Behaviours.Pairs.Pair;
import com.al0ne.Behaviours.abstractEntities.Entity;

import java.io.Serializable;
import java.util.ArrayList;

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
 * - ...
 */
public abstract class Quest implements Serializable{
    protected String questID;
    protected boolean completed;
    protected ArrayList<Pair> toAdd;

    public Quest(String questID){
        this.questID = questID;
        this.completed = false;
        this.toAdd = new ArrayList<>();
    }

    //to implement in different kind of quests
    public abstract void checkCompletion();

    public String getQuestID() {
        return questID;
    }

    public boolean isCompleted() {
        return completed;
    }

    public ArrayList<Pair> getToAdd() {
        return toAdd;
    }

    public void addEntity(Entity entity, int count){
        toAdd.add(new Pair(entity, count));
    }
}
