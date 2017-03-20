package com.al0ne.Engine;

import com.al0ne.Entities.Entity;
import com.al0ne.Items.Item;
import com.al0ne.Items.Pair;
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
    public static int wrongCommand=0;
    public static String lastCommand ="";

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
            case "kill":
            case "attack":
                return handleAttack(parsedInput, player);
            case "give":
                return ParseInput.handleGive(parsedInput, player);
                //we check if it's a simple use, e.g. use potion or a complex one, e.g. use x on y
            case "use":

                int tokenOn = ParseInput.checkForToken(parsedInput, "on");
                int tokenWith = ParseInput.checkForToken(parsedInput, "with");

                //case simple use
                if (tokenOn == -1 && tokenWith == -1) {
                    return ParseInput.useItem(parsedInput, player, false, tokenOn);
                }
                //case complex use
                else if (tokenOn > -1) {
                    return ParseInput.useItem(parsedInput, player, true, tokenWith);
                } else if (tokenWith > -1){
                    return ParseInput.useItem(parsedInput, player, true, tokenOn);
                }

            case "open":
                return ParseInput.customAction(parsedInput, player, "open");
            case "wield":
                return ParseInput.handleWield(parsedInput, player);
            case "l":
            case "look":
                game.getRoom().printRoom();
                wrongCommand=0;
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
                wrongCommand=0;
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
//                player.printStatus();
                return false;
            case "g":
            case "again":
                System.out.println("Using last command:");
                return parse(lastCommand, game, turns);
            case "drop":
                return takeOrDrop(parsedInput, player, true);

            case "qqq":
                System.exit(0);

            case "time":
                System.out.println(turns + " turns elapsed.");
                wrongCommand=0;
                return false;
            default:
                wrongCommand++;
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
        wrongCommand=0;
        System.out.println("Are you sure you want to quit? (Y to exit)");
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
            wrongCommand++;
            System.out.println("Sorry?");
            return false;
        } else {
            wrongCommand=0;
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
            wrongCommand++;
            System.out.println("The syntax is "+action.toUpperCase()+" x.");
            return false;
        }
        wrongCommand=0;
        String item = ParseInput.stitchFromTo(temp, 1, temp.length);

        ArrayList<Pair> possibleEntities = getPotentialItem(item, player, 1);
        ArrayList<Pair> possibleItems = getPotentialItem(item, player, 0);

//        if (!(possibleEntities.size() + possibleItems.size() == 1)) {
//            System.out.println("Be more specific.");
//            return false;
//        }

        if (possibleItems.size()==1 && player.customAction(action, possibleItems.get(0).getEntity())){
            System.out.println("You " + action + " the " + item);
            return true;
        } else if (possibleEntities.size()==1 && player.customAction(action, possibleEntities.get(0).getEntity())) {
            System.out.println("You " + action + " the " + item);
            return true;
        } else if (possibleEntities.size() == 0 && possibleItems.size()==0){
            System.out.println("You can't see it.");
            return true;
        }else{
            System.out.println("You can't " + action + " it.");
            return true;
        }
