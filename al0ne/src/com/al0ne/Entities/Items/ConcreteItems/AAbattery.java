package com.al0ne.Entities.Items.ConcreteItems;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Room;
import com.al0ne.Engine.Physics.Behaviours.BatteryBehaviour;

public class AAbattery extends Item{

    public AAbattery() {
        super("aabattery", "AA battery", "An AA battery. Hopefully it's charged",
                0.05, Size.MICRO, Material.IRON, null);
        addProperty(new BatteryBehaviour("aabattery"));
    }

    @Override
    public int used(Room currentRoom, Player player) {
        return 0;
    }
}
