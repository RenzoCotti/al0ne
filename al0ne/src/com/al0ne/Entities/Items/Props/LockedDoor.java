package com.al0ne.Entities.Items.Props;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Player;
import com.al0ne.Entities.Items.Behaviours.Weapons.Weapon;
import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.Prop;
import com.al0ne.Behaviours.Room;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 02/02/2017.
 */
public class LockedDoor extends Door {
    public LockedDoor(String id, String name, String description, String after, Material m, String key) {
        super(id, name, description, after, m);
        this.requiresItem=key;
    }

    public LockedDoor(String id, String name, String key) {
        super(id, name, "A sturdy wooden door", "a wooden door", Material.WOOD);
        this.requiresItem=key;
    }

    @Override
    public int usedWith(Item item, Room currentRoom, Player player){
        if(item.hasProperty("key")){
//            printToLog(requiresItem);
            if (item.getID().equals(requiresItem)){
                printToLog("The "+name+" is now open");
                currentRoom.unlockDirection(ID);
                active=true;
            } else{
                printToLog("The key doesn't seem to fit.");
            }
        } else if(( item.hasProperty("sharp") || item.hasProperty("blunt"))){
            Weapon temp = (Weapon) item;
            if (temp.getDamage() > 5){
                printToLog("You break the door open");
                active = true;
            } else {
                printToLog("You try to break the door, but to no avail.");
            }
        }
        else{
            printToLog("The "+ item.getName()+" doesn't seem to fit in the keyhole.");
        }
        return 2;
    }

    @Override
    public int used(Room currentRoom, Player player) {
        printToLog("The door is locked.");
        return 2;
    }
}
