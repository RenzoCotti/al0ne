package com.al0ne.Engine.UI.EditorUI;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.Pairs.Pair;
import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Room;
import com.al0ne.Behaviours.abstractEntities.Entity;
import com.al0ne.Engine.Editing.IdNameType;
import com.al0ne.Engine.Main;
import com.al0ne.Entities.Items.Behaviours.Drinkable;
import com.al0ne.Entities.Items.Behaviours.Food;
import com.al0ne.Entities.Items.Behaviours.Protective;
import com.al0ne.Entities.Items.Behaviours.Wearable.Armor;
import com.al0ne.Entities.Items.Behaviours.Wearable.Helmet;
import com.al0ne.Entities.Items.Behaviours.Wearable.Shield;
import com.al0ne.Entities.Items.Behaviours.Wearable.Weapon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

import static com.al0ne.Engine.UI.EditorUI.EditItem.createItemTable;
import static com.al0ne.Engine.UI.EditorUI.EditItem.getItems;

/**
 * Created by BMW on 27/05/2017.
 */
public class EditPlayer {

    public static Tab createPlayer(){
        Tab items = new Tab();
        items.setText("Player");
        items.setClosable(false);

        HBox totalContainer = new HBox();

        GridPane itemContent = new GridPane();

        TableView<IdNameType> itemTable = createItemTable();

        ArrayList<Item> inventory = new ArrayList<>();

        Label createPlayer = new Label("Create the player:");
        createPlayer.setStyle("-fx-font-weight: bold");
        itemContent.add(createPlayer, 0, 0);

        TextField nameText = new TextField();
        nameText.setPromptText("Bob");
        Label nameLabel = new Label("Name:");
        itemContent.add(nameLabel, 0, 1);
        itemContent.add(nameText, 1, 1);

        TextArea storyText = new TextArea();
        storyText.setPrefWidth(100);
        storyText.setPrefHeight(50);
        Label descLabel = new Label("Story:");
        storyText.setPromptText("You are 20 years old, and very adventurous.");
        itemContent.add(descLabel, 0, 2);
        itemContent.add(storyText, 1, 2);

        ToggleButton realisticMode = new RadioButton("Realistic mode");
        itemContent.add(realisticMode, 1, 3);


        Spinner<Integer> maxHealth = new Spinner<>();
        maxHealth.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50, 20, 1));
        Label healthLabel = new Label("Max Health:");
        itemContent.add(healthLabel, 0, 4);
        itemContent.add(maxHealth, 1, 4);

        Spinner<Double> weight = new Spinner<>();
        weight.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 100, 20, 1));
        Label weightLabel = new Label("Max Weight:");
        itemContent.add(weightLabel, 0, 5);
        itemContent.add(weight, 1, 5);

        Spinner<Integer> attack = new Spinner<>();
        attack.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 30, 1));
        Label attackLabel = new Label("Attack:");
        itemContent.add(attackLabel, 0, 6);
        itemContent.add(attack, 1, 6);

        Spinner<Integer> dex = new Spinner<>();
        dex.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 30, 1));
        Label dexLabel = new Label("Dexterity:");
        itemContent.add(dexLabel, 0, 7);
        itemContent.add(dex, 1, 7);

        Spinner<Integer> armor = new Spinner<>();
        armor.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 0, 1));
        Label armorLabel = new Label("Armor:");
        itemContent.add(armorLabel, 0, 8);
        itemContent.add(armor, 1, 8);

        Spinner<Integer> damage = new Spinner<>();
        damage.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 1, 1));
        Label damageLabel = new Label("Hand to hand damage:");
        itemContent.add(damageLabel, 0, 9);
        itemContent.add(damage, 1, 9);

        Label errorLabel = new Label("");
        errorLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: red;");

        Button addItem = new Button("Add Item");
        addItem.setOnAction(t->{
            int selectedIndex = itemTable.getSelectionModel().getSelectedIndex();
            if(selectedIndex > -1){
                IdNameType id = itemTable.getSelectionModel().getSelectedItem();
                Item i = Main.edit.getCurrentEdit().getItems().get(id.getId());
                inventory.add(i);
                itemTable.setStyle("");
                errorLabel.setText("");

            } else {
                errorLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: red;");
                itemTable.setStyle("-fx-border-color: red");
                errorLabel.setText("Please select an item to add.");
            }
        });
        itemContent.add(addItem, 0, 10);


        Button create = new Button("Create player");
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
                    Main.edit.getCurrentEdit().getCurrentWorld().getStartingRoom() != null){
                Room startingRoom = Main.edit.getCurrentEdit().getCurrentWorld().getStartingRoom();
                Player p = new Player(name, story, needs, startingRoom, maxHealthValue,
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
                errorLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: green;");
                errorLabel.setText("Player created successfully");
            } else{
                errorLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: red;");
                if(name.equals("")){
                    errorLabel.setText("Please insert a valid name");
                    nameText.setStyle("-fx-border-color: red;");
                }
                if(story.equals("")){
                    errorLabel.setText("Please insert a valid story");
                    storyText.setStyle("-fx-border-color: red;");
                }

                if(Main.edit.getCurrentEdit().getCurrentWorld().getStartingRoom() == null){
                    errorLabel.setText("Please create and \nset a room as starting room.");
                }

            }

        });
        itemContent.add(create, 0, 11);
        itemContent.add(errorLabel, 0, 12);

        itemContent.setPadding(new Insets(5, 10, 5, 5));

        totalContainer.getChildren().addAll(itemContent, itemTable);

        items.setContent(totalContainer);

        items.setOnSelectionChanged(t-> itemTable.setItems(getItems()));

        return items;
    }

}
