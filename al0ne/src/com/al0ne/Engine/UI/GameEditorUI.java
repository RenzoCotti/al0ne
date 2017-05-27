package com.al0ne.Engine.UI;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.Pairs.Pair;
import com.al0ne.Engine.Game;
import com.al0ne.Engine.GameChanges;
import com.al0ne.Engine.Main;
import com.al0ne.Engine.Utility;
import com.al0ne.Entities.Items.Behaviours.Protective;
import com.al0ne.Entities.Items.Behaviours.Wearable.Weapon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

import static com.al0ne.Engine.UI.GameEditorUI.updateList;

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
        Button create = new Button("Create new Game");
        create.setOnAction(t -> {
            if(!nameText.getText().equals("")){
                for(Game g : Main.edit.getGames().values()){
                    if(g.getGameName().equals(nameText.getText())){
                        return;
                    }
                }
                Game newGame = new Game(nameText.getText());
                Main.edit.addGame(newGame);
                Main.edit.setCurrentEdit(newGame);
                Popups.openWorldEditor(newGame);
                updateList((ListView<String>) gameList.getChildren().get(0));
            }
        });

        newGameBox.getChildren().addAll(nameLabel, nameText, create);



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
                Game current = Main.edit.getGames().get(games.getItems().get(selectedIndex));
                System.out.println(current.getGameName());
                Main.edit.setCurrentEdit(current);
            }
        });
        list.getChildren().addAll(games, load);

        return list;
    }

    public static ObservableList<String> getGameData(){

        ObservableList<String> data = FXCollections.observableArrayList();

        if (Main.edit.getGames().size()==0){}
        else {
            for(Game g: Main.edit.getGames().values()){
                data.add(g.getGameName());
            }
        }
        return data;
    }

    public static void updateList(ListView<String> list){
        list.setItems(getGameData());
    }



}
