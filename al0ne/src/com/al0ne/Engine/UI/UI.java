package com.al0ne.Engine.UI;


import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Room;
import com.al0ne.Engine.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

import static com.al0ne.Engine.Main.printToLog;
import static javafx.scene.input.KeyCode.ENTER;

/**
 * Created by BMW on 13/04/2017.
 */
public class UI {

    public static Scene createContent(){

        VBox root = new VBox(1);
        Scene done;


        Stage stage = new Stage();


        Main.log = new TextArea();
        Main.log.setPrefHeight(550);
        Main.log.setPrefWidth(800);
        Main.log.setEditable(false);
        Main.log.setWrapText(true);

        Main.input  = new TextField();


        Main.input.setPromptText("Type your commands here");

        Main.notes = new TextArea();
        Main.notes.setPromptText("Here you can write your notes.\nThey will be recorded upon saving the game.");
        Main.notes.setMinWidth(150);




        VBox sideMenu = SideMenu.createSideMenu();



        HBox container = new HBox();
        container.getChildren().addAll(Main.log, sideMenu);


        MenuBar menuBar = TopMenu.createTopMenu(stage);

        root.setPadding(new Insets(0));

        root.getChildren().addAll(menuBar, container, Main.input);

        root.setPrefSize(800,600);


        done = new Scene(root);

        Main.input.setOnKeyPressed(event -> {
            if (event.getCode().equals(ENTER)){
                Player player = Main.player;
                Room currentRoom = Main.currentRoom;
                try{
                    Main.hasNextLine(Main.input.getText(), done);
                    Main.input.clear();
                } catch (Exception ex){
                    Main.game.setPlayer(player);
                    Main.game.setRoom(currentRoom);
                    GameChanges.save("autosave", null, Main.game);
                    Utility.dumpToFile("gameDump", Main.log.getText());
                    Popups.crashPopup(stage);
                }
            }
        });

        return done;
    }



    public static void updateUI(Scene s){
        TableView inv = (TableView) s.lookup("#inv");
        inv.setItems(GameChanges.getInventoryData());
        Label healthLabel = (Label) s.lookup("#healthLabel");
        healthLabel.setText("Health: "+Main.player.getCurrentHealth()+" / "+ Main.player.getMaxHealth());
        Label weightLabel = (Label) s.lookup("#weightLabel");
        weightLabel.setText("Weight: "+Main.player.getCurrentWeight()+" / "+ Main.player.getMaxWeight()+" kg");
        Label totalArmor = (Label) s.lookup("#totalArmor");
        totalArmor.setText("Total Armor: "+Main.player.getArmorLevel());


        Label head = (Label) s.lookup("#headLabel");
        head.setText("Head: "+Main.player.getHelmetString());
        Label armor = (Label) s.lookup("#armorLabel");
        armor.setText("Armor: "+Main.player.getArmorString());
        Label weapon = (Label) s.lookup("#weaponLabel");
        weapon.setText("Weapon: "+Main.player.getWeaponString());
        Label offHand = (Label) s.lookup("#offHandLabel");
        offHand.setText("Off-Hand: "+Main.player.getOffHandString());

    }

}
