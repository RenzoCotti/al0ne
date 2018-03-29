package com.al0ne.AbstractEntities.Events;

import com.al0ne.Engine.Physics.InteractionResult.InteractionHealth;
import com.al0ne.Engine.Physics.InteractionResult.InteractionPrint;

import java.util.ArrayList;

public class HealthEvent extends Event{
    public HealthEvent(int probability, String message, int modifier) {
        super(probability);
        this.effects.add(new InteractionHealth(modifier));
        this.effects.add(new InteractionPrint(message));
    }
}
