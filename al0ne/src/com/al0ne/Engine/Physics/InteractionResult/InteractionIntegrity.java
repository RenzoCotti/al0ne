package com.al0ne.Engine.Physics.InteractionResult;

import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.abstractEntities.Interactable;

import static com.al0ne.Engine.Main.printToLog;

public class InteractionIntegrity extends InteractionBehaviour {
    private Integer modifier;
    private Interactable toModify;
    public InteractionIntegrity(Interactable toModify, int modifier) {
        this.modifier=modifier;
        this.toModify = toModify;
    }

    @Override
    public void interactionEffect(Player p) {
        toModify.modifyIntegrity(modifier);
    }
}
