package com.al0ne.Engine;

import com.al0ne.Items.Item;
import com.al0ne.Items.Pair;
import com.al0ne.Items.Prop;
import com.al0ne.Player;
import com.al0ne.Room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by BMW on 31/01/2017.
 */
public class ParseInput {
    public static boolean parse(String input, Game game, int turns){

        Player player = game.getPlayer();
        HashMap<String, Room> rooms = game.getAllRooms();

        String lowerInput = input.toLowerCase();

        String[] temp = lowerInput.split(" ");

        switch (temp[0]){

            //we check if it's a simple use, e.g. use potion or a complex one, e.g. use x on y
            case "use":

                int a = ParseInput.checkForToken(temp, "on");

                //case simple use
                if(a ==  -1){
                    String itemToUse = ParseInput.stitchFromTo(temp, 1, temp.length);
                    ArrayList<String> inventoryUse = getPotentialItem(itemToUse, player, 0);
                    ArrayList<String> propUse = getPotentialItem(itemToUse, player, 1);

                    //there are more possibilities from the items fetched
                    //// TODO: 09/03/2017 maybe problems with multiple equal items
                    if (inventoryUse.size()>1 || propUse.size()>1 || (propUse.size()==1 && inventoryUse.size()==1)){
                        System.out.println("Be more specific.");
                        return false;
                    }

                        if (inventoryUse.size()==1){
                            player.simpleUse(inventoryUse.get(0));
                        } else if (propUse.size()==1){
                            player.simpleUse(propUse.get(0));
                        } else{
                            System.out.println("You can't see such an item");

                            System.out.println(inventoryUse.size());
                            System.out.println(propUse.size());
                        }

                    return true;

                //case complex use
                } else if(a > -1){
                    String firstItem = ParseInput.stitchFromTo(temp, 1, a);
                    String secondItem = ParseInput.stitchFromTo(temp, a+1, temp.length);

                    ArrayList<String> first = getPotentialItem(firstItem, player, 0);
                    ArrayList<String> second = getPotentialItem(secondItem, player, 1);

                    if (first.size()>1 || second.size() >1){
                        System.out.println("Be more specific.");
                        return false;
                    }

                    try{
                        player.interactOnWith(second.get(0), first.get(0));
                    } catch (IndexOutOfBoundsException ex){
                        System.out.println("You can't see such items.");
                    }
                }
                return true;

            case "open":
                return false;
            case "look":
                game.getRoom().printRoom();
                return true;
            case "n":
            case "north":
                if (ParseInput.move(player, rooms, "north", temp)){
                    return true;
                }
                return false;
            case "s":
            case "south":
                if (ParseInput.move(player, rooms, "south", temp)){
                    return true;
                }
                return false;
            case "e":
            case "east":
                ParseInput.move(player, rooms, "east", temp);
                return true;
            case "w":
            case "west":
                if (ParseInput.move(player, rooms, "west", temp)){
                    return true;
                }
                return false;
            case "down":
                if (ParseInput.move(player, rooms, "down", temp)){
                    return true;
                }
                return false;
            //we try to get the item the player wants to take
            case "take":
                if (temp.length<=1){
                    System.out.println("Sorry?");
                    return false;
                }
                String item = ParseInput.stitchFromTo(temp, 1, temp.length);
                ArrayList<String> possibleItems = getPotentialItem(item, player, 2);

                if (possibleItems.size()>1){
                    System.out.println("Be more specific.");
                    return false;
                }

                try{
                    player.pickUpItem(possibleItems.get(0));
                } catch (IndexOutOfBoundsException ex){
                    System.out.println("You can't see such an item");
                }
                return true;
            case "x":
            case "examine":
                player.examine(ParseInput.stitchFromTo(temp, 1, temp.length));
                return true;
            case "i":
            case "inventory":
                player.printInventory();
                return true;
            case "?":
            case "help":
                System.out.println("Commands: help, north, west, east, south, take x, examine x, use x, use x on y, open x, look, quit, exit");
                return false;
            case "quit":
            case "exit":
                ParseInput.quit();
                return false;
            case "drop":
                String toDrop = ParseInput.stitchFromTo(temp, 1, temp.length);
                ArrayList<String> possibleDrop = getPotentialItem(toDrop, player, 0);

                if (possibleDrop.size()>1){
                    System.out.println("Be more specific.");

//                    for (String f : possibleItems){
//                        System.out.println(f);
//                    }
                    return false;
                }

                try{
                    player.drop(possibleDrop.get(0));
                } catch (IndexOutOfBoundsException ex){
                    System.out.println("You can't see such an item");
                }

                return true;
            case "kill":
                System.exit(0);

            case "time":
                System.out.println(turns+" turns elapsed.");
                return false;

            default:
                System.out.println("Sorry?");
                return false;
        }
    }


