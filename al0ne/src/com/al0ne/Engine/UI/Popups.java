package com.al0ne.Engine.UI;

import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.Pairs.Pair;
import com.al0ne.Behaviours.Room;
import com.al0ne.Behaviours.World;
import com.al0ne.Behaviours.abstractEntities.Entity;
import com.al0ne.Engine.Editing.IdNameType;
import com.al0ne.Engine.Editing.IdName;
import com.al0ne.Engine.GameChanges;
import com.al0ne.Engine.Main;
import com.al0ne.Engine.UI.EditorUI.*;
import com.al0ne.Engine.Utility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.IDN;
import java.util.ArrayList;
import java.util.HashMap;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 24/04/2017.
 */
public class Popups {
    public static void quitDialog(Stage stage){
        Stage s = new Stage();
        s.initModality(Modality.APPLICATION_MODAL);
        s.initOwner(stage);
        VBox dialogVbox = new VBox(20);

        HBox buttonBox = new HBox(20);

        Button quitButton = new Button("Yes");
        quitButton.setOnAction(a -> System.exit(0));

        Button cancel = new Button("No");
        cancel.setOnAction(b -> s.close());

        Text text = new Text("Are you sure you want to quit?");

        buttonBox.getChildren().addAll(quitButton, cancel);
        dialogVbox.getChildren().addAll(text, buttonBox);
        dialogVbox.setPadding(new Insets(20));

        Scene dialogScene = new Scene(dialogVbox, 250, 100);
        s.setScene(dialogScene);
        s.setTitle("Quit?");

        s.show();

    }

    public static void creditsPopup(Stage stage){
        Stage s = new Stage();
        s.initModality(Modality.APPLICATION_MODAL);
        s.initOwner(stage);
        VBox dialogVbox = new VBox(20);

        HBox buttonBox = new HBox(20);

        Button cancel = new Button("Close");
        cancel.setOnAction(b -> s.close());

        Text text = new Text("Thanks for trying out my game!\nSpecial thanks to: Valerie Burgener, " +
                "Lara Bruseghini, Dario Cotti, Giovanni Campana, Luca Hofer for being my beta testers :D");

        text.maxWidth(400);
        text.setWrappingWidth(400);

        buttonBox.getChildren().addAll(cancel);
        dialogVbox.getChildren().addAll(text, buttonBox);
        dialogVbox.setPadding(new Insets(20));
        dialogVbox.setMaxSize(200, 500);

        Scene dialogScene = new Scene(dialogVbox);

        s.setScene(dialogScene);

        s.setTitle("Credits");

        s.show();

    }


    public static void restartPopup(Stage stage){
        Stage s = new Stage();
        s.initModality(Modality.APPLICATION_MODAL);
        s.initOwner(stage);
        VBox dialogVbox = new VBox(20);

        HBox buttonBox = new HBox(20);

        Button restart = new Button("Restart");
        //maybe set initial world somewhere
        restart.setOnAction(b -> {
            GameChanges.restartGame();
            s.close();
        });

        Button cancel = new Button("Cancel");
        cancel.setOnAction(b -> s.close());

        Text text = new Text("Are you sure you want to restart?");

        buttonBox.getChildren().addAll(restart, cancel);
        dialogVbox.getChildren().addAll(text, buttonBox);
        dialogVbox.setPadding(new Insets(20));
        dialogVbox.setMaxSize(200, 500);

        Scene dialogScene = new Scene(dialogVbox);

        s.setScene(dialogScene);
        s.setTitle("Restart?");

        s.show();

    }

    public static void crashPopup(Stage stage){
        Stage s = new Stage();
        s.initModality(Modality.APPLICATION_MODAL);
        s.initOwner(stage);
        VBox dialogVbox = new VBox(20);

        Button quit = new Button("Quit");
        quit.setOnAction(b -> {
            System.exit(1);
        });

        Text text = new Text("Uh oh...\nIt appears I just crashed :/\n" +
                "Please send Mr. C the dumpGame.txt that has been created.\nYour game has been saved, BTW.");

        dialogVbox.getChildren().addAll(text, quit);
        dialogVbox.setPadding(new Insets(20));
        dialogVbox.setMaxSize(200, 500);

        Scene dialogScene = new Scene(dialogVbox);

        s.setScene(dialogScene);
        s.setTitle("Crash :/");

        s.show();
    }

