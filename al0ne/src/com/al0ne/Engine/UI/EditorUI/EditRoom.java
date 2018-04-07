package com.al0ne.Engine.UI.EditorUI;

import com.al0ne.AbstractEntities.Abstract.Item;
import com.al0ne.AbstractEntities.Pairs.Pair;
import com.al0ne.AbstractEntities.Room;
import com.al0ne.Engine.Editing.IdNameType;
import com.al0ne.Engine.Main;
import com.al0ne.Engine.UI.Popups;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by BMW on 27/05/2017.
 */
public class EditRoom extends EditTab{



    public EditRoom() {
        super("Rooms", "placeholder for rooms help");

        HashMap<String, Pair> entities = new HashMap<>();
        HashMap<String, Room> exits = new HashMap<>();

        Label errorMessage = new Label("");
        errorMessage.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");


        Label idLabel = new Label("Create new Room:");
        idLabel.setStyle("-fx-font-weight: bold");


        TextField nameText = new TextField();
        Label nameLabel = new Label("Name:");
        nameText.setPromptText("Shop");


        TextArea descText = new TextArea();
        descText.setPromptText("This shop is full of interesting items disposed on wooden shelves.");
        descText.setPrefWidth(200);
        descText.setPrefHeight(100);
        Label descLabel = new Label("Description:");


        TextArea customExit = new TextArea();
        customExit.setPrefWidth(200);
        customExit.setPrefHeight(100);
        Label customExitLabel = new Label("Exit description \n(optional):");
        customExit.setPromptText("To the east you can see a weird door, and to the south you can go back home");


        Label startingLabel = new Label("Is the starting room?");
        RadioButton startingRoom = new RadioButton();


        Label addEntityLabel = new Label("Add Item/Prop/NPC/...");
        Button addEntity = new Button("Add Entity");
        addEntity.setOnAction(t-> Popups.openAddEntity(entities));


        Label exitLabel = new Label("Connect to other room");
        Button addExit = new Button("Add Exit");
        addExit.setOnAction(t-> Popups.openAddExit(exits, idLabel.getText()));





        GridPane.setMargin(create, new Insets(20, 0, 10, 0));



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


            if(!name.equals("") && !desc.equals("") ){
                Room r;
                if(checkIfExists(name) && !(create.getText().equals("Save changes"))){
                    nameText.setStyle("-fx-border-color: red ;");
                    errorMessage.setText("A room with the same name already exists.");
                    return;
                } else if(!create.getText().equals("Save changes")){
                    r = new Room(nameText.getText(), descText.getText());

                } else {
                    //TODO this file needs to be tweaked
//                    r = Main.edit.getCurrentEdit().getCurrentWorld().getRooms().get(idLabel.getText());
//                    r.setName(nameText.getText());
//                    r.setLongDescription(descText.getText());
//                    r.setCustomDirection(customExit.getText());
//                    idLabel.setText("Create new Room:");
                }

                nameText.setStyle("");
                descText.setStyle("");
                customExit.setStyle("");


                if(!exit.equals("")){
//                    r.setCustomDirection(exit);
                }

                if(entities.size() > 0){
                    for(Pair p: entities.values()){
//                        r.addEntity(p.getEntity(), p.getCount());
                    }
                    entities.clear();
                }

                if(exits.keySet().size() > 0){
                    for(String dir : exits.keySet()){
//                        r.addExit(dir, exits.get(dir));
                    }
                    exits.clear();
                }

//                Main.edit.getCurrentEdit().getCurrentWorld().addRoom(r);

                if(isStarting){
//                    Main.edit.getCurrentEdit().getCurrentWorld().setStartingRoom(r);
                }



                //we update the item list and reset all the fields

//                list.getEntities().setAll(getTableItems());
                list.setItems(getTableItems());
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


        //LOADING OF ITEM
        edit.setOnAction(t -> {
            int selectedIndex = list.getSelectionModel().getSelectedIndex();
            if(selectedIndex > -1){
//                Room r = Main.edit.getCurrentEdit().getCurrentWorld().getRooms().get(list.getSelectionModel().getSelectedItem().getId());
//                LoadRoom loadRoom = new LoadRoom();
//                loadRoom.loadRoom(r);
            }
        });

        list.setRowFactory( tv -> {
            TableRow<IdNameType> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    IdNameType rowData = row.getItem();
//                    Room r = Main.edit.getCurrentEdit().getCurrentWorld().getRooms().get(rowData.getId());

                    LoadRoom loadRoom = new LoadRoom();
//                    loadRoom.loadRoom(r);
                }
            });
            return row ;
        });

        delete.setOnAction(t->{
            int selectedIndex = list.getSelectionModel().getSelectedIndex();
            if(selectedIndex > -1){
                IdNameType idName = list.getSelectionModel().getSelectedItem();
                list.getItems().remove(idName);
//                Main.edit.getCurrentEdit().getCurrentWorld().getRooms().remove(idName.getId());

                list.setItems(getTableItems());
            }
        });

        creationPane.add(idLabel, 0, 0);
        creationPane.add(nameLabel, 0, 1);
        creationPane.add(nameText, 1, 1);
        creationPane.add(descLabel, 0, 2);
        creationPane.add(descText, 1, 2);
        creationPane.add(customExitLabel, 0, 3);
        creationPane.add(customExit, 1, 3);
        creationPane.add(startingLabel, 0, 5);
        creationPane.add(startingRoom, 1, 5);
        creationPane.add(addEntityLabel, 0, 6);
        creationPane.add(addEntity, 1, 6);
        creationPane.add(exitLabel, 0, 7);
        creationPane.add(addExit, 1, 7);
    }

    @Override
    public ObservableList<IdNameType> getTableItems() {
        ArrayList<IdNameType> temp = new ArrayList<>();
//        for(Room r: Main.edit.getCurrentEdit().getCurrentWorld().getRooms().values()){
//            temp.add(new IdNameType(r.getID(), r.getName(), "Room"));
//        }

        return FXCollections.observableArrayList (temp);
    }


    public static ObservableList<IdNameType> getRooms(){
        ArrayList<IdNameType> temp = new ArrayList<>();
//        for(Room r: Main.edit.getCurrentEdit().getCurrentWorld().getRooms().values()){
//            temp.add(new IdNameType(r.getID(), r.getName(), "Room"));
//        }

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

}