    //this function makes a string from the begin-th element of the array to the end-th
    public static String stitchFromTo(String[] input, int begin, int end){
        String temp="";
        for (int i = begin; i != end; i++){
            temp+=input[i]+" ";
        }

        temp=temp.substring(0, temp.length()-1);

//        System.out.println(expected.equals(temp));
        return temp;
    }

    //this function looks for token in input
    public static int checkForToken(String[] input, String token){
        for (int i = 0; i != input.length; i++){
            if (input[i].equals(token)){
                return i;
            }
        }
        return -1;
    }

    //this function handles the command exit
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

    //this handles moving: it checks for exactly one token after the command
    public static boolean move(Player player, HashMap<String, Room> rooms, String direction, String[] temp){
//        System.out.println(player.getCurrentRoom().isLocked(direction));
        if (temp.length > 1) {
            System.out.println("Sorry?");
            return false;
        } else {
            player.moveToRoom(direction, rooms);
            return true;
        }
    }

    //this attempts to get items from a token;
    //number = 0: tries to get from the inventory
    //number = 1: tries to get from Props of currentRoom
    //number = 2: tries to get from Items of currentRoom
    public static ArrayList<String> getPotentialItem(String s, Player player, int number){
        ArrayList<String> potentialItems = new ArrayList<>();


        //case inventory
        if(number==0){

            //check if there is an exact match
            for (Pair pair : player.getInventory().values()){
                Item b = pair.getItem();
                if (b.getName().equals(s)){
                    potentialItems.add(b.getID());
//                    System.out.println("Perfect match found!");
                    return potentialItems;
                }
            }

            //otherwise, parse and check for partial matches
            String[] temp = s.split(" ");
            for (String token : temp){
                for(Pair pair : player.getInventory().values()){
                    Item a = pair.getItem();
                    String[] currentItem = a.getName().split(" ");
                    for (String b : currentItem){
                        if (b.toLowerCase().equals(token)){
                            if (!potentialItems.contains(a.getID())){
                                potentialItems.add(a.getID());
                            }
                        }
                    }
                }
            }
            //case prop
        } else if (number==1){

            for (Prop b : player.getCurrentRoom().getProps().values()){
                if (b.getName().equals(s)){
                    potentialItems.add(b.getID());
                    return potentialItems;
                }
            }


            String[] temp = s.split(" ");
            for (String token : temp){
                for(Prop a : player.getCurrentRoom().getProps().values()){
                    String[] currentItem = a.getName().split(" ");
                    for (String b : currentItem){
                        if (b.toLowerCase().equals(token)){
                            if (!potentialItems.contains(a.getID())){
                                potentialItems.add(a.getID());
                            }
                        }
                    }
                }
            }
            //case item
        } else if(number==2){

            for (Pair pair : player.getCurrentRoom().getItems().values()){
                Item b = pair.getItem();
                if (b.getName().toLowerCase().equals(s.toLowerCase())){
                    potentialItems.add(b.getID());
//                    System.out.println("Perfect match found!");
                    return potentialItems;
                }
            }


        String[] temp = s.split(" ");
        for (String token : temp){
            for(Pair pair : player.getCurrentRoom().getItems().values()){
                Item a = pair.getItem();
                String[] currentItem = a.getName().split(" ");
                for (String b : currentItem){
                    if (b.toLowerCase().equals(token)){
                        if (!potentialItems.contains(a.getID())){
                            potentialItems.add(a.getID());
                        }
                    }
                }
            }
        }
    }

        return potentialItems;
    }
}
