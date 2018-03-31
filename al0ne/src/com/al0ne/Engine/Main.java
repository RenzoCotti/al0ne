package com.al0ne.Engine;

import com.al0ne.AbstractEntities.*;
import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.Engine.Editing.EditingGame;
import com.al0ne.Engine.Editing.EditorInfo;
import com.al0ne.Engine.Game.Game;
import com.al0ne.Engine.Game.WarpGame;
import com.al0ne.Engine.TextParsing.HandleCommands;
import com.al0ne.Engine.TextParsing.ParseInput;
import com.al0ne.Engine.UI.MainMenu;
import com.al0ne.Engine.UI.PlayUI;
import com.al0ne.Engine.UI.Popups;
import com.al0ne.Engine.Utility.GameChanges;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Scanner;

public class Main extends Application{

    public static TextArea log;
    public static TextField input;

    public static int fontSize = 12;

    public static TextArea notes;

    public static Game game = new WarpGame();

    public static Player player = game.getPlayer();

    public static int turnCounter = game.getTurnCount();

    public static boolean started = false;

    public static ArrayList<String> oldCommands = new ArrayList<>();

    public static int maxHistory = 20;

    public static boolean sideMenuShown = true;

    public static int historyCounter = 0;

    public static String currentCommand = "";

    public static EditorInfo edit = new EditorInfo();

    public static String versionNumber = "Alpha v. 0.71";

    public static boolean nostalgiaMode = false;

    public static void main(String[] args) {

        for (String s: args){
            if(s.toLowerCase().equals("nostalgia")){
                nostalgiaMode = true;
                runGame();
            }
        }
        if(!nostalgiaMode){
            edit.addGame(new EditingGame(game));
            launch(args);
        }
    }

    public static void runGame(){
        started = true;
        HandleCommands.printWelcome();
        Main.player.getCurrentRoom().printRoom();
        printToLog();
        Main.player.getCurrentRoom().printName();


//        game.toggleDebugMode();

        if(!nostalgiaMode){
            input.requestFocus();
        } else {
            Scanner scanner = new Scanner(System.in);

            while(scanner.hasNextLine()){
                String userInput = scanner.nextLine();
                hasNextLine(userInput, null);
            }
        }
    }
    public static void hasNextLine(String s, Scene scene){
        currentCommand = s;
        if(ParseInput.parse(currentCommand, game, false)){
            Room currentRoom = player.getCurrentRoom();
            //we add a turn
            game.addTurn();

            //we make every aggro enemy attack
            GameChanges.attackIfAggro(player);

            //we make the statuses tick
            player.handleStatuses();

            player.removeIfDestroyed();

            GameChanges.consumeItems(player);

            currentRoom.triggerEvents(player);
        }


        if(ParseInput.wrongCommand >= 3){
            printToLog("Maybe you need some help? Type \"?\" to have an intro or \"help\" to see a list of all commands");
        }
        if (!(currentCommand.equals("g") || currentCommand.equals("again"))){
            ParseInput.lastCommand = currentCommand.toLowerCase();
            if(oldCommands.size()+1 == maxHistory){
                oldCommands.remove(0);
            }
            oldCommands.add(currentCommand);
        }

        if(!nostalgiaMode){
            //we finally update the PlayUI
            PlayUI.updateUI(scene);
        }


        if (!player.isAlive()){
            printToLog("You have died...");
            printToLog();
            printToLog("Game over!");

            if(!nostalgiaMode){
                input.setDisable(true);
                Popups.deathPopup();
            } else{
                System.exit(0);
            }

            return;
        }


        printToLog();
        player.getCurrentRoom().printName();
    }

    public static void printToLog(){
        if(started && !nostalgiaMode){
            log.appendText("\n");
        } else if(nostalgiaMode){
            System.out.println();
        }
    }

    public static void printToLog(String s){
        if(started && !nostalgiaMode){
            log.appendText(s+"\n");
        } else if(nostalgiaMode){
            System.out.println(s);
        }
    }

    public static void printToSingleLine(String s){
        if(started && !nostalgiaMode){
            log.appendText(s);
        } else if(nostalgiaMode){
            System.out.print(s);
        }
    }

    //clears the screen by printing 33 new lines
    public static void clearScreen() {
        for (int i = 0; i < 33; i++) {
            printToLog();
        }
    }



    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Al0ne "+versionNumber);
        primaryStage.setScene(MainMenu.createMainMenu(primaryStage));
        primaryStage.setOnCloseRequest(e -> Platform.exit());
        primaryStage.show();
    }

}