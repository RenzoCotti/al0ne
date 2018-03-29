package com.al0ne.Engine.TextParsing;

import com.al0ne.AbstractEntities.*;
import com.al0ne.AbstractEntities.Enums.Command;
import com.al0ne.AbstractEntities.Pairs.Pair;
import com.al0ne.AbstractEntities.Pairs.PotentialItems;
import com.al0ne.AbstractEntities.Pairs.SpellPair;
import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.Player.PlayerActions;
import com.al0ne.AbstractEntities.Abstract.*;
import com.al0ne.Engine.Main;
import com.al0ne.Engine.Utility.Utility;
import com.al0ne.ConcreteEntities.Items.Types.Container;
import com.al0ne.ConcreteEntities.Items.Types.Wearable.RangedWeapon;
import com.al0ne.ConcreteEntities.Items.Types.Wearable.Weapon;
import com.al0ne.ConcreteEntities.Items.ConcreteItems.Books.Spellbook;
import com.al0ne.ConcreteEntities.NPCs.Shopkeeper;
import com.al0ne.ConcreteEntities.Spells.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 24/04/2017.
 */
public class HandleCommands {
    //this function handles the command exit
    public static void quit() {
        ParseInput.wrongCommand = 0;
        if (ParseInput.lastCommand.equals("quit") || ParseInput.lastCommand.equals("exit")) {
            System.exit(0);
        }
        printToLog("Are you sure you want to quit? (retype \"quit\" to exit)");
    }

    //this handles moving: it checks for exactly one token after the command
    public static boolean move(Player player, HashMap<String, Room> rooms, String direction, String[] temp) {
        if (temp.length > 2) {
            ParseInput.wrongCommand++;
            printToLog("Sorry?");
            return false;
        } else {
            ParseInput.wrongCommand = 0;
            if (PlayerActions.moveToRoom(player, direction, rooms)) {
                Main.clearScreen();
                if (player.getCurrentRoom().isFirstVisit()) {
                    player.getCurrentRoom().printRoom();
                    player.getCurrentRoom().visit();
                } else{
                    player.getCurrentRoom().printEnemy();
                    player.getCurrentRoom().printNPCs();
                    player.getCurrentRoom().printDirections();
                    printToLog();
                }
                return true;
            }

            return false;
        }
    }

    //this handles trying to apply custom commands on objects
    public static boolean customAction(String[] temp, Player player, Command action, String s) {
        if (temp.length == 1) {
            ParseInput.wrongCommand++;
            printToLog("The syntax is " + action + " x.");
            return false;
        }

        ParseInput.wrongCommand = 0;
        String item = Utility.stitchFromTo(temp, 1, temp.length);

        ArrayList<Pair> possibleEntities = getPotentialItem(item, player, 1).getItems();
        ArrayList<Pair> possibleItems = getPotentialItem(item, player, 0).getItems();

//        if (!(possibleEntities.size() + possibleItems.size() == 1)) {
//            printToLog("Be more specific.");
//            return false;
//        }
        if (possibleItems.size() == 1 || possibleEntities.size() == 1) {
            String result = null;
            ArrayList<Pair> toUse;
            if(possibleItems.size() == 1){
                toUse = possibleItems;
                result = PlayerActions.customAction(player, action, possibleItems.get(0).getEntity());
            } else{
                result = PlayerActions.customAction(player, action, possibleEntities.get(0).getEntity());
                toUse = possibleEntities;
            }

            if(result != null && result.equals("")){
                printToLog("You " + s + " the " + toUse.get(0).getEntity().getName());
            } else if(result != null){
                printToLog("You " + s + " the " + toUse.get(0).getEntity().getName());
                printToLog(result);
            } else{
                printToLog("You can't " + s + " it.");
            }
            return true;
        } else if (possibleEntities.size() == 0 && possibleItems.size() == 0) {
            printToLog("You can't see it.");
            return true;
        } else {
            printToLog("You can't " + s + " it.");
            return true;
        }
    }

    public static ArrayList<Pair> getAllItemsMatching(String s, Player player) {
        ArrayList<Pair> result = getPotentialItem(s, player, 0).getItems();
        result.addAll(getPotentialItem(s, player, 1).getItems());
        return result;
    }

