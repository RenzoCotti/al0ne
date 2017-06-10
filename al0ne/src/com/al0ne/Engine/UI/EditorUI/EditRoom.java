package com.al0ne.Engine.UI.EditorUI;

import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.JunkItem;
import com.al0ne.Behaviours.Pairs.Pair;
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

        GridPane createRoomBox = new GridPane();


        HashMap<String, Pair> entities = new HashMap<>();
        HashMap<String, Room> exits = new HashMap<>();


        TableView<IdName> roomsList = createRoomTable();


        Label errorMessage = new Label("");
        errorMessage.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");


        Label idLabel = new Label("Create new Room:");
        idLabel.setStyle("-fx-font-weight: bold");
        createRoomBox.add(idLabel, 0, 0);

        TextField nameText = new TextField();
        Label nameLabel = new Label("Name:");
        nameText.setPromptText("Shop");
        createRoomBox.add(nameLabel, 0, 1);
        createRoomBox.add(nameText, 1, 1);

        TextArea descText = new TextArea();
        descText.setPromptText("This shop is full of interesting items disposed on wooden shelves.");
        descText.setPrefWidth(200);
        descText.setPrefHeight(100);
        Label descLabel = new Label("Description:");
        createRoomBox.add(descLabel, 0, 2);
        createRoomBox.add(descText, 1, 2);

        TextArea customExit = new TextArea();
        customExit.setPrefWidth(200);
        customExit.setPrefHeight(100);
        Label customExitLabel = new Label("Exit description \n(optional):");
        customExit.setPromptText("To the east you can see a weird door, and to the south you can go back home");
        createRoomBox.add(customExitLabel, 0, 3);
        createRoomBox.add(customExit, 1, 3);

        Label startingLabel = new Label("Is the starting room?");
        RadioButton startingRoom = new RadioButton();
        createRoomBox.add(startingLabel, 0, 5);
        createRoomBox.add(startingRoom, 1, 5);

        Label addEntityLabel = new Label("Add Item/Prop/NPC/...");
        Button addEntity = new Button("Add Entity");
        addEntity.setOnAction(t-> Popups.openAddEntity(entities));
        createRoomBox.add(addEntityLabel, 0, 6);
        createRoomBox.add(addEntity, 1, 6);

        Label exitLabel = new Label("Connect to other room");
        Button addExit = new Button("Add Exit");
        addExit.setOnAction(t-> Popups.openAddExit(exits, idLabel.getText()));
        createRoomBox.add(exitLabel, 0, 7);
        createRoomBox.add(addExit, 1, 7);





        Button create = new Button("Create Room");
        Button clear = new Button("Clear");
        createRoomBox.add(create, 0, 8);
        createRoomBox.add(clear, 0, 9);

        GridPane.setMargin(create, new Insets(20, 0, 10, 0));




        createRoomBox.setPadding(new Insets(5, 10, 5, 5));

        class LoadRoom{
            public void loadRoom(Room r){
                create.setText("Save changes");
                Main.edit.getCurrentEdit().setCurrentEntity(r);
                idLabel.setText(r.getID());
                for(String s: r.getEntities().keySet()){
                    entities.put(s, r.getEntities().get(s));
                }
                nameText.setText(r.getName());
                descText.setText(r.getLongDescription());
                if(r.getCustomDirections() != null){
                    customExit.setText(r.getCustomDirections());
                }
            }

            public void clearFields(){
                nameText.setStyle("");
                descText.setStyle("");
                customExit.setStyle("");
                errorMessage.setText("");
                create.setText("Create Room");
                nameText.clear();
                descText.clear();
                customExit.clear();
                entities.clear();
                exits.clear();
                Main.edit.getCurrentEdit().setCurrentEntity(null);
            }
        }



        create.setOnAction( t -> {
            String name = nameText.getText();
            String desc = descText.getText();
            String exit = customExit.getText();
            boolean isStarting = startingRoom.isSelected();


            if(checkIfNotEmpty(name) && checkIfNotEmpty(desc) ){
                Room r;
                if(checkIfExists(name) && !(create.getText().equals("Save changes"))){
                    nameText.setStyle("-fx-border-color: red ;");
                    errorMessage.setText("A room with the same name already exists.");
                    return;
                } else if(!create.getText().equals("Save changes")){
                    r = new Room(nameText.getText(), descText.getText());

                } else {
                    r = Main.edit.getCurrentEdit().getCurrentWorld().getRooms().get(idLabel.getText());
                    idLabel.setText("Create new Room:");
                }

                nameText.setStyle("");
                descText.setStyle("");
                customExit.setStyle("");


                if(checkIfNotEmpty(exit)){
                    r.addCustomDirection(exit);
                }

                if(entities.size() > 0){
                    for(Pair p: entities.values()){
                        r.addEntity(p.getEntity(), p.getCount());
                    }
                    entities.clear();
                }

                if(exits.keySet().size() > 0){
                    for(String dir : exits.keySet()){
                        r.addExit(dir, exits.get(dir));
                    }
                    exits.clear();
                }

                Main.edit.getCurrentEdit().getCurrentWorld().putRoom(r);

                if(isStarting){
                    Main.edit.getCurrentEdit().getCurrentWorld().setStartingRoom(r);
                }



                //we update the item list and reset all the fields

                ((TableView<IdName>)listRoom.getChildren().get(0)).getItems().setAll(getRooms());
                LoadRoom loadRoom = new LoadRoom();
                loadRoom.clearFields();

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

        clear.setOnAction(t->{
            LoadRoom loadRoom = new LoadRoom();
            loadRoom.clearFields();
        });


        createRoomBox.setPadding(new Insets(10, 10, 10, 10));

        //LOADING OF ITEM
        Button load = new Button("Edit Room");
        load.setOnAction(t -> {
            int selectedIndex = roomsList.getSelectionModel().getSelectedIndex();
            if(selectedIndex > -1){
                Room r = Main.edit.getCurrentEdit().getCurrentWorld().getRooms().get(roomsList.getSelectionModel().getSelectedItem().getId());
                LoadRoom loadRoom = new LoadRoom();
                loadRoom.loadRoom(r);
            }
        });

        roomsList.setRowFactory( tv -> {
            TableRow<IdName> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    IdName rowData = row.getItem();
                    Room r = Main.edit.getCurrentEdit().getCurrentWorld().getRooms().get(rowData.getId());

                    LoadRoom loadRoom = new LoadRoom();
                    loadRoom.loadRoom(r);
                }
            });
            return row ;
        });

        Button delete = new Button("Delete Room");
        delete.setOnAction(t->{
            int selectedIndex = roomsList.getSelectionModel().getSelectedIndex();
            if(selectedIndex > -1){
                IdName idName = roomsList.getSelectionModel().getSelectedItem();
                roomsList.getItems().remove(idName);
                Main.edit.getCurrentEdit().getCurrentWorld().getRooms().remove(idName.getId());

                roomsList.setItems(getRooms());
            }
        });
        listRoom.getChildren().addAll(roomsList, load, delete);


        temp.getChildren().addAll(createRoomBox, listRoom);
        parent.getChildren().addAll(temp, errorMessage);
        rooms.setContent(parent);


        return rooms;
    }

    public static ObservableList<IdName> getRooms(){
        ArrayList<IdName> temp = new ArrayList<>();
        for(Room r: Main.edit.getCurrentEdit().getCurrentWorld().getRooms().values()){
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


    public static TableView<IdName> createRoomTable(){
        TableView<IdName> roomsList = new TableView<>();
        roomsList.setPlaceholder(new Label("No rooms created."));
        roomsList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        TableColumn idColumn = new TableColumn("ID");
        idColumn.setMinWidth(120);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.setMinWidth(120);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));


        roomsList.getColumns().addAll(idColumn, nameColumn);

        roomsList.getItems().addAll(getRooms());
        return roomsList;
    }

}
