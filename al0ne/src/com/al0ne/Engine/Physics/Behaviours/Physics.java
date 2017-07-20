package com.al0ne.Engine.Physics.Behaviours;

import com.al0ne.Engine.Physics.Behaviour;

import java.io.Serializable;
import java.util.HashMap;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 20/07/2017.
 */
public class Physics implements Serializable{

    public static HashMap<Integer, Integer> isInteractedWith(Behaviour first, Behaviour second) {

        HashMap<Integer, Integer> result = propertyCheck(first, second);

        if(result == null){
            result = propertyCheck(second, first);
        }

        return result;
    }

    private static HashMap<Integer, Integer> propertyCheck(Behaviour first, Behaviour second){

        String firstName = first.getName();
        String secondName = second.getName();
        HashMap<Integer, Integer> result = new HashMap<>();

        switch (firstName){
            //case iron
            case "iron":
                switch (secondName){
                    case "water":
                        printToLog("It rusts a bit");
                        result.put(9, -15);
                        return result;
                    case "acid":
                        printToLog("It corrodes greatly!");
                        result.put(9, -40);
                        return result;
                    case "food":
                        printToLog("Debug!");
                        result.put(4, 0);
                        result.put(9, -40);
                        return result;
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
                        printToLog("The paper catches fire!");
                        result.put(4, 0);
                        return result;
                }

            case "lockeddoor":
                switch (secondName){
                    case "key":
                        LockedDoorBehaviour door = (LockedDoorBehaviour) first;
                        String doorName = door.getDoorName();
                        String direction = door.getDirection();

                        KeyBehaviour key = (KeyBehaviour) second;

                        return result;
                }
        }
        return null;
    }
}
