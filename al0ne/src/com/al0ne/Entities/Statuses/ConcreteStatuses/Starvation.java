package com.al0ne.Entities.Statuses.ConcreteStatuses;

import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Status;
import com.al0ne.Behaviours.abstractEntities.WorldCharacter;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 13/04/2017.
 */
public class Starvation extends Status{

    public Starvation() {
        super("starvation", -1, "You need some food, now.", "Your stomach aches in hunger.", "FoodBehaviour! Finally!");
    }

    @Override
    public boolean resolveStatus(WorldCharacter character) {
        if(character.modifyHealth(-1) && character instanceof Player){
            ((Player) character).setCauseOfDeath(name);
        }
        printToLog(onTick);
        return false;
    }
}
