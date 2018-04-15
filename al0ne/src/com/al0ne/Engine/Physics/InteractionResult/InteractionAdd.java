package com.al0ne.Engine.Physics.InteractionResult;

import com.al0ne.AbstractEntities.Abstract.Item;
import com.al0ne.AbstractEntities.Pairs.Pair;
import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.Abstract.Entity;

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
            p.addAmountItem(new Pair(toAdd,  amount), amount);
        } else {
            p.getCurrentRoom().addEntity(toAdd, amount);
        }
    }
}
