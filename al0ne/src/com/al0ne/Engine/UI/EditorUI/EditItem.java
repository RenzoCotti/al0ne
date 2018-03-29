package com.al0ne.Engine.UI.EditorUI;

import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.AbstractEntities.Abstract.Item;
import com.al0ne.AbstractEntities.Abstract.Entity;
import com.al0ne.Engine.Editing.IdNameType;
import com.al0ne.Engine.Main;
import com.al0ne.ConcreteEntities.Items.Types.Drinkable;
import com.al0ne.ConcreteEntities.Items.Types.Food;
import com.al0ne.ConcreteEntities.Items.Types.Protective;
import com.al0ne.ConcreteEntities.Items.Types.Wearable.Armor;
import com.al0ne.ConcreteEntities.Items.Types.Wearable.Helmet;
import com.al0ne.ConcreteEntities.Items.Types.Wearable.Shield;
import com.al0ne.ConcreteEntities.Items.Types.Wearable.Weapon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import java.util.ArrayList;

/**
 * Created by BMW on 17/07/2017.
 */
public class EditItem extends EditTab{
    public EditItem() {
        super("Items", "this is a placeholder for a help menu");

        Label createNewItem = new Label("Create new item:");
        createNewItem.setStyle("-fx-font-weight: bold");

        TextField nameText = new TextField();
        nameText.setPromptText("Bread loaf");
        Label nameLabel = new Label("Name:");


        TextArea descText = new TextArea();
        descText.setPrefWidth(100);
        descText.setPrefHeight(80);
        Label descLabel = new Label("Description:");
        descText.setPromptText("A fresh loaf of bread, looks delicious!");


        Spinner<Double> weightText = new Spinner<>();
        weightText.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 100, 0, 0.5));
        Label weightLabel = new Label("Weight:");


        Label sizeLabel = new Label("Size:");
        ObservableList<String> sizeList = FXCollections.observableArrayList(
                Size.getSizeStrings());
        ComboBox<String> sizeDisplay = new ComboBox<>(sizeList);
        sizeDisplay.getSelectionModel().select(sizeList.size()/2);


        Label materialLabel = new Label("Material:");
        ObservableList<String> materialList = FXCollections.observableArrayList(Material.getAllMaterialString());
        ComboBox<String> materialDisplay = new ComboBox<>(materialList);
        materialDisplay.getSelectionModel().select(materialList.size()-1);



        RadioButton canDrop = new RadioButton("Can be dropped?");
        RadioButton isUnique = new RadioButton("Is unique?");


        Label typeLabel = new Label("Type:");
        ObservableList<String> typeList = FXCollections.observableArrayList("Weapon", "Armor",
                "Food", "Scroll", "Coin", "Key", "Generic");
        ComboBox<String> typeDisplay = new ComboBox<>(typeList);

        //food
        Spinner<Integer> foodValue = new Spinner<>();
        foodValue.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0, 1));
        Label foodLabel = new Label("Nutrition:");

        Label foodType = new Label("Type");
        ObservableList<String> foodOrDrink = FXCollections.observableArrayList("Food", "Drink");
        ComboBox<String> foodDisplay = new ComboBox<>(foodOrDrink);
        foodDisplay.valueProperty().addListener( (ov, t, t1) ->{
            if(t1 == null){
                return;
            }
            if (t1.equals("Food")){
                creationPane.add(foodLabel, 0, 11);
                creationPane.add(foodValue, 1, 11);
            } else if( t1.equals("Drink")){
                creationPane.getChildren().remove(foodLabel);
                creationPane.getChildren().remove(foodValue);
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


        class LoadItem{
            public void loadItem(Item i){
                Main.edit.getCurrentEdit().setCurrentEntity(i);

                create.setText("Save changes");

                clearSelection();

                nameText.setText(i.getRootName());
                descText.setText(i.getLongDescription());
                sizeDisplay.setValue(Size.intToString(i.getSize()));
                materialDisplay.setValue(Material.materialToString(i.getMaterial()));
                weightText.getValueFactory().setValue(i.getWeight());

                //"Weapon", "Armor", "Food", "Scroll", "Coin", "Key", "Generic");

                if(i instanceof Food){
                    typeDisplay.getSelectionModel().select("Food");

                    foodDisplay.getSelectionModel().select("Food");
                    foodValue.getValueFactory().setValue(((Food) i).getFoodValue());
                } else if(i instanceof Drinkable){
                    typeDisplay.getSelectionModel().select("Food");
                    foodDisplay.getSelectionModel().select("Drink");
                } else if(i instanceof Weapon){
                    typeDisplay.getSelectionModel().select("Weapon");

                    damageDisplay.setValue(((Weapon) i).getDamageType());
                    damageText.getValueFactory().setValue(((Weapon) i).getDamage());
                    apText.getValueFactory().setValue(((Weapon) i).getArmorPenetration());
                } else if(i instanceof Protective){
                    typeDisplay.getSelectionModel().select("Armor");

                    armorText.getValueFactory().setValue(((Protective) i).getArmor());
                    encText.getValueFactory().setValue(((Protective) i).getEncumberment());

                    if(((Protective) i).getPart().equals("body")){
                        armorDisplay.getSelectionModel().select("Body Armor");
                    } else if(((Protective) i).getPart().equals("head")){
                        armorDisplay.getSelectionModel().select("Helmet");
                    } else if(((Protective) i).getPart().equals("off hand")){
                        armorDisplay.getSelectionModel().select("Shield");
                    }

                } else if(i instanceof Readable){
                    typeDisplay.getSelectionModel().select(3);
                    creationPane.add(contentLabel, 0, 10);
                    creationPane.add(contentText, 1, 10);
                }
            }

            public void clearSelection(){
                errorMessage.setText("");
                create.setText("Create Item");
                nameText.clear();
                descText.clear();
                weightText.getValueFactory().setValue(0.0);
                materialDisplay.getSelectionModel().select(materialList.size()-1);
                sizeDisplay.getSelectionModel().select(sizeList.size()/2);
                armorDisplay.getSelectionModel().clearSelection();
                damageDisplay.getSelectionModel().clearSelection();
                foodDisplay.getSelectionModel().clearSelection();
                typeDisplay.getSelectionModel().clearSelection();

                nameText.setStyle("");
                descText.setStyle("");
                sizeDisplay.setStyle("");
                materialDisplay.setStyle("");

                removeFields();
            }

            public void removeFields(){
                creationPane.getChildren().remove(foodLabel);
                creationPane.getChildren().remove(foodValue);
                creationPane.getChildren().remove(foodDisplay);
                creationPane.getChildren().remove(foodType);

                creationPane.getChildren().remove(damageTypeLabel);
                creationPane.getChildren().remove(damageDisplay);
                creationPane.getChildren().remove(damageText);
                creationPane.getChildren().remove(damageLabel);
                creationPane.getChildren().remove(apText);
                creationPane.getChildren().remove(apLabel);

                creationPane.getChildren().remove(armorTypeLabel);
                creationPane.getChildren().remove(armorDisplay);
                creationPane.getChildren().remove(armorText);
                creationPane.getChildren().remove(armorLabel);
                creationPane.getChildren().remove(encLabel);
                creationPane.getChildren().remove(encText);

                creationPane.getChildren().remove(contentText);
                creationPane.getChildren().remove(contentLabel);
            }

            public void addProperFields(String s){
                if(s == null){
                } else if(s.toLowerCase().equals("food")){
                    creationPane.add(foodType, 0, 10);
                    creationPane.add(foodDisplay, 1, 10);
                } else if(s.toLowerCase().equals("weapon")){
                    creationPane.add(damageTypeLabel, 0, 10);
                    creationPane.add(damageDisplay, 1, 10);
                    creationPane.add(damageLabel, 0, 11);
                    creationPane.add(damageText, 1, 11);
                    creationPane.add(apLabel, 0, 12);
                    creationPane.add(apText, 1, 12);
                } else if(s.toLowerCase().equals("armor")){
                    creationPane.add(armorTypeLabel, 0, 10);
                    creationPane.add(armorDisplay, 1, 10);
                    creationPane.add(armorLabel, 0, 11);
                    creationPane.add(armorText, 1, 11);
                    creationPane.add(encLabel, 0, 12);
                    creationPane.add(encText, 1, 12);
                } else if(s.toLowerCase().equals("scroll")){
                    creationPane.add(contentLabel, 0, 10);
                    creationPane.add(contentText, 1, 10);
                }
            }
        }


        typeDisplay.valueProperty().addListener((ov, t, t1) -> {
            LoadItem loadItem = new LoadItem();
            loadItem.removeFields();
            loadItem.addProperFields(t1);
        });




        clear.setOnAction(t->{
            LoadItem loadItem = new LoadItem();
            loadItem.clearSelection();
        });

        create.setOnAction( t -> {
            String name = nameText.getText();
            String desc = descText.getText();
            double weight = weightText.getValue();
            String material = materialDisplay.getSelectionModel().getSelectedItem();
            String size = sizeDisplay.getSelectionModel().getSelectedItem();
            String type = typeDisplay.getSelectionModel().getSelectedItem();


            boolean done = false;
            if(!name.equals("") && !desc.equals("") && material != null && size != null){
                if(checkIfExists(name) && !(create.getText().equals("Save changes"))){
                    nameText.setStyle("-fx-border-color: red ;");
                    errorMessage.setText("That name already exists.");
                    return;
                }

                nameText.setStyle("");
                descText.setStyle("");
                sizeDisplay.setStyle("");
                materialDisplay.setStyle("");


                Size s = Size.stringToSize(size.toLowerCase());
                Material m = Material.strToMaterial(material.toLowerCase());





                if( type != null ){
                    typeDisplay.setStyle("");



                    type = type.toLowerCase();

                    switch (type){
                        case "weapon":
                            damageDisplay.setStyle("");
                            String damageType = damageDisplay.getSelectionModel().getSelectedItem();
                            int armorpen = apText.getValue();
                            int damage = damageText.getValue();
                            if(damageType != null){
                                damageType = damageType.toLowerCase();
                                Weapon weapon = new Weapon(name, name, desc, damageType, armorpen, damage, weight, s, m);

                                Main.edit.getCurrentEdit().addItem(weapon);
                                done = true;
                            } else {
                                damageDisplay.setStyle("-fx-border-color: red ;");
                                errorMessage.setText("Please select a value for Damage type");
                                System.out.println("Damage type is null");
                            }
                            break;


                        case "armor":
                            int armor = armorText.getValue();
                            int encumb = encText.getValue();
                            String armorType = armorDisplay.getSelectionModel().getSelectedItem();
                            if(armorType != null){
                                armorDisplay.setStyle("");
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
                                errorMessage.setText("Please select a value for Armor type");
                                armorDisplay.setStyle("-fx-border-color: red ;");
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
                                errorMessage.setText("Please select a value for Food type");
                            }

                            break;
                        case "book":
                            break;
                        case "coin":
                            break;
                    }

                }  else {
                    typeDisplay.setStyle("-fx-border-color: red ;");
                    errorMessage.setText("Please select a value for Item type");
                }

                if(done){

                    //we update the item list and reset all the fields

                    list.setItems(getTableItems());

                    LoadItem loadItem = new LoadItem();
                    loadItem.clearSelection();

                }
            }  else {
                if(name.equals("")){
                    errorMessage.setText("Please select a value for Name");
                    nameText.setStyle("-fx-text-box-border: red ;");
                }
                if(desc.equals("")){
                    errorMessage.setText("Please select a value for Description");
                    descText.setStyle("-fx-text-box-border: red ;");
                }
                if(size == null){
                    errorMessage.setText("Please select a value for Size");
                    sizeDisplay.setStyle("-fx-border-color: red ;");
                }
                if(material == null){
                    errorMessage.setText("Please select a value for Material");
                    materialDisplay.setStyle("-fx-border-color: red ;");
                }
            }

        });

        edit.setOnAction(t -> {
            int selectedIndex = list.getSelectionModel().getSelectedIndex();
            if(selectedIndex > -1){
                IdNameType tempItem = list.getItems().get(selectedIndex);
                Item i = Main.edit.getCurrentEdit().getItems().get(tempItem.getId());

                LoadItem loadItem = new LoadItem();
                loadItem.loadItem(i);
            }
        });

        delete.setOnAction(t->{
            int selectedIndex = list.getSelectionModel().getSelectedIndex();
            if(selectedIndex > -1){
                IdNameType id = list.getSelectionModel().getSelectedItem();
                list.getItems().remove(id);
                Main.edit.getCurrentEdit().getItems().remove(id.getId());
                list.setItems(getTableItems());
            }
        });

        list.setRowFactory( tv -> {
            TableRow<IdNameType> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    IdNameType rowData = row.getItem();
                    Item i = Main.edit.getCurrentEdit().getItems().get(rowData.getId());

                    LoadItem loadItem = new LoadItem();
                    loadItem.loadItem(i);
                }
            });
            return row;
        });

        creationPane.add(createNewItem, 0, 0);
        creationPane.add(nameLabel, 0, 1);
        creationPane.add(nameText, 1, 1);
        creationPane.add(descLabel, 0, 3);
        creationPane.add(descText, 1, 3);
        creationPane.add(weightLabel, 0, 4);
        creationPane.add(weightText, 1, 4);
        creationPane.add(sizeLabel, 0, 5);
        creationPane.add(sizeDisplay, 1, 5);
        creationPane.add(materialLabel, 0, 6);
        creationPane.add(materialDisplay, 1, 6);
        creationPane.add(canDrop, 1, 7);
        creationPane.add(isUnique, 1, 8);
        creationPane.add(typeLabel, 0, 9);
        creationPane.add(typeDisplay, 1, 9);


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

    public static boolean checkIfExists(String s){
        for (Item i : Main.edit.getCurrentEdit().getItems().values()){
            if (i.getRootName().toLowerCase().equals(s.toLowerCase())){
                return true;
            }
        }
        return false;
    }
}
