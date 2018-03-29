package com.al0ne.Engine.Physics.InteractionResult;

import com.al0ne.AbstractEntities.Player.Player;

public class InteractionQuest extends InteractionBehaviour {
    private String questID;
    public InteractionQuest(String questID) {
        this.questID = questID;
    }

    @Override
    public void interactionEffect(Player p) {
        p.completeQuest(questID);
    }
}
