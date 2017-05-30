package com.al0ne.Engine.UI.EditorUI;

import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.Room;
import com.al0ne.Behaviours.abstractEntities.Entity;
import com.al0ne.Engine.Editing.IdName;
import com.al0ne.Engine.Main;
import com.al0ne.Engine.UI.Popups;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by BMW on 27/05/2017.
 */
public class EditRoom {

    public static Tab createRoom(){
        Tab rooms = new Tab();
        rooms.setText("Rooms");
        rooms.setClosable(false);

        VBox parent = new VBox();
        HBox temp = new HBox();

        VBox listRoom = new VBox();

        TableView<IdName> roomsList = new TableView<>();

        TableColumn idColumn = new TableColumn("ID");
        idColumn.setMinWidth(120);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.setMinWidth(120);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));

        HashMap<String, Room> exits = new HashMap<>();
        ArrayList<Entity> entities = new ArrayList<>();

        roomsList.getColumns().addAll(idColumn, nameColumn);

        roomsList.getItems().addAll(getRooms());

        ObservableList<IdName> roomsArray = getRooms();

        roomsList.setItems(roomsArray);

        Label errorMessage = new Label("");
        errorMessage.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");

        Label createNewRoom = new Label("Create new Room:");
        createNewRoom.setStyle("-fx-font-weight: bold");


        GridPane roomContent = new GridPane();
        roomContent.add(createNewRoom, 0, 0);


        Label idLabel = new Label("Create new Room:");
        idLabel.setStyle("-fx-font-weight: bold");
        roomContent.add(idLabel, 0, 0);

        TextField nameText = new TextField();
        Label nameLabel = new Label("Name:");
        nameText.setPromptText("Shop");
        roomContent.add(nameLabel, 0, 1);
        roomContent.add(nameText, 1, 1);

        TextArea descText = new TextArea();
        descText.setPromptText("This shop is full of interesting items disposed on wooden shelves.");
        descText.setPrefWidth(200);
        descText.setPrefHeight(100);
        Label descLabel = new Label("Description:");
        roomContent.add(descLabel, 0, 2);
        roomContent.add(descText, 1, 2);

        TextArea customExit = new TextArea();
        customExit.setPrefWidth(200);
        customExit.setPrefHeight(100);
        Label customExitLabel = new Label("Exit description \n(optional):");
        customExit.setPromptText("To the east you can see a weird door, and to the south you can go back home");
        roomContent.add(customExitLabel, 0, 3);
        roomContent.add(customExit, 1, 3);

        Button addEntity = new Button("Add Entity");
        addEntity.setOnAction(t-> Popups.openAddEntity(entities));
        roomContent.add(addEntity, 0, 5);

        Label addExitLabel = new Label("Add Exit:");
        ObservableList<String> directionList = FXCollections.observableArrayList("North", "East", "South",
                "West", "NorthWest", "NorthEast", "SouthWest", "SouthEast");
        ComboBox<String> directionDisplay = new ComboBox<>(directionList);
        roomContent.add(addExitLabel, 0, 4);
        roomContent.add(directionDisplay, 1, 4);
        Button addExit = new Button("Add Exit");
        addExit.setOnAction( t->{
            String direction = directionDisplay.getSelectionModel().getSelectedItem();
            if(direction != null){
                direction = direction.toLowerCase();

                if(roomsList.getSelectionModel().getSelectedItem() != null){
                    Room target = Main.edit.getCurrentEdit().getCurrentWorld().getRooms().
                            get(roomsList.getSelectionModel().getSelectedItem().getId());

                    exits.put(direction, target);
                } else {
                    roomsList.setStyle("-fx-border-color: red;");
                    errorMessage.setText("Please select a destination room.");
                }



            } else {
                directionDisplay.setStyle("-fx-border-color: red;");
                errorMessage.setText("Please select a direction.");
            }
        });
        roomContent.add(addExit, 2, 4);

        Button create = new Button("Create Room");
        roomContent.add(create, 0, 8);


        roomContent.setPadding(new Insets(5, 10, 5, 5));

        rooms.setContent(roomContent);


        create.setOnAction( t -> {
            String name = nameText.getText();
            String desc = descText.getText();
            String exit = customExit.getText();


            if(checkIfNotEmpty(name) && checkIfNotEmpty(desc) ){
                if(checkIfExists(name) && !(create.getText().equals("Save changes"))){
                    nameText.setStyle("-fx-border-color: red ;");
                    errorMessage.setText("A room with the same name already exists.");
                    return;
                }
                nameText.setStyle("");
                descText.setStyle("");
                customExit.setStyle("");

                Room r = new Room(nameText.getText(), descText.getText());

                if(checkIfNotEmpty(exit)){
                    r.addCustomDirection(exit);
                }

                if(entities.size() > 0){
                    for(Entity e: entities){
                        //TODO: ADD QUANTITY
                        r.addEntity(e);
                    }
                }

                if(exits.keySet().size() > 0){
                    for(String dir : exits.keySet()){
                        r.addExit(dir, exits.get(dir));
                    }

                    exits.clear();
                }

                Main.edit.getCurrentEdit().getCurrentWorld().putRoom(r);



                //we update the item list and reset all the fields

                ((TableView<IdName>)listRoom.getChildren().get(0)).getItems().setAll(getRooms());
                errorMessage.setText("");
                create.setText("Create Room");
                nameText.clear();
                descText.clear();
                customExit.clear();
                directionDisplay.getSelectionModel().clearSelection();


            }  else {
                if(name.equals("")){
                    errorMessage.setText("Please select a value for Name");
                    nameText.setStyle("-fx-text-box-border: red ;");
                }
                if(desc.equals("")){
                    errorMessage.setText("Please select a value for Description");
                    descText.setStyle("-fx-text-box-border: red ;");
                }
            }

        });


        roomContent.setPadding(new Insets(10, 10, 10, 10));

        //LOADING OF ITEM
        Button load = new Button("Edit Room");
        load.setOnAction(t -> {
            int selectedIndex = roomsList.getSelectionModel().getSelectedIndex();
            if(selectedIndex > -1){
                Room r = Main.edit.getCurrentEdit().getCurrentWorld().getRooms().get(roomsList.getSelectionModel().getSelectedItem().getId());
                create.setText("Save changes");

                nameText.setText(r.getName());
                descText.setText(r.getLongDescription());
                if(r.getCustomDirections() != null){
                    customExit.setText(r.getCustomDirections());
                }
            }
        });
        listRoom.getChildren().addAll(roomsList, load);


        temp.getChildren().addAll(roomContent, listRoom);
        parent.getChildren().addAll(temp, errorMessage);
        rooms.setContent(parent);

        return rooms;
    }

    public static ObservableList<IdName> getRooms(){
        ArrayList<IdName> temp = new ArrayList<>();
        for(Room r: Main.edit.getCurrentEdit().getCurrentWorld().getRooms().values()){
            System.out.println(r.getID());
            temp.add(new IdName(r.getID(), r.getName()));
        }

        return FXCollections.observableArrayList (temp);
    }


    public static boolean checkIfExists(String s){
        for (Item i : Main.edit.getCurrentEdit().getItems().values()){
            if (i.getRootName().toLowerCase().equals(s.toLowerCase())){
                return true;
            }
        }
        return false;
    }

    public static boolean checkIfNotEmpty(String s){
        return !s.equals((""));
    }

}
