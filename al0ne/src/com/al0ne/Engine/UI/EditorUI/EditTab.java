package com.al0ne.Engine.UI.EditorUI;


import com.al0ne.Engine.Editing.IdNameType;
import com.al0ne.Engine.UI.Popup;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This is a general superclass for the tabs in the world editor
 */
public abstract class EditTab extends Tab{

    protected HBox creationAndListContainer;
    protected VBox listContainer;

    protected TableView<IdNameType> list;
    protected GridPane creationPane;
    protected VBox creationPaneContainer;
    protected String name;

    protected Label errorMessage;

    protected Button create;
    protected Button clear;
    protected Button help;
    protected Button edit;
    protected Button delete;

    protected String helpString;

    protected static final String defaultErrorStyle = "-fx-text-fill: red; -fx-font-weight: bold;";

    boolean isEditing;




    public EditTab(String nameOfTab, String helpString){
        this.name = nameOfTab;
        this.errorMessage = new Label("");
        this.errorMessage.setStyle(defaultErrorStyle);

//        if(list == null){
            this.list = createTable();
            ObservableList<IdNameType> tableItemsArray = getTableItems();
            this.list.setItems(tableItemsArray);
//        } else{
//            this.list = list;
//            ObservableList<IdNameType> tableItemsArray = getTableItems();
//            this.list.setItems(tableItemsArray);
//        }


        this.create = new Button("Create");
        this.clear = new Button("Clear");
        this.help = new Button("?");
        this.edit = new Button("Edit");
        this.delete = new Button("Delete");
        this.listContainer = new VBox(10);
        this.creationAndListContainer = new HBox(10);
        this.creationPane = new GridPane();
        this.creationPaneContainer = new VBox(10);
        this.helpString = helpString;
        this.isEditing = false;



        help.setOnAction(t->{
            new Popup(300, 300, nameOfTab+" help", "Okay, got it.", helpString);
        });



        GridPane.setMargin(create, new Insets(20, 0, 10, 0));

        creationPaneContainer.getChildren().addAll(creationPane, clear, help, create, errorMessage);

        creationAndListContainer.setPadding(new Insets(10, 10, 10, 10));
        creationAndListContainer.getChildren().addAll(creationPaneContainer, listContainer);

        listContainer.getChildren().addAll(this.list, delete, edit);


        this.setText(nameOfTab);
        this.setClosable(false);

        this.setContent(creationAndListContainer);

    }

    public static TableView<IdNameType> createTable(){
        TableView<IdNameType> list = new TableView<>();
        list.setPlaceholder(new Label("Nothing created yet."));
        list.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        TableColumn idColumn = new TableColumn("ID");
        idColumn.setMinWidth(120);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.setMinWidth(120);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn typeColumn = new TableColumn("Type");
        typeColumn.setMinWidth(120);
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        list.getColumns().addAll(idColumn, nameColumn, typeColumn);

        return list;
    }

    public abstract ObservableList<IdNameType> getTableItems();

    public TableView<IdNameType> getList() {
        return list;
    }

    public void setList(TableView<IdNameType> list) {
        this.list = list;
    }

    public boolean isEditing() {
        return isEditing;
    }

    public void setEditing(boolean editing) {
        isEditing = editing;
    }
}
