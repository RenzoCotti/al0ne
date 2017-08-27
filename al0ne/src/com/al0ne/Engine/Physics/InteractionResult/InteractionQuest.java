package com.al0ne.Engine.Physics.InteractionResult;

import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.Pairs.Pair;
import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.abstractEntities.Entity;

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
