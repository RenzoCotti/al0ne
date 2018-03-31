package com.al0ne.Engine.UI;

import com.al0ne.AbstractEntities.*;
import com.al0ne.AbstractEntities.Pairs.Pair;
import com.al0ne.AbstractEntities.Abstract.Enemy;
import com.al0ne.AbstractEntities.Abstract.Entity;
import com.al0ne.AbstractEntities.Abstract.Item;
import com.al0ne.Engine.Editing.IdNameType;
import com.al0ne.Engine.Editing.IdNameTypeQty;
import com.al0ne.Engine.Utility.GameChanges;
import com.al0ne.Engine.Main;
import com.al0ne.Engine.UI.EditorUI.*;
import com.al0ne.Engine.Utility.Utility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 24/04/2017.
 */
public class Popups{

    public static void quitDialog(Stage s){

        HBox buttonBox = new HBox(20);


        Popup quitPopup = new Popup(100, 100, "Quit?", buttonBox, "Are you sure you want to quit?");



        Button quitButton = new Button("Yes");
        quitButton.setOnAction(a -> {
            s.close();
            quitPopup.stage.close();
        });

        Button cancel = new Button("No");
        cancel.setOnAction(b -> quitPopup.stage.close());

        buttonBox.getChildren().addAll(quitButton, cancel);

    }

    public static void creditsPopup(){
        new Popup(100, 300, "Credits", "Close", "Thanks for trying out " +
                "my game!\n\nSpecial thanks to: \n" +
                "Valerie, \n" +
                "Lara, \n" +
                "Dario, \n" +
                "Giovanni, \n" +
                "Luca, \n" +
                "JP,\n " +
                "Numa,\n " +
                "Lustro and \n" +
                "Aron\n " +
                "for being my beta testers :D");
    }


    public static void restartPopup(){

        HBox buttonBox = new HBox(20);
        Button restart = new Button("Restart");
        Button cancel = new Button("Cancel");
        buttonBox.getChildren().addAll(restart, cancel);


        Popup restartPopup = new Popup(200, 100, "Restart?",
                buttonBox, "Are you sure you want to restart?");

        restart.setOnAction(b -> {
            GameChanges.restartGame();
            restartPopup.stage.close();
        });
        cancel.setOnAction(b -> restartPopup.stage.close());
    }

    public static void crashPopup(){

        HBox buttonBox = new HBox(20);


        Button quitButton = new Button("Quit");
        quitButton.setOnAction(a -> System.exit(1));

        buttonBox.getChildren().addAll(quitButton);

        new Popup(500, 200, "Crash :/", buttonBox, "Uh oh...\nIt appears I just crashed :/\n" +
                "Please send Mr. C the dumpGame.txt that has been created.\nYour game has been saved, BTW.");
    }

    public static void deathPopup(){

        Button restart = new Button("Restart");
        Button quit = new Button("Quit");


        HBox buttons = new HBox();
        buttons.setSpacing(10);
        buttons.getChildren().addAll(restart, quit);

        int total = 0;
        int visited = 0;
        //TODO do we want to count all worlds?
        for(World w : Main.game.getWorlds()){
            for (Area a : w.getAreas()){
                total+=a.getRooms().values().size();
                for(Room r : a.getRooms().values()){
                    if (!r.isFirstVisit()){
                        visited++;
                    }
                }
            }
        }

        double percentage = ((double) visited/total)*100;

        percentage= Utility.twoDecimals(percentage);

        int value = 0;
        for (Pair p : Main.player.getInventory().values()){
            value+=((Item)p.getEntity()).getPrice();
        }



        String endString = "You have died...\nCause of death: "+Main.player.getCauseOfDeath()+".\n\nIn this game, you:\n- lasted for "+ Main.game.getTurnCount()+" turns.\n"+
                "- explored "+visited+"/"+total+" of all the available places ("+percentage+"%).\n" +
                "- had a total value of "+value+" in your inventory.\n";


        endString+="\nWant to restart?";

        Popup deathPopup = new Popup(300, 200, "R.I.P.", buttons, endString);

        quit.setOnAction(b -> {
            System.exit(0);
        });
        restart.setOnAction(b -> {
            GameChanges.restartGame();
            deathPopup.stage.close();
        });

    }



