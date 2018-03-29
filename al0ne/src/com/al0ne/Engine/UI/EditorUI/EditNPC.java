package com.al0ne.Engine.UI.EditorUI;

import com.al0ne.AbstractEntities.Abstract.Item;
import com.al0ne.AbstractEntities.NPC;
import com.al0ne.AbstractEntities.Pairs.Subject;
import com.al0ne.AbstractEntities.Abstract.Entity;
import com.al0ne.Engine.Editing.IdNameType;
import com.al0ne.Engine.Main;
import com.al0ne.ConcreteEntities.NPCs.Shopkeeper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by BMW on 27/05/2017.
 */
public class EditNPC extends EditTab{


    public EditNPC() {
        super("NPCs", "placeholder for NPC help");

        ArrayList<Item> inventory = new ArrayList<>();
        HashMap<String, Subject> subjects = new HashMap<>();

        Label createNPC = new Label("Create a new NPC:");
        createNPC.setStyle("-fx-font-weight: bold");


        TextField nameText = new TextField();
        nameText.setPromptText("Bob");
        Label nameLabel = new Label("Name:");


        TextArea descriptionText = new TextArea();
        descriptionText.setPrefWidth(100);
        descriptionText.setPrefHeight(50);
        Label descLabel = new Label("Description:");
        descriptionText.setPromptText("A clever looking man.");


        TextArea introText = new TextArea();
        introText.setPrefWidth(100);
        introText.setPrefHeight(50);
        Label introLabel = new Label("Intro dialog:");
        introText.setPromptText("Hi, I'm Bob. How are you?");



        Spinner<Integer> maxHealth = new Spinner<>();
        maxHealth.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50, 20, 1));
        Label healthLabel = new Label("Max Health:");


        Spinner<Integer> attack = new Spinner<>();
        attack.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 30, 1));
        Label attackLabel = new Label("Attack:");


        Spinner<Integer> dex = new Spinner<>();
        dex.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 30, 1));
        Label dexLabel = new Label("Dexterity:");


        Spinner<Integer> armor = new Spinner<>();
        armor.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 0, 1));
        Label armorLabel = new Label("Armor:");


        Spinner<Integer> damage = new Spinner<>();
        damage.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 1, 1));
        Label damageLabel = new Label("Hand to hand damage:");






        ToggleButton isShopkeeper = new RadioButton("Is shopkeeper?");

        Button addSubject = new Button("Add Subject");

        //todo open a new window
        Button addItem = new Button("Add Item");


        class LoadNPC{
            public void loadNPC(NPC npc){
                nameText.setText(npc.getName());
                descriptionText.setText(npc.getLongDescription());
                maxHealth.getValueFactory().setValue(npc.getMaxHealth());
                attack.getValueFactory().setValue(npc.getAttack());
                dex.getValueFactory().setValue(npc.getDexterity());
                damage.getValueFactory().setValue(npc.getDamage());
                armor.getValueFactory().setValue(npc.getArmorLevel());
                introText.setText(npc.getIntro());
                isShopkeeper.setSelected(npc.isShopkeeper());
                nameText.setStyle("");
                descriptionText.setStyle("");
                introText.setStyle("");
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
                errorMessage.setStyle(defaultErrorStyle);
                errorMessage.setText("");
            }

            public void removeFields(){

            }

            public void addProperFields(){

            }
        }

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

                Entity entity = Main.edit.getCurrentEdit().getCurrentEntity();


//                if(entity != null && create.getText().equals("Save changes")){
//
//                    if(entity.getType() == 'n'){
//                        npc = (NPC) entity;
//
//                    } else {
//                        IdNameType tempProp = list.getSelectionModel().getSelectedItem();
//                        npc = Main.edit.getCurrentEdit().getProps().get(tempProp.getId());
//                        Main.edit.getCurrentEdit().setCurrentEntity(old);
//                    }
//
//                    old.setName(name);
//                    old.setShortDescription(shortDesc);
//                    old.setLongDescription(desc);
//                    old.setMaterial(Material.strToMaterial(material));
//                }

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
                list.setItems(getTableItems());

                LoadNPC loadNPC = new LoadNPC();
                loadNPC.clearSelection();

                errorMessage.setStyle("-fx-font-weight: bold; -fx-text-fill: green;");
            } else{
                errorMessage.setStyle("-fx-font-weight: bold; -fx-text-fill: red;");
                if(name.equals("")){
                    errorMessage.setText("Please insert a valid name");
                    nameText.setStyle("-fx-border-color: red;");
                }
                if(description.equals("")){
                    errorMessage.setText("Please insert a valid description");
                    descriptionText.setStyle("-fx-border-color: red;");
                }
                if(intro.equals("")){
                    errorMessage.setText("Please insert a valid\n introductory speech.");
                    introText.setStyle("-fx-border-color: red;");
                }

            }

        });


        clear.setOnAction(t->{
            LoadNPC loadNPC = new LoadNPC();
            loadNPC.clearSelection();
        });

        edit.setOnAction(t -> {
            int selectedIndex = list.getSelectionModel().getSelectedIndex();
            if(selectedIndex > -1){
                IdNameType tempItem = list.getItems().get(selectedIndex);
                NPC npc = Main.edit.getCurrentEdit().getNpcs().get(tempItem.getId());

                Main.edit.getCurrentEdit().setCurrentEntity(npc);

                LoadNPC loadItem = new LoadNPC();
                loadItem.loadNPC(npc);
            }
        });

        delete.setOnAction(t->{
            int selectedIndex = list.getSelectionModel().getSelectedIndex();
            if(selectedIndex > -1){
                IdNameType id = list.getSelectionModel().getSelectedItem();
                list.getItems().remove(id);
                Main.edit.getCurrentEdit().getNpcs().remove(id.getId());
                list.setItems(getTableItems());
            }
        });

        list.setRowFactory( tv -> {
            TableRow<IdNameType> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    IdNameType rowData = row.getItem();
                    NPC npc = Main.edit.getCurrentEdit().getNpcs().get(rowData.getId());

                    LoadNPC loadNPC = new LoadNPC();
                    loadNPC.loadNPC(npc);
                }
            });
            return row;
        });



        //useless?
//        npcs.setOnSelectionChanged(t-> npcTable.setItems(getNPCs()));


        creationPane.add(createNPC, 0, 0);
        creationPane.add(nameLabel, 0, 1);
        creationPane.add(nameText, 1, 1);
        creationPane.add(descLabel, 0, 2);
        creationPane.add(descriptionText, 1, 2);
        creationPane.add(introLabel, 0, 3);
        creationPane.add(introText, 1, 3);
        creationPane.add(healthLabel, 0, 4);
        creationPane.add(maxHealth, 1, 4);
        creationPane.add(attackLabel, 0, 5);
        creationPane.add(attack, 1, 5);
        creationPane.add(dexLabel, 0, 6);
        creationPane.add(dex, 1, 6);
        creationPane.add(armorLabel, 0, 7);
        creationPane.add(armor, 1, 7);
        creationPane.add(damageLabel, 0, 8);
        creationPane.add(damage, 1, 8);
        creationPane.add(isShopkeeper, 1, 9);
        creationPane.add(addSubject, 0, 10);
        creationPane.add(addItem, 0, 11);

    }

    @Override
    public ObservableList<IdNameType> getTableItems() {
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

}
