package com.al0ne.Engine.Physics;

import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.Abstract.Interactable;
import com.al0ne.Engine.Physics.Behaviours.KeyBehaviour;
import com.al0ne.Engine.Physics.Behaviours.LockedDoorBehaviour;
import com.al0ne.Engine.Physics.Behaviours.MaterialBehaviours.RequiresBatteryBehaviour;
import com.al0ne.Engine.Physics.InteractionResult.*;
import com.al0ne.ConcreteEntities.Items.Types.ChargeItem;

import java.io.Serializable;
import java.util.ArrayList;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 20/07/2017.
 */
public class Physics implements Serializable{

    public static boolean interactionBetween(Player player, Interactable first, Interactable second) {

        ArrayList<InteractionBehaviour> result = null;
        //we first check interaction with first and second,
        for (Behaviour b: first.getBehaviours()){
            //and then of second with first
            for(Behaviour b1: second.getBehaviours()){
                result = Physics.propertyCheck(first, b, second, b1);

                //if 1 on 2 didnt do anything, do 2 on 1
                if(result == null){
                    result = Physics.propertyCheck(second, b1, first, b);
                } else {
                    //otherwise, something happened, exit this and process the result
                    break;
                }

                if(result != null) {
                    break;
                }
            }
            if(result != null){
                break;
            }
        }

        if(result == null){
            printToLog("The "+first.getName()+" isn't effective");
            return true;
        }

        Physics.useResult(result, player);

        return true;
    }

    private static ArrayList<InteractionBehaviour>
    propertyCheck(Interactable first, Behaviour b, Interactable second, Behaviour b1){

        String firstName = b.getName();
        String secondName = b1.getName();
        ArrayList<InteractionBehaviour> result = new ArrayList<>();


    /*return codes:
    * 0: didn't work
    * 1: it worked, no print
    * ------------------------------2: add entity to room
    * 3: add item to inventory
    * 4: remove this from where it was
    * 5: remove other from where it was
    * 6: unlock door
    * 7: checkRefill charge
    * 8: modify health
    * 9: modify integrity
    * */
        switch (firstName){
            //case iron
            case "iron":
                switch (secondName){
                    case "water":
                        printToLog("The "+first.getName()+" rusts a bit");
                        result.add(new InteractionIntegrity(first, -15));
                        return result;
                    case "acid":
                        printToLog("The "+first.getName()+" corrodes greatly!");
                        result.add(new InteractionIntegrity(first, -40));
                        return result;
                }

            case "paper":
                switch (secondName){
                    case "fire":
                        printToLog("The "+first.getName()+" catches fire!");
                        result.add(new InteractionDestroy(first));
                        return result;
                }

            case "lockeddoor":
                switch (secondName){
                    case "key":
                        LockedDoorBehaviour door = (LockedDoorBehaviour) b;
                        String doorName = door.getDoorName();
//                        String direction = door.getDirection();

                        KeyBehaviour key = (KeyBehaviour) b1;

                        String doorUnlocked = key.getDoorUnlocked();

                        if(doorName.equals(doorUnlocked)){
                            printToLog("You unlock the "+first.getName());
                            result.add(new InteractionUnlock(doorName));
                            return result;
                        }
                }
            case "requiresbattery":
                String batteryType = ((RequiresBatteryBehaviour) b).getBatteryType();
                if(batteryType.equals(secondName)){
                    result.add(new InteractionDestroy(second));
                    result.add(new InteractionRefill((ChargeItem) first));
                    printToLog("You recharge the "+first.getName());
                    return result;
                }
        }
        return null;
    }



    public static void useResult(ArrayList<InteractionBehaviour> result, Player player){

        for(InteractionBehaviour b : result){
            b.interactionEffect(player);
        }

    }

}