    //this attempts to get items from a token;
    //number = 0: tries to get from the inventory
    //number = 1: tries to get from ConcreteEntities
    //number = 2: tries to get from containers
    public static PotentialItems getPotentialItem(String s, Player player, int number) {

        ArrayList<Pair> potentialItems = new ArrayList<>();
        PotentialItems totalItems = new PotentialItems(potentialItems, 0);

        if(number == 0 || number == 1){
            HashMap<String, Pair> toCheck;
            if(number == 0){
                toCheck = player.getInventory();
            } else {
                toCheck = player.getCurrentRoom().getEntities();
            }

            //check if there is an exact match
            for (Pair pair : toCheck.values()) {
                Entity b;
                if(number == 0){
                    b = pair.getEntity();
                } else {
                    b = pair.getEntity();
                }

                if (b.getName().equals(s)) {
                    potentialItems.add(pair);
                    int reliability = s.split(" ").length;
                    totalItems.setReliability(reliability);
                    return totalItems;
                }
            }

            //otherwise, parse and check for partial matches
            String[] temp = s.split(" ");
            int max = 0;
            //we check the given string token by token
            for (Pair pair : toCheck.values()){
                int reliability = 0;
                //and we check with each pair in inventory if it contains the token
                for (String token : temp) {
                    Entity a = pair.getEntity();

                    String[] currentItem = a.getName().split(" ");
                    for (String b : currentItem) {
                        if (b.toLowerCase().equals(token)) {
                            reliability++;
                            if (reliability > max) {
                                potentialItems.clear();
                                max = reliability;
                                totalItems.setReliability(max);
                                potentialItems.add(pair);
                            } else if(reliability == max){
                                potentialItems.add(pair);
                            }
                        }
                    }
                }
            }
        } else if (number == 2) {
            ArrayList<Container> containers = player.getCurrentRoom().getContainers();
            //check if there is an exact match
            for (Container container : containers) {
                ArrayList<Pair> b = container.getItems();
                for (Pair p : b) {
                    Item i = (Item) p.getEntity();
                    if (i.getName().equals(s)) {
                        potentialItems.add(p);
                        int reliability = s.split(" ").length;
                        return new PotentialItems(potentialItems, reliability);
                    }
                }

            }

            //otherwise, parse and check for partial matches
            String[] temp = s.split(" ");
            //we check the given string token by token


            for (String token : temp) {
                //and we check with each pair in inventory if it contains the token
                for (Container container : containers) {
                    ArrayList<Pair> b = container.getItems();
                    for (Pair p : b) {
                        Item i = (Item) p.getEntity();
                        String itemName[] = i.getName().toLowerCase().split(" ");
                        for (String string : itemName) {
                            if (string.equals(token)) {
                                potentialItems.add(p);
                                int reliability = s.split(" ").length;
                                totalItems.setReliability(reliability);
                                return totalItems;
                            }
                        }
                    }
                }
            }
        }
        return totalItems;
    }

