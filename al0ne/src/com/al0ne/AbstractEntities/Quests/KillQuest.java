package com.al0ne.AbstractEntities.Quests;

import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.Abstract.WorldCharacter;

/**
 * Created by BMW on 26/07/2017.
 */
public class KillQuest extends Quest{

    protected String toKillID;
    protected int currentCount;
    protected int targetCount;

    public KillQuest(WorldCharacter wc, int count) {
        super("Kill");
        this.toKillID = wc.getID();
        this.targetCount = count;
        this.currentCount = 0;
        if(count == 1){
            setQuestName("Kill "+wc.getName());
        } else {
            setQuestName("Kill " + count + " " + wc.getName());
        }
    }

    @Override
    public void checkCompletion(Player player) {
        if(currentCount >= targetCount){
            questReward(player);
            setCompleted();
        }
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
