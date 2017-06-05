package com.al0ne.Engine.UI.EditorUI;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Engine.Editing.IdNameType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import static com.al0ne.Engine.UI.EditorUI.EditItem.createItem;
import static com.al0ne.Engine.UI.EditorUI.EditNPC.createNPC;
import static com.al0ne.Engine.UI.EditorUI.EditPlayer.createPlayer;
import static com.al0ne.Engine.UI.EditorUI.EditProp.createProp;
import static com.al0ne.Engine.UI.EditorUI.EditRoom.createRoom;

/**
 * Created by BMW on 17/05/2017.
 */
public class WorldEditorUI {
    public static VBox createEditor(){
        TabPane editor = new TabPane();
        editor.setId("editor");

        Tab rooms = createRoom();

        Tab items = createItem();

        Tab props = createProp();

        Tab npc = createNPC();

        Tab enemy = createEnemy();

        Tab player = createPlayer();
        player.setText("Player");

        Tab other = new Tab();
        other.setText("Other");
        other.setClosable(false);

        editor.getTabs().addAll(rooms, items, props, npc, enemy, player, other);
        VBox temp = new VBox();
        temp.getChildren().add(editor);
        return temp;
    }


    public static Tab createEnemy(){
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

}