    /*this function is used to handle using an item:
    * boolean complex: if true, assumes the action is like USE x WITH y, otherwise it's USE x*/
    public static boolean useItem(String[] temp, Player player, boolean complex, int tokenPosition) {

        String firstItem;
        String secondItem;

        PotentialItems inventoryItems;
        PotentialItems roomItems;
        //case complex use: check we have exactly two items, then make the player use them
        if (complex) {
            if (temp.length < 2) {
                ParseInput.wrongCommand++;
                printToLog("The syntax is USE x WITH y");
                return false;
            }
            ParseInput.wrongCommand = 0;


            //case use x with y
            firstItem = Utility.stitchFromTo(temp, 1, tokenPosition);
            secondItem = Utility.stitchFromTo(temp, tokenPosition + 1, temp.length);

            //we try to get all potential items from inv
            inventoryItems = getPotentialItem(firstItem, player, 0);
            //and from the room
            PotentialItems possibleEntities = getPotentialItem(firstItem, player, 1);
            if(inventoryItems.getReliability() < possibleEntities.getReliability()){
                inventoryItems = possibleEntities;
            }
            //prop from room
            roomItems = getPotentialItem(secondItem, player, 0);
            possibleEntities = getPotentialItem(secondItem, player, 1);
            if(roomItems.getReliability() < possibleEntities.getReliability()){
                roomItems = possibleEntities;
            }

            if (inventoryItems.getItems().size() > 1 || roomItems.getItems().size() > 1) {
                printToLog("Be more specific.");
                return false;
            }

//            System.out.println("candidate inv items: "+inventoryItems.getItems().size()+", item: "+firstItem);
//            System.out.println("candidate room items: "+roomItems.getItems().size()+", item: "+secondItem);

            if (inventoryItems.getItems().size() == 1 && roomItems.getItems().size() == 1) {
//                System.out.println("using: ");
//                for(Pair p : roomItems.getItems()){
//                    System.out.println(p.getEntity().getName());
//                }
//
//                System.out.println("on: ");
//                for(Pair p : inventoryItems.getItems()){
//                    System.out.println(p.getEntity().getName());
//                }
//                if (inventoryUse.get(0).getEntity().getType() == 'i' && itemUse.get(0).getEntity().getType() == 'p'){
                PlayerActions.interactOnWith(player, roomItems.getItems().get(0).getEntity(), inventoryItems.getItems().get(0).getEntity());

            } else {
                printToLog("You can't see such items");
            }

            return true;
            //case simple use: check we have just one item, then we make the player use it.
        } else {


            if (temp.length == 1) {
                ParseInput.wrongCommand++;
                printToLog("The syntax is USE x");
                return false;
            }
            ParseInput.wrongCommand = 0;


            firstItem = Utility.stitchFromTo(temp, 1, temp.length);

            inventoryItems = getPotentialItem(firstItem, player, 0);
            roomItems = getPotentialItem(firstItem, player, 1);

            //there are more possibilities from the items fetched
            if ((inventoryItems.getItems().size() + roomItems.getItems().size() == 0)) {
                printToLog("You can't see that.");
                return false;
            }

            //there are more possibilities from the items fetched
            if (!(inventoryItems.getItems().size() + roomItems.getItems().size() == 1)) {
                printToLog("Be more specific.");
                return false;
            }

            if (inventoryItems.getItems().size() == 1) {

                String result = PlayerActions.simpleUse(player, inventoryItems.getItems().get(0).getEntity());

                if (result == null) {
                    printToLog("You can't use it.");
                }
            } else if (roomItems.getItems().size() == 1) {
                String result = PlayerActions.simpleUse(player, roomItems.getItems().get(0).getEntity());
                if (result == null) {
                    printToLog("You can't use it.");
                }
            } else {
                printToLog("You can't use it.");
            }

            return true;
        }

    }


    public static boolean takePutContainer(String[] temp, Player player, int fromToken, boolean all, boolean take) {

        if(temp.length < 2 && take){
            printToLog("The syntax is TAKE x FROM container.");
            return false;
        } else if(temp.length < 2){
            printToLog("The syntax is PUT x IN container.");
            return false;
        }

        int amount;
        String item;
        String container;



        try{
            Integer.parseInt(temp[1]);
            item = Utility.stitchFromTo(temp, 2, fromToken);
            amount = Integer.parseInt(temp[1]);
            container = Utility.stitchFromTo(temp, fromToken + 1, temp.length);

        } catch (NumberFormatException ex){
            if(all){
                item = Utility.stitchFromTo(temp, 2, fromToken);
            } else{
                item = Utility.stitchFromTo(temp, 1, fromToken);
            }
            amount=1;
            container = Utility.stitchFromTo(temp, fromToken + 1, temp.length);
        }


        PotentialItems items;
        if (take) {
            items = HandleCommands.getPotentialItem(item, player, 2);
        } else {
            items = HandleCommands.getPotentialItem(item, player, 0);
        }
        PotentialItems containers = HandleCommands.getPotentialItem(container, player, 1);

        ArrayList<Pair> possibleItems = items.getItems();
        ArrayList<Pair> possibleContainers = containers.getItems();


        if ((possibleItems.size() == 0 || possibleContainers.size() == 0)) {
            printToLog("You can't see that.");
            return false;
        }

        if ((possibleItems.size() > 1 || possibleContainers.size() > 1)) {
            printToLog("Be more specific.");
            return false;
        }

        if (possibleItems.size() == 1 && possibleContainers.size() == 1) {

            Pair currentPair = possibleItems.get(0);

            int count = possibleItems.get(0).getCount();
            Item currentItem = (Item) currentPair.getEntity();
            Container currentContainer = (Container) possibleContainers.get(0).getEntity();

            if(all){
                amount=possibleItems.get(0).getCount();
            }

            boolean result;
            if(take){
                result = PlayerActions.takeFrom(player, possibleItems.get(0), currentContainer, amount);
            } else{
                result = PlayerActions.putIn(player, possibleItems.get(0), currentContainer, amount);
            }
            if (result && take) {
                if (all) {
                    printToLog(count + " x " +currentItem.getName() + " added to your inventory.");
                } else {
                    printToLog(currentItem.getName() + " added to your inventory.");
                }
                currentPair.setLocation('i');
            } else if(result){
                if (all) {
                    printToLog(count + " x " + currentItem.getName() + " put in " + currentContainer.getName());
                } else {
                    printToLog(currentItem.getName() + " put in " + currentContainer.getName());
                }
                currentPair.setLocation('c');
            }
        } else {
            printToLog("You can't take it.");
        }
        return true;
    }


