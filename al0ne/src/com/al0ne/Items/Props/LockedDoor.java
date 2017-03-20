package com.al0ne.Items.Props;

import com.al0ne.Entities.Player;
import com.al0ne.Items.Behaviours.Weapon;
import com.al0ne.Items.Item;
import com.al0ne.Items.Prop;
import com.al0ne.Room;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 02/02/2017.
 */
public class LockedDoor extends Prop {
    public LockedDoor(String id, String name, String description, String shortDescription, String after, String key) {
        super(id, name, description, shortDescription, after);
        this.requiresItem=key;
    }

    public LockedDoor(String id, String name, String key) {
        super(id, name, "A sturdy wooden door", "wooden door");
        this.requiresItem=key;
    }

    @Override
    public boolean usedWith(Item item, Room currentRoom){
        if(item.hasProperty("key")){
//            printToLog(requiresItem);
            if (item.getID().equals(requiresItem)){
                printToLog("The "+name+" is now open");
                active=true;
                return true;
            } else{
                printToLog("The key doesn't seem to fit.");
                return false;
            }
        } else if(( item.hasProperty("sharp") || item.hasProperty("blunt"))){
            Weapon temp = (Weapon) item;
            if (temp.getDamage() > 5){
                printToLog("You break the door open");
                active = true;
                return true;
            } else {
                printToLog("You try to break the door, but to no avail.");
                return false;
            }
        }
        else{
            printToLog("The "+ item.getName()+" doesn't seem to fit in the keyhole.");
            return false;
        }
    }

    @Override
    public boolean used(Room currentRoom, Player player) {
        printToLog("The door is locked.");
        return false;
    }
}
