package com.al0ne.Engine.Physics.InteractionResult;

import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.Quests.Quest;

public class InteractionAddQuest extends InteractionBehaviour {
    private Quest q;
    public InteractionAddQuest(Quest q) {
        this.q = q;
    }

    @Override
    public void interactionEffect(Player p) {
        p.getCurrentWorld().getQuests().get(q.getQuestID()).setVisibleToThePlayer(true);
    }
}
