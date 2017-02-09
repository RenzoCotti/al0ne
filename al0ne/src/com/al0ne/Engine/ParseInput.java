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

                    ArrayList<String> first = getPotentialItem(firstItem, player, 0);
                    ArrayList<String> second = getPotentialItem(secondItem, player, 1);



                    try{
                        player.interactOnWith(second.get(0), first.get(0));
                    } catch (IndexOutOfBoundsException ex){
                        System.out.println("You can't see such items");
                    }
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
                String item = ParseInput.stitchFromTo(temp, 1, temp.length);
                ArrayList<String> possibleItems = getPotentialItem(item, player, 2);

                if (possibleItems.size()>1){
                    System.out.println("Be more specific");

                    for (String f : possibleItems){
                        System.out.println(f);
                    }
                    break;
                }

                try{
                    player.pickUpItem(possibleItems.get(0));
                } catch (IndexOutOfBoundsException ex){
                    System.out.println("You can't see such an item");
                }
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
            case "kill":
                System.exit(0);

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


    public static ArrayList<String> getPotentialItem(String s, Player player, int number){
        ArrayList<String> potentialItems = new ArrayList<>();


        //case inventory
        if(number==0){

            //check if there is an exact match
            for (Item b : player.getInventory().values()){
                if (b.getName().equals(s)){
                    potentialItems.add(b.getID());
//                    System.out.println("Perfect match found!");
                    return potentialItems;
                }
            }

            //otherwise, parse and check for partial matches
            String[] temp = s.split(" ");
            for (String token : temp){
                for(Item a : player.getInventory().values()){
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

            for (Item b : player.getCurrentRoom().getItems().values()){
                if (b.getName().toLowerCase().equals(s.toLowerCase())){
                    potentialItems.add(b.getID());
//                    System.out.println("Perfect match found!");
                    return potentialItems;
                }
            }


        String[] temp = s.split(" ");
        for (String token : temp){
            for(Item a : player.getCurrentRoom().getItems().values()){
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
