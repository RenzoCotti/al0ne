package com.al0ne.Engine;

import com.al0ne.Behaviours.*;
import com.al0ne.Behaviours.Pairs.Pair;
import com.al0ne.Behaviours.Pairs.SpellPair;
import com.al0ne.Entities.Spells.*;
import com.al0ne.Entities.Items.Behaviours.Container;
import com.al0ne.Entities.Items.ConcreteItems.Spellbook;
import com.al0ne.Entities.NPCs.Shopkeeper;

import java.util.ArrayList;
import java.util.HashMap;

import static com.al0ne.Engine.Main.*;

/*
* This class handles parsing the input correctly
* */
public class ParseInput {
    //    static String last="help";
    public static int wrongCommand = 0;
    public static String lastCommand = "look";

    public static boolean parse(String input, Game game, boolean again) {

        Player player = game.getPlayer();

        HashMap<String, Room> rooms = game.getRooms();

        String lowerInput = input.toLowerCase();

        String[] parsedInput = lowerInput.split(" ");

        if (!input.equals("g") && !input.equals("again") && !again) {
            printToLog();
            printToLog("(" + input + ")");
        }

        switch (parsedInput[0]) {

            case "save":
                if (parsedInput.length < 2) {
                    printToLog("The syntax is: SAVE path_of_the_save_file");
                } else {
                    GameChanges.save(parsedInput[1], null, Main.game);
                }
                return false;
            case "warp":
                if (parsedInput.length < 2) {
                    printToLog("The syntax is: WARP world_name");
                } else {
                    if (GameChanges.changeWorld(Utility.stitchFromTo(parsedInput, 1, parsedInput.length))) {
                        printToLog();
                        currentRoom.printRoom();
                    }

                }
                return false;
            case "load":
                if (parsedInput.length < 2) {
                    printToLog("The syntax is: LOAD path_of_the_save_file");
                } else {
                    GameChanges.load(parsedInput[1], null);
                }
                return false;

            case "drink":
                return HandleCommands.customAction(parsedInput, player, "drink");
            case "talk":
                return HandleCommands.handleTalk(parsedInput, player);
            case "eat":
                return HandleCommands.customAction(parsedInput, player, "eat");
            case "read":
                return HandleCommands.customAction(parsedInput, player, "read");
            case "buy":
                return HandleCommands.handleBuy(parsedInput, player);
            case "move":
                return HandleCommands.customAction(parsedInput, player, "move");
            case "kill":
            case "attack":
                return HandleCommands.handleAttack(parsedInput, player);
            case "give":
                return HandleCommands.handleGive(parsedInput, player);
            case "death":
                player.killPlayer();
                return false;

            case "cast":
                return HandleCommands.handleCast(parsedInput, player, currentRoom);
                //we check if it's a simple use, e.g. use potion or a complex one, e.g. use x on y
            case "use":

                int tokenOn = Utility.checkForToken(parsedInput, "on");
                int tokenWith = Utility.checkForToken(parsedInput, "with");

                //case simple use
                if (tokenOn == -1 && tokenWith == -1) {
                    return HandleCommands.useItem(parsedInput, player, false, tokenOn);
                }
                //case complex use
                else if (tokenWith > -1) {
                    return HandleCommands.useItem(parsedInput, player, true, tokenWith);
                } else if (tokenOn > -1) {
                    return HandleCommands.useItem(parsedInput, player, true, tokenOn);
                }

            case "open":
                return HandleCommands.customAction(parsedInput, player, "open");
            case "wear":
            case "wield":
            case "equip":
                return HandleCommands.handleWear(parsedInput, player);
            case "l":
            case "look":
                game.getRoom().printRoom();
                wrongCommand = 0;
                return true;
            case "n":
            case "north":
                return HandleCommands.move(player, rooms, "north", parsedInput);
            case "s":
            case "south":
                return HandleCommands.move(player, rooms, "south", parsedInput);
            case "story":
                if(parsedInput.length == 1){
                    printToLog(player.getStory());
                } else{
                    printToLog("The syntax is: STORY");
                }
                return false;
            case "e":
            case "east":
                return HandleCommands.move(player, rooms, "east", parsedInput);
            case "w":
            case "west":
                return HandleCommands.move(player, rooms, "west", parsedInput);
            case "d":
            case "down":
                return HandleCommands.move(player, rooms, "down", parsedInput);
            case "u":
            case "up":
                return HandleCommands.move(player, rooms, "up", parsedInput);
            case "take":

                int tokenFrom = Utility.checkForToken(parsedInput, "from");
                int tokenAll = Utility.checkForToken(parsedInput, "all");

                //case simple take
                if (tokenFrom == -1 && tokenAll == -1) {
                    return HandleCommands.handleTake(parsedInput, player);
                }
                //case take from container:
                //take normally and all
                else if (tokenFrom == -1 && tokenAll > -1) {
                    return HandleCommands.handleTake(parsedInput, player);
                    //take from container normally
                } else if (tokenFrom > -1 && tokenAll == -1) {
                    return HandleCommands.takePutContainer(parsedInput, player, tokenFrom, false, true);
                    //take from container, all
                } else if (tokenFrom > -1 && tokenAll > -1) {
                    return HandleCommands.takePutContainer(parsedInput, player, tokenFrom, true, true);
                }
            case "put":
                int tokenIn = Utility.checkForToken(parsedInput, "in");
                tokenAll = Utility.checkForToken(parsedInput, "all");

                if (tokenIn > -1 && tokenAll == -1) {
                    return HandleCommands.takePutContainer(parsedInput, player, tokenIn, false, false);
                    //put in container
                } else if (tokenIn > -1 && tokenAll > -1) {
                    return HandleCommands.takePutContainer(parsedInput, player, tokenIn, true, false);
                } else if (tokenIn == -1) {
                    printToLog("The syntax is PUT item IN container.");
                }
            case "x":
            case "examine":
                return HandleCommands.handleExamine(parsedInput, player);
            case "i":
            case "inventory":
                player.printInventory();
                return false;
            case "?":
                HandleCommands.printHelp();
                wrongCommand = 0;
                return false;
            case "help":
                printToLog("Commands:");
                for (Command command : Command.values()) {
                    printToLog(command.toString());
                }
                wrongCommand = 0;
                return false;
            case "quit":
            case "exit":
                HandleCommands.quit();
                return false;
            case "weight":
                player.printWeight();
                return false;
            case "health":
                player.printHealth();
//                player.printHealthStatus();
                return false;
            case "g":
            case "again":
                printToLog("(" + lastCommand + ")");
                return parse(lastCommand, game, true);
            case "drop":
                return HandleCommands.handleDrop(parsedInput, player);
            case "equipment":
            case "worn":
                player.printArmor();
                player.printWielded();
                return false;
            case "status":
                for(Status s: player.getStatus().values()){
                    printToLog(s.getName()+" : "+s.getDuration());
                }
                return false;

            case "qqq":
                System.exit(0);

            case "time":
                printToLog(Main.game.getTurnCount() + " turns elapsed.");
                wrongCommand = 0;
                return false;
            default:
                wrongCommand++;
                printToLog("Sorry?");
                return false;
        }

    }




}
