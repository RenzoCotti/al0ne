package com.al0ne.Entities.Statuses.ConcreteStatuses;

import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Status;
import com.al0ne.Behaviours.WorldCharacter;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 13/04/2017.
 */
public class Starving extends Status{

    public Starving() {
        super("starving", -1, "You need some food, now.", "Your stomach aches in hunger.", "Food! Finally!");
    }

    @Override
    public boolean resolveStatus(WorldCharacter character) {
        character.modifyHealth(-1);
        printToLog(onTick);
        return false;
    }
}
