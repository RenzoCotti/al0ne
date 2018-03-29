package com.al0ne.ConcreteEntities.Statuses;

import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.Abstract.Status;
import com.al0ne.AbstractEntities.Abstract.WorldCharacter;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 17/04/2017.
 */
public abstract class TimerHealth extends Status{
    private Integer modifier;
    public TimerHealth(String name, Integer duration, Integer mod, String onApply, String tick, String resolve) {
        super(name, duration, onApply, tick, resolve);
        this.modifier = mod;
    }

    @Override
    public boolean resolveStatus(WorldCharacter character) {
        duration--;
        if(duration <= 0){
            int temp = character.getCurrentHealth();
            if(!character.modifyHealth(modifier) && character instanceof Player){
                ((Player) character).setCauseOfDeath(name);
            }
            if(temp != character.getCurrentHealth()){
                printToLog(onTick);
            }
            duration = maxDuration;
        }
        return false;
    }
}
