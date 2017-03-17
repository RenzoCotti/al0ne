package com.al0ne.Items.Props;

import com.al0ne.Entities.Player;
import com.al0ne.Items.Pair;
import com.al0ne.Items.Prop;
import com.al0ne.Room;

/**
 * Created by BMW on 18/02/2017.
 */
public class HideItem extends Prop{

//    private String useMessage;
    private Prop hidden;

    public HideItem(String id, String name, String description, String after, Prop hidden) {
        super(id, name, description, after);
        this.hidden=hidden;
    }

    public HideItem(String id, String name, String description, Prop hidden) {
        super(id, name, description);
        this.hidden=hidden;
    }

    @Override
    public boolean used(Room currentRoom, Player player){
        currentRoom.getEntities().remove(name);
        currentRoom.getEntities().put(hidden.getID(), new Pair(hidden, 1));
        return true;
    }
}
