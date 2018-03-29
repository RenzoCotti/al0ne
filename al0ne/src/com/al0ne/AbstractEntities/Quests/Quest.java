package com.al0ne.AbstractEntities.Quests;

import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.Engine.Physics.InteractionResult.InteractionBehaviour;
import com.al0ne.Engine.Physics.Physics;

import java.io.Serializable;
import java.util.ArrayList;

import static com.al0ne.Engine.Main.printToLog;

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
    public static int questCounter = 0;

    protected String questID;
    protected String questName;
    protected boolean completed;
//    protected ArrayList<Pair> toAdd;
    protected String requiredQuest;
    protected ArrayList<InteractionBehaviour> rewards;
    //TODO QUEST GIVER? ON COMPLETION MESSAGE?

    public Quest(String questName){
        this.questID = "quest"+(++questCounter);
        this.questName = questName;
        this.completed = false;
//        this.toAdd = new ArrayList<>();
        this.rewards = new ArrayList<>();
    }

    //to implement in different kind of quests
    public abstract boolean checkCompletion(Player player);

    public void questReward(Player player){
        printToLog("- - - Quest completed: "+getQuestName()+" - - -");
        Physics.useResult(rewards, player);
    }

    public String getQuestID() {
        return questID;
    }

    public String getQuestName() {
        return questName;
    }

    public void setQuestName(String questName) {
        this.questName = questName;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted() {
        this.completed = true;
    }

//    public ArrayList<Pair> getToAdd() {
//        return toAdd;
//    }

//    public void addEntity(Entity entity, int count){
//        toAdd.add(new Pair(entity, count));
//        this.rewards.put(3, toAdd);
//    }

    public String getRequiredQuest() {
        return requiredQuest;
    }

    public void setRequiredQuest(String requiredQuest) {
        this.requiredQuest = requiredQuest;
    }

    public ArrayList<InteractionBehaviour> getRewards() {
        return rewards;
    }

    public void addRewards(InteractionBehaviour b) {
        this.rewards.add(b);
    }
}
