package com.al0ne.Engine;

import com.al0ne.Interactables.Items.Archetypes.Interactable;
import com.al0ne.Interactables.Items.Archetypes.Pickable;
import com.al0ne.Player;
import com.al0ne.Room;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {


        HashMap<String, Room> rooms = CreateRooms.create();

        Player Grog = new Player(rooms.get("cave1"), 10.0);

        Game game = new Game(Grog, rooms);


        Player grog = game.getPlayer();

        game.getRoom().printRoom();

        grog.pickUpItem("apple");

        grog.moveToRoom("cave1", rooms);
        grog.moveToRoom("cave2", rooms);


        game.getRoom().printRoom();

        System.out.println();


        grog.pickUpItem("knife");
        grog.pickUpItem("apple");

        grog.printInventory();

        grog.getInventory().get("knife").printDescription();

        game.getRoom().printRoom();


//        grog.moveToRoom("cave1", rooms);
//
//        currentRoom = grog.getCurrentRoom();
//
//        currentRoom.printRoom();
//
//
//        grog.moveToRoom("cave2", rooms);
//
//        currentRoom = grog.getCurrentRoom();
//
//        currentRoom.printRoom();

        grog.interactWith(game.getRoom().getInteractables().get("apple"), grog.getItem("apple"));

//        grog.interactWith(currentRoom.getInteractable("rope"), grog.getItem("knife"));

//        grog.interactWith(currentRoom.getInteractable("rope"), grog.getItem("knife"));


    }
}
