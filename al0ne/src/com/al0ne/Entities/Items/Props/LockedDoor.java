package com.al0ne.Entities.Items.Props;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Player;
import com.al0ne.Entities.Items.Behaviours.Wearable.Weapon;
import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.Prop;
import com.al0ne.Behaviours.Room;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 02/02/2017.
 */
public class LockedDoor extends Prop {
    public LockedDoor(String name, String description, String shortDescription, String after, Material m, String key) {
        super(name, description, shortDescription, after, m);
        this.requiresItem=key;
    }

    public LockedDoor(String name, String key) {
        super(name, "A sturdy wooden door", "a wooden door", Material.WOOD);
        this.requiresItem=key;
    }

    @Override
    public boolean usedWith(Item item, Room currentRoom, Player player){
        if(item.hasProperty("key")){
//            printToLog(requiresItem);
            if (item.getID().equals(requiresItem)){
                printToLog("The "+name+" is now open");
                currentRoom.unlockDirection(ID);
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
    public int used(Room currentRoom, Player player) {
        printToLog("The door is locked.");
        return 0;
    }
}
