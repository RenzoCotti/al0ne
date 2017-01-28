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

        Room currentRoom = grog.getCurrentRoom();
        currentRoom.printDescription();
        currentRoom.printItems();
        currentRoom.printInteractables();

        grog.pickUpItem("apple");

        grog.moveToRoom("cave1", rooms);
        grog.moveToRoom("cave2", rooms);


        currentRoom=grog.getCurrentRoom();
        currentRoom.printDescription();
        currentRoom.printInteractables();
        currentRoom.printItems();

        System.out.println();


        grog.pickUpItem("knife");
        grog.pickUpItem("apple");

        grog.printInventory();

        grog.getInventory().get("knife").printDescription();

        grog.interactWith(currentRoom.getInteractables().get("rope"), grog.getInventory().get("apple"));

        grog.interactWith(currentRoom.getInteractables().get("rope"), grog.getInventory().get("knife"));

        grog.interactWith(currentRoom.getInteractables().get("rope"), grog.getInventory().get("knife"));


    }
}
