package com.al0ne.Behaviours.Quests;

import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.abstractEntities.WorldCharacter;

/**
 * Created by BMW on 26/07/2017.
 */
public class KillQuest extends Quest{

    protected String toKillID;
    protected int currentCount;
    protected int targetCount;

    public KillQuest(String questName, WorldCharacter wc, int count) {
        super(questName);
        this.toKillID = wc.getID();
        this.targetCount = count;
        this.currentCount = 0;
    }

    @Override
    public boolean checkCompletion(Player player) {
        if(currentCount >= targetCount){
            questReward(player);
            setCompleted();
            return true;
        }
        return false;
    }

    public String getToKillID() {
        return toKillID;
    }

    public int getCurrentCount() {
        return currentCount;
    }

    public int getTargetCount() {
        return targetCount;
    }

    public void addCurrentCount() {
        this.currentCount++;
    }
}
