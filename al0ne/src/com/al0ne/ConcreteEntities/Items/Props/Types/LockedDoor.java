package com.al0ne.ConcreteEntities.Items.Props.Types;

import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.Engine.Physics.Behaviours.LockedDoorBehaviour;
import com.al0ne.AbstractEntities.Room;
import com.al0ne.Engine.Utility.Utility;
import com.al0ne.ConcreteEntities.Items.ConcreteItems.Key;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 02/02/2017.
 */
public class LockedDoor extends Door {
    public LockedDoor(String name, String description, String after, Material m, Room r, String direction) {
        super(name, description, after, m);
        addBehaviour(new LockedDoorBehaviour(getID(), direction));
        r.lockDirection(direction, getID());
//        this.requiresItem=key;
    }

    public LockedDoor(String name, String description, Material m, Room r, String direction) {
        super(name, description, Utility.getArticle(name)+" "+name, m);
        addBehaviour(new LockedDoorBehaviour(getID(), direction));
        r.lockDirection(direction, getID());
    }

    public LockedDoor(Material m, Room r, String direction) {
        super("Locked door", "A locked door.", "A locked door", m);
        addBehaviour(new LockedDoorBehaviour(getID(), direction));
        r.lockDirection(direction, getID());
    }

//    @Override
//    public void usedWith(Interactable item, Room currentRoom, Player player){
//        if(item.hasProperty("key")){
////            printToLog(requiresItem);
//            if (item.getID().equals(requiresItem)){
//                printToLog("The "+name+" is now open");
//                currentRoom.unlockDirection(ID);
//                active=true;
//            } else{
//                printToLog("The key doesn't seem to fit.");
//            }
//        } else if(( item.hasProperty("sharp") || item.hasProperty("blunt"))){
//            Weapon temp = (Weapon) item;
//            if (temp.getDamage() > 5){
//                printToLog("You break the door open");
//                active = true;
//            } else {
//                printToLog("You try to break the door, but to no avail.");
//            }
//        }
//        else{
//            printToLog("The "+ item.getName()+" doesn't seem to fit in the keyhole.");
//        }
//    }

    public Key generateKey(String keyName){
        return new Key(keyName, getID());
    }

    @Override
    public boolean used(Player player) {
        printToLog("The door is locked.");
        return true;
    }
}
