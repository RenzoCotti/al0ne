package com.al0ne.Engine.UI.EditorUI;

import com.al0ne.AbstractEntities.Area;
import com.al0ne.AbstractEntities.Enums.TechLevel;
import com.al0ne.AbstractEntities.Room;
import com.al0ne.Engine.*;
import com.al0ne.Engine.Editing.EditingGame;
import com.al0ne.Engine.Game.Game;
import com.al0ne.Engine.UI.PlayUI;
import com.al0ne.Engine.UI.Popups;
import com.al0ne.Engine.Utility.GameChanges;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static com.al0ne.Engine.Main.clearScreen;
import static com.al0ne.Engine.Main.player;
import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 17/05/2017.
 */
public class GameEditorUI {
    public static HBox createEditor(Stage s){

        VBox listContainer = new VBox();

        ListView<String> gameList = new ListView<>();
        gameList.setId("gamelist");

        gameList.setOnMouseClicked(t-> {
            String gameName = gameList.getSelectionModel().getSelectedItem();
            if (t.getClickCount() == 2 && gameName != null) {
                EditingGame current = Main.edit.getGames().get(gameName);
                Main.edit.setCurrentEdit(current);
                Popups.openWorldEditor();
                s.close();
            }

        });


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
                        g.getWorlds().get(g.getCurrentWorldIndex()).getStartingArea().getStartingRoom() != null){

                    Stage game = PlayUI.createContent();

                    Main.game = g;
                    Main.player = g.getPlayer();
                    Main.turnCounter = g.getTurnCount();
                    Main.notes.setText(g.getNotes());
                    clearScreen();

                    printToLog("Game loaded successfully.");
                    printToLog();
                    Room currentRoom = player.getCurrentRoom();
                    currentRoom.printRoom();
                    currentRoom.printName();
                    s.close();

                    game.show();

                } else {
                    System.out.println("player is null, room is null or game is broken");
                }

            }
        });

        listContainer.getChildren().addAll(gameList, load, export, play);

        GridPane newGameBox = new GridPane();
        newGameBox.setPadding(new Insets(10, 10, 10, 10));

        TextField nameText = new TextField();
        Label nameLabel = new Label("Game name:");
        nameText.setPromptText("Awesome game");

        TextField worldText = new TextField();
        nameText.setPromptText("The village of Nir");
        Label worldLabel = new Label("Starting world name:");
        Label techLabel = new Label("Technology level:");
        ObservableList<String> techList = FXCollections.observableArrayList("Prehistoric", "Medieval",
                "Industrial", "Futuristic");
        ComboBox<String> techDisplay = new ComboBox<>(techList);
        techDisplay.getSelectionModel().select(0);
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

                String techString = techDisplay.getSelectionModel().getSelectedItem().toLowerCase();
                TechLevel techLevel = TechLevel.BASE;
                switch (techString){
                    case "prehistoric":
                        techLevel = TechLevel.BASE;
                        break;
                    case "medieval":
                        techLevel = TechLevel.LOW;
                        break;
                    case "industrial":
                        techLevel = TechLevel.MEDIUM;
                        break;
                    case "futuristic":
                        techLevel = TechLevel.HIGH;
                        break;
                }

                EditingGame newGame = new EditingGame(nameText.getText());
//                Area newArea = new Area(worldText.getText(), techLevel, );

                nameText.setText("");
                worldText.setText("");

                //TODO
//                newGame.getCurrentEdit().addWorld(newArea);
//                newGame.setCurrentWorld(newArea);

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





        newGameBox.add(nameLabel, 0, 0);
        newGameBox.add(nameText, 1, 0);
        newGameBox.add(worldLabel, 0, 1);
        newGameBox.add(worldText, 1, 1);
        newGameBox.add(techLabel, 0, 2);
        newGameBox.add(techDisplay, 1, 2);
        newGameBox.add(create, 0, 3);
        newGameBox.add(errorMessage, 0, 4);



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
