package com.al0ne.AbstractEntities.Events;

import com.al0ne.Engine.Physics.InteractionResult.InteractionPrint;

public class PrintEvent extends Event{
    public PrintEvent(int probability, String message) {
        super(probability);
        this.effects.add(new InteractionPrint(message));
    }
}
