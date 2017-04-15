package com.al0ne.Engine;

import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Room;
import com.al0ne.Behaviours.Status;
import com.al0ne.Behaviours.World;
import com.al0ne.Entities.Worlds.CreateAlpha;
import com.al0ne.Entities.Worlds.CreateSmallCave;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

import static javafx.scene.input.KeyCode.ENTER;

public class Main extends Application{

    public static TextArea log;
    public static TextField input;

    public static TextArea notes;

    public static World currentWorld = new CreateAlpha();

    public static Player player = new Player(currentWorld.getStartingRoom(), true);

    public static Game game = new Game(player, currentWorld, 0);

    public static Room currentRoom = player.getCurrentRoom();

    public static int turnCounter = game.getTurnCount();


    public static String currentCommand = "";

    public static void main(String[] args) {
        launch(args);
        runGame();
    }

    private static void runGame(){
        ParseInput.printWelcome();
        currentRoom.printRoom();
        currentRoom.printName();
    }
    public static void hasNextLine(String s){
            currentCommand = s;
            if(ParseInput.parse(currentCommand, game, turnCounter, false)){
                game.addTurn();
                if(player.getStatus().size()>0){
                    ArrayList<Status> toResolve = new ArrayList<>();
                    for (Status status: player.getStatus().values()){
                        if(status.resolveStatus(player)){
                            toResolve.add(status);
                        }
                    }
                    for (Status st : toResolve){
                        player.getStatus().remove(st.getName());
                    }

                    for (Status toApply : player.getToApply()){
                        player.getStatus().put(toApply.getName(), toApply);
                    }

                    player.getToApply().clear();
                }
            }
            if (!player.isAlive()){
                printToLog("You have died...");
                printToLog();
                printToLog("Game over!");
                input.setDisable(true);
                return;
            } else if(ParseInput.wrongCommand >= 3){
                printToLog("Maybe you need some help? Type \"?\" to have an intro or \"help\" to see a list of all commands");
            }
            if (!(currentCommand.equals("g") || currentCommand.equals("again"))){
                ParseInput.lastCommand = currentCommand;
            }


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



    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Al0ne Alpha v. 0.4");
        primaryStage.setScene(new Scene(UI.createContent()));
        primaryStage.setOnCloseRequest(e -> Platform.exit());
        primaryStage.show();

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




    public static boolean changeWorld(String s){
        switch (s){
            case "alphaworld":
                World a = new CreateAlpha();
                currentWorld = a;
                game.setWorld(a);
                player.setCurrentRoom(a.getStartingRoom());
                currentRoom = player.getCurrentRoom();
                ParseInput.clearScreen();
                return true;
            case "caveworld":
                World c = new CreateSmallCave();
                currentWorld = c;
                game.setWorld(c);
                player.setCurrentRoom(c.getStartingRoom());
                currentRoom = player.getCurrentRoom();
                ParseInput.clearScreen();
                return true;
            default:
                printToLog("404: world not found");
                return false;
        }
    }
}