    //this function handles both dropping and taking items:
    //drop: false-> it's a TAKE action, true-> it's a DROP action
    //we check if it's a drop/take all action, then we check if we have exactly
    //1 item, then we take/drop (all)
    public static boolean handleTake(String[] temp, Player player) {

        //TODO: FIX GET POSSIBLE ITEMS, ADD RELIABILITY OF GUESS, ADD ONLY IF BIG ENOUGH
        ArrayList<Pair> possibleItems;
        Room currentRoom = player.getCurrentRoom();

        if (temp.length == 1) {
            ParseInput.wrongCommand++;
            printToLog("The syntax is TAKE (ALL) x");
            return false;
        }
        ParseInput.wrongCommand = 0;

        //handles taking all items in room
        if(temp.length == 2 && temp[1].equals("all")){
            possibleItems = currentRoom.getItemList();

            if(possibleItems.size() == 0){
                printToLog("There are no items here.");
                return false;
            }
            for(Pair p : possibleItems){
                int oldCount = p.getCount();
                if(PlayerActions.pickUpItem(player, p, p.getCount()) == 0){
                    break;
                }
                Pair pair = player.getItemPair(p.getEntity().getID());

                Item currentItem = (Item) pair.getEntity();
                p.setLocation('i');
                if(oldCount > 1){
                    printToLog("Taken "+oldCount+"x "+currentItem.getName()+".");
                } else{
                    printToLog("Taken "+currentItem.getName()+".");
                }
            }
            return true;
        }


        boolean all = false;

        String item;

        //we try to get a number from temp, eg. take 12 rock
        int amt = 0;
        try{
            Integer.parseInt(temp[1]);
            item = Utility.stitchFromTo(temp, 2, temp.length);
            amt = Integer.parseInt(temp[1]);

        } catch (NumberFormatException ex){
            if (2 < temp.length && temp[1].equals("all")) {
                all = true;
                item = Utility.stitchFromTo(temp, 2, temp.length);
            } else {
                item = Utility.stitchFromTo(temp, 1, temp.length);
            }
        }


        possibleItems = getPotentialItem(item, player, 1).getItems();

        ArrayList<Pair> items = new ArrayList<>();

        //we get only the items we can pickup
        for (Pair p : possibleItems) {
            if (p.getEntity().getType() == 'i' || p.getEntity().getType() == 'w' ||
                    p.getEntity().getType() == 'C' || p.getEntity().getType() == 'p') {
                items.add(p);
            }
        }


        if (items.size() > 1) {
            printToLog("Be more specific.");
            return false;
        } else if(items.size() == 1){
            Pair currentPair = items.get(0);
            Interactable currentItem = (Interactable) currentPair.getEntity();
            int count = currentPair.getCount();
            if (all) {
                if (PlayerActions.pickUpItem(player, currentPair, count) == 1) {
                    currentPair.setLocation('i');
                    printToLog(count+"x "+currentItem.getName() + " added to your inventory.");
                }

            } else if (amt != 0) {
                currentPair.setLocation('i');
                if (PlayerActions.pickUpItem(player, items.get(0), amt) == 1) {
                    printToLog(amt+"x "+currentItem.getName() + " added to your inventory.");
                }
            } else {
                if (PlayerActions.pickUpItem(player, items.get(0), 1) == 1) {
                    currentPair.setLocation('i');
                    printToLog(currentItem.getName() + " added to your inventory.");
                }
            }
        } else {
            printToLog("You can't see such an item to take.");
        }

        return true;
    }

