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
import static com.al0ne.Engine.TextParsing.ParseInput.wrongCommand;
import static com.al0ne.Engine.Utility.Utility.getAllString;
import static com.al0ne.Engine.Utility.Utility.parseNumber;

/**
 * Created by BMW on 24/04/2017.
 */
public class HandleCommands {
    //this function handles the command exit
    public static void quit() {
        wrongCommand = 0;
        if (ParseInput.lastCommand.equals("quit") || ParseInput.lastCommand.equals("exit")) {
            System.exit(0);
        }
        printToLog("Are you sure you want to quit? (retype \"quit\" to exit)");
    }

    //this handles moving: it checks for exactly one token after the command
    public static boolean move(Player player, HashMap<String, Room> rooms, String direction, String[] temp) {
        if (temp.length > 2) {
            wrongCommand++;
            printToLog("Sorry?");
            return false;
        } else {
            wrongCommand = 0;
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
            wrongCommand++;
            printToLog("The syntax is " + action + " x.");
            return false;
        }

        wrongCommand = 0;
        String item = Utility.stitchFromTo(temp, 1, temp.length);

        Pair p = getPotentialItem(item, player, "both");

        if(p != null){
            if(PlayerActions.customAction(player, action, p.getEntity())){
                printToLog("You " + s + " the " + p.getEntity().getName());
                return true;
            } else {
                printToLog("You can't " + s + " it.");
            }

        } else {
            printToLog("You can't see a " + item + ". ");
        }
        return false;
    }

    public static ArrayList<Pair> getAllItemsMatching(String s, Player player) {
        ArrayList<Pair> result = getPotentialEntities(s, player, "room").getEntities();
        result.addAll(getPotentialEntities(s, player, "player").getEntities());
        return result;
    }

    public static ArrayList<Pair> getAllItemsInContainers(Player p){
        ArrayList<Container> containers = p.getCurrentRoom().getContainers();
        ArrayList<Pair> result = new ArrayList<>();
        for(Container c : containers){
            if(!c.isLocked())
                result.addAll(c.getItems());
        }
        
        return result;
    }

