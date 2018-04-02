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
 */
public abstract class Quest implements Serializable{
    private static int questCounter = 0;

    private String questID;
    private String questName;
    private boolean completed;
    protected boolean visibleToThePlayer;
    private ArrayList<InteractionBehaviour> rewards;

    public Quest(String questName){
        this.questID = "quest"+(++questCounter);
        this.questName = questName;
        this.completed = false;
        this.rewards = new ArrayList<>();
        this.visibleToThePlayer = false;
    }

    public Quest(String questName, boolean visible){
        this.questID = "quest"+(++questCounter);
        this.questName = questName;
        this.completed = false;
        this.rewards = new ArrayList<>();
        this.visibleToThePlayer = visible;
    }

    //to implement in different kind of quests
    public abstract void checkCompletion(Player player);

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
        this.setVisibleToThePlayer(false);
    }

    public ArrayList<InteractionBehaviour> getRewards() {
        return rewards;
    }

    public void addRewards(InteractionBehaviour b) {
        this.rewards.add(b);
    }

    public boolean isVisibleToThePlayer() {
        return visibleToThePlayer;
    }

    public void setVisibleToThePlayer(boolean visibleToThePlayer) {
        this.visibleToThePlayer = visibleToThePlayer;
    }
}
