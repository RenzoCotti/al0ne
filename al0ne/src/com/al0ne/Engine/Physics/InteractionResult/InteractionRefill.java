package com.al0ne.Engine.Physics.InteractionResult;

import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.ConcreteEntities.Items.Types.ChargeItem;

public class InteractionRefill extends InteractionBehaviour {
    private ChargeItem toRefill;
    public InteractionRefill(ChargeItem toRefill) {
        this.toRefill = toRefill;
    }

    @Override
    public void interactionEffect(Player player) {
        toRefill.refill();
    }
}
