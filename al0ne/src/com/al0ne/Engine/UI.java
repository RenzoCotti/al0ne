package com.al0ne.Engine;

import com.al0ne.Behaviours.Player;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;

import static com.al0ne.Engine.Main.printToLog;
import static javafx.scene.input.KeyCode.ENTER;

/**
 * Created by BMW on 13/04/2017.
 */
public class UI {
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

    public static Stage creditsPopup(Stage stage){
        Stage s = new Stage();
        s.initModality(Modality.APPLICATION_MODAL);
        s.initOwner(stage);
        VBox dialogVbox = new VBox(20);

        HBox buttonBox = new HBox(20);

        Button cancel = new Button("Close");
        cancel.setOnAction(b -> s.close());

        Text text = new Text("Thanks for trying out my game!\nSpecial thanks to: Valerie Burgener, " +
                "Lara Bruseghini, Dario Cotti, Giovanni Campana, Luca Hofer for being my beta testers :D");

        text.maxWidth(400);
        text.setWrappingWidth(400);

        buttonBox.getChildren().addAll(cancel);
        dialogVbox.getChildren().addAll(text, buttonBox);
        dialogVbox.setPadding(new Insets(20));
        dialogVbox.setMaxSize(200, 500);

        Scene dialogScene = new Scene(dialogVbox);

        s.setScene(dialogScene);

        return s;
    }

    public static Parent createContent(){

        VBox root = new VBox(1);

        Stage stage = new Stage();


        Main.log = new TextArea();
        Main.log.setPrefHeight(550);
        Main.log.setPrefWidth(800);
        Main.log.setEditable(false);
        Main.log.setWrapText(true);

        Main.input  = new TextField();
        Main.input.setOnKeyPressed(event -> {
            if (event.getCode().equals(ENTER)){

                Main.hasNextLine(Main.input.getText());
                Main.input.clear();
            }
        });

        Main.input.setPromptText("Type your commands here");

        Main.notes = new TextArea();
        Main.notes.setMinWidth(150);

        Label noteTitle = new Label("Notes:");
        noteTitle.setStyle("-fx-font-weight: bold");
        noteTitle.setMaxHeight(20);
        noteTitle.setPadding(new Insets(5, 0, 0, 20));

        VBox notes = new VBox();
        notes.getChildren().addAll(noteTitle, Main.notes);
        VBox.setVgrow(Main.notes, Priority.ALWAYS);




        HBox container = new HBox();
        container.getChildren().addAll(Main.log, notes);


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
            Stage s = UI.creditsPopup(stage);
            s.show();
        });

        creditsMenu.getItems().add(creditsButton);



        MenuItem save = new MenuItem("Save");
        save.setOnAction(t -> {
            FileChooser saveFile = new FileChooser();
            saveFile.setTitle("Save File");
            File file = saveFile.showSaveDialog(stage);
            if (file != null) {
                SaveLoad.save(file.getName(), file.getPath());
            }
        });

        MenuItem load = new MenuItem("Load");
        load.setOnAction(t -> {
            FileChooser loadFile = new FileChooser();
            loadFile.setTitle("Open Load File");
            loadFile.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Save files (*.save)", "*.save"));
            File file = loadFile.showOpenDialog(stage);

            if (file != null) {
                SaveLoad.load(file.getName(), file.getAbsolutePath());
            }
        });

        MenuItem quit = new MenuItem("Quit");
        quit.setOnAction(t -> {
            Stage s = UI.quitDialog(stage);
            s.show();
        });

        MenuItem restart = new MenuItem("Restart");
        restart.setOnAction(t -> {
            Stage s = restartPopup(stage);
            s.show();
        });



        fileMenu.getItems().addAll(save, load, restart, quit);

        menuBar.getMenus().addAll(fileMenu, creditsMenu, helpMenu);



        root.getChildren().addAll(menuBar, container, Main.input);

        root.setPrefSize(800,600);
        return root;
    }



    public static Stage restartPopup(Stage stage){
        Stage s = new Stage();
        s.initModality(Modality.APPLICATION_MODAL);
        s.initOwner(stage);
        VBox dialogVbox = new VBox(20);

        HBox buttonBox = new HBox(20);

        Button restart = new Button("Restart");
        //maybe set initial world somewhere
        restart.setOnAction(b -> {
            Main.changeWorld(Main.game.getWorld().getWorldName());
            Main.input.setDisable(false);
            Main.player = new Player(Main.currentWorld.getStartingRoom(), Main.player.hasNeeds());
            Main.game = new Game(Main.player, Main.currentWorld, 0);
            ParseInput.clearScreen();
            printToLog("Game restarted.");
            printToLog();
            Main.player.getCurrentRoom().printName();
            Main.player.getCurrentRoom().printRoom();
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


}
