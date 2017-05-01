package com.al0ne.Engine;

import com.al0ne.Behaviours.*;
import com.al0ne.Behaviours.Pairs.Pair;
import com.al0ne.Behaviours.Pairs.SpellPair;
import com.al0ne.Entities.Items.Behaviours.Container;
import com.al0ne.Entities.Items.ConcreteItems.Spellbook;
import com.al0ne.Entities.NPCs.Shopkeeper;
import com.al0ne.Entities.Spells.*;

import java.util.ArrayList;
import java.util.HashMap;

import static com.al0ne.Engine.Main.currentRoom;
import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 24/04/2017.
 */
public class HandleCommands {
    //this function handles the command exit
    public static void quit() {
        ParseInput.wrongCommand = 0;
        if (ParseInput.lastCommand.equals("quit")) {
            System.exit(0);
        }
        printToLog("Are you sure you want to quit? (retype \"quit\" to exit)");
    }

    //this handles moving: it checks for exactly one token after the command
    public static boolean move(Player player, HashMap<String, Room> rooms, String direction, String[] temp) {
        if (temp.length > 1) {
            ParseInput.wrongCommand++;
            printToLog("Sorry?");
            return false;
        } else {
            ParseInput.wrongCommand = 0;
            if (player.moveToRoom(direction, rooms)) {
                Main.clearScreen();
                if (player.getCurrentRoom().isFirstVisit()) {
                    player.getCurrentRoom().printRoom();
                    player.getCurrentRoom().visit();
                } else{
                    player.getCurrentRoom().printDirections();
                    printToLog();
                }
                return true;
            }

            return false;
        }
    }

    //this handles trying to apply custom commands on objects
    public static boolean customAction(String[] temp, Player player, String action) {
        if (temp.length == 1) {
            ParseInput.wrongCommand++;
            printToLog("The syntax is " + action + " x.");
            return false;
        }

        ParseInput.wrongCommand = 0;
        String item = Utility.stitchFromTo(temp, 1, temp.length);

        ArrayList<Pair> possibleEntities = getPotentialItem(item, player, 1);
        ArrayList<Pair> possibleItems = getPotentialItem(item, player, 0);

//        if (!(possibleEntities.size() + possibleItems.size() == 1)) {
//            printToLog("Be more specific.");
//            return false;
//        }

        if (possibleItems.size() == 1) {
            int result = player.customAction(action, possibleItems.get(0).getEntity());
            if(result == 1){
                printToLog("You " + action + " the " + item);
            }
            return true;
        } else if (possibleEntities.size() == 1) {
            int result = player.customAction(action, possibleEntities.get(0).getEntity());
            if(result == 1){
                printToLog("You " + action + " the " + item);
            }
            return true;
        } else if (possibleEntities.size() == 0 && possibleItems.size() == 0) {
            printToLog("You can't see it.");
            return true;
        } else {
            printToLog("You can't " + action + " it.");
            return true;
        }
    }

    public static ArrayList<Pair> getAllItemsMatching(String s, Player player) {
        ArrayList<Pair> result = getPotentialItem(s, player, 0);
        result.addAll(getPotentialItem(s, player, 1));
        return result;
    }

