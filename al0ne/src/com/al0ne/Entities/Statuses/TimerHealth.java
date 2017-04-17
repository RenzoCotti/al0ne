package com.al0ne.Entities.Statuses;

import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Status;

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
    public boolean resolveStatus(Player player) {
        duration--;
        if(duration <= 0){
            if(player.modifyHealth(modifier)){
                printToLog(onTick);
            }
            duration = maxDuration;
        }
        return false;
    }
}
