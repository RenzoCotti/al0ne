package com.al0ne.Engine.Physics;

import com.al0ne.Engine.Physics.Behaviour;
import com.al0ne.Engine.Physics.Behaviours.KeyBehaviour;
import com.al0ne.Engine.Physics.Behaviours.LockedDoorBehaviour;

import java.io.Serializable;
import java.util.HashMap;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 20/07/2017.
 */
public class Physics implements Serializable{

    public static HashMap<Integer, Object> isInteractedWith(String name, Behaviour first, Behaviour second) {

        HashMap<Integer, Object> result = propertyCheck(name, first, second);

        if(result == null){
            result = propertyCheck(name, second, first);
        }

        return result;
    }

    private static HashMap<Integer, Object> propertyCheck(String name, Behaviour first, Behaviour second){

        String firstName = first.getName();
        String secondName = second.getName();
        HashMap<Integer, Object> result = new HashMap<>();


    /*return codes:
    * 0: didn't work
    * 1: it worked, no print
    * ------------------------------2: add entity to room
    * 3: add item to inventory
    * 4: remove this from where it was
    * 5: remove other from where it was
    * 6: unlock door
    * 7: refill charge
    * 8: modify health
    * 9: modify integrity
    * */
        switch (firstName){
            //case iron
            case "iron":
                switch (secondName){
                    case "water":
                        printToLog("The "+name+" rusts a bit");
                        result.put(9, -15);
                        return result;
                    case "acid":
                        printToLog("The "+name+" corrodes greatly!");
                        result.put(9, -40);
                        return result;
//                    case "food":
//                        printToLog("Debug!");
//                        result.put(4, 0);
//                        result.put(9, -40);
//                        return result;
                }
            case "water":
                switch (secondName){
//                    case "iron":
//                        printToLog("It rusts a bit");
//                        result.put(4, 0);
//                        return result;
                }

            case "paper":
                switch (secondName){
                    case "fire":
                        printToLog("The "+name+" catches fire!");
                        result.put(4, 0);
                        return result;
                }

            case "lockeddoor":
                switch (secondName){
                    case "key":
                        LockedDoorBehaviour door = (LockedDoorBehaviour) first;
                        String doorName = door.getDoorName();
//                        String direction = door.getDirection();

                        KeyBehaviour key = (KeyBehaviour) second;

                        String doorUnlocked = key.getDoorUnlocked();

                        if(doorName.equals(doorUnlocked)){
                            printToLog("You unlock the "+name);
                            result.put(6, doorName);
                            return result;
                        }
                }
        }
        return null;
    }
}