    //this attempts to get items from a token;
    //number = 0: tries to get from the inventory
    //number = 1: tries to get from Entities
    //number = 2: tries to get from containers
    public static ArrayList<Pair> getPotentialItem(String s, Player player, int number) {

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
                            if (!potentialItems.contains(pair)) {
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
                            if (!potentialItems.contains(pair)) {
                                potentialItems.add(pair);
                            }
                        }
                    }
                }
            }
            //case it's a container
        } else if (number == 2) {
            ArrayList<Container> containers = player.getCurrentRoom().getContainers();
            //check if there is an exact match
            for (Container container : containers) {
                ArrayList<Pair> b = container.getItems();
                for (Pair p : b) {
                    Item i = (Item) p.getEntity();
                    if (i.getName().equals(s)) {
                        potentialItems.add(p);
                        return potentialItems;
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
                                return potentialItems;
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
    public static boolean useItem(String[] temp, Player player, boolean complex, int tokenPosition) {

        String firstItem;
        String secondItem;

        ArrayList<Pair> inventoryUse;
        ArrayList<Pair> itemUse;
        //case complex use: check we have exactly two items, then make the player use them
        if (complex) {
            if (temp.length < 2) {
                ParseInput.wrongCommand++;
                printToLog("The syntax is USE x WITH y");
                return false;
            }
            ParseInput.wrongCommand = 0;


            firstItem = Utility.stitchFromTo(temp, 1, tokenPosition);
            secondItem = Utility.stitchFromTo(temp, tokenPosition + 1, temp.length);

            inventoryUse = getPotentialItem(firstItem, player, 0);
            //prop from room
            itemUse = getPotentialItem(secondItem, player, 1);

            if (inventoryUse.size() > 1 || itemUse.size() > 1) {
                printToLog("Be more specific.");
                return false;
            }

            if (inventoryUse.size() == 1 && itemUse.size() == 1) {
//                if (inventoryUse.get(0).getEntity().getType() == 'i' && itemUse.get(0).getEntity().getType() == 'p'){
                if (!player.interactOnWith(itemUse.get(0).getEntity(), inventoryUse.get(0).getEntity())) {
                    printToLog("You can't use it.");
                }
//                }
//                } else{
//                    printToLog("USE x WITH y: x must be from your inventory, y must be an object you can see");
//                }

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

            inventoryUse = getPotentialItem(firstItem, player, 0);
            itemUse = getPotentialItem(firstItem, player, 1);

            //there are more possibilities from the items fetched
            if ((inventoryUse.size() + itemUse.size() == 0)) {
                printToLog("You can't see that.");
                return false;
            }

            //there are more possibilities from the items fetched
            if (!(inventoryUse.size() + itemUse.size() == 1)) {
                printToLog("Be more specific.");
                return false;
            }

            if (inventoryUse.size() == 1) {

                int result = player.simpleUse(inventoryUse.get(0).getEntity());

                if (result == 0) {
                    printToLog("You can't use it.");
                }
            } else if (itemUse.size() == 1) {
                int result = player.simpleUse(itemUse.get(0).getEntity());
                if (result == 0) {
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


        ArrayList<Pair> possibleItems;
        if (take) {
            possibleItems = HandleCommands.getPotentialItem(item, player, 2);
        } else {
            possibleItems = HandleCommands.getPotentialItem(item, player, 0);
        }
        ArrayList<Pair> possibleContainers = HandleCommands.getPotentialItem(container, player, 1);


        if ((possibleItems.size() == 0 || possibleContainers.size() == 0)) {
            printToLog("You can't see that.");
            return false;
        }

        if ((possibleItems.size() > 1 || possibleContainers.size() > 1)) {
            printToLog("Be more specific.");
            return false;
        }

        if (possibleItems.size() == 1 && possibleContainers.size() == 1) {

            int count = possibleItems.get(0).getCount();
            Item currentItem = (Item) possibleItems.get(0).getEntity();
            Container currentContainer = (Container) possibleContainers.get(0).getEntity();

            if(all){
                amount=possibleItems.get(0).getCount();
            }

            boolean result;
            if(take){
                result = player.takeFrom(possibleItems.get(0), currentContainer, amount);
            } else{
                result = player.putIn(possibleItems.get(0), currentContainer, amount);
            }
            if (result && take) {
                if (all) {
                    printToLog(count + " x " +currentItem.getName() + " added to your inventory.");
                } else {
                    printToLog(currentItem.getName() + " added to your inventory.");
                }
            } else if(result){
                if (all) {
                    printToLog(count + " x " + currentItem.getName() + " put in " + currentContainer.getName());
                } else {
                    printToLog(currentItem.getName() + " put in " + currentContainer.getName());
                }
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


        ArrayList<Pair> possibleItems;

        if (temp.length == 1) {
            ParseInput.wrongCommand++;
            printToLog("The syntax is TAKE (ALL) x");
            return false;
        }
        ParseInput.wrongCommand = 0;


        boolean all = false;

        String item;

        //we try to get a number from temp
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


        possibleItems = getPotentialItem(item, player, 1);

        ArrayList<Pair> items = new ArrayList<>();

        //we get only the items we can pickup
        for (Pair p : possibleItems) {
            if (p.getEntity().getType() == 'i' || p.getEntity().getType() == 'w' || p.getEntity().getType() == 'C') {
                items.add(p);
            }
        }


        if (items.size() > 1) {
            printToLog("Be more specific.");
            return false;
        } else if(items.size() == 1){
            Pair currentPair = items.get(0);
            Item currentItem = (Item) currentPair.getEntity();
            int count = currentPair.getCount();
            if (all) {
                if (player.pickUpItem(currentPair, count)) {
                    printToLog(count+" "+currentItem.getName() + " added to your inventory.");
                }

            } else if (amt != 0) {
                if (player.pickUpItem(items.get(0), amt)) {
                    printToLog(amt+" "+currentItem.getName() + " added to your inventory.");
                }
            } else {
                if (player.pickUpItem(items.get(0), 1)) {
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



        possibleItems = getPotentialItem(item, player, 0);

        if (possibleItems.size() > 1) {
            printToLog("Be more specific.");
            return false;
        } else if(possibleItems.size() == 1){
            Item i = (Item)possibleItems.get(0).getEntity();
            Pair p = possibleItems.get(0);
            if (all) {
                int result = player.drop(p, p.getCount());
                if (result == 1) {
                    printToLog("You drop all the " + i.getName());
                } else if(result == 0){
                    printToLog("You don't seem to have a " + i.getName() + " with you.");
                }
            } else if (amt != 0) {
                int result = player.drop(p, amt);
                if (result == 1) {
                    printToLog("You drop "+ amt+" "+ i.getName());
                } else if (result == 0){
                    printToLog("You don't seem to have a " + i.getName() + " with you.");
                }
            } else {
                int result = player.drop(p, 1);
                if (result == 1) {
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
            npc.printLongDescription(null, null);
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
        player.examine(items.get(0).getEntity());

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

        ArrayList<Pair> possibleItems = getPotentialItem(wieldItem, player, 0);

        if (possibleItems.size() == 1) {
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
        } else {
            printToLog("You don't seem to have a " + wieldItem);
            return false;
        }
    }




    public static void printWelcome() {
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


        int b = Utility.checkForToken(parsedInput, "with");
        int to = Utility.checkForToken(parsedInput, "to");
        if (parsedInput[1].equals("to")) {
            String npc = Utility.stitchFromTo(parsedInput, to + 1, parsedInput.length);

            NPC character = player.getCurrentRoom().getNPC(npc);

            if (!HandleCommands.isNPC(character, player, npc)) {
                return false;
            }

            character.printIntro();
            return true;
        } else if (b == -1 || !(parsedInput[1].equals("about"))) {
            printToLog("The syntax is: TALK ABOUT x WITH y");
            return false;
        } else {
            String subject = Utility.stitchFromTo(parsedInput, 2, b);
            String npc = Utility.stitchFromTo(parsedInput, b + 1, parsedInput.length);

            NPC character = player.getCurrentRoom().getNPC(npc);

            HandleCommands.isNPC(character, player, npc);

            if (player.talkToNPC(npc, subject)) {
                return true;
            } else {
                printToLog("\"Sorry, I don't know anything about it.\"");
                return true;
            }
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


            if (!HandleCommands.isNPC(character, player, npc)) {
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

            ArrayList<Pair> possibleItemFromInventory = getPotentialItem(item, player, 0);

            if ((possibleItemFromInventory.size() > 1)) {
                printToLog("Be more specific.");
                return false;
            } else if (possibleItemFromInventory.size() == 0) {
                printToLog("You don't have that item.");
                return false;
            } else {

                NPC character = player.getCurrentRoom().getNPC(npc);

                if (!HandleCommands.isNPC(character, player, npc)) {
                    return false;
                }

                if (player.give(character, possibleItemFromInventory.get(0).getEntity())) {
                    return true;
                } else {
                    return false;
                }

            }
        }

    }

    public static boolean handleAttack(String[] parsedInput, Player player, boolean execute) {
        if (parsedInput.length == 1) {
            ParseInput.wrongCommand++;
            printToLog("The syntax is ATTACK x or KILL x");
            return false;
        }
        ParseInput.wrongCommand = 0;

        String enemy = Utility.stitchFromTo(parsedInput, 1, parsedInput.length);
        ArrayList<Pair> entities = getPotentialItem(enemy, player, 1);
        if (entities.size() == 1 && !execute) {
            return player.attack(entities.get(0).getEntity());
        } else if (entities.size() == 1 && execute) {
            if(entities.get(0).getEntity().getType() == 'n'){
                ((Enemy)entities.get(0).getEntity()).handleLoot(currentRoom);
                currentRoom.getEntities().remove(entities.get(0).getEntity().getID());
                printToLog("You executed the "+entities.get(0).getEntity().getName());
                return true;
            }
            return false;
        } else if (entities.size() > 1) {
            printToLog("Be more specific");
            return false;
        } else {
            printToLog("You can't see a " + enemy);
            return false;
        }
    }

    public static boolean isNPC(NPC character, Player player, String npc) {
        Entity entity = player.getCurrentRoom().getEntity(npc);

        if ((character == null) && entity == null) {
            printToLog("You can't see " + npc + " here.");
            return false;
        } else if (entity != null && entity.getType() != 'n') {
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

    public static boolean handleCast(String[] parsedInput, Player player, Room currentRoom){
        if(parsedInput.length == 1){
            printToLog("The syntax is CAST spell (AT/ON target)");
            return false;
        }

        int tokenAt = Utility.checkForToken(parsedInput, "at");
        int tokenOn = Utility.checkForToken(parsedInput, "on");
        tokenAt = Math.max(tokenAt, tokenOn);
        if (tokenAt == -1) {


            String spellName = Utility.stitchFromTo(parsedInput, 1, parsedInput.length);

            if (player.hasItemInInventory("spellbook")) {
                Spellbook spellbook = (Spellbook) player.getItemPair("spellbook").getEntity();

                ArrayList<Spell> potentialSpells = getPotentialSpell(spellName, spellbook);

                if(potentialSpells.size() > 1){
                    printToLog("Be more specific.");
                    return false;
                }else if (potentialSpells.size() == 0){
                    printToLog("Your spellbook doesn't seem to have the spell "+spellName);
                    return false;
                }

                if (spellbook.hasSpell(potentialSpells.get(0).getID())) {
                    SpellPair spell = spellbook.getSpell(potentialSpells.get(0).getID());


                    if(spell.getCount() <= 0){
                        printToLog("You don't have any castings left.");
                        return false;
                    }
                    Spell s = spell.getSpell();
                    char castOn = s.getTarget();

                    switch (castOn) {
                        case 's':
                            SelfSpell ss = (SelfSpell) s;
                            if(ss.isCasted(player)){
                                spell.modifyCount(-1);
                                return true;
                            }
                            return false;
                        case 'w':
                            WorldSpell ws = (WorldSpell) s;
                            if(ws.isCasted(player, currentRoom)){
                                spell.modifyCount(-1);
                                return true;
                            }
                            return false;
                    }
                }
            } else{
                printToLog("You need a spellbook to cast spells.");
                return false;
            }



            return false;
        } else {


            String spellName = Utility.stitchFromTo(parsedInput, 1, tokenAt);
            String target = Utility.stitchFromTo(parsedInput, tokenAt + 1, parsedInput.length);
            if (player.hasItemInInventory("spellbook")) {

                Spellbook spellbook = (Spellbook) player.getItemPair("spellbook").getEntity();


                ArrayList<Spell> potentialSpells = getPotentialSpell(spellName, spellbook);

                if(potentialSpells.size() > 1){
                    printToLog("Be more specific.");
                    return false;
                } else if (potentialSpells.size() == 0){
                    printToLog("Your spellbook doesn't seem to have such a spell.");
                    return false;
                }


                if (spellbook.hasSpell(potentialSpells.get(0).getID())) {

                    SpellPair spell = spellbook.getSpell(potentialSpells.get(0).getID());

                    if(spell.getCount() <= 0){
                        printToLog("You don't have any castings left.");
                        return false;
                    }
                    Spell s = spell.getSpell();
                    char castOn = s.getTarget();

                    switch (castOn) {
                        case 'w':
                        case 'i':
                            ArrayList<Pair> possibleItemsFromInventory = getPotentialItem(target, player, 0);
                            ArrayList<Pair> possibleItems = getPotentialItem(target, player, 1);

                            if(possibleItems.size() + possibleItemsFromInventory.size() > 1){
                                printToLog("Be more specific.");
                                return false;
                            } else if(possibleItems.size() != 0){
                                printToLog("You need to be holding that item.");
                                return false;
                            } else if (possibleItemsFromInventory.size() == 0){
                                printToLog("You can't see a "+target);
                                return false;
                            }

                            TargetSpell ts = (TargetSpell) s;

                            if (ts.isCasted(player, possibleItemsFromInventory.get(0).getEntity())) {
                                spell.modifyCount(-1);
                                return true;
                            }
                            return false;


                        case 'e':
                            DamagingSpell ds = (DamagingSpell) s;
                            ArrayList<Enemy> enemies = getPotentialEnemy(target, player);
                            if(enemies.size() == 0){
                                printToLog("You can't see that enemy");
                                return false;
                            } else if(enemies.size()>1){
                                printToLog("Be more specific");
                                return false;
                            } else {
                                if (ds.isCasted(player, enemies.get(0))) {
                                    spell.modifyCount(-1);
                                    return true;
                                }
                                return false;
                            }
                    }
                    return false;

                }
            } else{
                printToLog("You need a spellbook to cast spells.");
                return false;
            }
        }
        return false;
    }

}
