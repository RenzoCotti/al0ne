package com.al0ne.Entities.Items.ConcreteItems;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Prop;
import com.al0ne.Behaviours.Room;

/**
 * Created by BMW on 07/05/2017.
 */
public class DoorUnlocker extends Prop{
    private String doorToUnlock;
    public DoorUnlocker(String name, String description, String shortDescription, String after, Material m,
                        String doorToUnlock) {
        super(name, description, shortDescription, after, m);
        this.doorToUnlock = doorToUnlock;
    }

    @Override
    public int used(Room currentRoom, Player player) {
        currentRoom.unlockDirection(doorToUnlock);
        return 1;
    }
}