    //this attempts to get items from a token;
    //0: all
    //1: room
    //2: inventory
    public static PotentialItems getPotentialEntities(String s, Player player, String where) {

        ArrayList<Pair> potentialEntities = new ArrayList<>();
        PotentialItems totalItems = new PotentialItems(potentialEntities, 0);

        //we create a collection of all items the player can see
        ArrayList<Pair> pool = new ArrayList<>();
        if(where.equals("room") || where.equals("both")){
            pool.addAll(player.getCurrentRoom().getEntities().values());
            pool.addAll(getAllItemsInContainers(player));
        }
        if (where.equals("player") || where.equals("both")) {
            pool.addAll(player.getInventory().values());
        }


        //we check if there is an exact match over all pairs
        for (Pair pair : pool) {
            Entity currentEntity = pair.getEntity();

            if (currentEntity.getName().toLowerCase().equals(s.toLowerCase())) {
                potentialEntities.add(pair);
                int reliability = s.split(" ").length;
                totalItems.setReliability(reliability);
                return totalItems;
            }
        }

        //otherwise, parse and check for partial matches
        String[] splitString = s.split(" ");

        //best match found so far
        int max = 0;

        //we check with each pair in inventory if it contains the token
        for (Pair pair : pool){
            int reliability = 0;

            //we check the given input string token by token
            for (String token : splitString) {
                Entity currentEntity = pair.getEntity();

                String[] splitName = currentEntity.getName().split(" ");


                for (String b : splitName) {
                    if (b.toLowerCase().equals(token)) {
                        reliability++;
                        // we found a better match, we wipe the arraylist and add it
                        if (reliability > max) {
                            potentialEntities.clear();
                            max = reliability;
                            totalItems.setReliability(max);
                            potentialEntities.add(pair);
                        } else if(reliability == max){
                            potentialEntities.add(pair);
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
                wrongCommand++;
                printToLog("The syntax is USE x WITH y");
                return false;
            }
            wrongCommand = 0;


            //case use x with y
            firstItem = Utility.stitchFromTo(temp, 1, tokenPosition);
            secondItem = Utility.stitchFromTo(temp, tokenPosition + 1, temp.length);

            //we try to get all potential items from inv
            inventoryItems = getPotentialEntities(firstItem, player, "player");

            //prop from room
            roomItems = getPotentialEntities(secondItem, player, "room");


            if (inventoryItems.getEntities().size() > 1 || roomItems.getEntities().size() > 1) {
                printToLog("Be more specific.");
                return false;
            }

            if (inventoryItems.getEntities().size() == 1 && roomItems.getEntities().size() == 1) {
                PlayerActions.interactOnWith(player, roomItems.getEntities().get(0).getEntity(),
                        inventoryItems.getEntities().get(0).getEntity());

            } else {
                printToLog("You can't see such items");
            }

            return true;
            //case simple use: check we have just one item, then we make the player use it.
        } else {

            if (temp.length == 1) {
                wrongCommand++;
                printToLog("The syntax is USE x");
                return false;
            }
            wrongCommand = 0;


            firstItem = Utility.stitchFromTo(temp, 1, temp.length);

            inventoryItems = getPotentialEntities(firstItem, player, "player");


            //there are more possibilities from the items fetched
            if (inventoryItems.getEntities().size() > 1) {
                printToLog("Be more specific.");
                return false;
            }

            if (inventoryItems.getEntities().size() == 1) {

                boolean result = PlayerActions.simpleUse(player, inventoryItems.getEntities().get(0).getEntity());

                if (!result) {
                    printToLog("You can't use it.");
                }
            } else {
                printToLog("You can't see a "+firstItem+".");
            }

            return true;
        }

    }


    public static boolean takePutContainer(String[] temp, Player player, int fromToken, boolean all, boolean take) {

        if(temp.length < 2 && take){
            wrongCommand++;
            printToLog("The syntax is TAKE x FROM container.");
            return false;
        } else if(temp.length < 2){
            wrongCommand++;
            printToLog("The syntax is PUT x IN container.");
            return false;
        }

        wrongCommand = 0;

        int amount;
        String item;
        String container;


        //we try to get a specific amount from the input, if there isn't we assume 1
        try {
            //take 23 stone, for example
            amount = Integer.parseInt(temp[1]);
            item = Utility.stitchFromTo(temp, 2, fromToken);
            container = Utility.stitchFromTo(temp, fromToken + 1, temp.length);

        } catch (NumberFormatException ex){
            //take all stone
            if (all) {
                item = Utility.stitchFromTo(temp, 2, fromToken);
            } else {
                //take stone
                item = Utility.stitchFromTo(temp, 1, fromToken);
            }
            amount=1;
            container = Utility.stitchFromTo(temp, fromToken + 1, temp.length);
        }



        ArrayList<Pair> possibleItems = getPotentialEntities(item, player, "room").getItems();
        ArrayList<Pair> possibleContainers = getPotentialEntities(container, player, "room").getEntities();


        if ((possibleItems.size() > 1 || possibleContainers.size() > 1)) {
            printToLog("Be more specific.");
            return false;
        } else if (possibleItems.size() == 1 && possibleContainers.size() == 1) {

            Pair currentPair = possibleItems.get(0);
            Item currentItem = (Item) currentPair.getEntity();
            int count = currentPair.getCount();

            Container currentContainer = (Container) possibleContainers.get(0).getEntity();

            if(all){
                amount=currentPair.getCount();
            }

            boolean result;
            if(take){
                result = PlayerActions.takeFrom(player, currentPair, currentContainer, amount);
            } else{
                result = PlayerActions.putIn(player, currentPair, currentContainer, amount);
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
            printToLog("You can't see it.");
        }
        return true;
    }


    public static boolean handleTake(String[] temp, Player player) {

        ArrayList<Pair> items;
        Room currentRoom = player.getCurrentRoom();

        if (temp.length == 1) {
            wrongCommand++;
            printToLog("The syntax is TAKE (ALL) x");
            return false;
        }
        wrongCommand = 0;

        //handles taking all items in room
        if(temp.length == 2 && temp[1].equals("all")){
            items = currentRoom.getItemList();

            if(items.size() == 0){
                printToLog("There are no items here.");
                return false;
            }
            for(Pair p : items){
                if(PlayerActions.pickUpItem(player, p, p.getCount()) == 0){
                    //we stop whenever we fail to pickup an item
                    break;
                }
            }
            return true;
        }



        int amt = parseNumber(temp);
        String item = getAllString(temp, amt);



        //we get the best guess for the item
        Pair p  = getPotentialItem(item, player, "room");
        if(p != null){
            int count = p.getCount();

            //means we are taking all
            if(amt == -1){
                amt = count;
            }

            PlayerActions.pickUpItem(player, p, amt);
        } else {
            printToLog("You can't see a "+item);
        }

        return true;
    }


    public static boolean handleDrop(String[] temp, Player player){


        if (temp.length == 1) {
            wrongCommand++;
            printToLog("The syntax is DROP (ALL) x");
            return false;
        }

        wrongCommand = 0;

        //drop all items in your inventory
        if(temp.length == 2 && temp[1].equals("all")){
            Collection<Pair> cp = player.getInventory().values();
            //this is done to prevent a concurrent exception i.e. we remove
            //stuff from the array we're iterating onto
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
                PlayerActions.drop(player, p, p.getCount());
            }
            return true;
        }


        String item;
        int amt = parseNumber(temp);
        item = getAllString(temp, amt);


        Pair p = getPotentialItem(item, player, "player");

        if(p != null){
            if (amt == -1) {
                amt = p.getCount();
            }

            PlayerActions.drop(player, p, amt);

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
            wrongCommand++;
            printToLog("The syntax is EXAMINE x");
            return false;
        }
        wrongCommand = 0;


        String toExamine = Utility.stitchFromTo(temp, 1, temp.length);
        ArrayList<Pair> items = getAllItemsMatching(toExamine, player);

        //there are more possibilities from the items fetched
        if (items.size() > 1) {
            printToLog("Be more specific.");
        } else if (items.size() == 1) {
            PlayerActions.examine(player, items.get(0).getEntity());
        } else {
            printToLog("You can't see such an item");
        }
        return false;
    }

    public static boolean handleWear(String[] parsedInput, Player player) {

        if (parsedInput.length == 1) {
            wrongCommand++;
            printToLog("The syntax is WIELD x");
            return false;
        }

        wrongCommand = 0;

        String wieldItem = Utility.stitchFromTo(parsedInput, 1, parsedInput.length);


        Pair inv = getPotentialItem(wieldItem, player, "player");
        Pair ground = getPotentialItem(wieldItem, player, "room");

        if(inv != null) {
            if (player.wear((Item) inv.getEntity())) {
                return true;
            } else return false;
        } else if(ground != null){
            Item i = (Item) ground.getEntity();
            printToLog("(first taking the "+i.getName()+")");
            if(PlayerActions.pickUpItem(player, ground, 1) == 1){
                Item item = (Item) player.getItemPair(i.getID()).getEntity();

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
        } else {
            printToLog("You don't seem to have see a " + wieldItem);
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
            wrongCommand++;
            printToLog("The syntax is TALK ABOUT x WITH y or TALK TO y");
            return false;
        }
        wrongCommand = 0;




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

            character = getPossibleNPC(player, npcName);
            if(character != null){
                PlayerActions.talkToNPC(player, character, subject);
                return true;
            } else return false;
        }

        //case generic talk
        if (parsedInput[1].equals("to")) {
            npcName = Utility.stitchFromTo(parsedInput, to + 1, parsedInput.length);

            character = getPossibleNPC(player, npcName);
            if(character != null){
                character.printIntro();
                return true;
            } else return false;

        } else if (with == -1 || !(parsedInput[1].equals("about"))) {
            printToLog("The syntax is: TALK ABOUT x WITH y");
            return false;
        } else {
            //case complex talk
            String subject = Utility.stitchFromTo(parsedInput, 2, with);
            npcName = Utility.stitchFromTo(parsedInput, with + 1, parsedInput.length);

            character = getPossibleNPC(player, npcName);
            if(character != null){
                if (!PlayerActions.talkToNPC(player, character, subject)) {
                    printToLog("\"Sorry, I don't know anything about it.\"");
                }
                return true;
            } else return false;
        }
    }

    public static boolean handleBuy(String[] parsedInput, Player player) {

        int c = Utility.checkForToken(parsedInput, "from");
        if (c == -1) {
            wrongCommand++;
            printToLog("The syntax is: BUY x FROM y");
            return false;
        } else {
            wrongCommand = 0;
            String item = Utility.stitchFromTo(parsedInput, 1, c);
            String npc = Utility.stitchFromTo(parsedInput, c + 1, parsedInput.length);

            NPC character = getPossibleNPC(player, npc);


            if (character == null) {
                return false;
            } else if(character.isShopkeeper()){
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
            wrongCommand++;
            printToLog("The syntax is: GIVE x TO y");
            return false;
        } else {
            wrongCommand = 0;

            String item = Utility.stitchFromTo(parsedInput, 1, d);
            String npc = Utility.stitchFromTo(parsedInput, d + 1, parsedInput.length);

            Pair i = getPotentialItem(item, player, "player");
            if (i != null) {
                NPC character = getPossibleNPC(player, npc);
                return character != null && PlayerActions.give(player, character, (Item) i.getEntity());
            }
            return false;
        }

    }

    public static boolean handleReload(String[] parsedInput, Player player){

        if (parsedInput.length == 1) {
            wrongCommand++;
            printToLog("The syntax is RELOAD x.");
            return false;
        }

        wrongCommand = 0;

        String weaponString = Utility.stitchFromTo(parsedInput, 1, parsedInput.length);

        Pair i = getPotentialItem(weaponString, player, "player");

        if(i != null){
            if(i.getEntity() instanceof Weapon){
                return PlayerActions.reload(player, (Weapon) i.getEntity());
            } else {
                printToLog("That's not a weapon.");
            }
        }
        return false;
    }

    public static boolean handleAttack(String[] parsedInput, Player player, boolean execute) {
        if (parsedInput.length == 1) {
            wrongCommand++;
            printToLog("The syntax is SHOOT AT/ATTACK/KILL x.");
            return false;
        }

        Room currentRoom = player.getCurrentRoom();

        String enemyName;
        String toCheck = parsedInput[0]+" at";

        //case for ranged weapons
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



        wrongCommand = 0;

        //we get all entities similar to that name
        WorldCharacter wc = getPotentialEnemy(enemyName, player);
        if (!execute) {
            if(wc == null){
                return false;
            }
            if( wc instanceof NPC && wc.isQuestCharacter()){
                printToLog("It's best not to attack "+ wc.getName()+".");
                return false;
            } else {
                return PlayerActions.attack(player, wc);
            }
        } else {
            if(wc != null){
                wc.handleLoot(player);
                currentRoom.getEntities().remove(wc.getID());
                printToLog("You executed the "+wc.getName());
                return true;
            }
            return false;
        }
    }

    public static NPC getPossibleNPC(Player player, String npc) {
        ArrayList<Pair> temp = getPotentialEntities(npc, player, "room").getEntities();
        if(temp.size() > 1){
            printToLog("Be more specific.");
            return null;
        } else if(temp.size() == 1 && temp.get(0).getEntity() instanceof NPC){
            return (NPC)temp.get(0).getEntity();
        } else {
            printToLog("You can't seem to see "+npc+".");
            return null;
        }
    }


    public static WorldCharacter getPotentialEnemy(String s, Player player) {

        ArrayList<Pair> temp = getPotentialEntities(s, player, "room").getEntities();
        if(temp.size() > 1){
            printToLog("Be more specific.");
            return null;
        } else if(temp.size() == 1 && temp.get(0).getEntity() instanceof Enemy){
            return (WorldCharacter)temp.get(0).getEntity();
        } else {
            printToLog("You can't seem to see a "+s+".");
            return null;
        }
    }

    public static Pair getPotentialItem(String s, Player player, String where) {

        ArrayList<Pair> temp = getPotentialEntities(s, player, where).getEntities();
        if(temp.size() > 1){
            printToLog("Be more specific.");
            return null;
        } else if(temp.size() == 1 && temp.get(0).getEntity() instanceof Item){
            return temp.get(0);
        } else {
            printToLog("You can't seem to see that item.");
            return null;
        }
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
        if(parsedInput.length == 1){
            printToLog("The syntax is CAST spell (AT/ON target)");
            return false;
        }

        int tokenAt = Utility.checkForToken(parsedInput, "at");
        int tokenOn = Utility.checkForToken(parsedInput, "on");
        tokenAt = Math.max(tokenAt, tokenOn);
        if (tokenAt == -1) {


            String spellName = Utility.stitchFromTo(parsedInput, 1, parsedInput.length);

            PlayerActions.castSpell(player, spellName, null);

            return false;
        } else {

            String spellName = Utility.stitchFromTo(parsedInput, 1, tokenAt);
            String target = Utility.stitchFromTo(parsedInput, tokenAt + 1, parsedInput.length);

            PlayerActions.castSpell(player, spellName, target);
        }
        return false;
    }

}