    public static boolean handleDrop(String[] temp, Player player){


        if (temp.length == 1) {
            ParseInput.wrongCommand++;
            printToLog("The syntax is DROP (ALL) x");
            return false;
        }

        ArrayList<Pair> possibleItems;

        ParseInput.wrongCommand = 0;

        if(temp.length == 2 && temp[1].equals("all")){
            Collection<Pair> cp = player.getInventory().values();
            ArrayList<Pair> toRemove = new ArrayList<>();

            if(cp.size() == 0){
                printToLog("You don't have any item with you.");
                return false;
            }
            for(Pair p : cp){
                if(((Item)p.getEntity()).canDrop()){
                    toRemove.add(p);
                }
            }
            for(Pair p : toRemove){
                if(PlayerActions.drop(player, p, p.getCount())==1){
                    Item currentItem = (Item) p.getEntity();
                    p.setLocation('r');
                    printToLog("Dropped "+currentItem.getName()+".");
                }
            }
            return true;
        }


        boolean all = false;

        String item;
        int amt=0;

        try{
            Integer.parseInt(temp[1]);
            item = Utility.stitchFromTo(temp, 2, temp.length);
            amt = Integer.parseInt(temp[1]);

        } catch (NumberFormatException ex){
            if (temp.length > 2 && temp[1].equals("all")) {
                all = true;
                item = Utility.stitchFromTo(temp, 2, temp.length);
            } else {
                item = Utility.stitchFromTo(temp, 1, temp.length);
            }
        }



        possibleItems = getPotentialItem(item, player, 0).getItems();

        if (possibleItems.size() > 1) {
            printToLog("Be more specific.");
            return false;
        } else if(possibleItems.size() == 1){
            Item i = (Item)possibleItems.get(0).getEntity();
            Pair p = possibleItems.get(0);
            if (all) {
                int result = PlayerActions.drop(player, p, p.getCount());
                if (result == 1) {
                    p.setLocation('r');
                    printToLog("You drop all the " + i.getName());
                } else if(result == 0){
                    printToLog("You don't seem to have a " + i.getName() + " with you.");
                }
            } else if (amt != 0) {
                int result = PlayerActions.drop(player, p, amt);
                if (result == 1) {
                    p.setLocation('r');
                    printToLog("You drop "+ amt+" "+ i.getName());
                } else if (result == 0){
                    printToLog("You don't seem to have a " + i.getName() + " with you.");
                }
            } else {
                int result = PlayerActions.drop(player, p, 1);
                if (result == 1) {
                    p.setLocation('r');
                    printToLog("You drop the " + i.getName());
                } else if (result == 0){
                    printToLog("You don't seem to have a " + i.getName() + " with you.");
                }
            }
        } else {
            printToLog("You can't see such an item to drop. ");
        }

        return true;
    }


    //this function handles examining an object:
    //we look in the room for props and items, as well as in the player's inventory
    //if the search returns exactly one item, we examine it
    public static boolean handleExamine(String[] temp, Player player) {

        if (temp.length == 1) {
            ParseInput.wrongCommand++;
            printToLog("The syntax is EXAMINE x");
            return false;
        }
        ParseInput.wrongCommand = 0;


        String toExamine = Utility.stitchFromTo(temp, 1, temp.length);
        ArrayList<Pair> items = getAllItemsMatching(toExamine, player);


        NPC npc = player.getCurrentRoom().getNPC(toExamine);

        if (npc != null) {
            npc.printLongDescription(null);
            return false;
        }

        //there are more possibilities from the items fetched
        if (items.size() > 1) {
            printToLog("Be more specific.");
            return false;
        } else if (items.size() == 0) {
            printToLog("You can't see such an item");
            return false;
        }
        PlayerActions.examine(player, items.get(0).getEntity());

        return false;
    }

