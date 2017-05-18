package com.al0ne.Engine.UI;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Enums.Size;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Created by BMW on 17/05/2017.
 */
public class Editor {
    public static VBox createEditor(){
        TabPane editor = new TabPane();
        editor.setId("editor");

        Tab rooms = createRoom();

        Tab items = createItem();


        Tab props = new Tab();
        props.setText("Props");
        props.setClosable(false);

        Tab characters = new Tab();
        characters.setText("Characters");
        characters.setClosable(false);

        Tab other = new Tab();
        other.setText("Other");
        other.setClosable(false);

        editor.getTabs().addAll(rooms, items, props, characters, other);
        VBox temp = new VBox();
        temp.getChildren().add(editor);
        return temp;
    }

    public static Tab createItem(){
        Tab items = new Tab();
        items.setText("Items");
        items.setClosable(false);

        GridPane itemContent = new GridPane();

//        Label createNewItem = new Label("Create new item:");
//        createNewItem.setStyle("-fx-font-weight: bold");

        TextField idText = new TextField();
        Label idLabel = new Label("ID:");
        itemContent.add(idLabel, 0, 0);
        itemContent.add(idText, 1, 0);

        TextField nameText = new TextField();
        Label nameLabel = new Label("Name:");
        itemContent.add(nameLabel, 0, 1);
        itemContent.add(nameText, 1, 1);

        TextField shortDesc = new TextField();
        Label shortDescLabel = new Label("Short Description:");
        itemContent.add(shortDescLabel, 0, 2);
        itemContent.add(shortDesc, 1, 2);

        TextArea descText = new TextArea();
        descText.setPrefWidth(100);
        descText.setPrefHeight(50);
        Label descLabel = new Label("Description:");
        itemContent.add(descLabel, 0, 3);
        itemContent.add(descText, 1, 3);

        TextField weightText = new TextField();
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

        itemContent.setPadding(new Insets(5, 10, 5, 5));

        items.setContent(itemContent);

        return items;
    }

    public static Tab createRoom(){
        Tab rooms = new Tab();
        rooms.setText("Rooms");
        rooms.setClosable(false);

        GridPane itemContent = new GridPane();

//        Label createNewItem = new Label("Create new item:");
//        createNewItem.setStyle("-fx-font-weight: bold");

        Label idLabel = new Label("Create new Room:");
        idLabel.setStyle("-fx-font-weight: bold");
        itemContent.add(idLabel, 0, 0);

        TextField nameText = new TextField();
        Label nameLabel = new Label("Name:");
        itemContent.add(nameLabel, 0, 1);
        itemContent.add(nameText, 1, 1);

        TextArea descText = new TextArea();
        descText.setPrefWidth(100);
        descText.setPrefHeight(50);
        Label descLabel = new Label("Description:");
        itemContent.add(descLabel, 0, 2);
        itemContent.add(descText, 1, 2);

        TextArea customExit = new TextArea();
        customExit.setPrefWidth(100);
        customExit.setPrefHeight(50);
        Label customExitLabel = new Label("Exit description \n(optional):");
        itemContent.add(customExitLabel, 0, 3);
        itemContent.add(customExit, 1, 3);

        Button addEntity = new Button("Add Entity");
        itemContent.add(addEntity, 0, 4);



        itemContent.setPadding(new Insets(5, 10, 5, 5));

        rooms.setContent(itemContent);

        return rooms;
    }
}
