package com.al0ne.Engine.UI;

import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.Pairs.Pair;
import com.al0ne.Behaviours.Room;
import com.al0ne.Behaviours.World;
import com.al0ne.Engine.Game;
import com.al0ne.Engine.GameChanges;
import com.al0ne.Engine.Main;
import com.al0ne.Engine.Utility;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 24/04/2017.
 */
public class Popups {
    public static void quitDialog(Stage stage){
        Stage s = new Stage();
        s.initModality(Modality.APPLICATION_MODAL);
        s.initOwner(stage);
        VBox dialogVbox = new VBox(20);

        HBox buttonBox = new HBox(20);

        Button quitButton = new Button("Yes");
        quitButton.setOnAction(a -> System.exit(0));

        Button cancel = new Button("No");
        cancel.setOnAction(b -> s.close());

        Text text = new Text("Are you sure you want to quit?");

        buttonBox.getChildren().addAll(quitButton, cancel);
        dialogVbox.getChildren().addAll(text, buttonBox);
        dialogVbox.setPadding(new Insets(20));

        Scene dialogScene = new Scene(dialogVbox, 250, 100);
        s.setScene(dialogScene);
        s.setTitle("Quit?");

        s.show();

    }

    public static void creditsPopup(Stage stage){
        Stage s = new Stage();
        s.initModality(Modality.APPLICATION_MODAL);
        s.initOwner(stage);
        VBox dialogVbox = new VBox(20);

        HBox buttonBox = new HBox(20);

        Button cancel = new Button("Close");
        cancel.setOnAction(b -> s.close());

        Text text = new Text("Thanks for trying out my game!\nSpecial thanks to: Valerie Burgener, " +
                "Lara Bruseghini, Dario Cotti, Giovanni Campana, Luca Hofer for being my beta testers :D");

        text.maxWidth(400);
        text.setWrappingWidth(400);

        buttonBox.getChildren().addAll(cancel);
        dialogVbox.getChildren().addAll(text, buttonBox);
        dialogVbox.setPadding(new Insets(20));
        dialogVbox.setMaxSize(200, 500);

        Scene dialogScene = new Scene(dialogVbox);

        s.setScene(dialogScene);

        s.setTitle("Credits");

        s.show();

    }


    public static void restartPopup(Stage stage){
        Stage s = new Stage();
        s.initModality(Modality.APPLICATION_MODAL);
        s.initOwner(stage);
        VBox dialogVbox = new VBox(20);

        HBox buttonBox = new HBox(20);

        Button restart = new Button("Restart");
        //maybe set initial world somewhere
        restart.setOnAction(b -> {
            GameChanges.restartGame();
            s.close();
        });

        Button cancel = new Button("Cancel");
        cancel.setOnAction(b -> s.close());

        Text text = new Text("Are you sure you want to restart?");

        buttonBox.getChildren().addAll(restart, cancel);
        dialogVbox.getChildren().addAll(text, buttonBox);
        dialogVbox.setPadding(new Insets(20));
        dialogVbox.setMaxSize(200, 500);

        Scene dialogScene = new Scene(dialogVbox);

        s.setScene(dialogScene);
        s.setTitle("Restart?");

        s.show();

    }

    public static void crashPopup(Stage stage){
        Stage s = new Stage();
        s.initModality(Modality.APPLICATION_MODAL);
        s.initOwner(stage);
        VBox dialogVbox = new VBox(20);

        Button quit = new Button("Quit");
        quit.setOnAction(b -> {
            System.exit(1);
        });

        Text text = new Text("Uh oh...\nIt appears I just crashed :/\n" +
                "Please send Mr. C the dumpGame.txt that has been created.\nYour game has been saved, BTW.");

        dialogVbox.getChildren().addAll(text, quit);
        dialogVbox.setPadding(new Insets(20));
        dialogVbox.setMaxSize(200, 500);

        Scene dialogScene = new Scene(dialogVbox);

        s.setScene(dialogScene);
        s.setTitle("Crash :/");

        s.show();
    }

