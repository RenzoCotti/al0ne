package com.al0ne.Items.Props;

import com.al0ne.Items.Prop;
import com.al0ne.Room;

/**
 * Created by BMW on 18/02/2017.
 */
public class HideItem extends Prop{

//    private String useMessage;
    private Prop hidden;

    public HideItem(String id, String name, String description, String after, String useMessage, Prop hidden) {
        super(id, name, description, after);
//        this.useMessage=useMessage;
        this.hidden=hidden;
    }

    public HideItem(String id, String name, String description, String useMessage, Prop hidden) {
        super(id, name, description);
//        this.useMessage=useMessage;
        this.hidden=hidden;
    }

    @Override
    public boolean used(Room currentRoom){
//        System.out.println(useMessage);
        currentRoom.getProps().remove(name);
//        for (Prop a : currentRoom.getProps().values()){
//            System.out.println(a.getName());
//        }
        currentRoom.getProps().put(hidden.getID(), hidden);
        return true;
    }
}
