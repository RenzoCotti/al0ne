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
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

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
        Main.log.setFont(Font.font("Verdana", Main.fontSize));

        Main.input  = new TextField();
        Main.input.setFont(Font.font("Verdana", Main.fontSize));


        Main.input.setPromptText("Type your commands here");

        Main.notes = new TextArea();
        Main.notes.setPromptText("Here you can write your notes.\nThey will be recorded upon saving the game.");
        Main.input.setFont(Font.font("Verdana", Main.fontSize));




        VBox sideMenu = SideMenu.createSideMenu();



        SplitPane container = new SplitPane();
        container.getItems().addAll(Main.log, sideMenu);
        container.setDividerPosition(0, 0.6);


//        MenuBar menuBar = TopMenu.createTopMenu(stage);

        ArrayList<Menu> menus = TopMenu.createSubMenus(stage);

        MenuBar menuBar = new MenuBar();

        menuBar.getMenus().addAll(menus);




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
                    ex.printStackTrace();
                }
            }
        });

        menus.get(1).getItems().get(0).setOnAction(t -> {
            if(Main.fontSize + 2 >= 20){
                Main.fontSize = 20;
            } else{
                Main.fontSize+=2;
            }

            UI.updateUI(done);
        });

        menus.get(1).getItems().get(1).setOnAction(t -> {
            if(Main.fontSize - 2 <= 10){
                Main.fontSize = 10;
            } else{
                Main.fontSize-=2;
            }

            UI.updateUI(done);
        });

//        done.get

        return done;
    }



    public static void updateUI(Scene s){
        TableView inv = (TableView) s.lookup("#inv");
        inv.setItems(GameChanges.getInventoryData());
        Label healthLabel = (Label) s.lookup("#healthLabel");
        healthLabel.setFont(Font.font("Verdana", Main.fontSize));
        healthLabel.setText("Health: "+Main.player.getCurrentHealth()+" / "+ Main.player.getMaxHealth());
        Label weightLabel = (Label) s.lookup("#weightLabel");
        weightLabel.setFont(Font.font("Verdana", Main.fontSize));
        weightLabel.setText("Weight: "+Main.player.getCurrentWeight()+" / "+ Main.player.getMaxWeight()+" kg");
        Label totalArmor = (Label) s.lookup("#totalArmor");
        totalArmor.setText("Total Armor: "+Main.player.getArmorLevel());
        totalArmor.setFont(Font.font("Verdana", Main.fontSize));


        Label head = (Label) s.lookup("#headLabel");
        head.setFont(Font.font("Verdana", Main.fontSize));
        head.setText("Head: "+Main.player.getHelmetString());
        Label armor = (Label) s.lookup("#armorLabel");
        armor.setFont(Font.font("Verdana", Main.fontSize));
        armor.setText("Armor: "+Main.player.getArmorString());
        Label weapon = (Label) s.lookup("#weaponLabel");
        weapon.setFont(Font.font("Verdana", Main.fontSize));
        weapon.setText("Weapon: "+Main.player.getWeaponString());
        Label offHand = (Label) s.lookup("#offHandLabel");
        offHand.setFont(Font.font("Verdana", Main.fontSize));
        offHand.setText("Off-Hand: "+Main.player.getOffHandString());

        Main.log.setFont(Font.font("Verdana", Main.fontSize));
        Main.input.setFont(Font.font("Verdana", Main.fontSize));
        Main.notes.setFont(Font.font("Verdana", Main.fontSize));

    }

}