    public static void deathPopup(){
        Stage s = new Stage();
        s.initModality(Modality.APPLICATION_MODAL);
        VBox dialogVbox = new VBox(20);
        dialogVbox.setPrefSize(200, 100);

        Button restart = new Button("Restart");
        restart.setOnAction(b -> {
            GameChanges.restartGame();
            s.close();
        });

        Button quit = new Button("Quit");
        quit.setOnAction(b -> {
            System.exit(0);
        });

        HBox buttons = new HBox();
        buttons.setSpacing(10);
        buttons.getChildren().addAll(restart, quit);

        int total = 0;
        int visited = 0;
        for(World w : Main.game.getWorlds().values()){
            total += w.getRooms().values().size();
            for(Room r : w.getRooms().values()){
                if (!r.isFirstVisit()){
                    visited++;
                }
            }
        }

        double percentage = ((double) visited/total)*100;

        percentage= Utility.twoDecimals(percentage);

        int value = 0;
        for (Pair p : Main.player.getInventory().values()){
            value+=((Item)p.getEntity()).getPrice();
        }



        String endString = "You have died...\nCause of death: "+Main.player.getCauseOfDeath()+".\n\nIn this game, you:\n- lasted for "+ Main.game.getTurnCount()+" turns.\n"+
                "- explored "+visited+"/"+total+" of all the available places ("+percentage+"%).\n" +
                "- had a total value of "+value+" in your inventory.\n";


        endString+="\nWant to restart?";

        Text text = new Text(endString);

        dialogVbox.getChildren().addAll(text, buttons);
        dialogVbox.setPadding(new Insets(20));

        Scene dialogScene = new Scene(dialogVbox);

        s.setScene(dialogScene);
        s.setTitle("R.I.P.");
        s.show();
    }



    public static void helpPopup(){
        Stage s = new Stage();
        s.initModality(Modality.APPLICATION_MODAL);
//        s.initOwner(stage);
        VBox dialogVbox = new VBox(20);
        dialogVbox.setPrefSize(200, 100);

        Button close = new Button("Close");
        close.setOnAction(b -> {
            GameChanges.restartGame();
            s.close();
        });

        HBox buttons = new HBox();
        buttons.setSpacing(10);
        buttons.getChildren().addAll(close);

        Text text = new Text("You can type \"north\" to go north, \"east\" to go east,... (shortcut: \"n\" for north, \"s\" " +
                "for south,...)\n\n"+
                "Useful commands: \n \"examine a\", where a is an object you can see (shortcut: \"x a\")\n"+
                "\"use x on y\", \"use x\", \n\"talk to x\", \n\"attack x\", \n\"take x\", \n\"inventory\" (shortcut: \"i\")\n");

        dialogVbox.getChildren().addAll(text, buttons);
        dialogVbox.setPadding(new Insets(20));

        Scene dialogScene = new Scene(dialogVbox);

        s.setScene(dialogScene);
        s.setTitle("Help");
        s.show();
    }

    public static void openEditor(){
        Stage s = new Stage();
        s.initModality(Modality.APPLICATION_MODAL);
        HBox dialogVbox = GameEditorUI.createEditor();
        dialogVbox.setPrefSize(500, 500);

        Scene dialogScene = new Scene(dialogVbox);

        s.setScene(dialogScene);
        s.setTitle("Game Editor");
        s.show();
    }

    public static void openWorldEditor(Game g){
        Stage s = new Stage();
        s.initModality(Modality.APPLICATION_MODAL);
        VBox dialogVbox = WorldEditorUI.createEditor(g);
        dialogVbox.setPrefSize(500, 500);

        Scene dialogScene = new Scene(dialogVbox);

        s.setScene(dialogScene);
        s.setTitle("World Editor");
        s.show();
    }


}
