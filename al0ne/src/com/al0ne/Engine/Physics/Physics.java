package com.al0ne.Engine.Physics;

import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.Pairs.InteractionResult;
import com.al0ne.Behaviours.Pairs.Pair;
import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Room;
import com.al0ne.Behaviours.abstractEntities.Interactable;
import com.al0ne.Engine.Physics.Behaviours.KeyBehaviour;
import com.al0ne.Engine.Physics.Behaviours.LockedDoorBehaviour;
import com.al0ne.Engine.Physics.Behaviours.MaterialBehaviours.RequiresBatteryBehaviour;
import com.al0ne.Entities.Items.Types.ChargeItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 20/07/2017.
 */
public class Physics implements Serializable{

    public static boolean interactionBetween(Player player, Interactable first, Interactable second) {

        HashMap<Integer, Object> result = null;
        InteractionResult ir = null;
        for (Behaviour b: first.getBehaviours()){
            for(Behaviour b1: second.getBehaviours()){
                result = Physics.propertyCheck(first, b, second, b1);

                if(result == null){
                    result = Physics.propertyCheck(second, b1, first, b);
                } else {
                    ir = new InteractionResult(result, first, second, b.toAdd);
                    break;
                }

                if(result != null) {
                    ir = new InteractionResult(result, second, first, b1.toAdd);
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

        Physics.useResult(ir, player);

        return true;
    }

    private static HashMap<Integer, Object> propertyCheck(Interactable first, Behaviour b, Interactable second, Behaviour b1){

        String firstName = b.getName();
        String secondName = b1.getName();
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
                        printToLog("The "+first.getName()+" rusts a bit");
                        result.put(9, -15);
                        return result;
                    case "acid":
                        printToLog("The "+first.getName()+" corrodes greatly!");
                        result.put(9, -40);
                        return result;
                }

            case "paper":
                switch (secondName){
                    case "fire":
                        printToLog("The "+first.getName()+" catches fire!");
                        result.put(4, 0);
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
                            result.put(6, doorName);
                            return result;
                        }
                }
            case "requiresbattery":
                String batteryType = ((RequiresBatteryBehaviour) b).getBatteryType();
                if(batteryType.equals(secondName)){
                    result.put(4, second.getID());
                    result.put(7, first.getID());
                    return result;
                }
        }
        return null;
    }



    public static void useResult(InteractionResult ir, Player player){


        Room currentRoom = player.getCurrentRoom();
        HashMap<Integer, Object> result = ir.getResult();
        ArrayList<Pair> toAdd = ir.getToAdd();

        Interactable first = ir.getFirst();
        Interactable second = ir.getSecond();


        for(Integer i : result.keySet()){
            switch (i){

                case 1:
                    //success, no need to print
                    break;
                case 3:
                    //tries to add to inventory, if can't add to room
                    for (Pair p: toAdd){
                        currentRoom.addEntity(p.getEntity(), p.getCount());
                    }
                    break;

//                case 2:
//                    //add to room
//                    Pair pair1 = interacted.getEntity().getPair();
//                    Entity entity = pair1.getEntity();
//                    int count = pair1.getCount();
//                    currentRoom.addEntity(entity, count);
//                    break;
                case 4:
                    //remove this
                    if(player.hasItemInInventory((String)result.get(i))){
                        Item item = (Item) player.getInventory().get(result.get(i)).getEntity();
                        player.removeOneItem(item);
                    } else{
                        currentRoom.getEntities().remove(result.get(i));
                    }
                    break;
                case 6:
                    currentRoom.unlockDirection((String)result.get(i));
                    break;
                case 7:
                    if(second == null || first == null){
                        System.out.println("probably a quest tried to recharge an item");
                        break;
                    }
                    //refill
                    ((ChargeItem) first).refill(player, second);
                    break;
                case 8:
                    //modify health
                    player.modifyHealth((Integer)result.get(i));
                    break;
                case 9:
                    if(second == null){
                        System.out.println("probably a quest tried to change an object's integrity");
                        break;
                    }
                    //modify integrity
                    ir.getFirst().modifyIntegrity((Integer) result.get(i));
                    break;

                default:
                    System.out.println("ERROR: no behaviour code found");
                    break;

            }
        }
    }

}
