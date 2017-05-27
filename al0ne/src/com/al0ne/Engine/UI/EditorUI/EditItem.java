package com.al0ne.Engine.UI.EditorUI;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.abstractEntities.Entity;
import com.al0ne.Engine.Game;
import com.al0ne.Engine.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by BMW on 27/05/2017.
 */
public class EditItem {

    public static Tab createItem(){
        Tab items = new Tab();
        items.setText("Items");
        items.setClosable(false);

        HBox temp = new HBox();

        GridPane itemContent = new GridPane();

        Label createNewItem = new Label("Create new item:");
        createNewItem.setStyle("-fx-font-weight: bold");
        itemContent.add(createNewItem, 0, 0);
        //todo: check for existing item in the db having the same (material+name) name

        TextField nameText = new TextField();
        nameText.setPromptText("Bread loaf");
        Label nameLabel = new Label("Name:");
        itemContent.add(nameLabel, 0, 1);
        itemContent.add(nameText, 1, 1);

        TextField shortDesc = new TextField();
        shortDesc.setPromptText("a bread loaf");
        Label shortDescLabel = new Label("Short Description:");
        itemContent.add(shortDescLabel, 0, 2);
        itemContent.add(shortDesc, 1, 2);

        TextArea descText = new TextArea();
        descText.setPrefWidth(100);
        descText.setPrefHeight(50);
        Label descLabel = new Label("Description:");
        descText.setPromptText("A fresh loaf of bread, looks delicious!");
        itemContent.add(descLabel, 0, 3);
        itemContent.add(descText, 1, 3);

        Spinner<Double> weightText = new Spinner<>();
        weightText.setEditable(true);
        weightText.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 100, 0, 0.5));
        Label weightLabel = new Label("Weight:");
        itemContent.add(weightLabel, 0, 4);
        itemContent.add(weightText, 1, 4);

        Label sizeLabel = new Label("Size:");
        ObservableList<String> sizeList = FXCollections.observableArrayList(
                Size.getSizeStrings());
        ComboBox<String> sizeDisplay = new ComboBox<>(sizeList);
        itemContent.add(sizeLabel, 0, 5);
        itemContent.add(sizeDisplay, 1, 5);

        Label materialLabel = new Label("Material:");
        ObservableList<String> materialList = FXCollections.observableArrayList(Material.getAllMaterialString());
        ComboBox<String> materialDisplay = new ComboBox<>(materialList);
        itemContent.add(materialLabel, 0, 6);
        itemContent.add(materialDisplay, 1, 6);

        ToggleButton canDrop = new RadioButton("Can be dropped?");
        ToggleButton isUnique = new RadioButton("Is unique?");
        itemContent.add(canDrop, 1, 7);
        itemContent.add(isUnique, 1, 8);

        Label typeLabel = new Label("Type\n(optional):");
        ObservableList<String> typeList = FXCollections.observableArrayList("Weapon", "Body Armor",
                "Helmet", "Shield", "Food", "Book", "Coin", "Key");
        ComboBox<String> typeDisplay = new ComboBox<>(typeList);
        itemContent.add(typeLabel, 0, 9);
        itemContent.add(typeDisplay, 1, 9);

        Button create = new Button("Create Item");
        create.setOnAction( t -> {
            if(checkIfNotEmptyAndNotExisting(nameText.getText(), "name")
                && checkIfNotEmpty(shortDesc.getText()) &&
                    checkIfNotEmpty(descText.getText()) &&
                    materialDisplay.getSelectionModel().getSelectedItem() != null
                    && sizeDisplay.getSelectionModel().getSelectedItem() != null){
                if(typeDisplay.getSelectionModel().getSelectedItem() != null){
                    Size s = Size.stringToSize(sizeDisplay.getSelectionModel().getSelectedItem().toLowerCase());
                    System.out.println(s);
                }
            }

        });
        itemContent.add(create, 0, 12);

        itemContent.setPadding(new Insets(10, 10, 10, 10));

        temp.getChildren().addAll(itemContent, createListItems(Main.edit.getCurrentEdit().getItems().values()));
        items.setContent(itemContent);

        return items;
    }


    public static VBox createListItems(Collection<Item> items){
        VBox list = new VBox();

        ListView<String> games = new ListView<>();
        ArrayList<String> temp = new ArrayList<>();
        for(Entity e: items){
            temp.add(e.getID());
        }
        ObservableList<String> itemsArray = FXCollections.observableArrayList (temp);
        games.setItems(itemsArray);


        Button load = new Button("Edit Item");
        load.setOnAction(t -> {
            int selectedIndex = games.getSelectionModel().getSelectedIndex();
            if(selectedIndex > -1){
                Item i = Main.edit.getCurrentEdit().getItems().get(games.getItems().get(selectedIndex));
                System.out.println(i.getName());
            }
        });
        list.getChildren().addAll(games, load);

        return list;
    }

    public static boolean checkIfNotEmptyAndNotExisting(String s, String field){
        for (Item i : Main.edit.getCurrentEdit().getItems().values()){
            if (field.equals("name") &&( s.equals("") || i.getName().equals(s) )){
                return false;
            }
        }
        return true;
    }

    public static boolean checkIfNotEmpty(String s){
        return !s.equals((""));
    }
}
