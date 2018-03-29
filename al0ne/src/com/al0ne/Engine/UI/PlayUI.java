package com.al0ne.Engine.UI;


import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.Room;
import com.al0ne.Engine.*;
import com.al0ne.Engine.Utility.GameChanges;
import com.al0ne.Engine.Utility.Utility;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;

import static com.al0ne.Engine.Main.printToLog;
import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.UP;

/**
 * Created by BMW on 13/04/2017.
 */
public class PlayUI {

    public static Stage createContent(){

        VBox root = new VBox();
        Scene done;


        Stage stage = new Stage();


        Main.log = new TextArea();
        Main.log.setPrefHeight(800);
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

        ArrayList<Menu> menus = TopMenu.createSubMenus(stage);

        MenuBar menuBar = new MenuBar();
        menuBar.setId("topmenu");

        menuBar.getMenus().addAll(menus);




        root.setPadding(new Insets(0));

        root.getChildren().addAll(menuBar, container, Main.input);

        root.setPrefSize(1000,700);
        root.setVgrow(Main.log, Priority.ALWAYS);

        done = new Scene(root);

        Main.input.setOnKeyPressed(event -> {
            if (event.getCode().equals(ENTER)){
                Main.historyCounter=0;
                Player player = Main.player;
                Room currentRoom = player.getCurrentRoom();
                try{
                    Main.hasNextLine(Main.input.getText(), done);
                    Main.input.clear();
                } catch (Exception ex){
                    Main.game.setPlayer(player);
                    Main.game.setRoom(currentRoom);
                    GameChanges.save("autosave", null, Main.game, true);
                    Utility.dumpToFile("gameDump", Main.log.getText());
                    Popups.crashPopup();
                    ex.printStackTrace();
                }
            } else if(event.getCode().equals(UP)){
                if(Main.historyCounter+1 < Main.maxHistory && Main.historyCounter+1 <= Main.oldCommands.size()){
                    Main.historyCounter++;
                    Main.input.requestFocus();
                    Main.input.setText(Main.oldCommands.get(Main.oldCommands.size() - Main.historyCounter));
                    Main.input.positionCaret(Main.input.getText().length());
                }
            } else if(event.getCode().equals(DOWN)){
                if(Main.historyCounter-1 > 0 ){
                    Main.historyCounter--;
                    Main.input.requestFocus();
                    Main.input.setText(Main.oldCommands.get(Main.oldCommands.size() - Main.historyCounter));
                    Main.input.positionCaret(Main.input.getText().length());
                } else if (Main.historyCounter-1 == 0){
                    Main.historyCounter = 0;
                    Main.input.requestFocus();
                    Main.input.setText("");
                }

            }
        });

        //bigger font
        menus.get(1).getItems().get(0).setOnAction(t -> {
            if(Main.fontSize + 2 >= 20){
                Main.fontSize = 20;

            } else{
                Main.fontSize+=2;
            }

            PlayUI.redrawUI(done);
        });

        //smaller font
        menus.get(1).getItems().get(1).setOnAction(t -> {
            if(Main.fontSize - 2 <= 10){
                Main.fontSize = 10;
            } else{
                Main.fontSize-=2;
            }

            PlayUI.redrawUI(done);
        });

        //hide menu
        menus.get(1).getItems().get(2).setOnAction(t -> {
            if(Main.sideMenuShown){
                container.getItems().remove(sideMenu);
                Main.sideMenuShown = false;
            } else {
                container.getItems().add(1, sideMenu);
                container.setDividerPosition(0, 0.6);
            }
        });


        Stage s = new Stage();

        s.setOnCloseRequest(e->{
            e.consume();
            Popups.quitDialog(s);
        });
        s.setScene(done);
        return s;
    }



    public static void updateUI(Scene s){
        TableView inv = (TableView) s.lookup("#inventoryTable");
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
        Label encumberment = (Label) s.lookup("#encumberment");
        encumberment.setText("Encumberment: "+Main.player.getEncumberment());
        encumberment.setFont(Font.font("Verdana", Main.fontSize));


        Label head = (Label) s.lookup("#headLabel");
        head.setFont(Font.font("Verdana", Main.fontSize));
        head.setText("Head: "+Utility.getHelmetString());
        Label armor = (Label) s.lookup("#armorLabel");
        armor.setFont(Font.font("Verdana", Main.fontSize));
        armor.setText("Armor: "+Utility.getArmorString());
        Label weapon = (Label) s.lookup("#weaponLabel");
        weapon.setFont(Font.font("Verdana", Main.fontSize));
        weapon.setText("Weapon: "+Utility.getWeaponString());
        Label offHand = (Label) s.lookup("#offHandLabel");
        offHand.setFont(Font.font("Verdana", Main.fontSize));
        offHand.setText("Off-Hand: "+Utility.getOffHandString());
    }

    public static void redrawUI(Scene s){
        updateUI(s);
        TableView inv = (TableView) s.lookup("#inventoryTable");
        inv.setStyle("-fx-font: "+Main.fontSize+"px \"Verdana\";");

        Main.log.setFont(Font.font("Verdana", Main.fontSize));
        Main.input.setFont(Font.font("Verdana", Main.fontSize));
        Main.notes.setFont(Font.font("Verdana", Main.fontSize));

        MenuBar menuBar = (MenuBar) s.lookup("#topmenu");
        menuBar.setStyle("-fx-font: "+Main.fontSize+"px \"Verdana\";");

        TabPane notes = (TabPane) s.lookup("#sideTabs");
        notes.setStyle("-fx-font: "+Main.fontSize+"px \"Verdana\";");
    }


}
