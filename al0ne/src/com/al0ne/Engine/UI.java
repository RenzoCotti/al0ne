package com.al0ne.Engine;


import com.al0ne.Behaviours.Pairs.Pair;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;

import static com.al0ne.Engine.Main.player;
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

    public static Scene createContent(){

        VBox root = new VBox(1);
        Scene done;


        Stage stage = new Stage();


        Main.log = new TextArea();
        Main.log.setPrefHeight(550);
        Main.log.setPrefWidth(800);
        Main.log.setEditable(false);
        Main.log.setWrapText(true);

        Main.input  = new TextField();


        Main.input.setPromptText("Type your commands here");

        Main.notes = new TextArea();
        Main.notes.setPromptText("Here you can write your notes.\nThey will be recorded upon saving the game.");
        Main.notes.setMinWidth(150);


        TabPane tabPane = new TabPane();
        Tab notes = new Tab();
        notes.setText("Notes");
        notes.setContent(Main.notes);
        notes.setClosable(false);

        Tab inventoryTab = new Tab();
        inventoryTab.setText("Inventory");
        inventoryTab.setClosable(false);



        TableView<SimpleItem> inv = new TableView<>();
        inv.setId("inv");
        TableColumn itemColumn = new TableColumn("Item");
        itemColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn amount = new TableColumn("Amount");
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        TableColumn weightColumn = new TableColumn("Weight");
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));

        inv.getColumns().addAll(itemColumn, amount, weightColumn);

        inv.setItems(GameChanges.getInventoryData());

        inventoryTab.setContent(inv);


        Tab stats = new Tab();
        stats.setText("Stats");
        stats.setClosable(false);

        Label playerStats = new Label("Player statistics:");
        playerStats.setStyle("-fx-font-weight: bold");

        playerStats.setPadding(new Insets(0, 0, 5, 0));
        Label weight = new Label("Weight: "+Main.player.getCurrentWeight()+" / "+ Main.player.getMaxWeight()+" kg");
        weight.setId("weightLabel");
        Label health = new Label("Health: "+Main.player.getCurrentHealth()+" / "+ Main.player.getMaxHealth());
        health.setId("healthLabel");


        Label equippedItems = new Label("Equipped items:");
        equippedItems.setPadding(new Insets(20, 0, 0, 0));
        equippedItems.setStyle("-fx-font-weight: bold");

        Label head = new Label("Head: "+Main.player.getHelmetString());
        head.setId("headLabel");
        Label armor = new Label("Armor: "+Main.player.getArmorString());
        armor.setId("armorLabel");
        Label weapon = new Label("Weapon: "+Main.player.getWeaponString());
        weapon.setId("weaponLabel");
        Label offHand = new Label("Off-Hand: "+Main.player.getOffHandString());
        offHand.setId("offHandLabel");


//        Label status = new Label("Status:");
//        status.setPadding(new Insets(20, 0, 0, 0));
//        status.setStyle("-fx-font-weight: bold");


        VBox listStats = new VBox();
        listStats.setPadding(new Insets(10, 10, 10, 10));
        listStats.getChildren().addAll(playerStats, health, weight, equippedItems, head, armor, weapon, offHand);

        stats.setContent(listStats);


        tabPane.getTabs().addAll(stats, inventoryTab, notes);

        VBox sideMenu = new VBox();



        sideMenu.getChildren().addAll(tabPane);
        sideMenu.setVgrow(tabPane, Priority.ALWAYS);



        HBox container = new HBox();
        container.getChildren().addAll(Main.log, sideMenu);


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
                GameChanges.save(file.getName(), file.getPath());
            }
        });

        MenuItem load = new MenuItem("Load");
        load.setOnAction(t -> {
            FileChooser loadFile = new FileChooser();
            loadFile.setTitle("Open Load File");
            loadFile.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Save files (*.save)", "*.save"));
            File file = loadFile.showOpenDialog(stage);

            if (file != null) {
                GameChanges.load(file.getName(), file.getAbsolutePath());
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


        done = new Scene(root);

        Main.input.setOnKeyPressed(event -> {
            if (event.getCode().equals(ENTER)){

                Main.hasNextLine(Main.input.getText(), done);
                Main.input.clear();
            }
        });

        return done;
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
            GameChanges.changeWorld(Main.game.getStartingWorld());
            Main.input.setDisable(false);
            Main.game = new Game(0, player.hasNeeds());
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

    public static void updateUI(Scene s){
        TableView inv = (TableView) s.lookup("#inv");
        inv.setItems(GameChanges.getInventoryData());
        Label healthLabel = (Label) s.lookup("#healthLabel");
        healthLabel.setText("Health: "+Main.player.getCurrentHealth()+" / "+ Main.player.getMaxHealth());
        Label weightLabel = (Label) s.lookup("#weightLabel");
        weightLabel.setText("Weight: "+Main.player.getCurrentWeight()+" / "+ Main.player.getMaxWeight()+" kg");


        Label head = (Label) s.lookup("#headLabel");
        head.setText("Head: "+Main.player.getHelmetString());
        Label armor = (Label) s.lookup("#armorLabel");
        armor.setText("Armor: "+Main.player.getArmorString());
        Label weapon = (Label) s.lookup("#weaponLabel");
        weapon.setText("Weapon: "+Main.player.getWeaponString());
        Label offHand = (Label) s.lookup("#offHandLabel");
        offHand.setText("Off-Hand: "+Main.player.getOffHandString());
    }


}
