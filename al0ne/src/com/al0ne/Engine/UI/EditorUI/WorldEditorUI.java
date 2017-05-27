package com.al0ne.Engine.UI.EditorUI;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.abstractEntities.Entity;
import com.al0ne.Engine.Game;
import com.al0ne.Engine.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collection;

import static com.al0ne.Engine.UI.EditorUI.EditItem.createItem;

/**
 * Created by BMW on 17/05/2017.
 */
public class WorldEditorUI {
    public static VBox createEditor(Game g){
        TabPane editor = new TabPane();
        editor.setId("editor");

        Tab rooms = createRoom(g);

        Tab items = createItem();


        Tab props = createProp(g);

        Tab npc = createNPC(g);

        Tab enemy = createEnemy(g);

        Tab other = new Tab();
        other.setText("Other");
        other.setClosable(false);

        editor.getTabs().addAll(rooms, items, props, npc, enemy, other);
        VBox temp = new VBox();
        temp.getChildren().add(editor);
        return temp;
    }

    public static Tab createProp(Game g){
        Tab items = new Tab();
        items.setText("Prop");
        items.setClosable(false);

        HBox temp = new HBox();

        GridPane itemContent = new GridPane();

        Label createNewProp = new Label("Create new Prop:");
        createNewProp.setStyle("-fx-font-weight: bold");
        itemContent.add(createNewProp, 0, 0);

        TextField nameText = new TextField();
        nameText.setPromptText("lever");
        Label nameLabel = new Label("Name:");
        itemContent.add(nameLabel, 0, 1);
        itemContent.add(nameText, 1, 1);

        TextField shortDesc = new TextField();
        shortDesc.setPromptText("a lever");
        Label shortDescLabel = new Label("Short Description\n(optional):");
        itemContent.add(shortDescLabel, 0, 2);
        itemContent.add(shortDesc, 1, 2);

        TextArea descText = new TextArea();
        descText.setPrefWidth(100);
        descText.setPrefHeight(50);
        Label descLabel = new Label("Description:");
        descText.setPromptText("A rusty lever, you probably can pull it.");
        itemContent.add(descLabel, 0, 3);
        itemContent.add(descText, 1, 3);

        TextArea afterText = new TextArea();
        afterText.setPrefWidth(100);
        afterText.setPrefHeight(50);
        Label afterTextLabel = new Label("After use\n(optional):");
        afterText.setPromptText("The lever has been pulled.");
        itemContent.add(afterTextLabel, 0, 4);
        itemContent.add(afterText, 1, 4);


        Label materialLabel = new Label("Material\n(optional):");
        ObservableList<String> materialList = FXCollections.observableArrayList(Material.getAllMaterialString());
        ComboBox<String> materialDisplay = new ComboBox<>(materialList);
        itemContent.add(materialLabel, 0, 5);
        itemContent.add(materialDisplay, 1, 5);

        ToggleButton isVisible = new RadioButton("Is visible immediately?");
        ToggleButton isDoor = new RadioButton("Is Door?");
        itemContent.add(isVisible, 1, 7);
        itemContent.add(isDoor, 1, 8);

        itemContent.setPadding(new Insets(5, 10, 5, 5));

        temp.getChildren().addAll(itemContent);
        items.setContent(temp);

        Button create = new Button("Create Prop");
        itemContent.add(create, 0, 12);

        return items;
    }

