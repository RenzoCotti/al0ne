package com.al0ne.Engine.Physics.InteractionResult;

import com.al0ne.AbstractEntities.Player.Player;

public class InteractionHealth extends InteractionBehaviour {
    private int modifier;
    public InteractionHealth(int modifier) {
        this.modifier = modifier;
    }

    @Override
    public void interactionEffect(Player p) {
        p.modifyHealth(modifier);
    }
}