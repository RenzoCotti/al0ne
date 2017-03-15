package com.al0ne.Engine;

import com.al0ne.Items.Item;
import com.al0ne.Items.Pair;
import com.al0ne.Items.Prop;
import com.al0ne.Entities.NPC;
import com.al0ne.Entities.Player;
import com.al0ne.Room;
import com.al0ne.Entities.NPCs.Shopkeeper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
/*
* This class handles parsing the input correctly
* */
public class ParseInput {
//    static String last="help";

    public static boolean parse(String input, Game game, int turns) {

        Player player = game.getPlayer();
        HashMap<String, Room> rooms = game.getAllRooms();

        String lowerInput = input.toLowerCase();

        String[] parsedInput = lowerInput.split(" ");


        switch (parsedInput[0]) {

            case "drink":
                return ParseInput.customAction(parsedInput, player, "drink");
            case "talk":
                return ParseInput.handleTalk(parsedInput, player);
            case "eat":
                return ParseInput.customAction(parsedInput, player, "eat");
            case "read":
                return ParseInput.customAction(parsedInput, player, "read");
            case "buy":
                return ParseInput.handleBuy(parsedInput, player);
            case "move":
                return ParseInput.customAction(parsedInput, player, "move");
            case "attack":
                String enemy = ParseInput.stitchFromTo(parsedInput, 1, parsedInput.length);
                return player.attack(enemy);

            case "give":
                return ParseInput.handleGive(parsedInput, player);
                //we check if it's a simple use, e.g. use potion or a complex one, e.g. use x on y
            case "use":

                int tokenPosition = ParseInput.checkForToken(parsedInput, "on");

                //case simple use
                if (tokenPosition == -1) {
                    return ParseInput.useItem(parsedInput, player, false, tokenPosition);
                }
                //case complex use
                else if (tokenPosition > -1) {
                    return ParseInput.useItem(parsedInput, player, true, tokenPosition);
                }

            case "open":
                return ParseInput.customAction(parsedInput, player, "open");
            case "wield":
                return ParseInput.handleWield(parsedInput, player);
            case "l":
            case "look":
                game.getRoom().printRoom();
                return true;
            case "n":
            case "north":
                return ParseInput.move(player, rooms, "north", parsedInput);
            case "s":
            case "south":
                return ParseInput.move(player, rooms, "south", parsedInput);
            case "e":
            case "east":
                return ParseInput.move(player, rooms, "east", parsedInput);
            case "w":
            case "west":
                return ParseInput.move(player, rooms, "west", parsedInput);
            case "d":
            case "down":
                return ParseInput.move(player, rooms, "down", parsedInput);
            case "u":
            case "up":
                return ParseInput.move(player, rooms, "up", parsedInput);
            case "take":
                return ParseInput.takeOrDrop(parsedInput, player, false);
            case "x":
            case "examine":
                return ParseInput.handleExamine(parsedInput, player);
            case "i":
            case "inventory":
                player.printInventory();
                return true;
            case "?":
            case "help":
                System.out.println("Commands:");
                for (Command command: Command.values()){
                    System.out.println(command);
                }
                return false;
            case "quit":
            case "exit":
                ParseInput.quit();
                return false;
            case "weight":
                player.printWeight();
                return false;
            case "health":
                player.printHealth();
                return false;
            case "g":
//                last = ParseInput.stitchFromTo(parsedInput, 0, parsedInput.length);
//                redo last
                return false;
            case "drop":
                return takeOrDrop(parsedInput, player, true);

            case "kill":
                System.exit(0);

            case "time":
                System.out.println(turns + " turns elapsed.");
                return false;

            default:
                System.out.println("Sorry?");
                return false;
        }

    }


    //this function makes a string from the begin-th element of the array to the end-th
    private static String stitchFromTo(String[] input, int begin, int end) {
        String temp = "";
        for (int i = begin; i != end; i++) {
            temp += input[i] + " ";
        }

        temp = temp.substring(0, temp.length() - 1);
        return temp;
    }

    //this function looks for token in input
    private static int checkForToken(String[] input, String token) {
        for (int i = 0; i != input.length; i++) {
            if (input[i].equals(token)) {
                return i;
            }
        }
        return -1;
    }

    //this function handles the command exit
    private static void quit() {
        System.out.println("Are you sure you want to quit? (Y/N)");
        Scanner test = new Scanner(System.in);
        if (test.hasNextLine()) {
            if (test.nextLine().equals("Y")) {
                System.exit(0);
            } else {
                System.out.println("Ok then, forget it.");
            }
        }
    }

