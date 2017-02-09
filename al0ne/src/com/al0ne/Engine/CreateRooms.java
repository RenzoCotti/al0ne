package com.al0ne.Engine;

import com.al0ne.Items.Items.Apple;
import com.al0ne.Items.Items.Key;
import com.al0ne.Items.Items.Knife;
import com.al0ne.Items.LockedDoor;
import com.al0ne.Room;
import java.util.HashMap;

public class CreateRooms {

    private static HashMap<String, Room> rooms = new HashMap<>();

    public static HashMap<String, Room>  create() {



        Room cave1 = new Room("cave1", "Cave 1", "You are in a pretty generic-looking cave. It feels pretty damp. You can see passageway east.");
        cave1.addExit("east","cave2");
//        cave1.addItem(new Potion());
        cave1.addItem(new Key("cave1key","Ordinary Key"));
        cave1.addProp(new LockedDoor("door", "Generic Door","A sturdy wooden door blocks the passage to the east.","A sturdy wooden door lies open to the east.","cave1key"));
        cave1.lockDirection("door", "east");

        CreateRooms.putRoom(cave1);


        Room cave2 = new Room("cave2", "Cave 2", "The rocks are crumbly here. A rope is secured tightly.");
        cave2.addExit("west","cave1");
        cave2.addItem(new Knife());
        cave2.addItem(new Apple());

        CreateRooms.putRoom(cave2);


        return rooms;

    }

    public static void putRoom(Room room){
        rooms.put(room.getId(), room);
    }
}
