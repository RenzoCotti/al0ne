package com.al0ne.Entities.Statuses.ConcreteStatuses;

import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Status;
import com.al0ne.Behaviours.abstractEntities.WorldCharacter;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 07/04/2017.
 */
public class BlackDeath extends Status{
    public BlackDeath() {
        super("blackdeath", 0, "You feel sick. Give up all hope.", "Your fever worsens.", "By a godly miracle, you recover from your fever.");
    }

    @Override
    public boolean resolveStatus(WorldCharacter character) {
        duration++;
        if(duration % 5 == 0){
            if(!character.modifyHealth(-1) && character instanceof Player){
                ((Player) character).setCauseOfDeath(name);
            }
            printToLog(onTick);
        }
        return false;
    }
}
