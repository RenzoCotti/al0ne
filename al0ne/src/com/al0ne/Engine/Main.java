package com.al0ne.Engine;

import com.al0ne.Entities.Player;
import com.al0ne.Items.Items.Coin;
import com.al0ne.Items.Items.HolySword;
import com.al0ne.Room;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {


        HashMap<String, Room> rooms = CreateAlpha.create();

        Player grog = new Player(rooms.get("startroom"));
        grog.addItem(new HolySword());

        Game game = new Game(grog, rooms);

        Room currentRoom = grog.getCurrentRoom();

        int turnCounter=0;

//        ParseInput.printWelcome();

        currentRoom.printRoom();
        System.out.println();

        currentRoom.printName();

        Scanner userInput = new Scanner(System.in);

        while (true){

            if(userInput.hasNextLine()){
                if(ParseInput.parse(userInput.nextLine(), game, turnCounter)){
                    turnCounter++;
                }
                if (!grog.isAlive()){
                    System.out.println("You have died...");
                    System.out.println();
                    System.out.println("Game over!");
                    System.exit(0);
                }
                System.out.println();
                grog.getCurrentRoom().printName();

            }

        }

    }
}
