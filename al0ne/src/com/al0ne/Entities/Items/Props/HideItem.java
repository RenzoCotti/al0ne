package com.al0ne.Entities.Items.Props;

import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Pairs.Pair;
import com.al0ne.Behaviours.Prop;
import com.al0ne.Behaviours.Room;

/**
 * Created by BMW on 18/02/2017.
 */
public class HideItem extends Prop{

//    private String useMessage;
    private Prop hidden;

    public HideItem(String id, String name, String description, String shortDescription, String after, Prop hidden) {
        super(id, name, description, shortDescription, after);
        this.hidden=hidden;
    }

    public HideItem(String id, String name, String description, String shortDescription, Prop hidden) {
        super(id, name, description, shortDescription);
        this.hidden=hidden;
    }

    @Override
    public int used(Room currentRoom, Player player){
        currentRoom.getEntities().remove(name);
        currentRoom.getEntities().put(hidden.getID(), new Pair(hidden, 1));
        return 1;
    }
}