    public static void helpPopup(){
        Popup helpPopup = new Popup(600, 300, "Help", "Close", "You can type " +
                "\"north\" to go north, \"east\" to go east,... (shortcut: \"n\" for north, \"s\" " +
                "for south,...)\n\nUseful commands: \n \"examine a\", where a is an object you can see (shortcut: \"x a\")\n"+
                "\"use x on y\", \"use x\", \n\"talk to x\", \n\"attack x\", \n\"take x\", \n\"inventory\" (shortcut: \"i\")\n");
    }

    public static void openEditor(){
        Stage s = new Stage();
        s.initModality(Modality.APPLICATION_MODAL);
        HBox editor = GameEditorUI.createEditor(s);
        editor.setPrefSize(600, 300);

        Scene dialogScene = new Scene(editor);

        s.setScene(dialogScene);
        s.setTitle("Game Editor");
        s.show();
    }

    public static void openWorldEditor(){
        Stage s = new Stage();
        s.initModality(Modality.APPLICATION_MODAL);
        VBox dialogVbox = WorldEditorUI.createEditor();
        dialogVbox.setPrefSize(800, 600);

        Scene dialogScene = new Scene(dialogVbox);

        s.setScene(dialogScene);
        s.setTitle("Area Editor");
        s.show();
    }

