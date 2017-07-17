package com.al0ne.Engine.UI.EditorUI;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * Created by BMW on 17/05/2017.
 */
public class WorldEditorUI {
    public static VBox createEditor(){
        TabPane editor = new TabPane();
        editor.setId("editor");

        Tab rooms = new EditRoom();

        EditTab items = new EditItem();

        Tab props = new EditProp();

        EditTab npc = new EditNPC();

        Tab enemy = new EditEnemy();

        Tab player = new EditPlayer();
        player.setText("Player");

        Tab other = new Tab();
        other.setText("Other");
        other.setClosable(false);

        editor.getTabs().addAll(rooms, items, props, npc, enemy, player, other);
        VBox temp = new VBox();
        temp.getChildren().add(editor);
        return temp;
    }
}
