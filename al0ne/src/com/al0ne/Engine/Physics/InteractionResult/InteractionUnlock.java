package com.al0ne.Engine.Physics.InteractionResult;

import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.Room;

public class InteractionUnlock extends InteractionBehaviour {
    private String doorID;
    public InteractionUnlock(String doorID) {
        this.doorID = doorID;
    }

    @Override
    public void interactionEffect(Player player) {
        Room currentRoom = player.getCurrentRoom();
        currentRoom.unlockDirection(doorID);
    }
}