    public static boolean handleWear(String[] parsedInput, Player player) {

        if (parsedInput.length == 1) {
            ParseInput.wrongCommand++;
            printToLog("The syntax is WIELD x");
            return false;
        }

        ParseInput.wrongCommand = 0;

        String wieldItem = Utility.stitchFromTo(parsedInput, 1, parsedInput.length);

        PotentialItems items = getPotentialItem(wieldItem, player, 0);
        PotentialItems entities = getPotentialItem(wieldItem, player, 1);

        ArrayList<Pair> possibleItems = items.getItems();
        ArrayList<Pair> possibleEntities = entities.getItems();

        if (possibleItems.size() == 1 && items.getReliability() > entities.getReliability()) {
            Item item = (Item) player.getItemPair(possibleItems.get(0).getEntity().getID()).getEntity();

            if (player.wear(item)) {
                return true;
            } else {
                printToLog("You don't seem to have a " + wieldItem);
                return false;
            }
        } else if (possibleItems.size() > 1) {
            printToLog("Be more specific.");
            return false;
        } else if(possibleEntities.size() == 1 && items.getReliability() < entities.getReliability()){
            printToLog("(first taking the "+possibleEntities.get(0).getEntity().getName()+")");
            if(PlayerActions.pickUpItem(player, possibleEntities.get(0), 1) == 1){
                possibleEntities.get(0).setLocation('i');
                Item item = (Item) player.getItemPair(possibleEntities.get(0).getEntity().getID()).getEntity();

                if (player.wear(item)) {
                   return true;
                } else {
                    printToLog("You can't seem to see a " + wieldItem);
                    return false;
                }
            } else{
                printToLog("You can't carry it.");
                return false;
            }
        } else if (possibleEntities.size() > 1) {
            printToLog("Be more specific.");
            return false;
        } else {
            printToLog("You don't seem to have a " + wieldItem);
            return false;
        }
    }




    public static void printWelcome() {
        if(Main.nostalgiaMode){
            printToLog("      __       _        _______  __     _  ________\n" +
                    "     /  \\     | |      /   _   \\|  \\   | ||  _____/\n" +
                    "    / /\\ \\    | |      |  | |  ||   \\  | || |\n" +
                    "   / /__\\ \\   | |      |  | |  || |\\ \\ | || +--/\n" +
                    "  /  ____  \\  | |      |  | |  || | \\ \\| || +--\\\n" +
                    " /  /    \\  \\ | |_____ |  |_|  || |  \\ ` || |_____        \n" +
                    "/__/      \\__\\|_______\\\\_______/|_|   \\__||_______\\\n\n\n");
        }
        
        printToLog("Welcome to my textual adventure game!");
        printToLog("I hope you enjoy your time playing");
        printToLog();
        printToLog("Please report any weird/unexpected behaviour to me :D");

        printToLog();
        printToLog();
        printToLog();
        printToLog();

    }

    public static void printHelp() {
        printToLog("You can type \"north\" to go north, \"east\" to go east,... (shortcut: \"n\" for north, \"s\" for south,...)");
        printToLog("Useful commands: \"examine a\", where a is an object you can see (shortcut: \"x a\")");
        printToLog("\"use x on y\", \"use x\", \"talk to x\", \"attack x\", \"take x\", \"inventory\" (shortcut: \"i\")");
        printToLog();
    }


    public static boolean handleTalk(String[] parsedInput, Player player) {

        if (parsedInput.length < 3) {
            ParseInput.wrongCommand++;
            printToLog("The syntax is TALK ABOUT x WITH y or also TALK TO y");
            return false;
        }
        ParseInput.wrongCommand = 0;




        int with = Utility.checkForToken(parsedInput, "with");
        int to = Utility.checkForToken(parsedInput, "to");

        String npcName;
        NPC character;

        if(parsedInput[0].equals("ask")){
            int about = Utility.checkForToken(parsedInput, "about");
            if(about == -1){
                printToLog("The syntax is: ASK x ABOUT y.");
                return false;
            }

            npcName = Utility.stitchFromTo(parsedInput, 1, about);
            String subject = Utility.stitchFromTo(parsedInput, about+1, parsedInput.length);

            character = player.getCurrentRoom().getNPC(npcName);

            if(character != null){
                PlayerActions.talkToNPC(player, npcName, subject);
            }
            return true;
        }

        //case generic talk
        if (parsedInput[1].equals("to")) {
            npcName = Utility.stitchFromTo(parsedInput, to + 1, parsedInput.length);

            character = player.getCurrentRoom().getNPC(npcName);

            if (!HandleCommands.isNPC(player, npcName)) {
                return false;
            }

            character.printIntro();
            return true;
        } else if (with == -1 || !(parsedInput[1].equals("about"))) {
            printToLog("The syntax is: TALK ABOUT x WITH y");
            return false;
        } else {
            //case complex talk
            String subject = Utility.stitchFromTo(parsedInput, 2, with);
            npcName = Utility.stitchFromTo(parsedInput, with + 1, parsedInput.length);

//            NPC character = player.getCurrentRoom().getNPC(npc);

            if(HandleCommands.isNPC(player, npcName)){
                if (PlayerActions.talkToNPC(player, npcName, subject)) {
                    return true;
                } else {
                    printToLog("\"Sorry, I don't know anything about it.\"");
                    return true;
                }
            }
            return false;
        }
    }