    //this handles moving: it checks for exactly one token after the command
    private static boolean move(Player player, HashMap<String, Room> rooms, String direction, String[] temp) {
        if (temp.length > 1) {
            System.out.println("Sorry?");
            return false;
        } else {
            if (player.moveToRoom(direction, rooms)){
                ParseInput.clearScreen();
                player.getCurrentRoom().printRoom();
                return true;
            }

            return false;
        }
    }

    //this handles trying to apply custom commands on objects
    private static boolean customAction(String[] temp, Player player, String action) {
        if(temp.length==1){
            System.out.println("Sorry?");
            return false;
        }
        String prop = ParseInput.stitchFromTo(temp, 1, temp.length);

        ArrayList<String> possibleProp = getPotentialItem(prop, player, 1);
        ArrayList<String> possibleItemFromInventory = getPotentialItem(prop, player, 0);
        ArrayList<String> possibleItem = getPotentialItem(prop, player, 2);

        if (!(possibleItemFromInventory.size() + possibleProp.size() + possibleItem.size() == 1)) {
            System.out.println("Be more specific.");
            return false;
        }

        if (possibleItem.size()== 1) {
            System.out.println("You need to hold that item to "+action+" it.");
            return false;
        }

        try {
            if (possibleProp.size() == 1) {
                if (player.customAction(action, possibleProp.get(0))) {
                    System.out.println("You " + action + " the " + prop);
                    return true;
                } else {
                    System.out.println("You can't " + action + " it.");
                    return false;
                }
            } else {
                if (player.customAction(action, possibleItemFromInventory.get(0))) {
                    System.out.println("You " + action + " the " + prop + ".");
                    return true;
                } else {
                    System.out.println("You can't " + action + " it.");
                    return false;
                }
            }
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("You can't see a " + prop);
            return false;
        }
    }

    //this attempts to get items from a token;
    //number = 0: tries to get from the inventory
    //number = 1: tries to get from Props of currentRoom
    //number = 2: tries to get from Items of currentRoom
    private static ArrayList<String> getPotentialItem(String s, Player player, int number) {
        ArrayList<String> potentialItems = new ArrayList<>();


        //case inventory
        if (number == 0) {

            //check if there is an exact match
            for (Pair pair : player.getInventory().values()) {
                Item b = pair.getItem();
                if (b.getName().equals(s)) {
                    potentialItems.add(b.getID());
                    return potentialItems;
                }
            }

            //otherwise, parse and check for partial matches
            String[] temp = s.split(" ");
            for (String token : temp) {
                for (Pair pair : player.getInventory().values()) {
                    Item a = pair.getItem();
                    String[] currentItem = a.getName().split(" ");
                    for (String b : currentItem) {
                        if (b.toLowerCase().equals(token)) {
                            if (!potentialItems.contains(a.getID())) {
                                potentialItems.add(a.getID());
                            }
                        }
                    }
                }
            }
            //case prop
        } else if (number == 1) {
            //we look for perfect match
            for (Prop b : player.getCurrentRoom().getProps().values()) {
                if (b.getName().equals(s)) {
                    potentialItems.add(b.getID());
                    return potentialItems;
                }
            }

            //we look for partial matches
            String[] temp = s.split(" ");
            for (String token : temp) {
                for (Prop a : player.getCurrentRoom().getProps().values()) {
                    String[] currentItem = a.getName().split(" ");
                    for (String b : currentItem) {
                        if (b.toLowerCase().equals(token)) {
                            if (!potentialItems.contains(a.getID())) {
                                potentialItems.add(a.getID());
                            }
                        }
                    }
                }
            }
            //case item
        } else if (number == 2) {

            //we iterate over all items in the room, we look for a perfect match
            for (Pair pair : player.getCurrentRoom().getItems().values()) {
                Item b = pair.getItem();
                if (b.getName().toLowerCase().equals(s.toLowerCase())) {
                    potentialItems.add(b.getID());
                    return potentialItems;
                }
            }

            //otherwise, we look for partial matches
            String[] temp = s.split(" ");
            for (String token : temp) {
                for (Pair pair : player.getCurrentRoom().getItems().values()) {
                    Item a = pair.getItem();
                    String[] currentItem = a.getName().split(" ");
                    for (String b : currentItem) {
                        if (b.toLowerCase().equals(token)) {
                            if (!potentialItems.contains(a.getID())) {
                                potentialItems.add(a.getID());
                            }
                        }
                    }
                }
            }
        }

        return potentialItems;
    }