    public static void deathPopup(){
        Stage s = new Stage();
        s.initModality(Modality.APPLICATION_MODAL);
        VBox dialogVbox = new VBox(20);
        dialogVbox.setPrefSize(200, 100);

        Button restart = new Button("Restart");
        restart.setOnAction(b -> {
            GameChanges.restartGame();
            s.close();
        });

        Button quit = new Button("Quit");
        quit.setOnAction(b -> {
            System.exit(0);
        });

        HBox buttons = new HBox();
        buttons.setSpacing(10);
        buttons.getChildren().addAll(restart, quit);

        int total = 0;
        int visited = 0;
        for(World w : Main.game.getWorlds().values()){
            total += w.getRooms().values().size();
            for(Room r : w.getRooms().values()){
                if (!r.isFirstVisit()){
                    visited++;
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

        Text text = new Text(endString);

        dialogVbox.getChildren().addAll(text, buttons);
        dialogVbox.setPadding(new Insets(20));

        Scene dialogScene = new Scene(dialogVbox);

        s.setScene(dialogScene);
        s.setTitle("R.I.P.");
        s.show();
    }



    public static void helpPopup(){
        Stage s = new Stage();
        s.initModality(Modality.APPLICATION_MODAL);
//        s.initOwner(stage);
        VBox dialogVbox = new VBox(20);
        dialogVbox.setPrefSize(200, 100);

        Button close = new Button("Close");
        close.setOnAction(b -> {
            GameChanges.restartGame();
            s.close();
        });

        HBox buttons = new HBox();
        buttons.setSpacing(10);
        buttons.getChildren().addAll(close);

        Text text = new Text("You can type \"north\" to go north, \"east\" to go east,... (shortcut: \"n\" for north, \"s\" " +
                "for south,...)\n\n"+
                "Useful commands: \n \"examine a\", where a is an object you can see (shortcut: \"x a\")\n"+
                "\"use x on y\", \"use x\", \n\"talk to x\", \n\"attack x\", \n\"take x\", \n\"inventory\" (shortcut: \"i\")\n");

        dialogVbox.getChildren().addAll(text, buttons);
        dialogVbox.setPadding(new Insets(20));

        Scene dialogScene = new Scene(dialogVbox);

        s.setScene(dialogScene);
        s.setTitle("Help");
        s.show();
    }

    public static void openEditor(){
        Stage s = new Stage();
        s.initModality(Modality.APPLICATION_MODAL);
        HBox dialogVbox = GameEditorUI.createEditor(s);
        dialogVbox.setPrefSize(300, 300);

        Scene dialogScene = new Scene(dialogVbox);

        s.setScene(dialogScene);
        s.setTitle("Game Editor");
        s.show();
    }

    public static void openWorldEditor(){
        Stage s = new Stage();
        s.initModality(Modality.APPLICATION_MODAL);
        VBox dialogVbox = WorldEditorUI.createEditor();
        dialogVbox.setPrefSize(800, 500);

        Scene dialogScene = new Scene(dialogVbox);

        s.setScene(dialogScene);
        s.setTitle("World Editor");
        s.show();
    }

    public static void openAddEntity(ArrayList<Entity> entities){
        Stage s = new Stage();
        s.initModality(Modality.APPLICATION_MODAL);
        HBox totalContainer = new HBox();
        VBox selectionContainer = new VBox();
        selectionContainer.setPrefSize(200, 300);

        TableView<IdName> entityList = new TableView<>();
        TableColumn entityID = new TableColumn("ID");
        entityID.setMinWidth(120);
        entityID.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn entityName = new TableColumn("Name");
        entityName.setMinWidth(120);
        entityName.setCellValueFactory(new PropertyValueFactory<>("name"));

        entityList.getColumns().addAll(entityID, entityName);

        Entity e = Main.edit.getCurrentEdit().getCurrentEntity();
        if(e != null && e instanceof Room){
            if(Main.edit.getCurrentEdit().getCurrentWorld().getRooms().get(e.getID()) != null){
                for(Pair p: Main.edit.getCurrentEdit().getCurrentWorld().getRooms().get(e.getID()).getEntities().values()){
                    entities.add(p.getEntity());
                }
            }
        }


        ObservableList<IdName> entityArray = FXCollections.observableArrayList(getEntities(entities));


        entityList.setItems(entityArray);


        TabPane parent = new TabPane();

        Tab item = new Tab();
        item.setText("Item");
        item.setClosable(false);

        TableView<IdNameType> itemList = new TableView<>();
        TableColumn idColumn = new TableColumn("ID");
        idColumn.setMinWidth(120);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.setMinWidth(120);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn typeColumn = new TableColumn("Type");
        typeColumn.setMinWidth(120);
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        itemList.getColumns().addAll(idColumn, nameColumn, typeColumn);

        ObservableList<IdNameType> itemsArray = EditItem.getItems();

        itemList.setItems(itemsArray);



        item.setContent(itemList);

        parent.getTabs().add(item);


        Tab props = new Tab();
        props.setClosable(false);
        props.setText("Props");

        TableView<IdNameType> propList = new TableView<>();
        TableColumn idProp = new TableColumn("ID");
        idProp.setMinWidth(120);
        idProp.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn nameProp = new TableColumn("Name");
        nameProp.setMinWidth(120);
        nameProp.setCellValueFactory(new PropertyValueFactory<>("name"));


        propList.getColumns().addAll(idProp, nameProp);

        ObservableList<IdNameType> propArray = EditProp.getProps();

        propList.setItems(propArray);

        props.setContent(propList);

        parent.getTabs().add(props);


//        Tab npc = new Tab();
//
//        TableView<IdName> npcList = new TableView<>();
//        TableColumn npcId = new TableColumn("ID");
//        npcId.setMinWidth(120);
//        npcId.setCellValueFactory(new PropertyValueFactory<>("id"));
//
//        TableColumn npcName = new TableColumn("Name");
//        npcName.setMinWidth(120);
//        npcName.setCellValueFactory(new PropertyValueFactory<>("name"));
//
//
//        npcList.getColumns().addAll(npcId, npcName);
//
//        ObservableList<IdName> itemsArray = EditItem.getItems();
//
//        itemList.setItems(itemsArray);
//
//        item.setContent(itemList);



        Button add = new Button("Add Entity");

        add.setOnAction(t->{
            String tab = parent.getSelectionModel().getSelectedItem().getText();
            if(tab.equals("Item")){
                IdNameType temp = ((TableView<IdNameType>)parent.getSelectionModel().getSelectedItem().getContent()).
                        getSelectionModel().getSelectedItem();
                entities.add(Main.edit.getCurrentEdit().getItems().get(temp.getId()));
            } else if (tab.equals("Props")){
                IdNameType temp = ((TableView<IdNameType>)parent.getSelectionModel().getSelectedItem().getContent()).
                        getSelectionModel().getSelectedItem();
                entities.add(Main.edit.getCurrentEdit().getProps().get(temp.getId()));
            }

            entityList.setItems(FXCollections.observableArrayList(getEntities(entities)));

        });
        Button done = new Button("Done");
        done.setOnAction(t->{
//            for (Entity e: entities){
//                System.out.println(e.getName());
//            }
            s.close();
        });

        HBox temp = new HBox();
        temp.getChildren().addAll(add, done);
        selectionContainer.getChildren().addAll(parent, temp);
        totalContainer.getChildren().addAll(selectionContainer, entityList);
        totalContainer.setPrefSize(600, 600);
        totalContainer.setPadding(new Insets(10, 10, 10, 10));

        Scene dialogScene = new Scene(totalContainer);

        s.setScene(dialogScene);
        s.setTitle("Add Entity");
        s.show();
    }


    public static ArrayList<IdName> getEntities(ArrayList<Entity> entities){
        ArrayList<IdName> temp = new ArrayList<>();
        for(Entity e : entities){
            temp.add(new IdName(e.getID(), e.getName()));
        }
        return temp;
    }

    public static void openAddExit(HashMap<String, Room> exits){
        Stage s = new Stage();
        s.initModality(Modality.APPLICATION_MODAL);
        HBox totalContainer = new HBox();
        VBox selectionContainer = new VBox();
        selectionContainer.setPrefSize(200, 300);

        Label errorMessage = new Label("");
        errorMessage.setStyle("-fx-font-weight: bold; -fx-color: red;");

        TableView<IdName> roomsList = EditRoom.createRoomTable();

        //exit view
        TableView<IdNameType> exitList = new TableView<>();
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

        exitList.setItems(getExits(exits));

        selectionContainer.getChildren().addAll(exitList);

        Label addExitLabel = new Label("Add Exit:");
        ObservableList<String> directionList = FXCollections.observableArrayList("North", "East", "South",
                "West", "NorthWest", "NorthEast", "SouthWest", "SouthEast");
        ComboBox<String> directionDisplay = new ComboBox<>(directionList);

        Button addExit = new Button("Add Exit");
        addExit.setOnAction(t->{
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

        selectionContainer.getChildren().addAll(addExitLabel, directionDisplay, addExit);
        totalContainer.getChildren().addAll(selectionContainer, roomsList);

        Scene dialogScene = new Scene(totalContainer);

        s.setScene(dialogScene);
        s.setTitle("Add Exit");
        s.show();

    }

    public static ObservableList<IdNameType> getExits(HashMap<String, Room> exits){
        ArrayList<IdNameType> temp = new ArrayList<>();
        for(String dir : exits.keySet()){
            Room r = exits.get(dir);
            temp.add(new IdNameType(dir, r.getID(), r.getName()));
        }
        return FXCollections.observableArrayList(temp);
    }


}
