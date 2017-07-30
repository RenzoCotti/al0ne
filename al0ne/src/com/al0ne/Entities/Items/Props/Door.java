package com.al0ne.Entities.Items.Props;

import com.al0ne.Behaviours.Enums.Command;
import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Prop;
import com.al0ne.Behaviours.Room;

public class Door extends Prop {

    public Door(String name) {
        super(name, "a sturdy door", "a sturdy door", "The door is now open.", Material.WOOD);
        addCommand(Command.OPEN);
    }

    public Door(String name, String description, String shortDescription, Material m) {
        super(name, description, shortDescription, "The "+name+" is now open.", m);
        addCommand(Command.OPEN);
    }


    @Override
    public String used(Player player){
        Room currentRoom = player.getCurrentRoom();
        currentRoom.unlockDirection(ID);

        active=true;
        return "";
    }
}
