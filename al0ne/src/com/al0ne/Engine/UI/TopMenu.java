package com.al0ne.Engine.UI;

import com.al0ne.Engine.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 24/04/2017.
 */
public class TopMenu {


    public static ArrayList<Menu> createSubMenus(Stage stage){
        Menu fileMenu = new Menu("Game");
        Menu fileOptions = new Menu("Options");
        Menu editorMenu = new Menu("Editor");
        Menu helpMenu = new Menu("Help");

        MenuItem openEditor = new MenuItem("Open editor");
        openEditor.setOnAction(t -> {
            Popups.openEditor();
        });
        editorMenu.getItems().add(openEditor);


        MenuItem questionButton = new MenuItem("Help");
        questionButton.setOnAction(t -> Popups.helpPopup());

        MenuItem creditsButton = new MenuItem("Credits");
        creditsButton.setOnAction(t -> {
            Popups.creditsPopup(stage);
        });

        helpMenu.getItems().addAll(questionButton, creditsButton);




        MenuItem biggerFont = new MenuItem("Bigger font");
        MenuItem smallerFont = new MenuItem("Smaller font");
        MenuItem disableSideMenu = new MenuItem("Toggle Side Menu");

        fileOptions.getItems().addAll(biggerFont, smallerFont, disableSideMenu);




        MenuItem save = new MenuItem("Save");
        save.setOnAction(t -> {
            FileChooser saveFile = new FileChooser();
            saveFile.setTitle("Save File");
            File file = saveFile.showSaveDialog(stage);
            if (file != null) {
                GameChanges.save(file.getName(), file.getPath(), Main.game);
            }
        });

        MenuItem load = new MenuItem("Load");
        load.setOnAction(t -> {
            FileChooser loadFile = new FileChooser();
            loadFile.setTitle("Open Load File");
            loadFile.setSelectedExtensionFilter(new FileChooser.ExtensionFilter(
                    "Save files (*.save)", "*.save"));
            File file = loadFile.showOpenDialog(stage);

            if (file != null) {
                GameChanges.load(file.getName(), file.getAbsolutePath());
            }
        });

        MenuItem quit = new MenuItem("Quit");
        quit.setOnAction(t -> {
            Popups.quitDialog(stage);
        });

        MenuItem restart = new MenuItem("Restart");
        restart.setOnAction(t -> {
            Popups.restartPopup(stage);
        });



        fileMenu.getItems().addAll(save, load, restart, quit);


        ArrayList<Menu> menus = new ArrayList<>();
        menus.add(fileMenu);
        menus.add(fileOptions);
        menus.add(editorMenu);
        menus.add(helpMenu);

        return menus;

    }
}
