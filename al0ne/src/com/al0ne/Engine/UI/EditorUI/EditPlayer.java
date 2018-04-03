package com.al0ne.Engine.UI.EditorUI;


import com.al0ne.AbstractEntities.Abstract.Item;
import com.al0ne.AbstractEntities.Pairs.Pair;
import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.Room;
import com.al0ne.AbstractEntities.Abstract.Entity;
import com.al0ne.Engine.Editing.IdNameType;
import com.al0ne.Engine.Main;
import com.al0ne.ConcreteEntities.Items.Types.Drinkable;
import com.al0ne.ConcreteEntities.Items.Types.Food;
import com.al0ne.ConcreteEntities.Items.Types.Wearable.Armor;
import com.al0ne.ConcreteEntities.Items.Types.Wearable.Weapon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import java.util.ArrayList;

/**
 * Created by BMW on 27/05/2017.
 */
public class EditPlayer extends EditTab{


    public EditPlayer() {
        super("Player", "Placeholder for player help menu");

        ArrayList<Item> inventory = new ArrayList<>();

        Label createPlayer = new Label("Create your character:");
        createPlayer.setStyle("-fx-font-weight: bold");


        TextField nameText = new TextField();
        nameText.setPromptText("Bob");
        Label nameLabel = new Label("Name:");


        TextArea storyText = new TextArea();
        storyText.setPrefWidth(100);
        storyText.setPrefHeight(50);
        Label descLabel = new Label("Your story:");
        storyText.setPromptText("You are 20 years old, and very adventurous.");


        ToggleButton realisticMode = new RadioButton("Realistic mode");



        Spinner<Integer> maxHealth = new Spinner<>();
        maxHealth.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50, 20, 1));
        Label healthLabel = new Label("Max Health:");


        Spinner<Double> weight = new Spinner<>();
        weight.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 100, 20, 1));
        Label weightLabel = new Label("Max Weight:");


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

//
//        Label errorLabel = new Label("");
//        errorLabel.setStyle(defaultErrorStyle);

        Button addItem = new Button("Add Item");
        addItem.setOnAction(t->{
            int selectedIndex = list.getSelectionModel().getSelectedIndex();
            if(selectedIndex > -1){
                IdNameType id = list.getSelectionModel().getSelectedItem();
                Item i = Main.edit.getCurrentEdit().getItems().get(id.getId());
                inventory.add(i);
                list.setStyle("");
                errorMessage.setText("");

            } else {
                errorMessage.setStyle(defaultErrorStyle);
                list.setStyle("-fx-border-color: red");
                errorMessage.setText("Please select an item to add.");
            }
        });


        create.setOnAction(t->{
            int maxHealthValue = maxHealth.getValue();
            int armorValue = armor.getValue();
            int damageValue = damage.getValue();
            int attackValue = attack.getValue();
            int dexValue = dex.getValue();
            double weightValue = weight.getValue();
            String name = nameText.getText();
            String story = storyText.getText();
            boolean needs = realisticMode.isSelected();

            if(!name.equals("") && !story.equals("") &&
                    Main.edit.getCurrentEdit().getCurrentWorld().getStartingArea().getStartingRoom() != null){
                Room startingRoom = Main.edit.getCurrentEdit().getCurrentWorld().getStartingArea().getStartingRoom();
                Player p = new Player(story, needs, maxHealthValue,
                        attackValue, dexValue, armorValue, damageValue, weightValue);

                if(inventory.size() > 0){
                    for(Item i : inventory){
                        p.addOneItem(new Pair(i, 1));
                    }
                }

                Main.edit.getCurrentEdit().getCurrentWorld().setPlayer(p);

                nameText.setStyle("");
                storyText.setStyle("");
                create.setText("Save changes");
                errorMessage.setStyle("-fx-font-weight: bold; -fx-text-fill: green;");
                errorMessage.setText("Your character was \ncreated successfully.");
            } else{
                errorMessage.setStyle(defaultErrorStyle);
                if(name.equals("")){
                    errorMessage.setText("Please insert a valid name");
                    nameText.setStyle("-fx-border-color: red;");
                }
                if(story.equals("")){
                    errorMessage.setText("Please insert a valid story");
                    storyText.setStyle("-fx-border-color: red;");
                }

                if(Main.edit.getCurrentEdit().getCurrentWorld().getStartingArea() == null){
                    errorMessage.setText("Please create and \nset a room as starting room.");
                }

            }

        });
        creationPane.add(createPlayer, 0, 0);
        creationPane.add(nameLabel, 0, 1);
        creationPane.add(nameText, 1, 1);
        creationPane.add(descLabel, 0, 2);
        creationPane.add(storyText, 1, 2);
        creationPane.add(realisticMode, 1, 3);
        creationPane.add(healthLabel, 0, 4);
        creationPane.add(maxHealth, 1, 4);
        creationPane.add(weightLabel, 0, 5);
        creationPane.add(weight, 1, 5);
        creationPane.add(attackLabel, 0, 6);
        creationPane.add(attack, 1, 6);
        creationPane.add(dexLabel, 0, 7);
        creationPane.add(dex, 1, 7);
        creationPane.add(armorLabel, 0, 8);
        creationPane.add(armor, 1, 8);
        creationPane.add(damageLabel, 0, 9);
        creationPane.add(damage, 1, 9);
        creationPane.add(addItem, 0, 10);



//
//        totalContainer.getChildren().addAll(itemContent, itemTable);
//
//        items.setContent(totalContainer);
//
        this.setOnSelectionChanged(t-> list.setItems(getTableItems()));
    }

    @Override
    public ObservableList<IdNameType> getTableItems() {
        ArrayList<IdNameType> temp = new ArrayList<>();
        for(Entity e: Main.edit.getCurrentEdit().getItems().values()){
            if(e instanceof Armor){
                temp.add(new IdNameType(e.getID(), e.getName(), "Armor"));
            } else if(e instanceof Weapon){
                temp.add(new IdNameType(e.getID(), e.getName(), "Weapon"));
            } else if(e instanceof Food || e instanceof Drinkable){
                temp.add(new IdNameType(e.getID(), e.getName(), "Food"));
            } else if(e instanceof Readable){
                temp.add(new IdNameType(e.getID(), e.getName(), "Scroll"));
            }
        }

        return FXCollections.observableArrayList (temp);
    }


}