    public static boolean handleBuy(String[] parsedInput, Player player) {

        int c = Utility.checkForToken(parsedInput, "from");
        if (c == -1) {
            ParseInput.wrongCommand++;
            printToLog("The syntax is: BUY x FROM y");
            return false;
        } else {
            ParseInput.wrongCommand = 0;
            String item = Utility.stitchFromTo(parsedInput, 1, c);
            String npc = Utility.stitchFromTo(parsedInput, c + 1, parsedInput.length);

            NPC character = player.getCurrentRoom().getNPC(npc);


            if (!HandleCommands.isNPC(player, npc)) {
                return false;
            }

            if (character.isShopkeeper()) {
                Shopkeeper shopkeeper = (Shopkeeper) character;
                shopkeeper.buy(player, item);
                return true;
            } else {
                printToLog("\"Sorry, I don't have it.\"");
                return false;
            }
        }
    }

    public static boolean handleGive(String[] parsedInput, Player player) {

        int d = Utility.checkForToken(parsedInput, "to");
        if (d == -1) {
            ParseInput.wrongCommand++;
            printToLog("The syntax is: GIVE x TO y");
            return false;
        } else {
            ParseInput.wrongCommand = 0;

            String item = Utility.stitchFromTo(parsedInput, 1, d);
            String npc = Utility.stitchFromTo(parsedInput, d + 1, parsedInput.length);

            ArrayList<Pair> possibleItemFromInventory = getPotentialItem(item, player, 0).getItems();

            if ((possibleItemFromInventory.size() > 1)) {
                printToLog("Be more specific.");
                return false;
            } else if (possibleItemFromInventory.size() == 0) {
                printToLog("You don't have that item.");
                return false;
            } else {

                NPC character = player.getCurrentRoom().getNPC(npc);

                if (!HandleCommands.isNPC(player, npc)) {
                    return false;
                }

                if (PlayerActions.give(player, character, possibleItemFromInventory.get(0).getEntity())) {
                    return true;
                } else {
                    return false;
                }

            }
        }

    }

    public static boolean handleReload(String[] parsedInput, Player player){

        if (parsedInput.length == 1) {
            ParseInput.wrongCommand++;
            printToLog("The syntax is RELOAD x.");
            return false;
        }

        ParseInput.wrongCommand = 0;

        String weaponString = Utility.stitchFromTo(parsedInput, 1, parsedInput.length);

        PotentialItems possibleItems = getPotentialItem(weaponString, player, 0);

        if(possibleItems.getItems().size() > 1){
            printToLog("Be more specific.");
        } else if(possibleItems.getItems().size() == 0){
            printToLog("You can't see that item.");
        } else {
            if(possibleItems.getItems().get(0).getEntity() instanceof Weapon){
                return PlayerActions.reload(player, (Weapon) possibleItems.getItems().get(0).getEntity());
            } else {
                printToLog("That's not a weapon.");
            }
        }
        return false;
    }

    public static boolean handleAttack(String[] parsedInput, Player player, boolean execute) {
        if (parsedInput.length == 1) {
            ParseInput.wrongCommand++;
            printToLog("The syntax is SHOOT AT/ATTACK/KILL x.");
            return false;
        }

        Room currentRoom = player.getCurrentRoom();

        String enemyName;
        String toCheck = parsedInput[0]+" at";
        if(toCheck.equals("shoot at") || toCheck.equals("fire at")){
            enemyName = Utility.stitchFromTo(parsedInput, 2, parsedInput.length);

            Weapon weapon = player.getWeapon();
            if( weapon != null && !(weapon instanceof RangedWeapon) ){
                printToLog("You can't shoot with your "+ weapon.getName()+".");
                return false;
            } else if(weapon == null){
                printToLog("You can't shoot with your fists.");
                return false;
            }
        } else {
            enemyName = Utility.stitchFromTo(parsedInput, 1, parsedInput.length);
        }



        ParseInput.wrongCommand = 0;

        //we get all entities similar to that name
        ArrayList<Pair> entities = getPotentialItem(enemyName, player, 1).getItems();
        if (entities.size() == 1 && !execute) {
            Entity enemy = entities.get(0).getEntity();
            if( enemy instanceof NPC && ((NPC) enemy).isQuestCharacter()){
                printToLog("It's best not to attack "+ enemy.getName()+".");
                return false;
            } else if(enemy instanceof WorldCharacter){
                return PlayerActions.attack(player, (WorldCharacter) entities.get(0).getEntity());
            } else {
                printToLog("The "+enemy.getName().toLowerCase() +" isn't threatening.");
            }
            return false;
        } else if (entities.size() == 1) {
            if(entities.get(0).getEntity().getType() == 'n'){
                ((Enemy)entities.get(0).getEntity()).handleLoot(player);
                currentRoom.getEntities().remove(entities.get(0).getEntity().getID());
                printToLog("You executed the "+entities.get(0).getEntity().getName());
                return true;
            }
            return false;
        } else if (entities.size() > 1) {
            printToLog("Be more specific");
            return false;
        } else {
            printToLog("You can't see a " + enemyName);
            return false;
        }
    }

