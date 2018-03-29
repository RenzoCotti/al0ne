package com.al0ne.ConcreteEntities.Statuses;

import com.al0ne.AbstractEntities.Abstract.Status;
import com.al0ne.AbstractEntities.Abstract.WorldCharacter;
import com.al0ne.ConcreteEntities.Statuses.ConcreteStatuses.Dehydration;
import com.al0ne.ConcreteEntities.Statuses.ConcreteStatuses.Starvation;

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
    public boolean resolveStatus(WorldCharacter character) {
        if(!character.hasStatus(finalStatus)){
            duration --;

            if(duration <= 5){

                Status finalStatus;
                switch(this.finalStatus){
                    case "starvation":
                        finalStatus = new Starvation();
                        break;
                    case "dehydration":
                        finalStatus = new Dehydration();
                        break;
                    default:
                            //error
                        System.out.println("error in resolving status: "+this.finalStatus);
                        finalStatus = new Dehydration();
                }
                if(duration != 0){
                    printToLog(finalStatus.getOnApply());
                    return false;
                } else{
                    printToLog(finalStatus.getOnApply());
                    this.toApply.clear();
                    this.toApply.add(finalStatus);
                    duration = clock;
                    return true;
                }
            }
            return false;
        } else{
            return false;
        }

    }
}