    public static void openAddEntity(HashMap<String, Pair> entities){
        Stage s = new Stage();
        s.initModality(Modality.APPLICATION_MODAL);
        HBox totalContainer = new HBox();
        VBox selectionContainer = new VBox();
        selectionContainer.setMinSize(300, 300);

        selectionContainer.setPadding(new Insets(10, 10, 10, 10));

        TableView<IdNameTypeQty> entityList = new TableView<>();
        entityList.setMinSize(400, 300);

        entityList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn entityID = new TableColumn("ID");
        entityID.setMinWidth(80);
        entityID.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn entityName = new TableColumn("Name");
        entityName.setMinWidth(80);
        entityName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn entityType = new TableColumn("Type");
        entityType.setMinWidth(80);
        entityType.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn entityQty = new TableColumn("Qty");
        entityQty.setMinWidth(80);
        entityQty.setCellValueFactory(new PropertyValueFactory<>("qty"));


        entityList.getColumns().addAll(entityID, entityName, entityType, entityQty);

        Entity e = Main.edit.getCurrentEdit().getCurrentEntity();
        ObservableList<IdNameTypeQty> entityArray;

        //case this is a room we're editing
        if(e != null && e instanceof Room){
            //and it's a room that already exist, fill the HashMap
            //TODO FIX
            HashMap<String, Room> rooms = Main.edit.getCurrentEdit().getCurrentWorld().getStartingArea().getRooms();
            if(rooms.get(e.getID()) != null){
                for(String id: rooms.get(e.getID()).getEntities().keySet()){
                    entities.put(id, rooms.get(e.getID()).getEntities().get(id));
                }
                entityArray = FXCollections.observableArrayList(getEntities(entities, e.getID()));
            } else {
                entityArray = FXCollections.observableArrayList(getEntities(entities, null));
            }
        } else {
            entityArray = FXCollections.observableArrayList(getEntities(entities, null));
        }




        entityList.setItems(entityArray);


        TabPane entityToAddList = new TabPane();

        Tab item = new Tab();
        item.setText("Item");
        item.setClosable(false);
        TableView<IdNameType> itemList = EditTab.createTable();
        item.setContent(itemList);

        Tab props = new Tab();
        props.setClosable(false);
        props.setText("Props");
        TableView<IdNameType> propList = EditTab.createTable();
        props.setContent(propList);

        Tab npcs = new Tab();
        npcs.setClosable(false);
        npcs.setText("NPCs");
        TableView<IdNameType> npcList = EditNPC.createTable();
        npcs.setContent(npcList);


        entityToAddList.getTabs().addAll(item, props, npcs);

        Spinner<Integer> quantity = new Spinner<>();
        quantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1, 1));
        Label quantityLabel = new Label("How many?:");


        Button add = new Button("Add Entity");

        Label errorMessage = new Label("");
        errorMessage.setStyle("-fx-font-weight: bold; -fx-text-fill: red;");

        add.setOnAction(t->{
            String tab = entityToAddList.getSelectionModel().getSelectedItem().getText();
            int quantityValue = quantity.getValue();
            IdNameType temp = ((TableView<IdNameType>)entityToAddList.getSelectionModel().getSelectedItem().getContent()).
                    getSelectionModel().getSelectedItem();
            if(temp != null){
                if(tab.equals("Item")){

                    Item currentItem = Main.edit.getCurrentEdit().getItems().get(temp.getId());
                    entities.put(currentItem.getID(), new Pair(currentItem, quantityValue));
                } else if (tab.equals("Props")){
                    Prop currentProp = Main.edit.getCurrentEdit().getProps().get(temp.getId());
                    entities.put(currentProp.getID(), new Pair(currentProp, quantityValue));
                } else if (tab.equals("NPCs")){
                    NPC currentNPC = Main.edit.getCurrentEdit().getNpcs().get(temp.getId());
                    entities.put(currentNPC.getID(), new Pair(currentNPC, quantityValue));
                }
                errorMessage.setText("");
                itemList.setStyle("");
            } else{
                errorMessage.setText("Please select the item\nyou'd like to add");
                itemList.setStyle("-fx-border-color: red");
            }



            quantity.getValueFactory().setValue(1);

            if(e != null && e instanceof Room){
                //and it's a room that already exist, fill the HashMap
                //TODO
                HashMap<String, Room> rooms = Main.edit.getCurrentEdit().
                        getCurrentWorld().getStartingArea().getRooms();
                if(rooms.get(e.getID()) != null){
                    entityList.setItems(FXCollections.observableArrayList(getEntities(entities, e.getID())));
                } else {
                    entityList.setItems(FXCollections.observableArrayList(getEntities(entities, null)));
                }
            } else {
                entityList.setItems(FXCollections.observableArrayList(getEntities(entities, null)));
            }



        });
        Button done = new Button("Done");
        done.setOnAction(t->{
            s.close();
        });

        HBox buttonsAlign = new HBox();
        buttonsAlign.getChildren().addAll(add, done);

        selectionContainer.getChildren().addAll(entityToAddList, quantityLabel, quantity, buttonsAlign, errorMessage);
        totalContainer.getChildren().addAll(selectionContainer, entityList);
        totalContainer.setPrefSize(800, 600);
        totalContainer.setPadding(new Insets(10, 10, 10, 10));

        Scene dialogScene = new Scene(totalContainer);

        s.setScene(dialogScene);
        s.setTitle("Add Entity");
        s.show();
    }




    public static void openAddExit(HashMap<String, Room> exits, String room){
        Stage s = new Stage();
        s.initModality(Modality.APPLICATION_MODAL);
        HBox totalContainer = new HBox();
        VBox addExit = new VBox();
        addExit.setMinSize(400, 300);
        addExit.setPadding(new Insets(10, 10, 10, 10));

        VBox totalExits = new VBox();


        Label errorMessage = new Label("");
        errorMessage.setStyle("-fx-font-weight: bold; -fx-text-fill: red;");

        Label chooseTarget = new Label("Choose target:");
        TableView<IdNameType> roomsList = EditTab.createTable();
        roomsList.setItems(EditRoom.getRooms());

        addExit.getChildren().addAll(chooseTarget, roomsList);


        //exit view
        TableView<IdNameType> exitList = new TableView<>();
        exitList.setMinSize(300, 200);
        exitList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Label exitListRooms;
        if(room.equals("Create new Room:")){
            exitListRooms  = new Label("Exits of this room:");
        } else{
            exitListRooms = new Label("Exits of "+room+":");
        }
        TableColumn roomID = new TableColumn("Room ID");
        roomID.setMinWidth(120);
        roomID.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn directionColumn = new TableColumn("Direction");
        directionColumn.setMinWidth(120);
        directionColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn name = new TableColumn("Room name");
        name.setMinWidth(120);
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        exitList.getColumns().addAll(directionColumn, roomID, name);

        exitList.setItems(getExits(exits, room));

        totalExits.getChildren().addAll(exitListRooms, exitList);

        Label addExitLabel = new Label("Choose direction:");
        ObservableList<String> directionList = FXCollections.observableArrayList("North", "East", "South",
                "West", "NorthWest", "NorthEast", "SouthWest", "SouthEast");
        ComboBox<String> directionDisplay = new ComboBox<>(directionList);

        Button addExitButton = new Button("Add Exit");
        addExitButton.setOnAction(t->{
            String direction = directionDisplay.getSelectionModel().getSelectedItem();
            if(direction != null){
                direction = direction.toLowerCase();

                if(roomsList.getSelectionModel().getSelectedItem() != null){
//                    Room target = Main.edit.getCurrentEdit().getCurrentWorld().getRooms().
//                            get(roomsList.getSelectionModel().getSelectedItem().getId());
//
//                    exits.put(direction, target);
//                    exitList.setItems(getExits(exits, room));
                } else {
                    roomsList.setStyle("-fx-border-color: red;");
                    errorMessage.setText("Please select a destination room.");
                }



            } else {
                directionDisplay.setStyle("-fx-border-color: red;");
                errorMessage.setText("Please select a direction.");
            }
        });

        Button doneButton = new Button("Done");
        doneButton.setOnAction(t-> s.close());

        addExit.getChildren().addAll(addExitLabel, directionDisplay, addExitButton, doneButton, errorMessage);
        totalContainer.getChildren().addAll(addExit, totalExits);

        Scene dialogScene = new Scene(totalContainer);

        s.setScene(dialogScene);
        s.setTitle("Add Exit");
        s.show();

    }

    public static ObservableList<IdNameType> getExits(HashMap<String, Room> exits, String ID){
        ArrayList<IdNameType> temp = new ArrayList<>();
        for(String dir : exits.keySet()){
            Room r = exits.get(dir);
            temp.add(new IdNameType(r.getID(), r.getName(), dir));
        }
//        if(!ID.equals("Create new Room:")){
//            HashMap<String, String> exitsRoom = Main.edit.getCurrentEdit().getCurrentWorld().getRooms().get(ID).getExits();
//            for(String dir : exitsRoom.keySet()){
//                String id = exitsRoom.get(dir);
//                Room r = Main.edit.getCurrentEdit().getCurrentWorld().getRooms().get(id);
//                temp.add(new IdNameType(id, r.getName(), dir));
//            }
//        }
        return FXCollections.observableArrayList(temp);
    }

    public static ArrayList<IdNameTypeQty> getEntities(HashMap<String, Pair> entities, String roomID){
        ArrayList<IdNameTypeQty> temp = new ArrayList<>();
        for(String id : entities.keySet()){
            Entity e = entities.get(id).getEntity();
            if(e instanceof Item){
                temp.add(new IdNameTypeQty(e.getID(), e.getName(), "Item", entities.get(id).getCount()));
            } else if(e instanceof Prop){
                temp.add(new IdNameTypeQty(e.getID(), e.getName(), "Prop", entities.get(id).getCount()));
            } else if(e instanceof NPC){
                temp.add(new IdNameTypeQty(e.getID(), e.getName(), "NPC", entities.get(id).getCount()));
            } else if(e instanceof Enemy){
                temp.add(new IdNameTypeQty(e.getID(), e.getName(), "Enemy", entities.get(id).getCount()));
            }
        }
        return temp;
    }

}
