package com.al0ne.Entities.Items.Props;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Pairs.Pair;
import com.al0ne.Behaviours.Prop;
import com.al0ne.Behaviours.Room;

/**
 * Created by BMW on 18/02/2017.
 */
public class HideProp extends Prop{

//    private String useMessage;
    private Prop hidden;

    public HideProp(String name, String description, String shortDescription, String after, Material m, Prop hidden) {
        super(name, description, shortDescription, after, m);
        this.hidden=hidden;
    }
    public HideProp(String name, String description, String shortDescription, Material m, Prop hidden) {
        super(name, description, shortDescription, description, m);
        this.hidden=hidden;
    }

    //this object when used removes itself from the room and adds the item it's hiding in the room
    @Override
    public String used(Player player){
        Room currentRoom = player.getCurrentRoom();
        currentRoom.getEntities().remove(name);
        currentRoom.getEntities().put(hidden.getID(), new Pair(hidden, 1));
        return "";
    }
}
