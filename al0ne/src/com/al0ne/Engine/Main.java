package com.al0ne.Engine;

import com.al0ne.Player;
import com.al0ne.Room;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {


        HashMap<String, Room> rooms = CreateRooms.create();

        Player Grog = new Player(rooms.get("cave1"), 10.0);

        Game game = new Game(Grog, rooms);


        Player grog = game.getPlayer();

        game.getRoom().printRoom();

        Scanner userInput = new Scanner(System.in);

        while (true){

            if(userInput.hasNextLine()){
                ParseInput.parse(userInput.nextLine(), game);
            }

        }

//        grog.pickUpItem("health potion");
//
//        grog.moveToRoom("east", rooms);
//
//
//
//        grog.pickUpItem("knife");
//        grog.pickUpItem("apple");
//
//        grog.printInventory();
//
//        grog.getInventory().get("knife").printDescription();
//
//        grog.examine("rope");
//
//
//        grog.examine("apple");
//
//
//
//        grog.interactOnWith("apple", "knife");

//        grog.interactOnWith("rope", "knife");

//        grog.interactOnWith(currentRoom.getInteractable("rope"), grog.getItem("knife"));


    }
}
