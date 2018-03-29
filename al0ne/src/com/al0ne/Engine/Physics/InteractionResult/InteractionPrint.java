package com.al0ne.Engine.Physics.InteractionResult;

import com.al0ne.AbstractEntities.Player.Player;

import static com.al0ne.Engine.Main.printToLog;

public class InteractionPrint extends InteractionBehaviour {
    private String message;
    public InteractionPrint(String s) {
        this.message=s;
    }

    @Override
    public void interactionEffect(Player p) {
        printToLog(message);
    }
}
