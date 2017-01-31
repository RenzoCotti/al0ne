package com.al0ne.Engine;

import com.al0ne.Player;
import com.al0ne.Room;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by BMW on 31/01/2017.
 */
public class ParseInput {
    public static void parse(String input, Game game){

        Player player = game.getPlayer();
        HashMap<String, Room> rooms = game.getAllRooms();

        String lowerInput = input.toLowerCase();

        String[] temp = lowerInput.split(" ");

        switch (temp[0]){
            case "use":
//                return lowerInput;
            case "open":
                break;
            case "north":
                if (temp.length > 1) break;
                else {
                    player.moveToRoom("north", rooms);
                    break;
                }
            case "south":
                if (temp.length > 1) break;
                else {
                    player.moveToRoom("south", rooms);
                    break;
                }
            case "east":
                if (temp.length > 1) break;
                else {
                    player.moveToRoom("east", rooms);
                    break;
                }
            case "west":
                if (temp.length > 1) break;
                else {
                    player.moveToRoom("west", rooms);
                    break;
                }
            case "take":
                player.pickUpItem(ParseInput.stitchFromTo(temp, 1, temp.length));
                break;
            case "examine":
                player.examine(ParseInput.stitchFromTo(temp, 1, temp.length));
                break;
            case "help":
                System.out.println("Commands: help, north, west, east, south, take x, examine x, use x, use x with y, open x");
                break;
            case "quit":
                System.out.println("Are you sure you want to quit? (Y/N)");
                Scanner test = new Scanner(System.in);
                while (true){
                    if(test.hasNextLine()){
                        if (test.nextLine().equals("Y")){
                            System.exit(0);
                        } else{
                            System.out.println("Ok then, forget it.");
                        }
                    }
                    break;
                }
                break;

            default:
                System.out.println("Sorry?");
                break;
        }
    }

    public static String stitchFromTo(String[] input, int begin, int end){
        String temp="";
        for (int i = begin; i != end; i++){
            temp+=input[i]+" ";
        }
        return temp.trim();
    }

    public static int checkForToken(String[] input, String token){
        for (int i = 0; i != input.length; i++){
            if (input[i].equals(token)){
                return i;
            }
        }
        return -1;
    }

}
