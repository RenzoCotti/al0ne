package com.al0ne.Engine;

import com.al0ne.Items.Items.Apple;
import com.al0ne.Items.Items.Key;
import com.al0ne.Items.Items.Knife;
import com.al0ne.Items.Items.Potion;
import com.al0ne.Items.LockedDoor;
import com.al0ne.Room;
import java.util.HashMap;

public class CreateRooms {

    public static HashMap<String, Room>  create() {

        HashMap<String, Room> rooms = new HashMap<>();

        Room cave1 = new Room("You are in a pretty generic-looking cave. It feels pretty damp. You can see passageway east.", "Cave 1");
        cave1.addExit("east","cave2");
//        cave1.addItem(new Potion());
        cave1.addItem(new Key("ordinary key"));
        cave1.addProp(new LockedDoor("generic door","A sturdy wooden door blocks the passage to the east.","A sturdy wooden door lies open to the east.","ordinary key"));
        cave1.lockDirection("generic door", "east");

        rooms.put("cave1", cave1);


        Room cave2 = new Room("The rocks are crumbly here. A rope is secured tightly.", "Cave 2");
        cave2.addExit("west","cave1");
        cave2.addItem(new Knife());
        cave2.addItem(new Apple());

        rooms.put("cave2", cave2);


        return rooms;

    }
}
