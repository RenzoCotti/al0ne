package com.al0ne.Entities.Statuses.ConcreteStatuses;

import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Status;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 13/04/2017.
 */
public class Dehydrated extends Status {
    public Dehydrated() {
        super("dehydrated", -1, "You need some water, badly.", "Your body withers from the lack of water.", "Finally some fresh water!");
    }

    @Override
    public boolean resolveStatus(Player player) {
        player.modifyHealth(-1);
        printToLog(onTick);
        return false;
    }
}
