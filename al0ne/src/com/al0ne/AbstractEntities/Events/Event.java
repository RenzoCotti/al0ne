package com.al0ne.AbstractEntities.Events;

import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.Engine.Physics.InteractionResult.InteractionBehaviour;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Event implements Serializable{
    //concept: you get in a room, and each turn that passes, you have a x out of 10 chance that something will happen

    private int probability;
    protected ArrayList<InteractionBehaviour> effects;

    public Event(int probability) {
        this.probability = probability;
        this.effects = new ArrayList<>();
    }

    public Event() {
        this.probability = 100;
        this.effects = new ArrayList<>();
    }

    public int getProbability() {
        return probability;
    }

    public void resolveEvent(Player p){
        for(InteractionBehaviour ib : effects){
            ib.interactionEffect(p);
        }
    }
}
