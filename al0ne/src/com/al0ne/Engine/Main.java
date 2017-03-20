package com.al0ne.Engine;

import com.al0ne.Entities.Player;
import com.al0ne.Room;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.HashMap;

import static javafx.scene.input.KeyCode.ENTER;

public class Main extends Application{

    private static TextArea log;
    private static TextField input;

    public static HashMap<String, Room> rooms = CreateAlpha.create();

    public static Player grog = new Player(rooms.get("startroom"));

    public static Game game = new Game(grog, rooms);

    public static Room currentRoom = grog.getCurrentRoom();

    public static int turnCounter=0;


    public static String currentCommand = "";

    public static void main(String[] args) {

        launch(args);
        runGame();


    }

    public static void runGame(){
        ParseInput.printWelcome();
        currentRoom.printRoom();
        currentRoom.printName();
    }
    public static void hasNextLine(String s){
            currentCommand = s;
            if(ParseInput.parse(currentCommand, game, turnCounter)){
                turnCounter++;
            }
            if (!grog.isAlive()){
                printToLog("You have died...");
                printToLog();
                printToLog("Game over!");
                System.exit(0);
            } else if(ParseInput.wrongCommand >= 3){
                printToLog("Maybe you need some help? Type \"?\" to have an intro or \"help\" to see a list of all commands");
            }
            if (!(currentCommand.equals("g") || currentCommand.equals("again"))){
                ParseInput.lastCommand = currentCommand;
            }
            printToLog();
            grog.getCurrentRoom().printName();
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

    private Parent createContent(){
        log = new TextArea();
        log.setPrefHeight(500);
        log.setEditable(false);

        input  = new TextField();
        input.setOnKeyPressed(event -> {
            if (event.getCode().equals(ENTER)){

                hasNextLine(input.getText());
                input.clear();
            }
        });

        VBox root = new VBox(1, log, input);


        root.setPrefSize(800,600);
        return root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Al0ne Alpha v. 0.2");
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.setOnCloseRequest(e -> Platform.exit());
        primaryStage.show();

        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        Platform.runLater(() ->{
                            runGame();
                        });
                        return null;
                    }
                };
            }
        };
        service.restart();
    }
}