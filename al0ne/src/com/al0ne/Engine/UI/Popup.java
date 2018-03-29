package com.al0ne.Engine.UI;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by BMW on 17/07/2017.
 */
public class Popup {

    protected Stage stage;
    protected VBox content;


    public Popup(Integer width, Integer height, String title, String button, String content) {
        if(width == null || height == null){
            init(400, 300, title);
        } else{
            init(width, height, title);
        }
        init(width, height, title);
        Button close = new Button(button);

        close.setOnAction(t->{
            this.stage.close();
        });

        HBox buttons = new HBox();
        buttons.setSpacing(10);
        buttons.getChildren().addAll(close);
        Text text = new Text(content);
        this.content.getChildren().addAll(text, buttons);
        this.stage.show();
    }

    public Popup(Integer width, Integer height, String title, HBox buttons, String content) {
        if(width == null || height == null){
            init(500, 300, title);
        } else{
            init(width, height, title);
        }
        Text text = new Text(content);
        this.content.getChildren().addAll(text, buttons);
        this.stage.show();
    }

    public void init(Integer width, Integer height, String title){
        this.stage = new Stage();
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.content = new VBox(20);
        this.content.setPrefSize(width, height);
        this.content.setPadding(new Insets(20));
        Scene dialogScene = new Scene(this.content);
        this.stage.setScene(dialogScene);
        this.stage.setTitle(title);
    }
//
//    public static Stage promptPopup(String title, String buttonText, String content){
//        Stage s = new Stage();
//        s.initModality(Modality.APPLICATION_MODAL);
//        VBox dialogVbox = new VBox(20);
//        dialogVbox.setPrefSize(300, 200);
//
//        Button close = new Button(buttonText);
//        close.setOnAction(b -> {
//            s.close();
//        });
//
//        HBox buttons = new HBox();
//        buttons.setSpacing(10);
//        buttons.getChildren().addAll(close);
//
//        Text text = new Text(content);
//
//        dialogVbox.getChildren().addAll(text, buttons);
//        dialogVbox.setPadding(new Insets(20));
//
//        Scene dialogScene = new Scene(dialogVbox);
//
//        s.setScene(dialogScene);
//        s.setTitle(title);
//        s.show();
//        return s;
//    }
}
