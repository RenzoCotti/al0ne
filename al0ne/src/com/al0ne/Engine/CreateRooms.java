package com.al0ne.Engine;

import com.al0ne.Interactables.Cuttable.Rope;
import com.al0ne.Interactables.Items.Sharp.Knife;
import com.al0ne.Room;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by BMW on 28/01/2017.
 */
public class CreateRooms {

    public static HashMap<String, Room>  create() {

        HashMap<String, Room> rooms = new HashMap<>();

        Room cave1 = new Room("You are in a pretty generic-looking cave. It feels pretty damp.", "Cave 1");
        cave1.addExit("cave2");

        rooms.put("cave1", cave1);


        Room cave2 = new Room("The rocks are crumbly here.", "Cave 2");
        cave2.addExit("cave1");
        cave2.addItem(new Knife());
        cave2.addItem(new Rope());

        rooms.put("cave2", cave2);


        return rooms;

    }
}