    /*this function is used to handle using an item:
    * boolean complex: if true, assumes the action is like USE x ON y, otherwise it's USE x*/
    private static boolean useItem(String[] temp, Player player, boolean complex, int tokenPosition) {
        if(temp.length==1){
            System.out.println("Sorry?");
            return false;
        }

        String firstItem;
        String secondItem;

        ArrayList<String> inventoryUse;
        ArrayList<String> propUse;
        ArrayList<String> itemUse;
        //case complex use: check we have exactly two items, then make the player use them
        if (complex) {
            firstItem = ParseInput.stitchFromTo(temp, 1, tokenPosition);
            secondItem = ParseInput.stitchFromTo(temp, tokenPosition + 1, temp.length);

            inventoryUse = getPotentialItem(firstItem, player, 0);
            propUse = getPotentialItem(secondItem, player, 1);
            itemUse = getPotentialItem(firstItem, player, 2);

            if (!(inventoryUse.size() == 1) || !(propUse.size() == 1)) {
                System.out.println("Be more specific.");
                return false;
            }

            if (itemUse.size() > 0) {
                System.out.println("You need to be holding that item to use it.");
            }

            if (inventoryUse.size() == 1 && propUse.size() == 1) {
                player.interactOnWith(propUse.get(0), inventoryUse.get(0));
            } else {
                System.out.println("You can't see such items");
            }

            return true;
            //case simple use: check we have just one item, then we make the player use it.
        } else {
            firstItem = ParseInput.stitchFromTo(temp, 1, temp.length);

            inventoryUse = getPotentialItem(firstItem, player, 0);
            propUse = getPotentialItem(firstItem, player, 1);
            itemUse = getPotentialItem(firstItem, player, 2);

            //there are more possibilities from the items fetched
            if (!(inventoryUse.size() + propUse.size() == 1)) {
                System.out.println("Be more specific.");
                return false;
            }

            if (itemUse.size() > 0) {
                System.out.println("You need to be holding that item to use it.");
            }

            if (inventoryUse.size() == 1) {
                player.simpleUse(inventoryUse.get(0));
            } else if (propUse.size() == 1) {
                player.simpleUse(propUse.get(0));
            } else {
                System.out.println("You can't see such an item");

                System.out.println(inventoryUse.size());
                System.out.println(propUse.size());
            }

            return true;
        }

    }

    //this function handles both dropping and taking items:
    //drop: false-> it's a TAKE action, true-> it's a DROP action
    //we check if it's a drop/take all action, then we check if we have exactly
    //1 item, then we take/drop (all)
    private static boolean takeOrDrop(String[] temp, Player player, boolean drop) {

//        player.printWeight();

        if (temp.length < 3) {
            System.out.println("Sorry?");
            return false;
        }

        boolean all = false;

        String item;
        if (temp.length >= 2 && temp[1].equals("all")) {
            all = true;
            item = ParseInput.stitchFromTo(temp, 2, temp.length);
        } else {
            item = ParseInput.stitchFromTo(temp, 1, temp.length);
        }

        ArrayList<String> possibleItems;
        if (!drop) {
            possibleItems = getPotentialItem(item, player, 2);

            if (possibleItems.size() > 1) {
                System.out.println("Be more specific.");
                return false;
            }


            if (all && possibleItems.size() == 1) {
                player.pickUpItem(possibleItems.get(0), true);
            } else if (possibleItems.size() == 1) {
                player.pickUpItem(possibleItems.get(0), false);
            } else {
                System.out.println("You can't see such an item (take)");
            }
        } else {
            possibleItems = getPotentialItem(item, player, 0);

            if (possibleItems.size() > 1) {
                System.out.println("Be more specific.");
                return false;
            }


            if (all && possibleItems.size() == 1) {
                player.drop(possibleItems.get(0), true);
            } else if (possibleItems.size() == 1) {
                player.drop(possibleItems.get(0), false);
            } else {
                System.out.println("You can't see such an item (drop) "+possibleItems.size());
            }
        }

//        player.printWeight();
        return true;

    }

    //this function handles examining an object:
    //we look in the room for props and items, as well as in the player's inventory
    //if the search returns exactly one item, we examine it
    private static boolean handleExamine(String[] temp, Player player){

        if(temp.length==1){
            System.out.println("Sorry?");
            return false;
        }


        String toExamine = ParseInput.stitchFromTo(temp, 1, temp.length);
        ArrayList<String> inventoryExamine = getPotentialItem(toExamine, player, 0);
        ArrayList<String> propExamine = getPotentialItem(toExamine, player, 1);
        ArrayList<String> itemExamine = getPotentialItem(toExamine, player, 2);

        NPC npc = player.getCurrentRoom().getNPC(toExamine);

        if ( npc != null){
            npc.printDescription();
            return true;
        }

        //there are more possibilities from the items fetched
        if (!(inventoryExamine.size() + propExamine.size() + itemExamine.size() == 1)){
            System.out.println("Be more specific.");
            return false;
        }

        if (inventoryExamine.size()==1){
            player.examine(inventoryExamine.get(0));
        } else if (propExamine.size()==1){
            player.examine(propExamine.get(0));
        } else if (itemExamine.size() == 1){
            player.examine(itemExamine.get(0));
        }else{
            System.out.println("You can't see such an item");
        }

        return true;
    }