    public static boolean isNPC(Player player, String npc) {
        Entity entity = player.getCurrentRoom().getEntity(npc);

        if (entity == null) {
            printToLog("You can't see " + npc + " here.");
            return false;
        } else if (entity.getType() != 'n') {
            printToLog("You can't talk to it. ");
            return false;
        }
        return true;
    }


    public static ArrayList<Enemy> getPotentialEnemy(String s, Player player) {

        ArrayList<Enemy> potentialEnemies = new ArrayList<>();


        //check if there is an exact match
        for (Pair pair : player.getCurrentRoom().getEntities().values()) {
            Entity en = pair.getEntity();

            if (en.getType() == 'e' && en.getName().equals(s)) {
                potentialEnemies.add((Enemy) en);
                return potentialEnemies;
            }
        }

        //otherwise, parse and check for partial matches
        String[] temp = s.split(" ");
        //we check the given string token by token
        for (String token : temp) {
            //and we check with each pair in inventory if it contains the token
            for (Pair p : player.getCurrentRoom().getEntities().values()) {
                Entity en = p.getEntity();
                String[] currentEnemy = en.getName().split(" ");
                for (String b : currentEnemy) {
                    if (b.toLowerCase().equals(token)) {
                        if (!potentialEnemies.contains(en)) {
                            potentialEnemies.add((Enemy) en);
                        }
                    }
                }
            }
        }

        return potentialEnemies;
    }


    public static ArrayList<Spell> getPotentialSpell(String s, Spellbook sb) {

        ArrayList<Spell> potentialSpells = new ArrayList<>();


        //check if there is an exact match
        for (SpellPair pair : sb.getSpells().values()) {
            Spell spell = pair.getSpell();

            if (spell.getName().equals(s)) {
                potentialSpells.add(spell);
                return potentialSpells;
            }
        }

        //otherwise, parse and check for partial matches
        String[] temp = s.split(" ");
        //we check the given string token by token
        for (String token : temp) {

            for (SpellPair pair : sb.getSpells().values()) {
                Spell spell = pair.getSpell();

                String[] currentSpellName = spell.getName().split(" ");
                for (String b : currentSpellName) {
                    if (b.toLowerCase().equals(token)) {
                        if (!potentialSpells.contains(spell)) {
                            potentialSpells.add(spell);
                        }
                    }
                }
            }
        }

        return potentialSpells;
    }

    public static boolean handleCast(String[] parsedInput, Player player){
        Room currentRoom = player.getCurrentRoom();
        if(parsedInput.length == 1){
            printToLog("The syntax is CAST spell (AT/ON target)");
            return false;
        }

        int tokenAt = Utility.checkForToken(parsedInput, "at");
        int tokenOn = Utility.checkForToken(parsedInput, "on");
        tokenAt = Math.max(tokenAt, tokenOn);
        if (tokenAt == -1) {


            String spellName = Utility.stitchFromTo(parsedInput, 1, parsedInput.length);

            PlayerActions.castSpell(player, spellName);



            return false;
        } else {


            String spellName = Utility.stitchFromTo(parsedInput, 1, tokenAt);
            String target = Utility.stitchFromTo(parsedInput, tokenAt + 1, parsedInput.length);

            PlayerActions.castAtTarget(player, spellName, target);
        }
        return false;
    }

}
