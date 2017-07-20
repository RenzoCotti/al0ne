package com.al0ne.Engine.Physics.Behaviours;

import com.al0ne.Engine.Physics.Behaviour;

import java.io.Serializable;
import java.util.HashMap;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 20/07/2017.
 */
public class Physics implements Serializable{

    public static HashMap<Integer, Integer> isInteractedWith(String first, String second) {
        HashMap<Integer, Integer> result = propertyCheck(first, second);

        if(result == null){
            result = propertyCheck(second, first);
        }

        return result;
    }

    private static HashMap<Integer, Integer> propertyCheck(String first, String second){
        HashMap<Integer, Integer> result = new HashMap<>();

        switch (first){
            //case iron
            case "iron":
                switch (second){
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
                switch (second){
//                    case "iron":
//                        printToLog("It rusts a bit");
//                        result.put(4, 0);
//                        return result;
                }

            case "paper":
                switch (second){
                    case "fire":
                        printToLog("The paper catches fire!");
                        result.put(4, 0);
                        return result;
                }
        }
        return null;
    }
}
