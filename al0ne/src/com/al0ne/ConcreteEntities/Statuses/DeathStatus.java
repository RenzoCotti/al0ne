package com.al0ne.ConcreteEntities.Statuses;

import com.al0ne.AbstractEntities.Abstract.Status;
import com.al0ne.AbstractEntities.Abstract.WorldCharacter;

import static com.al0ne.Engine.Main.printToLog;

public class DeathStatus extends Status{
    public DeathStatus(String name, Integer duration, String onApply, String tick, String resolve) {
        super(name, duration, onApply, tick, resolve);
    }

    @Override
    public boolean resolveStatus(WorldCharacter character) {
        if(duration % 10 == 0){
            printToLog(onTick);
            duration--;
        } else{
            duration--;
        }
        if(duration <= 0){
            character.setAlive(false);
            printToLog(onResolve);
            character.setCauseOfDeath(name);
        }
        return false;
    }
}
