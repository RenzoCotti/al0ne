package com.al0ne.Entities.Statuses;

import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Status;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 07/04/2017.
 */
public class BlackDeath extends Status{
    public BlackDeath() {
        super("blackdeath", 0, "You feel sick. Give up all hope.", "Your fever worsens.", "By a godly miracle, you recover from your fever.");
    }

    @Override
    public boolean resolveStatus(Player player) {
        duration++;
        if(duration % 5 == 0){
            player.modifyHealth(-1);
            printToLog(onTick);
        }
        return false;
    }
}
