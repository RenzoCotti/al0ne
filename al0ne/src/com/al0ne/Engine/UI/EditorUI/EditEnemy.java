package com.al0ne.Engine.UI.EditorUI;

import com.al0ne.AbstractEntities.Abstract.Item;
import com.al0ne.AbstractEntities.Abstract.Enemy;
import com.al0ne.AbstractEntities.Abstract.Entity;
import com.al0ne.Engine.Editing.IdNameType;
import com.al0ne.Engine.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import java.util.ArrayList;


/**
 * Created by BMW on 27/05/2017.
 */
public class EditEnemy extends EditTab{


    public EditEnemy() {
        super("Enemy", "placeholder for enemy help");

        ArrayList<Item> inventory = new ArrayList<>();

        Label createNewProp = new Label("Create new Enemy:");
        createNewProp.setStyle("-fx-font-weight: bold");


        TextField nameText = new TextField();
        nameText.setPromptText("wolf");
        Label nameLabel = new Label("Name:");


        TextField shortDesc = new TextField();
        shortDesc.setPromptText("a wolf");
        Label shortDescLabel = new Label("Short Description\n(optional):");


        TextArea descText = new TextArea();
        descText.setPrefWidth(100);
        descText.setPrefHeight(50);
        Label descLabel = new Label("Description:");
        descText.setPromptText("A ferocious wolf, with black fur.");


        TextArea resistanceText = new TextArea();
        resistanceText.setPrefWidth(100);
        resistanceText.setPrefHeight(50);
        Label resistanceLabel = new Label("Resistances:");
        resistanceText.setPromptText("TODO");



        Spinner<Integer> maxHealth = new Spinner<>();
        maxHealth.setEditable(true);
        maxHealth.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50, 0, 1));
        Label healthLabel = new Label("Max Health:");


        Spinner<Integer> attack = new Spinner<>();
        attack.setEditable(true);
        attack.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0, 1));
        Label attackLabel = new Label("Attack:");


        Spinner<Integer> dex = new Spinner<>();
        dex.setEditable(true);
        dex.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0, 1));
        Label dexLabel = new Label("Dexterity:");


        Spinner<Integer> armor = new Spinner<>();
        armor.setEditable(true);
        armor.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 0, 1));
        Label armorLabel = new Label("Armor:");


        Spinner<Integer> damage = new Spinner<>();
        damage.setEditable(true);
        damage.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 0, 1));
        Label damageLabel = new Label("Damage:");


        TextArea status = new TextArea();
        status.setPrefWidth(100);
        status.setPrefHeight(50);
        Label statusLabel = new Label("Inflicts status:");
        status.setPromptText("TODO");


        ToggleButton aggressive = new RadioButton("Aggressive?");
        ToggleButton isElite = new RadioButton("Can be elite?");




        class LoadEnemy{
            public void loadEnemy(Enemy enemy){
                //load loot in inventory todo
                nameText.setText(enemy.getName());
                descText.setText(enemy.getLongDescription());
                isElite.setSelected(enemy.isElite());
                maxHealth.getValueFactory().setValue(enemy.getMaxHealth());
                attack.getValueFactory().setValue(enemy.getAttack());
                dex.getValueFactory().setValue(enemy.getDexterity());
                damage.getValueFactory().setValue(enemy.getDamage());
                armor.getValueFactory().setValue(enemy.getArmorLevel());
                nameText.setStyle("");
                descText.setStyle("");
                resistanceText.setStyle("");
            }

            public void clearSelection(){
                nameText.clear();
                descText.clear();
                resistanceText.clear();
                shortDesc.clear();
                maxHealth.getValueFactory().setValue(30);
                armor.getValueFactory().setValue(0);
                damage.getValueFactory().setValue(1);
                attack.getValueFactory().setValue(30);
                dex.getValueFactory().setValue(30);
                nameText.setStyle("");
                descText.setStyle("");
                resistanceText.setStyle("");
                shortDesc.setStyle("");
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
            String description = descText.getText();
            String shortDescription = shortDesc.getText();
            boolean isAggro = aggressive.isSelected();
            boolean elite = isElite.isSelected();
            //todo, add resistances

            if(!name.equals("") && !description.equals("")){
                Enemy enemy;
//                if(){
//
//                }
                enemy = new Enemy(name, description, shortDescription,
                        maxHealthValue, attackValue, dexValue, armorValue, damageValue);

                if(elite){
                    enemy.setElite();
                }



                //adding loot, todo
                if(inventory.size() > 0){
                    for(Item i : inventory){
                        enemy.simpleAddItem(i, 1);
                    }
                }

                if(isAggro){
                    enemy.setAggro(true);
                }


                Main.edit.getCurrentEdit().addEnemy(enemy);
                list.setItems(getTableItems());

                LoadEnemy loadEnemy = new LoadEnemy();
                loadEnemy.clearSelection();

                errorMessage.setStyle("-fx-font-weight: bold; -fx-text-fill: green;");
            } else{
                errorMessage.setStyle(defaultErrorStyle);
                if(name.equals("")){
                    errorMessage.setText("Please insert a valid name");
                    nameText.setStyle("-fx-border-color: red;");
                }
                if(description.equals("")){
                    errorMessage.setText("Please insert a valid description");
                    descText.setStyle("-fx-border-color: red;");
                }
                if(shortDesc.equals("")){
                    errorMessage.setText("Please insert a short description.");
                    shortDesc.setStyle("-fx-border-color: red;");
                }

            }

        });


        clear.setOnAction(t->{
            LoadEnemy loadEnemy = new LoadEnemy();
            loadEnemy.clearSelection();
        });

        edit.setOnAction(t -> {
            int selectedIndex = list.getSelectionModel().getSelectedIndex();
            if(selectedIndex > -1){
                IdNameType tempItem = list.getItems().get(selectedIndex);
                Enemy enemy = Main.edit.getCurrentEdit().getEnemies().get(tempItem.getId());

                LoadEnemy loadItem = new LoadEnemy();
                loadItem.loadEnemy(enemy);
            }
        });

        list.setRowFactory( tv -> {
            TableRow<IdNameType> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    IdNameType rowData = row.getItem();
                    Enemy enemy = Main.edit.getCurrentEdit().getEnemies().get(rowData.getId());

                    LoadEnemy loadEnemy = new LoadEnemy();
                    loadEnemy.loadEnemy(enemy);
                }
            });
            return row;
        });




        //useless?
