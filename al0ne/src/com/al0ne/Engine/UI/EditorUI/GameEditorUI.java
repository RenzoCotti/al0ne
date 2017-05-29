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

/**
 * Created by BMW on 17/05/2017.
 */
public class GameEditorUI {
    public static HBox createEditor(){

        VBox gameList = createList();

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
                newGame.getCurrentEdit().addWorld(newWorld);
                newGame.setCurrentWorld(newWorld);
                Main.edit.addGame(newGame);
                Main.edit.setCurrentEdit(newGame);
                Popups.openWorldEditor();
                updateList((ListView<String>) gameList.getChildren().get(0));
            } else{
                if(!nameText.getText().equals("")){
                    errorMessage.setText("Please insert a name.");
                    nameText.setStyle("-fx-border-color: red;");
                }

                if(!worldText.getText().equals("")){
                    errorMessage.setText("Please insert a name.");
                    nameText.setStyle("-fx-border-color: red;");
                }
            }
        });

        newGameBox.getChildren().addAll(nameLabel, nameText, worldLabel, worldText, create, errorMessage);



        HBox temp = new HBox();
        temp.getChildren().addAll(newGameBox, gameList);
        return temp;
    }

    public static VBox createList(){
        VBox list = new VBox();

        ListView<String> games = new ListView<>();
        games.setId("gamelist");
        ObservableList<String> items =FXCollections.observableArrayList (getGameData());
        games.setItems(items);


        Button load = new Button("Load Game");
        load.setOnAction(t -> {
            int selectedIndex = games.getSelectionModel().getSelectedIndex();
            if(selectedIndex > -1){
                EditingGame current = Main.edit.getGames().get(games.getItems().get(selectedIndex));
                Main.edit.setCurrentEdit(current);
                Popups.openWorldEditor();
            }
        });
        list.getChildren().addAll(games, load);

        return list;
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

    public static void updateList(ListView<String> list){
        list.setItems(getGameData());
    }



}
