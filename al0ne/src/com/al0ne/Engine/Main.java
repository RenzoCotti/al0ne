package com.al0ne.Engine;

import com.al0ne.Entities.Player;
import com.al0ne.Room;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

import javax.swing.filechooser.FileNameExtensionFilter;
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

        VBox root = new VBox(1);

        Stage stage = new Stage();


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


        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");
        Menu helpMenu = new Menu("Help");
        Menu creditsMenu = new Menu("Credits");





        MenuItem save = new MenuItem("Save");
        save.setOnAction(t -> {
            FileChooser saveFile = new FileChooser();
            saveFile.setTitle("Save File");
            File file = saveFile.showSaveDialog(stage);
            if (file != null) {
                save(file.getName(), file.getPath());
            }
        });

        MenuItem load = new MenuItem("Load");
        load.setOnAction(t -> {
            FileChooser loadFile = new FileChooser();
            loadFile.setTitle("Open Load File");
            loadFile.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Save files (*.save)", "*.save"));
            File file = loadFile.showOpenDialog(stage);

            if (file != null) {
                load(file.getName(), file.getAbsolutePath());
            }
        });

        MenuItem quit = new MenuItem("Quit");
        quit.setOnAction(t -> {
            Stage s = quitDialog(stage);
            s.show();
        });



        fileMenu.getItems().addAll(save, load, quit);

        menuBar.getMenus().addAll(fileMenu, helpMenu, creditsMenu);



        root.getChildren().addAll(menuBar, log, input);

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

    public static Stage quitDialog(Stage stage){
        Stage s = new Stage();
        s.initModality(Modality.APPLICATION_MODAL);
        s.initOwner(stage);
        VBox dialogVbox = new VBox(20);

        HBox buttonBox = new HBox(20);

        Button quitButton = new Button("Yes");
        quitButton.setOnAction(a -> System.exit(0));

        Button cancel = new Button("No");
        cancel.setOnAction(b -> s.close());

        Text text = new Text("Are you sure you want to quit?");

        buttonBox.getChildren().addAll(quitButton, cancel);
        dialogVbox.getChildren().addAll(text, buttonBox);
        dialogVbox.setPadding(new Insets(20));

        Scene dialogScene = new Scene(dialogVbox, 250, 100);
        s.setScene(dialogScene);

        return s;
    }


    public static void save(String s, String path){
        FileOutputStream fop = null;
        ObjectOutputStream oos = null;
        File file;

        try {
            if (path != null){
                file = new File(path+".save");
            } else{
                file = new File("./"+s+".save");
            }
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

    public static void load(String s, String path){
        Game loaded = deserializeGame(s, path);
        if (loaded == null){
            return;
        }

        game = loaded;
        player = loaded.getPlayer();
        rooms = loaded.getAllRooms();
        turnCounter = loaded.getTurnCount();
        currentRoom = loaded.getRoom();

        printToLog("Game loaded successfully.");

    }

    public static Game deserializeGame(String filename, String path) {

        Game game = null;

        FileInputStream fin = null;
        ObjectInputStream ois = null;

        try {
            if (path != null){
                fin = new FileInputStream(path);
            } else{
                fin = new FileInputStream(filename);
            }
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