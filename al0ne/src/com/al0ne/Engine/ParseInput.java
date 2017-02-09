package com.al0ne.Engine;

import com.al0ne.Items.Item;
import com.al0ne.Items.Prop;
import com.al0ne.Player;
import com.al0ne.Room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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

                int a = ParseInput.checkForToken(temp, "on");
                if(a ==  -1){
//                    String itemToUse = ParseInput.stitchFromTo(temp, 1, temp.length);
                } else if(a > -1){
                    String firstItem = ParseInput.stitchFromTo(temp, 1, a);
                    String secondItem = ParseInput.stitchFromTo(temp, a+1, temp.length);

                    ArrayList<String> first = getPotentialItem(firstItem, player, true);
                    ArrayList<String> second = getPotentialItem(secondItem, player, false);


//                    for (String s : first){
//                        System.out.println(s);
//                    }
//
//                    System.out.println();
//
//                    for (String s : second){
//                        System.out.println(s);
//                    }


                    player.interactOnWith(first.get(0), second.get(0));
                }
                break;

            case "open":
                break;
            case "look":
                game.getRoom().printRoom();
                break;
            case "north":
                ParseInput.move(player, rooms, "north", temp);
                break;
            case "south":
                ParseInput.move(player, rooms, "south", temp);
                break;
            case "east":
                ParseInput.move(player, rooms, "east", temp);
                break;
            case "west":
                ParseInput.move(player, rooms, "west", temp);
                break;
            case "take":
                player.pickUpItem(ParseInput.stitchFromTo(temp, 1, temp.length));
                break;
            case "examine":
                player.examine(ParseInput.stitchFromTo(temp, 1, temp.length));
                break;
            case "inventory":
                player.printInventory();
                break;
            case "help":
                System.out.println("Commands: help, north, west, east, south, take x, examine x, use x, use x on y, open x, look, quit, exit");
                break;
            case "quit":
                ParseInput.quit();
                break;
            case "exit":
                ParseInput.quit();
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



        temp=temp.substring(0, temp.length()-1);

//        System.out.println(expected.equals(temp));
        return temp;
    }

    public static int checkForToken(String[] input, String token){
        for (int i = 0; i != input.length; i++){
            if (input[i].equals(token)){
                return i;
            }
        }
        return -1;
    }

    public static void quit(){
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
    }

    public static void move(Player player, HashMap<String, Room> rooms, String direction, String[] temp){
        if (temp.length > 1) {
            System.out.println("Sorry?");
        }
        else if(player.getCurrentRoom().isLocked(direction)){
            System.out.println("The way "+direction+" is blocked.");
        }
        else {
            player.moveToRoom(direction, rooms);
        }
    }


    public static ArrayList<String> getPotentialItem(String s, Player player, boolean inventory){
        ArrayList<String> potentialItems = new ArrayList<>();

        if(inventory){
            String[] temp = s.split(" ");
            for (String token : temp){
                for(Item a : player.getCurrentRoom().getItems().values()){
                    String[] currentItem = a.getName().split(" ");
                    for (String b : currentItem){
                        if (b.toLowerCase().equals(token)){
                            potentialItems.add(a.getID());
                        }
                    }
                }
            }
        } else{
            String[] temp = s.split(" ");
            for (String token : temp){
                for(Prop a : player.getCurrentRoom().getProps().values()){
                    String[] currentItem = a.getName().split(" ");
                    for (String b : currentItem){
                        if (b.toLowerCase().equals(token)){
                            potentialItems.add(a.getID());
                        }
                    }
                }
            }
        }

        return potentialItems;
    }
}
