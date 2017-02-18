package com.al0ne.Items.Props;

import com.al0ne.Items.Item;
import com.al0ne.Items.Prop;
import com.al0ne.Room;

/**
 * Created by BMW on 02/02/2017.
 */
public class Door extends Prop {

    private String usedMessage;
    public Door() {
        super("door", "Door", "A sturdy wooden door");
        this.usedMessage="You open the door";
    }

    public Door(String id, String name, String description, String after, String usedMessage) {
        super(id, name, description, after);
        this.usedMessage=usedMessage;
    }

    @Override
    public boolean usedWith(Item item) {
        return false;
    }

    @Override
    public boolean used(Room currentRoom){
        System.out.println(usedMessage);
        currentRoom.unlockDirection(id);

        System.out.println(id);


        active=true;
        return true;
    }
}
