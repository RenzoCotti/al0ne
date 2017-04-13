package com.al0ne.Entities.Statuses;

import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Status;

import static com.al0ne.Engine.Main.printToLog;



public class Thirsty extends Status{

    public static final int THIRST_CLOCK = 20;
    public Thirsty() {
        super("thirsty", THIRST_CLOCK, "You'd like some water.");
    }

    @Override
    public boolean resolveStatus(Player player) {
        if(!player.hasStatus("dehydrated")){
            duration --;

            if(duration <= 5){

                Status dehydrated = new Dehydrated();
                if(duration != 0){
                    printToLog(dehydrated.getOnApply());
                    return false;
                } else{
                    printToLog(dehydrated.getOnApply());
                    player.queueStatus(dehydrated);
                    duration = THIRST_CLOCK;
                    return false;
                }
            }
            return false;
        } else{
            return false;
        }

    }
}
