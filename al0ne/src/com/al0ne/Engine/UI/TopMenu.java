package com.al0ne.Engine.UI;

import com.al0ne.Engine.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 24/04/2017.
 */
public class TopMenu {
    public static MenuBar createTopMenu(Stage stage){
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");
        Menu helpMenu = new Menu("?");
        Menu creditsMenu = new Menu("Credits");


        MenuItem questionButton = new MenuItem("Help");

        questionButton.setOnAction(t -> HandleCommands.printHelp());

        MenuItem commandsButton = new MenuItem("Commands");

        commandsButton.setOnAction(t -> {
            printToLog("Commands:");
            for (Command command: Command.values()){
                printToLog(command.toString());
            }
        });

        helpMenu.getItems().addAll(questionButton, commandsButton);




        MenuItem creditsButton = new MenuItem("Thanks");

        creditsButton.setOnAction(t -> {
            Popups.creditsPopup(stage);
        });

        creditsMenu.getItems().add(creditsButton);



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
            loadFile.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Save files (*.save)", "*.save"));
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

        menuBar.getMenus().addAll(fileMenu, creditsMenu, helpMenu);

        return menuBar;
    }
}
