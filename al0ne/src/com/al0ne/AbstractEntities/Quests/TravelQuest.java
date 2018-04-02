package com.al0ne.AbstractEntities.Quests;

import com.al0ne.AbstractEntities.Events.CompleteQuestEvent;
import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.Room;

/**
 * Created by BMW on 28/07/2017.
 */
public class TravelQuest extends Quest{

    public String targetRoom;

    public TravelQuest(Room r) {
        super("Go to "+r.getName());
        this.targetRoom = r.getID();
    }

    public TravelQuest( Room r, boolean visible) {
        super("Go to "+r.getName(), visible);
        this.targetRoom = r.getID();
        this.visibleToThePlayer = true;
        r.addEvent(new CompleteQuestEvent(this));
    }

    @Override
    public void checkCompletion(Player player) {
        if (player.getCurrentRoom().getID().equals(targetRoom)){
            setCompleted();
            questReward(player);
        }
    }

    public String getTargetRoom() {
        return targetRoom;
    }

}
