package com.al0ne.Engine;

import com.al0ne.Entities.Player;
import com.al0ne.Room;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.HashMap;

import static javafx.scene.input.KeyCode.ENTER;

public class Main extends Application{

    private static TextArea log;
    private static TextField input;

    public static HashMap<String, Room> rooms = CreateAlpha.create();

    public static Player player = new Player(rooms.get("startroom"));

    public static Game game = new Game(player, rooms, 0);

    public static Room currentRoom = player.getCurrentRoom();

    public static int turnCounter = game.getTurnCount();


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
                game.addTurn();
            }
            if (!player.isAlive()){
                printToLog("You have died...");
                printToLog();
                printToLog("Game over!");
                input.setDisable(true);
//                System.exit(0);
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


    public static void save(String s){
        FileOutputStream fop = null;
        ObjectOutputStream oos = null;
        File file;

        try {

            file = new File("./"+s+".DAT");
            fop = new FileOutputStream(file);
            oos = new ObjectOutputStream(fop);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                printToLog("File already exists. Specify a non existing file name.");
                return;
            } else {
                file.createNewFile();
            }

            // get the content in bytes
            oos.writeObject(game);

            oos.flush();
            oos.close();

            printToLog("Saving successful");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fop != null) {
                try {
                    fop.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void load(String s){
        Game loaded = deserializeGame(s);
        if (loaded == null){
            return;
        }

        game = loaded;
        player = loaded.getPlayer();
        rooms = loaded.getAllRooms();
        turnCounter = loaded.getTurnCount();
        currentRoom = loaded.getRoom();

    }

    public static Game deserializeGame(String filename) {

        Game game = null;

        FileInputStream fin = null;
        ObjectInputStream ois = null;

        try {

            fin = new FileInputStream(filename);
            ois = new ObjectInputStream(fin);
            game = (Game) ois.readObject();

        } catch (Exception ex) {
            printToLog("File not found");
            return null;
//            ex.printStackTrace();
        } finally {

            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        return game;

    }
}