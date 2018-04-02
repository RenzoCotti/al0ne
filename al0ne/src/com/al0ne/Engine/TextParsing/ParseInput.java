package com.al0ne.Engine.TextParsing;

import com.al0ne.AbstractEntities.*;
import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.Abstract.Status;
import com.al0ne.AbstractEntities.Player.PrintPlayer;
import com.al0ne.Engine.*;
import com.al0ne.AbstractEntities.Enums.Command;
import com.al0ne.Engine.Game.Game;
import com.al0ne.Engine.Utility.GameChanges;
import com.al0ne.Engine.Utility.Utility;

import java.util.HashMap;

import static com.al0ne.Engine.Main.*;

/*
* This class handles parsing the input correctly
* */
public class ParseInput {
    public static int wrongCommand = 0;
    public static String lastCommand = "look";

    public static boolean parse(String input, Game game, boolean again) {

        Player player = game.getPlayer();

        HashMap<String, Room> rooms = player.getCurrentArea().getRooms();

        String lowerInput = input.toLowerCase();

        String[] parsedInput = lowerInput.split(" ");

        if (!input.equals("g") && !input.equals("again") && !again && !input.equals("")) {
            printToLog();
            printToLog("(" + input + ")");
        }

        Command c = game.getCommands().toCommand(parsedInput[0]);
        if(parsedInput.length == 2 && parsedInput[0].equals("go")){
            c = game.getCommands().toCommand(parsedInput[0]+" "+parsedInput[1]);
        } else if(c == null){
            c = Command.NONE;
        }

        switch (c) {

            case SAVE:
                if (parsedInput.length < 2) {
                    printToLog("The syntax is: SAVE path_of_the_save_file");
                } else {
                    GameChanges.save(parsedInput[1], null, Main.game, true);
                }
                return false;

            case LOAD:
                if (parsedInput.length < 2) {
                    printToLog("The syntax is: LOAD path_of_the_save_file");
                } else {
                    GameChanges.load(parsedInput[1], null, true);
                }
                return false;

            case TALK:
                return HandleCommands.handleTalk(parsedInput, player);

            case LIGHT:
            case PRESS:
            case TIDY:
            case DRINK:
            case EAT:
            case READ:
            case MOVE:
            case OPEN:
                String s = game.getCommands().stringify(c);
                return HandleCommands.customAction(parsedInput, player, c, s);

            case SHOOT:
            case ATTACK:
                return HandleCommands.handleAttack(parsedInput, player, false);
            case RELOAD:
                return HandleCommands.handleReload(parsedInput, player);
            case GIVE:
                return HandleCommands.handleGive(parsedInput, player);


            case BUY:
                return HandleCommands.handleBuy(parsedInput, player);

            case CAST:
                return HandleCommands.handleCast(parsedInput, player);
            //we check if it's a simple use, e.g. use potion or a complex one, e.g. use x on y
            case USE:

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
            case EQUIP:
                return HandleCommands.handleWear(parsedInput, player);
            case LOOK:
                game.getRoom().printRoom();
                wrongCommand = 0;
                return true;
            case NORTH:
            case SOUTH:
            case EAST:
            case WEST:
            case UP:
            case DOWN:
            case NORTHEAST:
            case NORTHWEST:
            case SOUTHEAST:
            case SOUTHWEST:
                String direction = game.getCommands().stringify(c);
                return HandleCommands.move(player, rooms, direction, parsedInput);
            case STORY:
                if(parsedInput.length == 1){
                    printToLog(player.getStory());
                } else{
                    printToLog("The syntax is: STORY");
                }
                return false;

            case TAKE:

                int tokenFrom = Utility.checkForToken(parsedInput, "from");
                int tokenAll = Utility.checkForToken(parsedInput, "all");

                //case simple take
                if (tokenFrom == -1 && tokenAll == -1) {
                    return HandleCommands.handleTake(parsedInput, player);
                }
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
            case PUT:
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
            case EXAMINE:
                return HandleCommands.handleExamine(parsedInput, player);
            case INVENTORY:
                PrintPlayer.printInventory(player);
                return false;
            case HELP:
                HandleCommands.printHelp();
                wrongCommand = 0;
                return false;
            case COMMANDS:
                printToLog("Commands:");
                for (Command command : Command.values()) {
                    printToLog(command.toString());
                }
                wrongCommand = 0;
                return false;
            case QUIT:
                HandleCommands.quit();
                return false;

            case AGAIN:
                printToLog("(" + lastCommand + ")");
                return parse(lastCommand, game, true);
            case DROP:
                return HandleCommands.handleDrop(parsedInput, player);
            case EQUIPMENT:
                PrintPlayer.printArmor(player);
                PrintPlayer.printWielded(player);
                return false;
            case HEALTH:
//                player.printHealth();
                PrintPlayer.printHealthStatus(player);
                return false;
            case QUEST:
                PrintPlayer.printQuests(player);
                return false;
            case WAIT:
                printToLog("You wait.");
                return true;
            case LINUX:
                printToLog("This isn't a UNIX shell, ya dingus!");
                return false;


            case DEBUG:
                game.toggleDebugMode();
                if(game.isInDebugMode()){
                    printToLog("Debug mode: ON");
                }else{
                    printToLog("Debug mode: OFF");
                }
                return true;
            case STATUS:
                if(game.isInDebugMode()){
                    for(Status status: player.getStatus().values()){
                        printToLog(status.getName()+" : "+status.getDuration());
                    }
                    return false;
                }
            case QQQ:
                if(game.isInDebugMode()){
                    System.exit(0);
                }
            case TIME:
                if(game.isInDebugMode()){

                    printToLog(Main.game.getTurnCount() + " turns elapsed.");
                    wrongCommand = 0;
                    return false;
                }

            case WEIGHT:
                if(game.isInDebugMode()){

                    PrintPlayer.printWeight(player);
                    return false;
                }

            case WARP:
                if(game.isInDebugMode()){

                    if (parsedInput.length != 2) {
                        printToLog("The syntax is: WARP world_number");
                    } else {
                        int number;
                        try {
                            number = Integer.parseInt(parsedInput[1]);
                            GameChanges.changeWorld(number);
                            printToLog();
                        } catch (Exception e){
                            printToLog("List of worlds available for warp");
                            printToLog("#\tName");
                            printToLog("---------------------------------");

                            for(int i = 0; i < Main.game.getWorldCount(); i++){
                                printToLog(i+"\t"+Main.game.getWorld(i).getWorldName());
                            }
                        }

                    }
                    return false;
                }
            case DEATH:
                if(game.isInDebugMode()) {

                    player.killPlayer();
                    return false;
                }
            case EXECUTE:
                if(game.isInDebugMode()) {

                    return HandleCommands.handleAttack(parsedInput, player, true);
                }
            case NONE:
            default:
                wrongCommand++;
                printToLog("Sorry?");
                return false;
        }

    }




}
