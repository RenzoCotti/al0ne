package com.al0ne.Engine.UI.EditorUI;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.abstractEntities.Entity;
import com.al0ne.Engine.Main;
import com.al0ne.Entities.Items.Behaviours.Drinkable;
import com.al0ne.Entities.Items.Behaviours.Food;
import com.al0ne.Entities.Items.Behaviours.Protective;
import com.al0ne.Entities.Items.Behaviours.Wearable.*;
import com.al0ne.Entities.Items.ConcreteItems.Weapon.Axe;
import com.al0ne.Entities.Items.ConcreteItems.Weapon.Mace;
import com.al0ne.Entities.Items.ConcreteItems.Weapon.Sword;
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

        VBox listItem = new VBox();

        ListView<String> games = new ListView<>();

        ObservableList<String> itemsArray = getItems();

        games.setItems(itemsArray);






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

        Label typeLabel = new Label("Type:");
        ObservableList<String> typeList = FXCollections.observableArrayList("Weapon", "Armor",
                "Food", "Scroll", "Coin", "Key", "Generic");
        ComboBox<String> typeDisplay = new ComboBox<>(typeList);

        //food
        Spinner<Integer> foodValue = new Spinner<>();
        foodValue.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0, 1));
        Label foodLabel = new Label("Nutrition:");

        Label foodType = new Label("Food Type");
        ObservableList<String> foodOrDrink = FXCollections.observableArrayList("Food", "Drink");
        ComboBox<String> foodDisplay = new ComboBox<>(foodOrDrink);
        foodDisplay.valueProperty().addListener( (ov, t, t1) ->{
            if (t1.equals("Food")){
                itemContent.add(foodLabel, 0, 11);
                itemContent.add(foodValue, 1, 11);
            } else if( t1.equals("Drink")){
                itemContent.getChildren().remove(foodLabel);
                itemContent.getChildren().remove(foodValue);
            }
        });


        //weapon
        Label damageTypeLabel = new Label("Damage type:");
        ObservableList<String> damageList = FXCollections.observableArrayList("Sharp", "Blunt", "Holy",
                "Unholy");
        ComboBox<String> damageDisplay = new ComboBox<>(damageList);

        Spinner<Integer> damageText = new Spinner<>();
        damageText.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0, 1));
        Label damageLabel = new Label("Damage:");

        Spinner<Integer> apText = new Spinner<>();
        apText.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0, 1));
        Label apLabel = new Label("Armor Piercing:");

        //armor
        Label armorTypeLabel = new Label("Armor type:");
        ObservableList<String> armorList = FXCollections.observableArrayList("Body Armor", "Helmet", "Shield");
        ComboBox<String> armorDisplay = new ComboBox<>(armorList);

        Spinner<Integer> armorText = new Spinner<>();
        armorText.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0, 1));
        Label armorLabel = new Label("Armor:");

        Spinner<Integer> encText = new Spinner<>();
        encText.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0, 1));
        Label encLabel = new Label("Encumberment:");

        TextArea contentText = new TextArea();
        contentText.setPrefWidth(100);
        contentText.setPrefHeight(50);
        Label contentLabel = new Label("Content:");
        contentText.setPromptText("Hello, I am writing to you to say hi.");

        typeDisplay.valueProperty().addListener((ov, t, t1) -> {
            //TODO: ADD SWITCHING FOR DIFFERENT TYPES
            itemContent.getChildren().remove(foodLabel);
            itemContent.getChildren().remove(foodValue);
            itemContent.getChildren().remove(foodType);
            itemContent.getChildren().remove(foodDisplay);
            itemContent.getChildren().remove(damageTypeLabel);
            itemContent.getChildren().remove(damageDisplay);
            itemContent.getChildren().remove(damageText);
            itemContent.getChildren().remove(damageLabel);
            itemContent.getChildren().remove(apText);
            itemContent.getChildren().remove(apLabel);
            itemContent.getChildren().remove(armorTypeLabel);
            itemContent.getChildren().remove(armorDisplay);
            itemContent.getChildren().remove(armorText);
            itemContent.getChildren().remove(armorLabel);
            itemContent.getChildren().remove(contentText);
            itemContent.getChildren().remove(contentLabel);

            if(t1.toLowerCase().equals("food")){
                itemContent.add(foodType, 0, 10);
                itemContent.add(foodDisplay, 1, 10);
            } else if(t1.toLowerCase().equals("weapon")){
                itemContent.add(damageTypeLabel, 0, 10);
                itemContent.add(damageDisplay, 1, 10);
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
        });

        itemContent.add(typeLabel, 0, 9);
        itemContent.add(typeDisplay, 1, 9);

        Button create = new Button("Create Item");
        create.setOnAction( t -> {
            String material = materialDisplay.getSelectionModel().getSelectedItem();
            String size = sizeDisplay.getSelectionModel().getSelectedItem();
            String type = typeDisplay.getSelectionModel().getSelectedItem();

            boolean done = false;
            if(checkIfNotEmptyAndNotExisting(nameText.getText(), "name") &&
                    checkIfNotEmpty(descText.getText()) &&
                    material != null && size != null){

                String name = nameText.getText();
                String desc = descText.getText();
                double weight = weightText.getValue();


                if( type != null ){
                    Size s = Size.stringToSize(size.toLowerCase());
                    Material m = Material.strToMaterial(material.toLowerCase());

                    type = type.toLowerCase();

                    switch (type){
                        case "weapon":
                            String damageType = damageDisplay.getSelectionModel().getSelectedItem();
                            int armorpen = apText.getValue();
                            int damage = damageText.getValue();
                            if(damageType != null){
                                damageType = damageType.toLowerCase();
                                Weapon weapon = new Weapon(name, name, desc, damageType, armorpen, damage, weight, s, m);

                                Main.edit.getCurrentEdit().addItem(weapon);
                                done = true;
                            } else {
                                System.out.println("Damage type is null");
                            }
                            break;


                        case "armor":
                            int armor = armorText.getValue();
                            int encumb = encText.getValue();
                            String armorType = armorDisplay.getSelectionModel().getSelectedItem();
                            if(armorType != null){
                                armorType = armorType.toLowerCase();
                                switch (armorType){
                                    case "body armor":
                                        Armor bodyArmor = new Armor(name, name, desc, weight, armor, encumb, s, m);
                                        Main.edit.getCurrentEdit().addItem(bodyArmor);
                                        done = true;
                                        break;
                                    case "helmet":
                                        Helmet helmet = new Helmet(name, name, desc, weight, armor, encumb, s, m);
                                        Main.edit.getCurrentEdit().addItem(helmet);
                                        done = true;

                                        break;
                                    case "shield":
                                        Shield shield = new Shield(name, name, desc, weight, armor, encumb, s, m);
                                        Main.edit.getCurrentEdit().addItem(shield);
                                        done = true;
                                        break;
                                }

                            } else {
                                System.out.println("armor type is null");
                            }

                            break;
                        case "food":
                            String foodInput = foodDisplay.getSelectionModel().getSelectedItem();
                            if(foodInput != null && foodInput.equals("Food")){
                                int foodVal = foodValue.getValue();
                                Food food = new Food(name, desc, weight, s, foodVal);
                                Main.edit.getCurrentEdit().addItem(food);
                                done = true;
                            } else if(foodInput != null && foodInput.equals("Drink")){
                                Drinkable drink = new Drinkable(name, desc, weight, s);
                                Main.edit.getCurrentEdit().addItem(drink);
                                done = true;
                            } else {
                                System.out.println("food type is null");
                            }

                            break;
                        case "book":
                            break;
                        case "coin":
                            break;
                    }

                }  else {
                    System.out.println("Item type is null");
                }

                if(done){
                    ((ListView<String>)listItem.getChildren().get(0)).setItems(getItems());
                    nameText.clear();
                    descText.clear();
                    weightText.getValueFactory().setValue(0.0);
                    materialDisplay.getSelectionModel().select(-1);
                    sizeDisplay.getSelectionModel().select(-1);

                    itemContent.getChildren().remove(foodLabel);
                    itemContent.getChildren().remove(foodValue);
                    itemContent.getChildren().remove(foodDisplay);
                    itemContent.getChildren().remove(foodType);
                    itemContent.getChildren().remove(damageTypeLabel);
                    itemContent.getChildren().remove(damageDisplay);
                    itemContent.getChildren().remove(damageText);
                    itemContent.getChildren().remove(damageLabel);
                    itemContent.getChildren().remove(apText);
                    itemContent.getChildren().remove(apLabel);
                    itemContent.getChildren().remove(armorTypeLabel);
                    itemContent.getChildren().remove(armorDisplay);
                    itemContent.getChildren().remove(armorText);
                    itemContent.getChildren().remove(armorLabel);
                    itemContent.getChildren().remove(contentText);
                    itemContent.getChildren().remove(contentLabel);
                }
            }  else {
                System.out.println("name, description, material or size are null");
            }

        });
        itemContent.add(create, 0, 14);

        itemContent.setPadding(new Insets(10, 10, 10, 10));

        Button load = new Button("Edit Item");
        load.setOnAction(t -> {
            int selectedIndex = games.getSelectionModel().getSelectedIndex();
            if(selectedIndex > -1){
                Item i = Main.edit.getCurrentEdit().getItems().get(games.getItems().get(selectedIndex));
                //TODO: ADD SWITCHING FOR DIFFERENT TYPES
                itemContent.getChildren().remove(foodLabel);
                itemContent.getChildren().remove(foodValue);
                itemContent.getChildren().remove(foodDisplay);
                itemContent.getChildren().remove(foodType);
                itemContent.getChildren().remove(damageTypeLabel);
                itemContent.getChildren().remove(damageDisplay);
                itemContent.getChildren().remove(damageText);
                itemContent.getChildren().remove(damageLabel);
                itemContent.getChildren().remove(apText);
                itemContent.getChildren().remove(apLabel);
                itemContent.getChildren().remove(armorTypeLabel);
                itemContent.getChildren().remove(armorDisplay);
                itemContent.getChildren().remove(armorText);
                itemContent.getChildren().remove(armorLabel);
                itemContent.getChildren().remove(contentText);
                itemContent.getChildren().remove(contentLabel);

                nameText.setText(i.getRootName());
                descText.setText(i.getLongDescription());
                sizeDisplay.setValue(Size.intToString(i.getSize()));
                materialDisplay.setValue(Material.materialToString(i.getMaterial()));
                weightText.getValueFactory().setValue(i.getWeight());

                if(i instanceof Food){
                    itemContent.add(foodType, 0, 10);
                    itemContent.add(foodDisplay, 1, 10);

                    itemContent.add(foodLabel, 0, 11);
                    itemContent.add(foodValue, 1, 11);

                    foodDisplay.setValue("Food");
                    foodValue.getValueFactory().setValue(((Food) i).getFoodValue());
                } else if(i instanceof Drinkable){
                    itemContent.add(foodType, 0, 10);
                    itemContent.add(foodDisplay, 1, 10);

                    foodDisplay.setValue("Drink");
                } else if(i instanceof Weapon){
                    itemContent.add(damageTypeLabel, 0, 10);
                    itemContent.add(damageDisplay, 1, 10);
                    itemContent.add(damageLabel, 0, 11);
                    itemContent.add(damageText, 1, 11);
                    itemContent.add(apLabel, 0, 12);
                    itemContent.add(apText, 1, 12);

                    damageDisplay.setValue(((Weapon) i).getDamageType());
                    damageText.getValueFactory().setValue(((Weapon) i).getDamage());
                    apText.getValueFactory().setValue(((Weapon) i).getArmorPenetration());
                } else if(i instanceof Protective){
                    itemContent.add(armorTypeLabel, 0, 10);
                    itemContent.add(armorDisplay, 1, 10);
                    itemContent.add(armorLabel, 0, 11);
                    itemContent.add(armorText, 1, 11);

                    armorText.getValueFactory().setValue(((Protective) i).getArmor());
                    //TODO fix armor
//                    armorDisplay.setValue(i.get);
                } else if(i instanceof Readable){
                    itemContent.add(contentLabel, 0, 10);
                    itemContent.add(contentText, 1, 10);
                }
            }
        });
        listItem.getChildren().addAll(games, load);


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

}
