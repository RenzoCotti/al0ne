package com.al0ne.Engine;

import com.al0ne.Items.Items.Apple;
import com.al0ne.Items.Items.Knife;
import com.al0ne.Items.Items.Mushroom;
import com.al0ne.Items.Prop;
import com.al0ne.Entities.NPC;
import com.al0ne.Room;
import com.al0ne.Entities.NPCs.Shopkeeper;
import com.al0ne.Entities.Enemies.Wolf;

import java.util.HashMap;

/**
 * Created by BMW on 14/03/2017.
 */
public class CreateAlpha {
    private static HashMap<String, Room> rooms = new HashMap<>();

    public static HashMap<String, Room>  create() {



        Room startRoom = new Room("startroom", "Generic Room", "You are in a pretty generic-looking cave. It feels pretty damp.");
        startRoom.addExit("north","daggerroom");
        startRoom.addExit("south","mushroomroom");

        CreateAlpha.putRoom(startRoom);

        Room ladderRoom = new Room("ladderroom", "Dusty Room", "It's very dusty in here.");
        ladderRoom.addProp(new Prop("ladder", "Ladder", "a wooden ladder"));
        ladderRoom.addExit("up", "emonroom");
        CreateAlpha.putRoom(ladderRoom);

        Room daggerRoom = new Room("daggerroom", "Empty room", "The room is completely empty.");
        daggerRoom.addItem(new Knife());
        daggerRoom.addExit("south", "startroom");
        daggerRoom.addExit("east", "wolfroom");
        CreateAlpha.putRoom(daggerRoom);

        Room emonRoom = new Room("emonroom", "Attic", "You're in a wooden attic.");
        NPC emon = new NPC("emon", "Emon", "A handy man. Probably fixes small keys.");
        emonRoom.addExit("down", "ladderRoom");
        emon.addSubject("keys", "Yup, I fix small keys.");
        emon.addSubject("beer", "I love beer!");
        emonRoom.addNPC(emon);
        CreateAlpha.putRoom(emonRoom);

        Room mushRoom = new Room("mushroomroom", "Mushy Room", "The air is very damp");
        mushRoom.addItem(new Mushroom());
        mushRoom.addExit("north", "startroom");
        mushRoom.addExit("east", "cavernroom");
        CreateAlpha.putRoom(mushRoom);

        Room wolfRoom = new Room("wolfroom", "Wolf Room", "You see some bones scattered on the ground.");
        wolfRoom.addEnemy(new Wolf());
        wolfRoom.addExit("west", "daggerroom");
        wolfRoom.addExit("north", "shoproom");
        wolfRoom.addExit("down", "priestroom");
        CreateAlpha.putRoom(wolfRoom);

        Room shopRoom = new Room("shoproom", "Shop", "You see several items neatly disposed on a table");
        Shopkeeper bob = new Shopkeeper("shopkeeper", "Bob", "A fairly chubby man with a glint in his eyes.");
        bob.addToInventory(new Knife(), 5);
        bob.addToInventory(new Apple(), 2);
        shopRoom.addNPC(bob);
        shopRoom.addExit("south", "wolfroom");
        CreateAlpha.putRoom(shopRoom);



        return rooms;

    }

    public static void putRoom(Room room){
        rooms.put(room.getID(), room);
    }
}
