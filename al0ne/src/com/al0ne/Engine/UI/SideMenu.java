package com.al0ne.Engine.UI;

import com.al0ne.AbstractEntities.Abstract.Item;
import com.al0ne.AbstractEntities.Pairs.Pair;
import com.al0ne.Engine.Utility.GameChanges;
import com.al0ne.Engine.Main;
import com.al0ne.Engine.Utility.Utility;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import static com.al0ne.Engine.Main.game;
import static com.al0ne.Engine.Utility.Utility.capitalise;

/**
 * Created by BMW on 24/04/2017.
 */
public class SideMenu {

    public static VBox createSideMenu() {
        TabPane tabPane = new TabPane();
        tabPane.setId("sideTabs");
        Tab notes = new Tab();
        notes.setText("Notes");
        notes.setContent(Main.notes);
        notes.setClosable(false);

        Tab inventoryTab = new Tab();
        inventoryTab.setText("Inventory");
        inventoryTab.setClosable(false);



        TableView<SimpleItem> inv = new TableView<>();
        inv.setPlaceholder(new Label("You have no items"));
        inv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        inv.setId("inventoryTable");
        TableColumn itemColumn = new TableColumn("Item");
        itemColumn.setMinWidth(120);
        itemColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn amount = new TableColumn("Amt");
        amount.setMinWidth(40);
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        TableColumn weightColumn = new TableColumn("Kg");
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        weightColumn.setMinWidth(40);
        TableColumn value = new TableColumn("$");
        value.setCellValueFactory(new PropertyValueFactory<>("value"));
        value.setMinWidth(40);
        TableColumn damage = new TableColumn("ATK/ENC");
        damage.setCellValueFactory(new PropertyValueFactory<>("damage"));
        damage.setMinWidth(40);
        TableColumn defense = new TableColumn("DEF/AP");
        defense.setCellValueFactory(new PropertyValueFactory<>("defense"));
        defense.setMinWidth(40);

        if(Main.game.isInDebugMode()){
            inv.getColumns().addAll(amount, itemColumn, weightColumn, value, damage, defense);
        } else {
            inv.getColumns().addAll(amount, itemColumn, weightColumn);
        }



        inv.setItems(GameChanges.getInventoryData());

        VBox description = new VBox();
        description.setPadding(new Insets(20, 20, 20, 20));

        Text descTitleLabel = new Text("");
        Label descLabel = new Label("");
        descLabel.setPadding(new Insets(10, 0, 0, 0));
        descTitleLabel.setStyle("-fx-font-weight: bold");



        description.managedProperty().bind(description.visibleProperty());
        description.getChildren().addAll(descTitleLabel, descLabel);

        VBox inventoryAndDesc = new VBox();
        inventoryAndDesc.getChildren().addAll(inv, description);

        inv.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                String id = newSelection.getId();
                Pair p = Main.player.getInventory().get(id);
                Item i = (Item) p.getEntity();
                descTitleLabel.setText(capitalise(i.getName()));
                if(p.getCount() > 1){
                    descTitleLabel.setText(descTitleLabel.getText()+", x"+p.getCount());
                }
                descLabel.setText("("+i.getWeight()+" Kg)\n"+capitalise(i.getLongDescription()));
            }
        });

        inventoryTab.setContent(inventoryAndDesc);


        Tab stats = new Tab();
        stats.setText("Stats");
        stats.setId("statsTab");
        stats.setClosable(false);

        Label playerStats = new Label("Player statistics:");
        playerStats.setStyle("-fx-font-weight: bold");

        playerStats.setPadding(new Insets(0, 0, 5, 0));
        Label weight = new Label("Weight: "+Main.player.getCurrentWeight()+" / "+ Main.player.getMaxWeight()+" kg");
        weight.setId("weightLabel");
        Label health = new Label("Health: "+Main.player.getCurrentHealth()+" / "+ Main.player.getMaxHealth());
        health.setId("healthLabel");
        Label totalArmor = new Label("Total Armor: "+Main.player.getArmorLevel());
        totalArmor.setId("totalArmor");
        Label encumberment = new Label("Encumberment: "+Main.player.getEncumberment());
        encumberment.setId("encumberment");
        encumberment.setPadding(new Insets(10, 0, 0, 0));


        Label equippedItems = new Label("Equipped items:");
        equippedItems.setPadding(new Insets(20, 0, 0, 0));
        equippedItems.setStyle("-fx-font-weight: bold");

        Label head = new Label("Head: "+ Utility.getHelmetString());
        head.setId("headLabel");
        Label armor = new Label("Armor: "+Utility.getArmorString());
        armor.setId("armorLabel");
        Label weapon = new Label("Weapon: "+Utility.getWeaponString());
        weapon.setId("weaponLabel");
        Label offHand = new Label("Off-Hand: "+Utility.getOffHandString());
        offHand.setId("offHandLabel");


        VBox listStats = new VBox();
        listStats.setPadding(new Insets(10, 10, 10, 10));
        listStats.getChildren().addAll(playerStats, health, totalArmor, weight, equippedItems, head, armor, weapon, offHand, encumberment);

        for(Node n: listStats.getChildren()){
            n.setStyle("-fx-font: "+Main.fontSize+"px \"Verdana\";");
        }

        stats.setContent(listStats);


        tabPane.getTabs().addAll(stats, inventoryTab, notes);


        VBox sideMenu = new VBox();



        sideMenu.getChildren().addAll(tabPane);
        sideMenu.setVgrow(tabPane, Priority.ALWAYS);

        return sideMenu;
    }
}