    public static boolean handleWield(String[] parsedInput, Player player){

        if(parsedInput.length==1){
            System.out.println("Sorry?");
            return false;
        }

        String wieldItem = ParseInput.stitchFromTo(parsedInput, 1, parsedInput.length);

        ArrayList<String> possibleItems = getPotentialItem(wieldItem, player, 0);

        if(possibleItems.size() == 1){
            Item item = player.getPairFromInventory(possibleItems.get(0)).getItem();

            if(player.wield(item)){
                return true;
            } else{
                System.out.println("You don't seem to have a "+wieldItem);
                return false;
            }
        } else {
            System.out.println("Be more specific.");
            return false;
        }
    }


    //clears the screen by printing 20 new lines
    private static void clearScreen(){
        for (int i=0; i<30; i++){
            System.out.println();
        }
    }

    public static void printWelcome(){
        System.out.println("Welcome to my textual adventure game!");
        System.out.println("I hope you enjoy your time playing");
        System.out.println();
        System.out.println("Please report any weird/unexpected behaviour to me");

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("Press ENTER to continue...");
        try
        {
            System.in.read();
            clearScreen();
        }
        catch(Exception e)
        {}
    }


    public static boolean handleTalk(String[] parsedInput, Player player){

        if(parsedInput.length==1){
            System.out.println("Sorry?");
            return false;
        }

        int b = ParseInput.checkForToken(parsedInput, "with");
        int to = ParseInput.checkForToken(parsedInput, "to");
        if (parsedInput[1].equals("to")) {
            String npc = ParseInput.stitchFromTo(parsedInput, to + 1, parsedInput.length);

            NPC character = player.getCurrentRoom().getNPC(npc);


            if (character == null || !(character.getName().toLowerCase().equals(npc))) {
                System.out.println("You can't see " + npc + " here.");
                return true;
            }

            character.printIntro();
            return true;
        } else if( b == -1 || !(parsedInput[1].equals("about")) ){
            System.out.println("The syntax is: TALK ABOUT x WITH y");
            return false;
        } else {
            String subject = ParseInput.stitchFromTo(parsedInput, 2, b);
            String npc = ParseInput.stitchFromTo(parsedInput, b+1, parsedInput.length);

            NPC character = player.getCurrentRoom().getNPC(npc);


            if (character == null || !(character.getName().toLowerCase().equals(npc))){
                System.out.println("You can't see "+npc+" here.");
                return true;
            }

            if( player.talkToNPC(npc, subject) ){
                return true;
            } else{
                System.out.println("\"Sorry, I don't know anything about it.\"");
                return true;
            }
        }
    }

    public static boolean handleBuy(String[] parsedInput, Player player){
        int c = ParseInput.checkForToken(parsedInput, "from");
        if( c == -1){
            System.out.println("The syntax is: BUY x FROM y");
            return false;
        } else {
            String item = ParseInput.stitchFromTo(parsedInput, 1, c);
            String npc = ParseInput.stitchFromTo(parsedInput, c+1, parsedInput.length);

            NPC character = player.getCurrentRoom().getNPC(npc);


            if (character == null || !(character.getName().toLowerCase().equals(npc))){
                System.out.println("You can't see "+npc+" here.");
                return false;
            }

            if( character.isShopkeeper()){
                Shopkeeper shopkeeper = (Shopkeeper) character;
                shopkeeper.buy(player, item);
                return true;
            } else{
                System.out.println("\"Sorry, I don't have it.\"");
                return false;
            }
        }
    }

    public static boolean handleGive(String[] parsedInput, Player player){

        int d = ParseInput.checkForToken(parsedInput, "to");
        if( d == -1){
            System.out.println("The syntax is: GIVE x TO y");
            return false;
        } else {
            String item = ParseInput.stitchFromTo(parsedInput, 1, d);
            String npc = ParseInput.stitchFromTo(parsedInput, d + 1, parsedInput.length);

            ArrayList<String> possibleItemFromInventory = getPotentialItem(item, player, 0);

            if (!(possibleItemFromInventory.size() == 1)) {
                System.out.println("Be more specific.");
                return false;
            } else {

                NPC character = player.getCurrentRoom().getNPC(npc);

                if (character == null || !(character.getName().toLowerCase().equals(npc))) {
                    System.out.println("You can't see " + npc + " here.");
                    return false;
                }

                if (player.give(character, possibleItemFromInventory.get(0))) {
                    return true;
                } else{
                    return false;
                }

            }
        }

    }

}
