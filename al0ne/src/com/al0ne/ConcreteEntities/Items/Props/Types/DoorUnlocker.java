package com.al0ne.ConcreteEntities.Items.Props.Types;

import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.Prop;
import com.al0ne.AbstractEntities.Room;

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
    public boolean used(Player player) {
        Room currentRoom = player.getCurrentRoom();
        currentRoom.unlockDirection(doorToUnlock);
        return true;
    }
}
