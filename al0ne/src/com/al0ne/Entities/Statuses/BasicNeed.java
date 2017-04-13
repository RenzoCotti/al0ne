package com.al0ne.Entities.Statuses;

import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Status;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 13/04/2017.
 */
public class BasicNeed extends Status {

    private int clock;
    private String finalStatus;
    public BasicNeed(String name, int clock, String onApply, String finalStatus) {
        super(name, clock, onApply);
        this.clock = clock;
        this.finalStatus = finalStatus;
    }

    @Override
    public boolean resolveStatus(Player player) {
        if(!player.hasStatus(finalStatus)){
            duration --;

            if(duration <= 5){

                Status finalStatus;
                switch(this.finalStatus){
                    case "starving":
                        finalStatus = new Starving();
                        break;
                    case "dehydrated":
                        finalStatus = new Dehydrated();
                        break;
                    default:
                            //error
                        System.out.println("error in resolving status: "+this.finalStatus);
                        finalStatus = new Dehydrated();
                }
                if(duration != 0){
                    printToLog(finalStatus.getOnApply());
                    return false;
                } else{
                    printToLog(finalStatus.getOnApply());
                    player.queueStatus(finalStatus);
                    duration = clock;
                    return false;
                }
            }
            return false;
        } else{
            return false;
        }

    }
}
