package com.al0ne.Behaviours.Quests;

import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Room;
import com.al0ne.Engine.Main;

/**
 * Created by BMW on 28/07/2017.
 */
public class TravelQuest extends Quest{

    public String targetRoom;

    public TravelQuest(String questName, Room r) {
        super(questName);
        this.targetRoom = r.getID();
    }

    @Override
    public boolean checkCompletion(Player player) {
        if (player.getCurrentRoom().getID().equals(targetRoom)){
            questReward(player);
            return true;
        }
        return false;
    }

    public String getTargetRoom() {
        return targetRoom;
    }

}