    public static Tab createNPC(Game g){
        Tab items = new Tab();
        items.setText("NPC");
        items.setClosable(false);

        GridPane itemContent = new GridPane();

        Label createNewProp = new Label("Create new NPC:");
        createNewProp.setStyle("-fx-font-weight: bold");
        itemContent.add(createNewProp, 0, 0);

        TextField nameText = new TextField();
        nameText.setPromptText("Bob");
        Label nameLabel = new Label("Name:");
        itemContent.add(nameLabel, 0, 1);
        itemContent.add(nameText, 1, 1);

        TextField shortDesc = new TextField();
        shortDesc.setPromptText("a clever-looking man");
        Label shortDescLabel = new Label("Short Description\n(optional):");
        itemContent.add(shortDescLabel, 0, 2);
        itemContent.add(shortDesc, 1, 2);

        TextArea descText = new TextArea();
        descText.setPrefWidth(100);
        descText.setPrefHeight(50);
        Label descLabel = new Label("Description:");
        descText.setPromptText("A chubby man, wearing a scarf and a silly hat.");
        itemContent.add(descLabel, 0, 3);
        itemContent.add(descText, 1, 3);

        TextArea introText = new TextArea();
        introText.setPrefWidth(100);
        introText.setPrefHeight(50);
        Label introLabel = new Label("Intro dialog:");
        introText.setPromptText("Hi, I'm Bob. How are you?");
        itemContent.add(introLabel, 0, 4);
        itemContent.add(introText, 1, 4);


        Spinner<Integer> maxHealth = new Spinner<>();
        maxHealth.setEditable(true);
        maxHealth.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50, 0, 1));
        Label healthLabel = new Label("Max Health:");
        itemContent.add(healthLabel, 0, 5);
        itemContent.add(maxHealth, 1, 5);

        Spinner<Integer> attack = new Spinner<>();
        attack.setEditable(true);
        attack.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0, 1));
        Label attackLabel = new Label("Attack:");
        itemContent.add(attackLabel, 0, 6);
        itemContent.add(attack, 1, 6);

        Spinner<Integer> dex = new Spinner<>();
        dex.setEditable(true);
        dex.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0, 1));
        Label dexLabel = new Label("Dexterity:");
        itemContent.add(dexLabel, 0, 7);
        itemContent.add(dex, 1, 7);

        Spinner<Integer> armor = new Spinner<>();
        armor.setEditable(true);
        armor.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 0, 1));
        Label armorLabel = new Label("Armor:");
        itemContent.add(armorLabel, 0, 8);
        itemContent.add(armor, 1, 8);

        Spinner<Integer> damage = new Spinner<>();
        damage.setEditable(true);
        damage.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 0, 1));
        Label damageLabel = new Label("Damage:");
        itemContent.add(damageLabel, 0, 9);
        itemContent.add(damage, 1, 9);

        ToggleButton isShopkeeper = new RadioButton("Is shopkeeper?");
        itemContent.add(isShopkeeper, 1, 10);

        Button addItem = new Button("Add Item");
        itemContent.add(addItem, 0, 11);

        Button addSubject = new Button("Add Subject");
        itemContent.add(addSubject, 0, 12);

        Button create = new Button("Create NPC");
        itemContent.add(create, 0, 15);

        itemContent.setPadding(new Insets(5, 10, 5, 5));

        items.setContent(itemContent);

        return items;
    }


    public static Tab createEnemy(Game g){
        Tab enemy = new Tab();
        enemy.setText("Enemy");
        enemy.setClosable(false);

        GridPane enemyContent = new GridPane();

        Label createNewProp = new Label("Create new Enemy:");
        createNewProp.setStyle("-fx-font-weight: bold");
        enemyContent.add(createNewProp, 0, 0);

        TextField nameText = new TextField();
        nameText.setPromptText("wolf");
        Label nameLabel = new Label("Name:");
        enemyContent.add(nameLabel, 0, 1);
        enemyContent.add(nameText, 1, 1);

        TextField shortDesc = new TextField();
        shortDesc.setPromptText("a wolf");
        Label shortDescLabel = new Label("Short Description\n(optional):");
        enemyContent.add(shortDescLabel, 0, 2);
        enemyContent.add(shortDesc, 1, 2);

        TextArea descText = new TextArea();
        descText.setPrefWidth(100);
        descText.setPrefHeight(50);
        Label descLabel = new Label("Description:");
        descText.setPromptText("A ferocious wolf, with black fur.");
        enemyContent.add(descLabel, 0, 3);
        enemyContent.add(descText, 1, 3);

        TextArea introText = new TextArea();
        introText.setPrefWidth(100);
        introText.setPrefHeight(50);
        Label introLabel = new Label("Resistances:");
        introText.setPromptText("TODO");
        enemyContent.add(introLabel, 0, 4);
        enemyContent.add(introText, 1, 4);


        Spinner<Integer> maxHealth = new Spinner<>();
        maxHealth.setEditable(true);
        maxHealth.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50, 0, 1));
        Label healthLabel = new Label("Max Health:");
        enemyContent.add(healthLabel, 0, 5);
        enemyContent.add(maxHealth, 1, 5);

        Spinner<Integer> attack = new Spinner<>();
        attack.setEditable(true);
        attack.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0, 1));
        Label attackLabel = new Label("Attack:");
        enemyContent.add(attackLabel, 0, 6);
        enemyContent.add(attack, 1, 6);

        Spinner<Integer> dex = new Spinner<>();
        dex.setEditable(true);
        dex.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0, 1));
        Label dexLabel = new Label("Dexterity:");
        enemyContent.add(dexLabel, 0, 7);
        enemyContent.add(dex, 1, 7);

        Spinner<Integer> armor = new Spinner<>();
        armor.setEditable(true);
        armor.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 0, 1));
        Label armorLabel = new Label("Armor:");
        enemyContent.add(armorLabel, 0, 8);
        enemyContent.add(armor, 1, 8);

        Spinner<Integer> damage = new Spinner<>();
        damage.setEditable(true);
        damage.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 0, 1));
        Label damageLabel = new Label("Damage:");
        enemyContent.add(damageLabel, 0, 9);
        enemyContent.add(damage, 1, 9);

        TextArea status = new TextArea();
        status.setPrefWidth(100);
        status.setPrefHeight(50);
        Label statusLabel = new Label("Inflicts status:");
        status.setPromptText("TODO");
        enemyContent.add(statusLabel, 0, 10);
        enemyContent.add(status, 1, 10);

        ToggleButton aggressive = new RadioButton("Aggressive?");
        enemyContent.add(aggressive, 1, 11);

        enemy.setContent(enemyContent);


        Button create = new Button("Create Enemy");
        enemyContent.add(create, 0, 15);

        enemyContent.setPadding(new Insets(5, 10, 5, 5));


        return enemy;
    }



    public static Tab createRoom(Game g){
        Tab rooms = new Tab();
        rooms.setText("Rooms");
        rooms.setClosable(false);

        GridPane itemContent = new GridPane();

//        Label createNewItem = new Label("Create new item:");
//        createNewItem.setStyle("-fx-font-weight: bold");

        Label idLabel = new Label("Create new Room:");
        idLabel.setStyle("-fx-font-weight: bold");
        itemContent.add(idLabel, 0, 0);

        TextField nameText = new TextField();
        Label nameLabel = new Label("Name:");
        nameText.setPromptText("Shop");
        itemContent.add(nameLabel, 0, 1);
        itemContent.add(nameText, 1, 1);

        TextArea descText = new TextArea();
        descText.setPromptText("This shop is full of interesting items disposed on wooden shelves.");
        descText.setPrefWidth(200);
        descText.setPrefHeight(100);
        Label descLabel = new Label("Description:");
        itemContent.add(descLabel, 0, 2);
        itemContent.add(descText, 1, 2);

        TextArea customExit = new TextArea();
        customExit.setPrefWidth(200);
        customExit.setPrefHeight(100);
        Label customExitLabel = new Label("Exit description \n(optional):");
        customExit.setPromptText("To the east you can see a weird door, and to the south you can go back home");
        itemContent.add(customExitLabel, 0, 3);
        itemContent.add(customExit, 1, 3);

        Button addEntity = new Button("Add Entity");
        itemContent.add(addEntity, 0, 4);

        Button create = new Button("Create Room");
        itemContent.add(create, 0, 8);


        itemContent.setPadding(new Insets(5, 10, 5, 5));

        rooms.setContent(itemContent);

        return rooms;
    }

}
