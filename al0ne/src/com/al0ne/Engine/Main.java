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
import java.util.Iterator;

import static javafx.scene.input.KeyCode.ENTER;

public class Main extends Application{

    private static TextArea log;
    private static TextField input;

    public static World currentWorld = new CreateSmallCave();

    public static Player player = new Player(currentWorld.getStartingRoom());

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
    private static void hasNextLine(String s){
            currentCommand = s;
            if(ParseInput.parse(currentCommand, game, turnCounter, false)){
                game.addTurn();
                if(player.getStatus().size()>0){
                    Iterator<Status> it = player.getStatus().iterator();
                    while(it.hasNext()) {
                        Status st = it.next();
                        if(st.resolveStatus(player)){
                            it.remove();
                        }
                    }
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

    private Parent createContent(){

        VBox root = new VBox(1);

        Stage stage = new Stage();


        log = new TextArea();
        log.setPrefHeight(550);
        log.setEditable(false);
        log.setWrapText(true);

        input  = new TextField();
        input.setOnKeyPressed(event -> {
            if (event.getCode().equals(ENTER)){

                hasNextLine(input.getText());
                input.clear();
            }
        });


        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");
        Menu helpMenu = new Menu("?");
        Menu creditsMenu = new Menu("Credits");


        MenuItem questionButton = new MenuItem("Help");

        questionButton.setOnAction(t -> ParseInput.printHelp());

        MenuItem commandsButton = new MenuItem("Commands");

        commandsButton.setOnAction(t -> {
            printToLog("Commands:");
            for (Command command: Command.values()){
                printToLog(command.toString());
            }
        });

        helpMenu.getItems().addAll(questionButton, commandsButton);




        MenuItem creditsButton = new MenuItem("Thanks");

        creditsButton.setOnAction(t -> {
            Stage s = creditsPopup(stage);
            s.show();
        });

        creditsMenu.getItems().add(creditsButton);



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

        MenuItem restart = new MenuItem("Restart");
        restart.setOnAction(t -> {
            Stage s = restartPopup(stage);
            s.show();
        });



        fileMenu.getItems().addAll(save, load, restart, quit);

        menuBar.getMenus().addAll(fileMenu, creditsMenu, helpMenu);



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
                        Platform.runLater(Main::runGame);
                        return null;
                    }
                };
            }
        };
        service.restart();
    }

    private static Stage quitDialog(Stage stage){
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


    private static Stage creditsPopup(Stage stage){
        Stage s = new Stage();
        s.initModality(Modality.APPLICATION_MODAL);
        s.initOwner(stage);
        VBox dialogVbox = new VBox(20);

        HBox buttonBox = new HBox(20);

        Button cancel = new Button("Close");
        cancel.setOnAction(b -> s.close());

        Text text = new Text("Thanks for trying out my game\nSpecial thanks to: Valerie Burgener, " +
                "Lara Bruseghini, Dario Cotti for being my beta testers :D");

        buttonBox.getChildren().addAll(cancel);
        dialogVbox.getChildren().addAll(text, buttonBox);
        dialogVbox.setPadding(new Insets(20));
        dialogVbox.setMaxSize(200, 500);

        Scene dialogScene = new Scene(dialogVbox);

        s.setScene(dialogScene);

        return s;
    }

    private static Stage restartPopup(Stage stage){
        Stage s = new Stage();
        s.initModality(Modality.APPLICATION_MODAL);
        s.initOwner(stage);
        VBox dialogVbox = new VBox(20);

        HBox buttonBox = new HBox(20);

        Button restart = new Button("Restart");
        //maybe set initial world somewhere
        restart.setOnAction(b -> {
            changeWorld(game.getWorld().getWorldName());
            input.setDisable(false);
            player = new Player(currentWorld.getStartingRoom());
            game = new Game(player, currentWorld, 0);
            ParseInput.clearScreen();
            printToLog("Game restarted.");
            printToLog();
            player.getCurrentRoom().printName();
            player.getCurrentRoom().printRoom();
            s.close();

        });

        Button cancel = new Button("Cancel");
        cancel.setOnAction(b -> s.close());

        Text text = new Text("Are you sure you want to restart?");

        buttonBox.getChildren().addAll(restart, cancel);
        dialogVbox.getChildren().addAll(text, buttonBox);
        dialogVbox.setPadding(new Insets(20));
        dialogVbox.setMaxSize(200, 500);

        Scene dialogScene = new Scene(dialogVbox);

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
            printToLog("Failed to load the game.");
            return;
        }

        game = loaded;
        player = loaded.getPlayer();
        turnCounter = loaded.getTurnCount();
        currentRoom = loaded.getRoom();

        printToLog("Game loaded successfully.");
        printToLog();
        currentRoom.printRoom();
        currentRoom.printName();


    }

    private static Game deserializeGame(String filename, String path) {

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

    public static void changeWorld(String s){
        switch (s){
            case "alphaworld":
                World a = new CreateAlpha();
                currentWorld = a;
                game.setWorld(a);
                player.setCurrentRoom(a.getStartingRoom());
                currentRoom = player.getCurrentRoom();
                return;
            case "caveworld":
                World c = new CreateSmallCave();
                currentWorld = c;
                game.setWorld(c);
                player.setCurrentRoom(c.getStartingRoom());
                currentRoom = player.getCurrentRoom();
                return;
            default:
                printToLog("404: world not found");
        }
    }
}