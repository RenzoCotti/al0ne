package com.al0ne.Engine.Physics.InteractionResult;

import com.al0ne.AbstractEntities.Player.Player;

public class InteractionCompleteQuest extends InteractionBehaviour {
    private String questID;
    public InteractionCompleteQuest(String questID) {
        this.questID = questID;
    }

    @Override
    public void interactionEffect(Player p) {
        p.getCurrentWorld().completeQuest(questID);
    }
}
