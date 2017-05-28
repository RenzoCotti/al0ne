package com.al0ne.Engine.UI.EditorUI;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.abstractEntities.Entity;
import com.al0ne.Engine.Game;
import com.al0ne.Engine.Main;
import com.al0ne.Entities.Items.Behaviours.Wearable.Weapon;
import com.al0ne.Entities.Items.ConcreteItems.Weapon.Sword;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by BMW on 27/05/2017.
 */
public class EditItem {

    public static Tab createItem(){
        Tab items = new Tab();
        items.setText("Items");
        items.setClosable(false);

        HBox temp = new HBox();

        VBox listItem = createListItems();

        listItem.setPrefWidth(200);


        GridPane itemContent = new GridPane();

        Label createNewItem = new Label("Create new item:");
        createNewItem.setStyle("-fx-font-weight: bold");
        itemContent.add(createNewItem, 0, 0);
        //todo: check for existing item in the db having the same (material+name) name

        TextField nameText = new TextField();
        nameText.setPromptText("Bread loaf");
        Label nameLabel = new Label("Name:");
        itemContent.add(nameLabel, 0, 1);
        itemContent.add(nameText, 1, 1);

        TextArea descText = new TextArea();
        descText.setPrefWidth(100);
        descText.setPrefHeight(50);
        Label descLabel = new Label("Description:");
        descText.setPromptText("A fresh loaf of bread, looks delicious!");
        itemContent.add(descLabel, 0, 3);
        itemContent.add(descText, 1, 3);

        Spinner<Double> weightText = new Spinner<>();
        weightText.setEditable(true);
        weightText.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 100, 0, 0.5));
        Label weightLabel = new Label("Weight:");
        itemContent.add(weightLabel, 0, 4);
        itemContent.add(weightText, 1, 4);

        Label sizeLabel = new Label("Size:");
        ObservableList<String> sizeList = FXCollections.observableArrayList(
                Size.getSizeStrings());
        ComboBox<String> sizeDisplay = new ComboBox<>(sizeList);
        itemContent.add(sizeLabel, 0, 5);
        itemContent.add(sizeDisplay, 1, 5);

        Label materialLabel = new Label("Material:");
        ObservableList<String> materialList = FXCollections.observableArrayList(Material.getAllMaterialString());
        ComboBox<String> materialDisplay = new ComboBox<>(materialList);
        itemContent.add(materialLabel, 0, 6);
        itemContent.add(materialDisplay, 1, 6);

        ToggleButton canDrop = new RadioButton("Can be dropped?");
        ToggleButton isUnique = new RadioButton("Is unique?");
        itemContent.add(canDrop, 1, 7);
        itemContent.add(isUnique, 1, 8);

        Label typeLabel = new Label("Type\n(optional):");
        ObservableList<String> typeList = FXCollections.observableArrayList("Weapon", "Armor",
                "Food", "Scroll", "Coin", "Key", "Generic");
        ComboBox<String> typeDisplay = new ComboBox<>(typeList);

        //food
        Spinner<Integer> foodValue = new Spinner<>();
        foodValue.setEditable(true);
        foodValue.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0, 1));
        Label foodLabel = new Label("Nutrition:");


        //weapon
        Label weaponTypeLabel = new Label("Weapon type:");
        ObservableList<String> weaponList = FXCollections.observableArrayList("Sword", "Axe", "Mace",
                "Spear", "Dagger");
        ComboBox<String> weaponDisplay = new ComboBox<>(weaponList);

        Spinner<Integer> damageText = new Spinner<>();
        damageText.setEditable(true);
        damageText.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0, 1));
        Label damageLabel = new Label("Damage:");

        Spinner<Integer> apText = new Spinner<>();
        apText.setEditable(true);
        apText.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0, 1));
        Label apLabel = new Label("Armor Piercing:");

        //armor
        Label armorTypeLabel = new Label("Armor type:");
        ObservableList<String> armorList = FXCollections.observableArrayList("Body Armor", "Helmet", "Shield");
        ComboBox<String> armorDisplay = new ComboBox<>(armorList);

        Spinner<Integer> armorText = new Spinner<>();
        armorText.setEditable(true);
        armorText.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0, 1));
        Label armorLabel = new Label("Armor:");

        TextArea contentText = new TextArea();
        contentText.setPrefWidth(100);
        contentText.setPrefHeight(50);
        Label contentLabel = new Label("Content:");
        contentText.setPromptText("Hello, I am writing to you to say hi.");

        typeDisplay.valueProperty().addListener((ov, t, t1) -> {
            //TODO: ADD SWITCHING FOR DIFFERENT TYPES
            itemContent.getChildren().remove(foodLabel);
            itemContent.getChildren().remove(foodValue);
            itemContent.getChildren().remove(weaponTypeLabel);
            itemContent.getChildren().remove(weaponDisplay);
            itemContent.getChildren().remove(damageText);
            itemContent.getChildren().remove(damageLabel);
            itemContent.getChildren().remove(apText);
            itemContent.getChildren().remove(apLabel);
            itemContent.getChildren().remove(armorTypeLabel);
            itemContent.getChildren().remove(armorDisplay);
            itemContent.getChildren().remove(armorText);
            itemContent.getChildren().remove(armorLabel);

            if(t1.toLowerCase().equals("food")){
                itemContent.add(foodLabel, 0, 10);
                itemContent.add(foodValue, 1, 10);
            } else if(t1.toLowerCase().equals("weapon")){
                itemContent.add(weaponTypeLabel, 0, 10);
                itemContent.add(weaponDisplay, 1, 10);
                itemContent.add(damageLabel, 0, 11);
                itemContent.add(damageText, 1, 11);
                itemContent.add(apLabel, 0, 12);
                itemContent.add(apText, 1, 12);
            } else if(t1.toLowerCase().equals("armor")){
                itemContent.add(armorTypeLabel, 0, 10);
                itemContent.add(armorDisplay, 1, 10);
                itemContent.add(armorLabel, 0, 11);
                itemContent.add(armorText, 1, 11);
            } else if(t1.toLowerCase().equals("scroll")){
                itemContent.add(contentLabel, 0, 10);
                itemContent.add(contentText, 1, 10);
            }
            System.out.println(t1);
        });

        itemContent.add(typeLabel, 0, 9);
        itemContent.add(typeDisplay, 1, 9);

        Button create = new Button("Create Item");
        create.setOnAction( t -> {
            String material = materialDisplay.getSelectionModel().getSelectedItem();
            String size = sizeDisplay.getSelectionModel().getSelectedItem();
            String type = typeDisplay.getSelectionModel().getSelectedItem();
            if(checkIfNotEmptyAndNotExisting(nameText.getText(), "name") &&
                    checkIfNotEmpty(descText.getText()) &&
                    material != null && size != null){
                if( type != null ){
                    Size s = Size.stringToSize(size.toLowerCase());
                    Material m = Material.strToMaterial(material.toLowerCase());

                    type = type.toLowerCase();

                    switch (type){
                        case "weapon":
                            String weaponType = weaponDisplay.getSelectionModel().getSelectedItem();
                            if(weaponType != null){
                                switch (weaponType.toLowerCase()){
                                    case "sword":
                                        Sword sword = new Sword("sword"+material.toLowerCase(), nameText.getText(),
                                                descText.getText(), "sharp", apText.getValue(), damageText.getValue(), weightText.getValue(), s, m );
                                        Main.edit.getCurrentEdit().addItem(sword);


                                        ((ListView<String>)listItem.getChildren().get(0)).setItems(getItems());
                                        System.out.println(sword);

                                        break;
                                    case "axe":
                                        break;
                                    case "mace":
                                        break;
                                    case "spear":
                                        break;
                                    case "dagger":
                                        break;

                                }
                            }
                            break;
                        case "armor":
                            break;
                        case "food":
                            System.out.println(foodValue.getValue());
                            break;
                        case "book":
                            break;
                        case "coin":
                            break;
                    }

                } else {

                }
            }

        });
        itemContent.add(create, 0, 14);

        itemContent.setPadding(new Insets(10, 10, 10, 10));


        temp.getChildren().addAll(itemContent, listItem);
        items.setContent(temp);

        return items;
    }

    public static ObservableList<String> getItems(){
        ArrayList<String> temp = new ArrayList<>();
        for(Entity e: Main.edit.getCurrentEdit().getItems().values()){
            temp.add(e.getID());
        }

        return FXCollections.observableArrayList (temp);
    }


    public static VBox createListItems(){
        VBox list = new VBox();

        ListView<String> games = new ListView<>();

        ObservableList<String> itemsArray = getItems();

        games.setItems(itemsArray);


        Button load = new Button("Edit Item");
        load.setOnAction(t -> {
            int selectedIndex = games.getSelectionModel().getSelectedIndex();
            if(selectedIndex > -1){
                Item i = Main.edit.getCurrentEdit().getItems().get(games.getItems().get(selectedIndex));
                System.out.println(i.getName());
            }
        });
        list.getChildren().addAll(games, load);

        return list;
    }

    public static boolean checkIfNotEmptyAndNotExisting(String s, String field){
        for (Item i : Main.edit.getCurrentEdit().getItems().values()){
            if (field.equals("name") &&( s.equals("") || i.getName().equals(s) )){
                return false;
            }
        }
        return true;
    }

    public static boolean checkIfNotEmpty(String s){
        return !s.equals((""));
    }

    public static VBox weaponStats(){
        VBox weapon = new VBox();

        HBox type = new HBox();
        Label weaponTypeLabel = new Label("Weapon type:");
        ObservableList<String> weaponList = FXCollections.observableArrayList("Sword", "Axe", "Mace",
                "Spear", "Dagger");
        ComboBox<String> weaponDisplay = new ComboBox<>(weaponList);
        type.getChildren().addAll(weaponTypeLabel, weaponDisplay);

        HBox damage = new HBox();
        Spinner<Integer> damageText = new Spinner<>();
        damageText.setEditable(true);
        damageText.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0, 1));
        Label damageLabel = new Label("Damage:");
        damage.getChildren().addAll(damageLabel, damageText);

        HBox ap = new HBox();
        Spinner<Integer> apText = new Spinner<>();
        apText.setEditable(true);
        apText.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0, 1));
        Label apLabel = new Label("Armor Piercing:");
        ap.getChildren().addAll(apLabel, apText);

        weapon.getChildren().addAll(type, damage, ap);


        return weapon;
    }
}
