package com.al0ne.Engine;

import com.al0ne.Items.Items.Beer;
import com.al0ne.Player;
import com.al0ne.Room;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {


        HashMap<String, Room> rooms = CreateRooms.create();

        Player Grog = new Player(rooms.get("cave3"), 10.0);

        Game game = new Game(Grog, rooms);


        Player grog = game.getPlayer();

        grog.addItem(new Beer());
        grog.addItem(new Beer());
        grog.addItem(new Beer());

        game.getRoom().printRoom();

        Scanner userInput = new Scanner(System.in);

        while (true){

            if(userInput.hasNextLine()){
                ParseInput.parse(userInput.nextLine(), game);
            }

        }

    }
}