//            else {
//                if (player.customAction(action, possibleItems.get(0).getEntity())) {
//                    System.out.println("You " + action + " the " + prop + ".");
//                    return true;
//                } else {
//                    System.out.println("You can't " + action + " it.");
//                    return false;
//                }
//            }
    }

    private static ArrayList<Pair> getAllItemsMatching (String s, Player player){
        ArrayList<Pair> result = getPotentialItem(s, player, 0);
        result.addAll(getPotentialItem(s, player, 1));
        return result;
    }

    //this attempts to get items from a token;
    //number = 0: tries to get from the inventory
    //number = 1: tries to get from Entities
    private static ArrayList<Pair> getPotentialItem(String s, Player player, int number) {

        ArrayList<Pair> potentialItems = new ArrayList<>();

        //case inventory
        if (number == 0) {

            //check if there is an exact match
            for (Pair pair : player.getInventory().values()) {
                Item b = (Item) pair.getEntity();
                if (b.getName().equals(s)) {
                    potentialItems.add(pair);
                    return potentialItems;
                }
            }

            //otherwise, parse and check for partial matches
            String[] temp = s.split(" ");
            //we check the given string token by token
            for (String token : temp) {
                //and we check with each pair in inventory if it contains the token
                for (Pair pair : player.getInventory().values()) {
                    Item a = (Item) pair.getEntity();
                    String[] currentItem = a.getName().split(" ");
                    for (String b : currentItem) {
                        if (b.toLowerCase().equals(token)) {
                            if (!potentialItems.contains(a)) {
                                potentialItems.add(pair);
                            }
                        }
                    }
                }
            }
            //case entity
        } else if (number == 1) {
            HashMap<String, Pair> entityList = player.getCurrentRoom().getEntities();
            //check if there is an exact match
            for (Pair pair : entityList.values()) {
                Entity b = pair.getEntity();
                if (b.getName().equals(s)) {
                    potentialItems.add(pair);
                    return potentialItems;
                }
            }

            //otherwise, parse and check for partial matches
            String[] temp = s.split(" ");
            //we check the given string token by token
            for (String token : temp) {
                //and we check with each pair in inventory if it contains the token
                for (Pair pair : entityList.values()) {
                    Entity a = pair.getEntity();
                    String[] currentEntity = a.getName().split(" ");
                    for (String b : currentEntity) {
                        if (b.toLowerCase().equals(token)) {
                            if (!potentialItems.contains(a)) {
                                potentialItems.add(pair);
                            }
                        }
                    }
                }
            }
        }
        return potentialItems;
    }

    /*this function is used to handle using an item:
    * boolean complex: if true, assumes the action is like USE x WITH y, otherwise it's USE x*/
    private static boolean useItem(String[] temp, Player player, boolean complex, int tokenPosition) {



        String firstItem;
        String secondItem;

        ArrayList<Pair> inventoryUse;
        ArrayList<Pair> itemUse;
        //case complex use: check we have exactly two items, then make the player use them
        if (complex) {
            if(temp.length==1){
                wrongCommand++;
                System.out.println("The syntax is USE x WITH y");
                return false;
            }
            wrongCommand=0;


            firstItem = ParseInput.stitchFromTo(temp, 1, tokenPosition);
            secondItem = ParseInput.stitchFromTo(temp, tokenPosition + 1, temp.length);

            inventoryUse = getPotentialItem(firstItem, player, 0);
            //prop from room
            itemUse = getPotentialItem(secondItem, player, 1);

            if (!(inventoryUse.size() == 1) || !(itemUse.size() == 1)) {
                System.out.println("Be more specific.");
                return false;
            }

            if (inventoryUse.size() == 1 && itemUse.size() == 1) {
                player.interactOnWith(itemUse.get(0).getEntity(), inventoryUse.get(0).getEntity());
            } else {
                System.out.println("You can't see such items");
            }

            return true;
            //case simple use: check we have just one item, then we make the player use it.
        } else {

            if(temp.length==1){
                wrongCommand++;
                System.out.println("The syntax is USE x");
                return false;
            }
            wrongCommand=0;


            firstItem = ParseInput.stitchFromTo(temp, 1, temp.length);

            inventoryUse = getPotentialItem(firstItem, player, 0);
            itemUse = getPotentialItem(firstItem, player, 2);

            //there are more possibilities from the items fetched
            if (!(inventoryUse.size() + itemUse.size() == 1)) {
                System.out.println("Be more specific.");
                return false;
            }

            if (inventoryUse.size() == 1) {
                player.simpleUse(inventoryUse.get(0).getEntity());
            } else if (itemUse.size() == 1) {
                player.simpleUse(itemUse.get(0).getEntity());
            } else {
                System.out.println("You can't use it.");
            }

            return true;
        }

    }

    //this function handles both dropping and taking items:
    //drop: false-> it's a TAKE action, true-> it's a DROP action
    //we check if it's a drop/take all action, then we check if we have exactly
    //1 item, then we take/drop (all)
    private static boolean takeOrDrop(String[] temp, Player player, boolean drop) {


        ArrayList<Pair> possibleItems;
        if (!drop) {

            if(temp.length==1){
                wrongCommand++;
                System.out.println("The syntax is TAKE (ALL) x");
                return false;
            }
            wrongCommand=0;


            boolean all = false;

            String item;
            if (temp.length > 2 && temp[1].equals("all")) {
                all = true;
                item = ParseInput.stitchFromTo(temp, 2, temp.length);
            } else {
                item = ParseInput.stitchFromTo(temp, 1, temp.length);
            }



            possibleItems = getPotentialItem(item, player, 1);

            ArrayList<Pair> items = new ArrayList<>();

            for (Pair p : possibleItems){
                if (p.getEntity().getType()=='i'){
                    items.add(p);
                }
            }



            if (items.size() > 1) {
                System.out.println("Be more specific.");
                return false;
            }


            if (all && items.size() == 1) {
                if (player.pickUpItem(items.get(0), true)){
                    System.out.println(items.get(0).getEntity().getName()+" added to your inventory.");
                }

            } else if (items.size() == 1) {
                if (player.pickUpItem(items.get(0), false)){
                    System.out.println(items.get(0).getEntity().getName()+" added to your inventory.");
                }
            } else if(possibleItems.size() != 0){
                System.out.println("You can't take it.");
            } else {
                System.out.println("You can't see such an item to take.");
            }
        } else {


            if(temp.length==1){
                wrongCommand++;
                System.out.println("The syntax is DROP (ALL) x");
                return false;
            }

            wrongCommand=0;



            boolean all = false;

            String item;
            if (temp.length > 2 && temp[1].equals("all")) {
                all = true;
                item = ParseInput.stitchFromTo(temp, 2, temp.length);
            } else {
                item = ParseInput.stitchFromTo(temp, 1, temp.length);
            }


            possibleItems = getPotentialItem(item, player, 0);

            if (possibleItems.size() > 1) {
                System.out.println("Be more specific.");
                return false;
            }


            if (all && possibleItems.size() == 1) {
                if(player.drop(possibleItems.get(0), true)){
                    System.out.println("You drop all the "+possibleItems.get(0).getEntity().getName());
                } else {
                    System.out.println("You don't seem to have a "+possibleItems.get(0).getEntity().getName()+" with you.");
                }
            } else if (possibleItems.size() == 1) {
                if (player.drop(possibleItems.get(0), false)){
                    System.out.println("You drop the "+possibleItems.get(0).getEntity().getName());
                } else {
                    System.out.println("You don't seem to have a "+possibleItems.get(0).getEntity().getName()+" with you.");
                }
            } else {
                System.out.println("You can't see such an item to drop. ");
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
            wrongCommand++;
            System.out.println("The syntax is EXAMINE x");
            return false;
        }
        wrongCommand=0;



        String toExamine = ParseInput.stitchFromTo(temp, 1, temp.length);
        ArrayList<Pair> items = getAllItemsMatching(toExamine, player);


        NPC npc = player.getCurrentRoom().getNPC(toExamine);

        if ( npc != null){
            npc.printLongDescription();
            return true;
        }

        //there are more possibilities from the items fetched
        if (items.size() > 1){
            System.out.println("Be more specific.");
            return false;
        } else if (items.size() == 0){
            System.out.println("You can't see such an item");
            return false;
        }
        player.examine(items.get(0).getEntity());

        return true;
    }

    public static boolean handleWield(String[] parsedInput, Player player){

        if(parsedInput.length==1){
            wrongCommand++;
            System.out.println("The syntax is WIELD x");
            return false;
        }

        wrongCommand=0;

        String wieldItem = ParseInput.stitchFromTo(parsedInput, 1, parsedInput.length);

        ArrayList<Pair> possibleItems = getPotentialItem(wieldItem, player, 0);

        if(possibleItems.size() == 1){
            Item item = (Item) player.getItemPair(possibleItems.get(0).getEntity().getID()).getEntity();

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

        if(parsedInput.length < 3){
            wrongCommand++;
            System.out.println("The syntax is TALK ABOUT x WITH y or also TALK TO y");
            return false;
        }
        wrongCommand=0;


        int b = ParseInput.checkForToken(parsedInput, "with");
        int to = ParseInput.checkForToken(parsedInput, "to");
        if (parsedInput[1].equals("to")) {
            String npc = ParseInput.stitchFromTo(parsedInput, to + 1, parsedInput.length);

            NPC character = player.getCurrentRoom().getNPC(npc);

            if (!ParseInput.isNPC(character,player, npc)){
                return false;
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

            ParseInput.isNPC(character,player, npc);

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
            wrongCommand++;
            System.out.println("The syntax is: BUY x FROM y");
            return false;
        } else {
            wrongCommand=0;
            String item = ParseInput.stitchFromTo(parsedInput, 1, c);
            String npc = ParseInput.stitchFromTo(parsedInput, c+1, parsedInput.length);

            NPC character = player.getCurrentRoom().getNPC(npc);


            if (!ParseInput.isNPC(character,player, npc)){
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
            wrongCommand++;
            System.out.println("The syntax is: GIVE x TO y");
            return false;
        } else {
            wrongCommand=0;

            String item = ParseInput.stitchFromTo(parsedInput, 1, d);
            String npc = ParseInput.stitchFromTo(parsedInput, d + 1, parsedInput.length);

            ArrayList<Pair> possibleItemFromInventory = getPotentialItem(item, player, 0);

            if (!(possibleItemFromInventory.size() == 1)) {
                System.out.println("Be more specific.");
                return false;
            } else {

                NPC character = player.getCurrentRoom().getNPC(npc);

                if (!ParseInput.isNPC(character,player, npc)){
                    return false;
                }

                if (player.give(character, possibleItemFromInventory.get(0).getEntity())) {
                    return true;
                } else{
                    return false;
                }

            }
        }

    }

    public static boolean handleAttack(String[] parsedInput, Player player){
        if(parsedInput.length==1){
            wrongCommand++;
            System.out.println("The syntax is ATTACK x or KILL x");
            return false;
        }
        wrongCommand=0;

        String enemy = ParseInput.stitchFromTo(parsedInput, 1, parsedInput.length);
        return player.attack(enemy);
    }

    public static boolean isNPC(NPC character, Player player, String npc){
        Entity entity = player.getCurrentRoom().getEntity(npc);

        if ((character == null ) && entity == null) {
            System.out.println("You can't see " + npc + " here.");
            return false;
        } else if (entity != null && entity.getType() != 'n'){
            System.out.println("You can't talk to it. ");
            return false;
        }
        return true;
    }

}
