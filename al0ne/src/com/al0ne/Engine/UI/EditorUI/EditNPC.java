package com.al0ne.Engine.UI.EditorUI;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.NPC;
import com.al0ne.Behaviours.Pairs.Pair;
import com.al0ne.Behaviours.Pairs.Subject;
import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Room;
import com.al0ne.Behaviours.abstractEntities.Entity;
import com.al0ne.Engine.Editing.IdNameType;
import com.al0ne.Engine.Main;
import com.al0ne.Entities.Items.Behaviours.Drinkable;
import com.al0ne.Entities.Items.Behaviours.Food;
import com.al0ne.Entities.Items.Behaviours.Protective;
import com.al0ne.Entities.Items.Behaviours.Wearable.Weapon;
import com.al0ne.Entities.NPCs.Shopkeeper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.HashMap;

import static com.al0ne.Engine.UI.EditorUI.EditItem.createItemTable;
import static com.al0ne.Engine.UI.EditorUI.EditItem.getItems;

/**
 * Created by BMW on 27/05/2017.
 */
public class EditNPC {

    public static Tab createNPC(){
        Tab npcs = new Tab();
        npcs.setText("NPC");
        npcs.setClosable(false);

        HBox totalContainer = new HBox();

        GridPane itemContent = new GridPane();

        TableView<IdNameType> npcTable = createNPCTable();

        ArrayList<Item> inventory = new ArrayList<>();
        HashMap<String, Subject> subjects = new HashMap<>();

        Label createNPC = new Label("Create a new NPC:");
        createNPC.setStyle("-fx-font-weight: bold");
        itemContent.add(createNPC, 0, 0);

        TextField nameText = new TextField();
        nameText.setPromptText("Bob");
        Label nameLabel = new Label("Name:");
        itemContent.add(nameLabel, 0, 1);
        itemContent.add(nameText, 1, 1);

        TextArea descriptionText = new TextArea();
        descriptionText.setPrefWidth(100);
        descriptionText.setPrefHeight(50);
        Label descLabel = new Label("Description:");
        descriptionText.setPromptText("A clever looking man.");
        itemContent.add(descLabel, 0, 2);
        itemContent.add(descriptionText, 1, 2);

        TextArea introText = new TextArea();
        introText.setPrefWidth(100);
        introText.setPrefHeight(50);
        Label introLabel = new Label("Intro dialog:");
        introText.setPromptText("Hi, I'm Bob. How are you?");
        itemContent.add(introLabel, 0, 3);
        itemContent.add(introText, 1, 3);


        Spinner<Integer> maxHealth = new Spinner<>();
        maxHealth.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50, 20, 1));
        Label healthLabel = new Label("Max Health:");
        itemContent.add(healthLabel, 0, 4);
        itemContent.add(maxHealth, 1, 4);

        Spinner<Integer> attack = new Spinner<>();
        attack.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 30, 1));
        Label attackLabel = new Label("Attack:");
        itemContent.add(attackLabel, 0, 5);
        itemContent.add(attack, 1, 5);

        Spinner<Integer> dex = new Spinner<>();
        dex.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 30, 1));
        Label dexLabel = new Label("Dexterity:");
        itemContent.add(dexLabel, 0, 6);
        itemContent.add(dex, 1, 6);

        Spinner<Integer> armor = new Spinner<>();
        armor.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 0, 1));
        Label armorLabel = new Label("Armor:");
        itemContent.add(armorLabel, 0, 7);
        itemContent.add(armor, 1, 7);

        Spinner<Integer> damage = new Spinner<>();
        damage.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 1, 1));
        Label damageLabel = new Label("Hand to hand damage:");
        itemContent.add(damageLabel, 0, 8);
        itemContent.add(damage, 1, 8);

        Label errorLabel = new Label("");
        errorLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: red;");

        ToggleButton isShopkeeper = new RadioButton("Is shopkeeper?");
        itemContent.add(isShopkeeper, 1, 9);

        Button addSubject = new Button("Add Subject");
        itemContent.add(addSubject, 0, 10);


        //todo open a new window
        Button addItem = new Button("Add Item");
        itemContent.add(addItem, 0, 11);


        class LoadNPC{
            public void loadNPC(NPC npc){

            }

            public void clearSelection(){
                nameText.clear();
                descriptionText.clear();
                introText.clear();
                maxHealth.getValueFactory().setValue(30);
                armor.getValueFactory().setValue(0);
                damage.getValueFactory().setValue(1);
                attack.getValueFactory().setValue(30);
                dex.getValueFactory().setValue(30);
                isShopkeeper.setSelected(false);
                nameText.setStyle("");
                descriptionText.setStyle("");
                introText.setStyle("");
            }

            public void removeFields(){

            }

            public void addProperFields(){

            }
        }



        Button create = new Button("Create NPC");
        create.setOnAction(t->{
            int maxHealthValue = maxHealth.getValue();
            int armorValue = armor.getValue();
            int damageValue = damage.getValue();
            int attackValue = attack.getValue();
            int dexValue = dex.getValue();
            String name = nameText.getText();
            String description = descriptionText.getText();
            String intro = introText.getText();
            boolean shopkeeper = isShopkeeper.isSelected();

            if(!name.equals("") && !description.equals("") && !intro.equals("")){

                NPC npc;

                if(shopkeeper){
                    npc = new Shopkeeper(name, description, intro, maxHealthValue, attackValue, dexValue, armorValue, damageValue);
                } else{
                    npc = new NPC(name, description, intro, maxHealthValue, attackValue, dexValue, armorValue, damageValue);
                }

                if(inventory.size() > 0){
                    for(Item i : inventory){
                        npc.simpleAddItem(i, 1);
                    }
                }

                Main.edit.getCurrentEdit().addNPC(npc);
                npcTable.setItems(getNPCs());

                LoadNPC loadNPC = new LoadNPC();
                loadNPC.clearSelection();

                errorLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: green;");
            } else{
                errorLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: red;");
                if(name.equals("")){
                    errorLabel.setText("Please insert a valid name");
                    nameText.setStyle("-fx-border-color: red;");
                }
                if(description.equals("")){
                    errorLabel.setText("Please insert a valid description");
                    descriptionText.setStyle("-fx-border-color: red;");
                }
                if(intro.equals("")){
                    errorLabel.setText("Please insert a valid\n introductory speech.");
                    introText.setStyle("-fx-border-color: red;");
                }

            }

        });
        itemContent.add(create, 0, 12);
        GridPane.setMargin(create, new Insets(20, 0, 10, 0));


        Button clear = new Button("Clear");
        clear.setOnAction(t->{
            LoadNPC loadNPC = new LoadNPC();
            loadNPC.clearSelection();
        });
        itemContent.add(clear, 0, 13);
        itemContent.add(errorLabel, 0, 14);


        itemContent.setPadding(new Insets(5, 10, 5, 5));

        totalContainer.getChildren().addAll(itemContent, npcTable);

        npcs.setContent(totalContainer);

        npcs.setOnSelectionChanged(t-> npcTable.setItems(getNPCs()));

        return npcs;
    }

    public static ObservableList<IdNameType> getNPCs(){
        ArrayList<IdNameType> temp = new ArrayList<>();
        for(Entity e: Main.edit.getCurrentEdit().getNpcs().values()){
            if(e instanceof Shopkeeper){
                temp.add(new IdNameType(e.getID(), e.getName(), "Shopkeeper"));
            } else if(e instanceof NPC){
                temp.add(new IdNameType(e.getID(), e.getName(), "NPC"));
            }
        }
        return FXCollections.observableArrayList (temp);
    }

    public static TableView<IdNameType> createNPCTable(){
        TableView<IdNameType> itemList = new TableView<>();
        itemList.setPlaceholder(new Label("No NPCs created."));
        itemList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


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


        ObservableList<IdNameType> itemsArray = getNPCs();

        itemList.setItems(itemsArray);
        return itemList;
    }

}
