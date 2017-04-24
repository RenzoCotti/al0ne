package com.al0ne.Engine;

import com.al0ne.Behaviours.*;
import com.al0ne.Engine.UI.Popups;
import com.al0ne.Engine.UI.UI;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class Main extends Application{

    public static TextArea log;
    public static TextField input;

    public static TextArea notes;

    public static WarpGame game = new WarpGame(true);

    public static Player player = game.getPlayer();

    public static Room currentRoom = player.getCurrentRoom();

    public static int turnCounter = game.getTurnCount();


    public static String currentCommand = "";

    public static void main(String[] args) {
        launch(args);
        runGame();
    }

    private static void runGame(){
        HandleCommands.printWelcome();
        currentRoom.printRoom();
        currentRoom.printName();
    }
    public static void hasNextLine(String s, Scene scene){
        currentCommand = s;
        if(ParseInput.parse(currentCommand, game, false)){
            //we add a turn
            game.addTurn();

            //we make every aggro enemy attack
            GameChanges.attackIfAggro(player, currentRoom);

            //we make the statuses tick
            GameChanges.handleStatus(player);
        }


        if(ParseInput.wrongCommand >= 3){
            printToLog("Maybe you need some help? Type \"?\" to have an intro or \"help\" to see a list of all commands");
        }
        if (!(currentCommand.equals("g") || currentCommand.equals("again"))){
            ParseInput.lastCommand = currentCommand;
        }



        if (!player.isAlive()){
            printToLog("You have died...");
            printToLog();
            printToLog("Game over!");
            input.setDisable(true);

            Popups.deathPopup();
            return;
        }

        //we finally update the UI
        UI.updateUI(scene);



        printToLog();
        player.getCurrentRoom().printName();
    }

    public static void printToLog(){
        log.appendText("\n");
    }

    public static void printToLog(String s){
        log.appendText(s+"\n");
    }

    public static void printToSingleLine(String s){
        log.appendText(s);
    }

    //clears the screen by printing 20 new lines
    public static void clearScreen() {
        for (int i = 0; i < 30; i++) {
            printToLog();
        }
    }



    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Al0ne Alpha v. 0.4");
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