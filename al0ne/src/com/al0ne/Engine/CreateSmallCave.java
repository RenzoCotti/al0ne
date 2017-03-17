package com.al0ne.Engine;

import com.al0ne.Items.Items.Apple;
import com.al0ne.Items.Items.Beer;
import com.al0ne.Items.Items.Knife;
import com.al0ne.Entities.NPC;
import com.al0ne.Items.Props.*;
import com.al0ne.Room;
import com.al0ne.Entities.Enemies.Wolf;

import java.util.HashMap;

public class CreateSmallCave {

    private static HashMap<String, Room> rooms = new HashMap<>();

    public static HashMap<String, Room>  create() {



        Room cave1 = new Room("cave1", "Cave 1", "You are in a pretty generic-looking cave. It feels pretty damp. You can see passageway east.");
        cave1.addExit("east","cave2");
//        cave1.addItem(new Potion());
//        cave1.addItem(new Key("cave1key","Ordinary Key"));
//        cave1.addItem(new Key("cave2key","Next Room Key"));
//        cave1.addProp(new LockedDoor("cave1door", "Generic Door","A sturdy wooden door blocks the passage to the east.","A sturdy wooden door lies open to the east.","cave1key"));
        cave1.addEntity(new Door("cave1door", "Generic Door"));
        cave1.lockDirection("east", "cave1door");

        cave1.addEntity(new MoneyTree());

        CreateSmallCave.putRoom(cave1);


        Room cave2 = new Room("cave2", "Cave 2", "The rocks are crumbly here.");
        cave2.addExit("west","cave1");
        cave2.addExit("north","cave4");
        cave2.addExit("south","cave3");
        cave2.addExit("east","bossroom");
        cave2.addItem(new Knife());
        cave2.addItem(new Apple());

        cave2.addEntity(new CuttableRope());

        CreateSmallCave.putRoom(cave2);

        Room cave3 = new Room("cave3", "Cave 3", "Nothing worth of notice here.");
        cave3.addExit("north", "cave2");
        cave3.addExit("down", "cellar");
        Door trapdoor1 = new Door("trapdoor", "Trapdoor","You can see a trapdoor on the floor.","The trapdoor is open.");
        HideItem rug = new HideItem("rug", "Rug", "A ragged rug. Ruggity rug.", "The rug is now out of the way.", trapdoor1);
        rug.addCommand("move");
        cave3.addEntity(rug);
        cave3.lockDirection("down", "trapdoor");

        CreateSmallCave.putRoom(cave3);

        Room cellar = new Room("cellar", "Cellar", "Very damp and filled to the brim with bottles of beer! :D");
        cellar.addExit("up", "cave3");
        cellar.addItem(new Beer());
        cellar.addItem(new Beer());
        cellar.addItem(new Beer());
        cellar.addItem(new Beer());

        CreateSmallCave.putRoom(cellar);

        Room cave4 = new Room("cave4", "Shop Room", "Lots of items are in this room, all with a price tag on.");
        cave4.addExit("south", "cave2");
        NPC emon = new NPC("emon", "Emon", "A handy man. Probably fixes small keys.", "Hi, i'm eamon and i fix small keys");
        emon.addSubject("keys", "Yup, I fix small keys.");
        emon.addSubject("beer", "I love beer!");
        cave4.addEntity(emon);

        CreateSmallCave.putRoom(cave4);

        Room bossRoom = new Room("bossroom", "Boss Room", "Lots of bones cover the ground. You shiver.");
        bossRoom.addExit("west", "cave2");
        Wolf boss = new Wolf();
        bossRoom.addEntity(boss);

        CreateSmallCave.putRoom(bossRoom);

        return rooms;

    }

    public static void putRoom(Room room){
        rooms.put(room.getID(), room);
    }
}
