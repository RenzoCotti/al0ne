package com.al0ne.Engine;

import com.al0ne.Behaviours.*;
import com.al0ne.Engine.TextParsing.HandleCommands;
import com.al0ne.Engine.TextParsing.ParseInput;
import com.al0ne.Engine.UI.Popups;
import com.al0ne.Engine.UI.UI;
import com.al0ne.Entities.Worlds.AlphaWorld;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class Main extends Application{

    public static TextArea log;
    public static TextField input;

    public static int fontSize = 12;

    public static TextArea notes;

    public static WarpGame game = new WarpGame();

    public static Player player = game.getPlayer();

    public static Room currentRoom = player.getCurrentRoom();

    public static int turnCounter = game.getTurnCount();

    public static boolean started = false;

    public static ArrayList<String> oldCommands = new ArrayList<>();

    public static int maxHistory = 20;

    public static boolean sideMenuShown = true;

    public static int historyCounter = 0;

    public static String currentCommand = "";

    public static HashMap<String, Game> editorGames = new HashMap<>();

    public static void main(String[] args) {
        launch(args);
        runGame();
    }

    private static void runGame(){
        started = true;
        editorGames.put(game.getGameName(), game);
        editorGames.put(game.getGameName(), game);
        HandleCommands.printWelcome();
        currentRoom.printRoom();
        printToLog();
        currentRoom.printName();
        game.toggleDebugMode();
    }
    public static void hasNextLine(String s, Scene scene){
        currentCommand = s;
        if(ParseInput.parse(currentCommand, game, false)){
            //we add a turn
            game.addTurn();

            //we make every aggro enemy attack
            GameChanges.attackIfAggro(player, currentRoom);

            //we make the statuses tick
            player.handleStatuses();

            currentRoom.handleQuestRoom(player);
        }


        if(ParseInput.wrongCommand >= 3){
            printToLog("Maybe you need some help? Type \"?\" to have an intro or \"help\" to see a list of all commands");
        }
        if (!(currentCommand.equals("g") || currentCommand.equals("again"))){
            ParseInput.lastCommand = currentCommand;
            if(oldCommands.size()+1 == maxHistory){
                oldCommands.remove(0);
            }
            oldCommands.add(currentCommand);
        }

        //we finally update the UI
        UI.updateUI(scene);

        if (!player.isAlive()){
            printToLog("You have died...");
            printToLog();
            printToLog("Game over!");
            input.setDisable(true);

            Popups.deathPopup();
            return;
        }



        printToLog();
        player.getCurrentRoom().printName();
    }

    public static void printToLog(){
        if(started){
            log.appendText("\n");
        }
    }

    public static void printToLog(String s){
        if(started){
            log.appendText(s+"\n");
        }
    }

    public static void printToSingleLine(String s){
        log.appendText(s);
    }

    //clears the screen by printing 20 new lines
    public static void clearScreen() {
        for (int i = 0; i < 33; i++) {
            printToLog();
        }
    }



    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Al0ne AlphaWorld v. 0.4");
        primaryStage.setScene(UI.createContent());
        primaryStage.setOnCloseRequest(e -> Platform.exit());
        primaryStage.show();

        input.requestFocus();

        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        Platform.runLater(Main::runGame);
                        return null;
                    }
                };
            }
        };
        service.restart();
    }

}