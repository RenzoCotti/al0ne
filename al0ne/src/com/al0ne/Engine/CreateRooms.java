package com.al0ne.Engine;

import com.al0ne.Items.Behaviours.Food;
import com.al0ne.Items.Items.Apple;
import com.al0ne.Items.Items.Beer;
import com.al0ne.Items.Items.Key;
import com.al0ne.Items.Items.Knife;
import com.al0ne.Items.Props.CuttableRope;
import com.al0ne.Items.Props.Door;
import com.al0ne.Items.Props.HideItem;
import com.al0ne.Items.Props.LockedDoor;
import com.al0ne.Room;
import java.util.HashMap;

public class CreateRooms {

    private static HashMap<String, Room> rooms = new HashMap<>();

    public static HashMap<String, Room>  create() {



        Room cave1 = new Room("cave1", "Cave 1", "You are in a pretty generic-looking cave. It feels pretty damp. You can see passageway east.");
        cave1.addExit("east","cave2");
//        cave1.addItem(new Potion());
        cave1.addItem(new Key("cave1key","Ordinary Key"));
//        cave1.addItem(new Key("cave2key","Next Room Key"));
        cave1.addProp(new LockedDoor("cave1door", "Generic Door","A sturdy wooden door blocks the passage to the east.","A sturdy wooden door lies open to the east.","cave1key"));
//        cave1.lockDirection("east", "cave1door");

        CreateRooms.putRoom(cave1);


        Room cave2 = new Room("cave2", "Cave 2", "The rocks are crumbly here.");
        cave2.addExit("west","cave1");
        cave2.addExit("south","cave3");
        cave2.addItem(new Knife());
        cave2.addItem(new Apple());

        cave2.addProp(new CuttableRope());

        CreateRooms.putRoom(cave2);

        Room cave3 = new Room("cave3", "Cave 3", "Nothing worth of notice here.");
        cave3.addExit("north", "cave2");
        cave3.addExit("down", "cellar");
        Door trapdoor1 = new Door("trapdoor", "Trapdoor","You can see a trapdoor on the floor.","The trapdoor is open.", "You open the trapdoor");
        cave3.addProp(new HideItem("rug", "Rug", "A ragged rug. Ruggity rug.", "The rug is now out of the way.", "You move the rug. You find a trapdoor underneath.", trapdoor1));
        cave3.lockDirection("down", "trapdoor");

        CreateRooms.putRoom(cave3);

        Room cellar = new Room("cellar", "Cellar", "Very damp and filled to the brim with bottles of beer! :D");
        cellar.addExit("up", "cave3");
        cellar.addItem(new Beer());
        cellar.addItem(new Beer());
        cellar.addItem(new Beer());
        cellar.addItem(new Beer());

        CreateRooms.putRoom(cellar);

        return rooms;

    }

    public static void putRoom(Room room){
        rooms.put(room.getId(), room);
    }
}
