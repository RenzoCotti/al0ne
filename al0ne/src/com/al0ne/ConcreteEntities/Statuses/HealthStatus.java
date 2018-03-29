package com.al0ne.ConcreteEntities.Statuses;

import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.Abstract.Status;
import com.al0ne.AbstractEntities.Abstract.WorldCharacter;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 06/04/2017.
 */
public class HealthStatus extends Status{

    private Integer modifier;


    public HealthStatus(String name, Integer duration, Integer healthModifier, String onApply, String tick, String resolve) {
        super(name, duration, onApply, tick, resolve);
        this.modifier=healthModifier;
    }

    @Override
    public boolean resolveStatus(WorldCharacter character) {
        duration--;
        if(!character.modifyHealth(modifier) && character instanceof Player){
            ((Player) character).setCauseOfDeath(name);
        }
        printToLog(onTick);

        if(duration <= 0){
            printToLog(onResolve);
            return true;
        }
        return  false;
    }


}
