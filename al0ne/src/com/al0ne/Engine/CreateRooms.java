package com.al0ne.Engine;

import com.al0ne.Interactables.Cuttable.Rope;
import com.al0ne.Interactables.Items.Edible.Apple;
import com.al0ne.Interactables.Items.Sharp.Knife;
import com.al0ne.Room;
import java.util.HashMap;

public class CreateRooms {

    public static HashMap<String, Room>  create() {

        HashMap<String, Room> rooms = new HashMap<>();

        Room cave1 = new Room("You are in a pretty generic-looking cave. It feels pretty damp.", "Cave 1");
        cave1.addExit("east","cave2");

        rooms.put("cave1", cave1);


        Room cave2 = new Room("The rocks are crumbly here. A rope is secured tightly.", "Cave 2");
        cave2.addExit("west","cave1");
        cave2.addItem(new Knife());
        cave2.addItem(new Apple());
        cave2.addInteractable(new Rope());

        rooms.put("cave2", cave2);


        return rooms;

    }
}
