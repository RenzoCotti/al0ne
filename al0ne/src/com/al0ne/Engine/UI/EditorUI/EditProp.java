package com.al0ne.Engine.UI.EditorUI;

import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.ConcreteEntities.Items.Props.Types.InvisibleProp;
import com.al0ne.AbstractEntities.Prop;
import com.al0ne.AbstractEntities.Abstract.Entity;
import com.al0ne.Engine.Editing.IdNameType;
import com.al0ne.Engine.Main;
import com.al0ne.ConcreteEntities.Items.Props.Types.Door;
import com.al0ne.ConcreteEntities.Items.Props.Types.LockedDoor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import java.util.ArrayList;

/**
 * Created by BMW on 27/05/2017.
 */
public class EditProp extends EditTab{

    public EditProp() {
        super("Prop", "Placeholder for prop help");

        Label createNewProp = new Label("Create new prop:");
        createNewProp.setStyle("-fx-font-weight: bold");
        //todo: check for existing item in the db having the same (material+name) name

        TextField nameText = new TextField();
        nameText.setPromptText("Lever");
        Label nameLabel = new Label("Name:");


        TextArea descText = new TextArea();
        descText.setPrefWidth(100);
        descText.setPrefHeight(50);
        Label descLabel = new Label("Description:");
        descText.setPromptText("A lever that probably activates something somewhere.");


        TextArea shortDescText = new TextArea();
        shortDescText.setPrefWidth(100);
        shortDescText.setPrefHeight(50);
        Label shortDescLabel = new Label("Short Description:");
        shortDescText.setPromptText("a rusty lever");


        Label materialLabel = new Label("Material:");
        ObservableList<String> materialList = FXCollections.observableArrayList(Material.getAllMaterialString());
        ComboBox<String> materialDisplay = new ComboBox<>(materialList);
        materialDisplay.getSelectionModel().select(materialList.size()-1);



        Label typeLabel = new Label("Type:");
        ObservableList<String> typeList = FXCollections.observableArrayList("Prop", "Door", "Locked Door",
                "Hide item", "Hidden Prop");
        ComboBox<String> typeDisplay = new ComboBox<>(typeList);
        typeDisplay.getSelectionModel().select("Prop");



        Label errorMessage = new Label("");
        errorMessage.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");




        class LoadProp{
            public void loadProp(Prop p){

            }

            public void clearSelection(){
                nameText.setStyle("");
                descText.setStyle("");
                nameText.clear();
                descText.clear();
                shortDescText.clear();
                typeDisplay.getSelectionModel().select("Prop");
                materialDisplay.getSelectionModel().select(materialList.size()-1);
                create.setText("Create new Prop");
            }

            public void removeFields(){

            }

            public void addProperFields(){

            }
        }

        create.setOnAction( t -> {

            String name = nameText.getText();
            String desc = descText.getText();
            String material = materialDisplay.getSelectionModel().getSelectedItem();

            String shortDesc = shortDescText.getText();

            String propType = typeDisplay.getSelectionModel().getSelectedItem();
            if(!name.equals("") && !desc.equals("")){

                Entity entity = Main.edit.getCurrentEdit().getCurrentEntity();
                //we are editing an item
                if(entity != null && create.getText().equals("Save changes")){
                    Prop old;
                    if(entity instanceof Prop){
                        old = (Prop) entity;

                    } else {
                        IdNameType tempProp = list.getSelectionModel().getSelectedItem();
                        old = Main.edit.getCurrentEdit().getProps().get(tempProp.getId());
                        Main.edit.getCurrentEdit().setCurrentEntity(old);
                    }

                    old.setName(name);
                    old.setShortDescription(shortDesc);
                    old.setLongDescription(desc);
                    old.setMaterial(Material.strToMaterial(material));
                } else{
                    //creating a generic prop
                    Prop p;
                    if(propType.equals("Prop")){
                        p = new Prop(name, desc, shortDesc, null, Material.strToMaterial(material));


                    } else if(propType.toLowerCase().equals("hidden prop")){
                        p = new InvisibleProp(name, desc, shortDesc, Material.strToMaterial(material));
                    } else{
                        //TODO: ADD OTHER TYPES
                        p = new Prop(name, desc, shortDesc, null, Material.strToMaterial(material));
                    }

                    Main.edit.getCurrentEdit().addProp(p);
                    list.setItems(getTableItems());
                }

                LoadProp loadProp = new LoadProp();
                loadProp.clearSelection();

                list.setItems(getTableItems());


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

        clear.setOnAction(t->{
            LoadProp loadProp = new LoadProp();
            loadProp.clearSelection();
        });



        //LOADING OF PROP
        edit.setOnAction(t -> {
            IdNameType tempProp = list.getSelectionModel().getSelectedItem();
            if(tempProp != null){
                create.setText("Save changes");
                Prop p = Main.edit.getCurrentEdit().getProps().get(tempProp.getId());
                Main.edit.getCurrentEdit().setCurrentEntity(p);
                nameText.setText(p.getName());
                descText.setText(p.getLongDescription());
                shortDescText.setText(p.getShortDescription());
            }

        });

        delete.setOnAction(t->{
            int selectedIndex = list.getSelectionModel().getSelectedIndex();
            if(selectedIndex > -1){
                IdNameType id = list.getSelectionModel().getSelectedItem();
                list.getItems().remove(id);
                Main.edit.getCurrentEdit().getProps().remove(id.getId());
                list.setItems(getTableItems());
            }
        });

        list.setRowFactory( tv -> {
            TableRow<IdNameType> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    IdNameType rowData = row.getItem();
                    Prop p = Main.edit.getCurrentEdit().getProps().get(rowData.getId());

                    LoadProp loadProp = new LoadProp();
                    loadProp.loadProp(p);
                }
            });
            return row;
        });

        creationPane.add(createNewProp, 0, 0);
        creationPane.add(nameLabel, 0, 1);
        creationPane.add(nameText, 1, 1);
        creationPane.add(descLabel, 0, 3);
        creationPane.add(descText, 1, 3);
        creationPane.add(shortDescLabel, 0, 4);
        creationPane.add(shortDescText, 1, 4);
        creationPane.add(materialLabel, 0, 6);
        creationPane.add(materialDisplay, 1, 6);
        creationPane.add(typeLabel, 0, 7);
        creationPane.add(typeDisplay, 1, 7);

    }

    @Override
    public ObservableList<IdNameType> getTableItems() {
        ArrayList<IdNameType> temp = new ArrayList<>();
        for(Entity e: Main.edit.getCurrentEdit().getProps().values()){
            if(e instanceof LockedDoor){
                temp.add(new IdNameType(e.getID(), e.getName(), "Locked Door"));
            } else if(e instanceof Door){
                temp.add(new IdNameType(e.getID(), e.getName(), "Door"));
            } else if(e instanceof InvisibleProp){
                temp.add(new IdNameType(e.getID(), e.getName(), "Hidden Prop"));
            } else {
                temp.add(new IdNameType(e.getID(), e.getName(), "Prop"));
            }
        }

        return FXCollections.observableArrayList (temp);
    }

}
