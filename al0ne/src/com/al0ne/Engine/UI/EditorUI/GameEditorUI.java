package com.al0ne.Engine.UI.EditorUI;

import com.al0ne.Behaviours.World;
import com.al0ne.Engine.*;
import com.al0ne.Engine.Editing.EditingGame;
import com.al0ne.Engine.UI.Popups;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static com.al0ne.Engine.Main.clearScreen;
import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 17/05/2017.
 */
public class GameEditorUI {
    public static HBox createEditor(Stage s){

        VBox listContainer = new VBox();

        ListView<String> gameList = new ListView<>();
        gameList.setId("gamelist");
        ObservableList<String> items =FXCollections.observableArrayList (getGameData());
        gameList.setItems(items);


        Button load = new Button("Load Game");
        load.setOnAction(t -> {
            int selectedIndex = gameList.getSelectionModel().getSelectedIndex();
            if(selectedIndex > -1){
                EditingGame current = Main.edit.getGames().get(gameList.getItems().get(selectedIndex));
                Main.edit.setCurrentEdit(current);
                Popups.openWorldEditor();
                s.close();
            }
        });

        Button export = new Button("Export game");
        export.setOnAction(t->{
            int selectedIndex = gameList.getSelectionModel().getSelectedIndex();
            if(selectedIndex > -1){
                String gameName = gameList.getSelectionModel().getSelectedItem();
                EditingGame eg = Main.edit.getGames().get(gameName);

                GameChanges.save(gameName, null, eg.getCurrentEdit(), true);
            }
        });

        Button play = new Button("Play game");
        play.setOnAction(t->{
            int selectedIndex = gameList.getSelectionModel().getSelectedIndex();
            if(selectedIndex > -1){
                String gameName = gameList.getSelectionModel().getSelectedItem();
                Game g = GameChanges.copyGame(Main.edit.getGames().get(gameName).getCurrentEdit());
                if(g != null && g.getPlayer() != null &&
                        g.getWorlds().get(g.getCurrentWorld()).getStartingRoom() != null){
                    Main.game = g;
                    Main.player = g.getPlayer();
                    Main.turnCounter = g.getTurnCount();
                    Main.currentRoom = g.getRoom();
                    Main.notes.setText(g.getNotes());
                    clearScreen();

                    printToLog("Game loaded successfully.");
                    printToLog();
                    Main.currentRoom.printRoom();
                    Main.currentRoom.printName();
                    s.close();
                } else {
                    System.out.println("player is null, room is null or game is broken");
                }

            }
        });

        listContainer.getChildren().addAll(gameList, load, export, play);

        VBox newGameBox = new VBox();

        TextField nameText = new TextField();
        Label nameLabel = new Label("Name:");
        nameText.setPromptText("Awesome game");

        TextField worldText = new TextField();
        nameText.setPromptText("The village of Nir");
        Label worldLabel = new Label("Starting world name:");
        Button create = new Button("Create new Game");

        Label errorMessage = new Label("");
        errorMessage.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");


        create.setOnAction(t -> {
            if(!nameText.getText().equals("") && !worldText.getText().equals("")){
                for(EditingGame g : Main.edit.getGames().values()){
                    if(g.getCurrentEdit().getGameName().equals(nameText.getText())){
                        errorMessage.setText("A game with the same name already exists.");
                        nameText.setStyle("-fx-border-color: red;");
                        return;
                    }
                }
                nameText.setStyle("");
                worldText.setStyle("");
                errorMessage.setText("");
                EditingGame newGame = new EditingGame(nameText.getText());
                World newWorld = new World(worldText.getText());

                nameText.setText("");
                worldText.setText("");

                newGame.getCurrentEdit().addWorld(newWorld);
                newGame.setCurrentWorld(newWorld);

                Main.edit.addGame(newGame);
                Main.edit.setCurrentEdit(newGame);

                Popups.openWorldEditor();
                gameList.setItems(getGameData());
            } else{
                if(nameText.getText().equals("")){
                    errorMessage.setText("Please insert a name.");
                    nameText.setStyle("-fx-border-color: red;");
                }

                if(worldText.getText().equals("")){
                    errorMessage.setText("Please insert a world name.");
                    worldText.setStyle("-fx-border-color: red;");
                }
            }
        });



        newGameBox.getChildren().addAll(nameLabel, nameText, worldLabel, worldText, create, errorMessage);



        HBox temp = new HBox();
        temp.getChildren().addAll(newGameBox, listContainer);
        return temp;
    }

    public static ObservableList<String> getGameData(){

        ObservableList<String> data = FXCollections.observableArrayList();

        if (Main.edit.getGames().size()==0){}
        else {
            for(EditingGame g: Main.edit.getGames().values()){
                data.add(g.getCurrentEdit().getGameName());
            }
        }
        return data;
    }



}
