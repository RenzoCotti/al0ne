package com.al0ne.Engine.UI.EditorUI;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.Prop;
import com.al0ne.Behaviours.abstractEntities.Entity;
import com.al0ne.Engine.Editing.IdName;
import com.al0ne.Engine.Editing.IdNameType;
import com.al0ne.Engine.Main;
import com.al0ne.Entities.Items.Behaviours.Drinkable;
import com.al0ne.Entities.Items.Behaviours.Food;
import com.al0ne.Entities.Items.Behaviours.Protective;
import com.al0ne.Entities.Items.Behaviours.Wearable.Armor;
import com.al0ne.Entities.Items.Behaviours.Wearable.Helmet;
import com.al0ne.Entities.Items.Behaviours.Wearable.Shield;
import com.al0ne.Entities.Items.Behaviours.Wearable.Weapon;
import com.al0ne.Entities.Items.Props.Door;
import com.al0ne.Entities.Items.Props.LockedDoor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

/**
 * Created by BMW on 27/05/2017.
 */
public class EditProp {

    public static Tab createProp(){
        Tab props = new Tab();
        props.setText("Props");
        props.setClosable(false);

        HBox temp = new HBox();

        VBox listProps = new VBox();

        TableView<IdNameType> propList = new TableView<>();
        TableColumn idColumn = new TableColumn("ID");
        idColumn.setMinWidth(120);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.setMinWidth(120);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn typeColumn = new TableColumn("Type");
        typeColumn.setMinWidth(120);
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        propList.getColumns().addAll(idColumn, nameColumn, typeColumn);


        ObservableList<IdNameType> propArray = getProps();

        propList.setItems(propArray);

        listProps.getChildren().add(propList);






        GridPane propContent = new GridPane();

        Label createNewProp = new Label("Create new prop:");
        createNewProp.setStyle("-fx-font-weight: bold");
        propContent.add(createNewProp, 0, 0);
        //todo: check for existing item in the db having the same (material+name) name

        TextField nameText = new TextField();
        nameText.setPromptText("Lever");
        Label nameLabel = new Label("Name:");
        propContent.add(nameLabel, 0, 1);
        propContent.add(nameText, 1, 1);

        TextArea descText = new TextArea();
        descText.setPrefWidth(100);
        descText.setPrefHeight(50);
        Label descLabel = new Label("Description:");
        descText.setPromptText("A lever that probably activates something somewhere.");
        propContent.add(descLabel, 0, 3);
        propContent.add(descText, 1, 3);

        TextArea shortDescText = new TextArea();
        shortDescText.setPrefWidth(100);
        shortDescText.setPrefHeight(50);
        Label shortDescLabel = new Label("Short Description:");
        shortDescText.setPromptText("a rusty lever");
        propContent.add(shortDescLabel, 0, 4);
        propContent.add(shortDescText, 1, 4);

        Label materialLabel = new Label("Material:");
        ObservableList<String> materialList = FXCollections.observableArrayList(Material.getAllMaterialString());
        ComboBox<String> materialDisplay = new ComboBox<>(materialList);
        materialDisplay.getSelectionModel().select(materialList.size()-1);
        propContent.add(materialLabel, 0, 6);
        propContent.add(materialDisplay, 1, 6);



        Label typeLabel = new Label("Type:");
        ObservableList<String> typeList = FXCollections.observableArrayList("Door", "Locked Door");
        ComboBox<String> typeDisplay = new ComboBox<>(typeList);
        propContent.add(typeLabel, 0, 7);
        propContent.add(typeDisplay, 1, 7);


        ToggleButton canDrop = new RadioButton("Is visible immediately?");
        propContent.add(canDrop, 1, 78);


        Label errorMessage = new Label("");
        errorMessage.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");

        Button create = new Button("Create Prop");
        create.setOnAction( t -> {

            String name = nameText.getText();
            String desc = descText.getText();
            String material = materialDisplay.getSelectionModel().getSelectedItem();

            String shortDesc = shortDescText.getText();

            String propType = typeDisplay.getSelectionModel().getSelectedItem();
            if(!name.equals("") && !desc.equals("")){
                if(propType == null){
                    Entity entity = Main.edit.getCurrentEdit().getCurrentEntity();
                    if(entity != null && entity.getType() == 'p'){
                        Prop old = (Prop) entity;
                        old.setName(name);
                        old.setShortDescription(shortDesc);
                        old.setLongDescription(desc);
                        old.setMaterial(Material.strToMaterial(material));
                    } else {
                        Prop p = new Prop(name, desc, shortDesc, null, Material.strToMaterial(material));
                        Main.edit.getCurrentEdit().addProp(p);
                        propList.setItems(getProps());
                    }
                    nameText.setStyle("");
                    descText.setStyle("");
                    nameText.clear();
                    descText.clear();
                    shortDescText.clear();
                    typeDisplay.getSelectionModel().clearSelection();
                    materialDisplay.getSelectionModel().select(materialList.size()-1);


                } else{
                    //todo
                }
            } else {
                if(name.equals("")){
                    nameText.setStyle("-fx-border-color: red;");
                    errorMessage.setText("Please insert a name");
                }

                if(desc.equals("")){
                    descText.setStyle("-fx-border-color: red;");
                    errorMessage.setText("Please insert a description");
                }
            }


        });
        propContent.add(create, 0, 10);

        //LOADING OF PROP
        Button load = new Button("Edit Prop");
        load.setOnAction(t -> {
            IdNameType tempProp = propList.getSelectionModel().getSelectedItem();
            if(tempProp != null){
                create.setText("Save changes");
                Prop p = Main.edit.getCurrentEdit().getProps().get(tempProp.getId());
                Main.edit.getCurrentEdit().setCurrentEntity(p);
                System.out.println(p.getName());
                nameText.setText(p.getName());
                descText.setText(p.getLongDescription());
                shortDescText.setText(p.getShortDescription());

            }

        });

        listProps.getChildren().add(load);

        VBox propBox = new VBox();
        propBox.getChildren().addAll(propContent, errorMessage);
        temp.getChildren().addAll(propBox, listProps);

        props.setContent(temp);

        return props;
    }

    public static ObservableList<IdNameType> getProps(){
        ArrayList<IdNameType> temp = new ArrayList<>();
        for(Entity e: Main.edit.getCurrentEdit().getProps().values()){
            if(e instanceof LockedDoor){
                temp.add(new IdNameType(e.getID(), e.getName(), "Locked Door"));
            } else if(e instanceof Door){
                temp.add(new IdNameType(e.getID(), e.getName(), "Door"));
            } else {
                temp.add(new IdNameType(e.getID(), e.getName(), "Prop"));
            }
//            else if(e instanceof Food || e instanceof Drinkable){
//                temp.add(new IdNameType(e.getID(), e.getName(), "Food"));
//            } else if(e instanceof Readable){
//                temp.add(new IdNameType(e.getID(), e.getName(), "Scroll"));
//            }
        }

        return FXCollections.observableArrayList (temp);
    }

}