//        npcs.setOnSelectionChanged(t-> npcTable.setItems(getNPCs()));


        creationPane.add(createNewProp, 0, 0);
        creationPane.add(nameLabel, 0, 1);
        creationPane.add(nameText, 1, 1);
        creationPane.add(shortDescLabel, 0, 2);
        creationPane.add(shortDesc, 1, 2);
        creationPane.add(descLabel, 0, 3);
        creationPane.add(descText, 1, 3);
        creationPane.add(resistanceLabel, 0, 4);
        creationPane.add(resistanceText, 1, 4);
        creationPane.add(healthLabel, 0, 5);
        creationPane.add(maxHealth, 1, 5);
        creationPane.add(attackLabel, 0, 6);
        creationPane.add(attack, 1, 6);
        creationPane.add(dexLabel, 0, 7);
        creationPane.add(dex, 1, 7);
        creationPane.add(armorLabel, 0, 8);
        creationPane.add(armor, 1, 8);
        creationPane.add(damageLabel, 0, 9);
        creationPane.add(damage, 1, 9);
        creationPane.add(statusLabel, 0, 10);
        creationPane.add(status, 1, 10);
        creationPane.add(aggressive, 1, 11);
        creationPane.add(isElite, 1, 12);

    }

    @Override
    public ObservableList<IdNameType> getTableItems() {
        ArrayList<IdNameType> temp = new ArrayList<>();
        for(Entity e: Main.edit.getCurrentEdit().getEnemies().values()){
            temp.add(new IdNameType(e.getID(), e.getName(), "Enemy"));
        }
        return FXCollections.observableArrayList (temp);
    }

}
