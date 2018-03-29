package com.al0ne.ConcreteEntities.Items.Props.Types;

import com.al0ne.AbstractEntities.Enums.Command;
import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.Prop;
import com.al0ne.AbstractEntities.Room;

public class Door extends Prop {

    public Door(String name, Material m) {
        super(name, "A sturdy door.", "a sturdy door", "The door is now open.", m);
        addCommand(Command.OPEN);
    }

    public Door(String name, String description, String shortDescription, Material m) {
        super(name, description, shortDescription, "The "+name+" is now open.", m);
        addCommand(Command.OPEN);
    }


    @Override
    public boolean used(Player player){
        Room currentRoom = player.getCurrentRoom();
        currentRoom.unlockDirection(ID);

        active=true;
        return true;
    }
}
