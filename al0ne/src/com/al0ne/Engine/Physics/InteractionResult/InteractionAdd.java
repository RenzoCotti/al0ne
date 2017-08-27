package com.al0ne.Engine.Physics.InteractionResult;

import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.Pairs.Pair;
import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.abstractEntities.Entity;

public class InteractionAdd extends InteractionBehaviour {
    private Integer amount;
    private Entity toAdd;
    public InteractionAdd(Entity toAdd, int amount) {
        this.amount=amount;
        this.toAdd = toAdd;
    }

    @Override
    public void interactionEffect(Player p) {

        if (toAdd instanceof Item){
            if (!p.addAmountItem(new Pair(toAdd,  amount), amount)){
                p.getCurrentRoom().addEntity(toAdd, amount);
            }
        } else {
            p.getCurrentRoom().addEntity(toAdd, amount);
        }
    }
}
