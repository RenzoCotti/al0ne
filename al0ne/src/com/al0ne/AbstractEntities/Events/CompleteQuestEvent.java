package com.al0ne.AbstractEntities.Events;

import com.al0ne.AbstractEntities.Quests.Quest;
import com.al0ne.Engine.Physics.InteractionResult.InteractionCompleteQuest;

public class CompleteQuestEvent extends Event{
    public CompleteQuestEvent(Quest q) {
        this.effects.add(new InteractionCompleteQuest(q.getQuestID()));
    }
}
