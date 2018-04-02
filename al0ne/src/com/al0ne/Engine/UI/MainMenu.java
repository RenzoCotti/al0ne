package com.al0ne.Engine.UI;

import com.al0ne.Engine.Utility.GameChanges;
import com.al0ne.Engine.Main;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by BMW on 10/06/2017.
 */
public class MainMenu {

    public static Scene createMainMenu(Stage stage){
        VBox mainContainer = new VBox();
        mainContainer.setPrefSize(650, 500);
        mainContainer.setPadding(new Insets(50, 10, 10, 10));


        VBox titleBox = new VBox();
        Label titleASCII = new Label("      __       _        _______  __     _  ________\n" +
                "     /  \\     | |      /   _   \\|  \\   | ||  _____/\n" +
                "    / /\\ \\    | |      |  | |  ||   \\  | || |\n" +
                "   / /__\\ \\   | |      |  | |  || |\\ \\ | || +--/\n" +
                "  /  ____  \\  | |      |  | |  || | \\ \\| || +--\\\n" +
                " /  /    \\  \\ | |_____ |  |_|  || |  \\ ` || |_____        \n" +
                "/__/      \\__\\|_______\\\\_______/|_|   \\__||_______\\");
        titleASCII.setStyle("-fx-font-family: monospace");

        Label title = new Label("Al0ne");
        title.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");
        titleASCII.setMinSize(450, 150);


        titleBox.getChildren().add(titleASCII);

        titleBox.setAlignment(Pos.TOP_CENTER);

        mainContainer.getChildren().add(titleBox);

//        mainContainer.add(title, 1, 0);
//
//        for (int i = 0; i < 3; i++) {
//            ColumnConstraints column = new ColumnConstraints(100);
//            mainContainer.getColumnConstraints().add(column);
//        }

        VBox buttonContainer = new VBox(5);
        buttonContainer.setAlignment(Pos.BOTTOM_CENTER);
        buttonContainer.setPadding(new Insets(100, 0, 0, 0));

        Button playGame = new Button("Play game");
        playGame.setOnAction(t->{
            Stage s = PlayUI.createContent();
            s.show();
            Main.runGame();
        });
        Button loadGame = new Button("Load game");
        loadGame.setOnAction(t->{
            FileChooser loadFile = new FileChooser();
            loadFile.setTitle("Load File");
            loadFile.setSelectedExtensionFilter(new FileChooser.ExtensionFilter(
                    "Save files (*.save) ", "*.save"));
            File file = loadFile.showOpenDialog(stage);

            if (file != null) {
                GameChanges.load(file.getName(), file.getAbsolutePath(), true);
            }
        });
//        Button openEditor = new Button("Open Editor Disabled");
//        openEditor.setOnAction(t->{
//            Popups.openEditor();
//        });

//        Button loadEditor = new Button("Load Editor Disabled");
//        loadEditor.setOnAction(t->{
//            FileChooser loadFile = new FileChooser();
//            loadFile.setTitle("Load Editor");
//            loadFile.setSelectedExtensionFilter(new FileChooser.ExtensionFilter(
//                    "Editor files (*.edtr) ", "*.edtr"));
//            File file = loadFile.showOpenDialog(stage);
//
//            if (file != null) {
//                if(GameChanges.load(file.getName(), file.getAbsolutePath(), false)){
//                    Popups.openEditor();
//                } else {
//                    System.out.println("the editor file is corrupted");
//                }
//            }
//        });
        Button exit = new Button("Exit");
        exit.setOnAction(t->System.exit(0));

        buttonContainer.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        buttonContainer.getChildren().addAll(playGame, loadGame, exit);

//        mainContainer.add(buttonContainer, 1, 3);
        mainContainer.getChildren().add(buttonContainer);


//        VBox.setVgrow(mainContainer, Priority.ALWAYS);


        Scene s = new Scene(mainContainer);

        return s;
    }
}
