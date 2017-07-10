package com.al0ne.Entities.Items.Props;

import com.al0ne.Behaviours.Enums.Command;
import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.Prop;
import com.al0ne.Behaviours.Room;

public class Door extends Prop {

    public Door(String id, String name) {
        super(id, name, "a sturdy door", "a sturdy door", "The door is now open.", Material.WOOD);
        addCommand(Command.OPEN);
    }

    public Door(String id, String name, String description, String shortDescription, Material m) {
        super(id, name, description, shortDescription, "The "+name+" is now open.", m);
        addCommand(Command.OPEN);
    }


    @Override
    public int used(Room currentRoom, Player player){
        currentRoom.unlockDirection(ID);

        active=true;
        return 1;
    }
}
