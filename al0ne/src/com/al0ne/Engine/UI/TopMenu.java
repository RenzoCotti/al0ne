package com.al0ne.Engine.UI;

import com.al0ne.Engine.*;
import com.al0ne.Engine.Utility.GameChanges;
import com.al0ne.Engine.Utility.Utility;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
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


        MenuItem saveEditor = new MenuItem("Save editor");
        saveEditor.setOnAction(t -> {
            FileChooser saveFile = new FileChooser();
            saveFile.setTitle("Save Editor");
            File file = saveFile.showSaveDialog(stage);
            if (file != null) {
                GameChanges.save(file.getName(), file.getPath(), Main.edit, false);
            }
        });
        editorMenu.getItems().add(saveEditor);

        MenuItem loadEditor = new MenuItem("Load editor");
        loadEditor.setOnAction(t -> {
            FileChooser loadFile = new FileChooser();
            loadFile.setTitle("Load Editor");
            loadFile.setSelectedExtensionFilter(new FileChooser.ExtensionFilter(
                    "Editor files (*.edtr)", "*.edtr"));
            File file = loadFile.showOpenDialog(stage);

            if (file != null) {
                if(GameChanges.load(file.getName(), file.getAbsolutePath(), false)){
                    Popups.openEditor();
                } else {
                    System.out.println("the editor file is corrupted");
                }
            }
        });
        editorMenu.getItems().add(loadEditor);


        MenuItem questionButton = new MenuItem("Help");
        questionButton.setOnAction(t -> Popups.helpPopup());

        MenuItem creditsButton = new MenuItem("Credits");
        creditsButton.setOnAction(t -> {
            Popups.creditsPopup();
        });

        helpMenu.getItems().addAll(questionButton, creditsButton);




        MenuItem biggerFont = new MenuItem("Bigger font");
        MenuItem smallerFont = new MenuItem("Smaller font");
        MenuItem disableSideMenu = new MenuItem("Toggle Side Menu");
        MenuItem dumpGameState = new MenuItem("Save debug log");

        fileOptions.getItems().addAll(biggerFont, smallerFont, disableSideMenu, dumpGameState);

        dumpGameState.setOnAction(t->{
            Utility.dumpToFile("debuglog", Main.log.getText());
        });




        MenuItem save = new MenuItem("Save");
        save.setOnAction(t -> {
            FileChooser saveFile = new FileChooser();
            saveFile.setTitle("Save File");
            File file = saveFile.showSaveDialog(stage);
            if (file != null) {
                GameChanges.save(file.getName(), file.getPath(), Main.game, true);
            }
        });

        MenuItem load = new MenuItem("Load");
        load.setOnAction(t -> {
            FileChooser loadFile = new FileChooser();
            loadFile.setTitle("Load File");
            loadFile.setSelectedExtensionFilter(new FileChooser.ExtensionFilter(
                    "Save files (*.save)", "*.save"));
            File file = loadFile.showOpenDialog(stage);

            if (file != null) {
                GameChanges.load(file.getName(), file.getAbsolutePath(), true);
            }
        });

        MenuItem quit = new MenuItem("Quit");
        quit.setOnAction(t -> {
            Popups.quitDialog(stage);
        });

        MenuItem restart = new MenuItem("Restart");
        restart.setOnAction(t -> {
            Popups.restartPopup();
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
