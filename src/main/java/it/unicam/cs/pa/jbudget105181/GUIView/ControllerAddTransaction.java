package it.unicam.cs.pa.jbudget105181.GUIView;

import it.unicam.cs.pa.jbudget105181.Controller.MainController;
import it.unicam.cs.pa.jbudget105181.Model.ITag;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class ControllerAddTransaction implements ControllerFXML{
    private MainController controller;
    @FXML private DatePicker transactionDate;
    @FXML private RadioButton programmedTransaction;
    @FXML private RadioButton instantTransaction;
    @FXML private TextField numweekTransaction;
    @FXML private Label numweeklabel;
    @FXML private Button backButton;
    @FXML private Button saveButton;


    @FXML private TableView<ITag> tableAllTag;
    @FXML private TableView<ITag> tableAddedTag;

    @FXML private TableColumn<ITag,String> columnNameA;
    @FXML private TableColumn<ITag,String> columnDescriptionA;

    @FXML private TableColumn<ITag,String> columnNameB;
    @FXML private TableColumn<ITag,String> columnDescriptionB;

    @FXML private ObservableList<ITag> lTags;
    @FXML private ObservableList<ITag> lTagsAdded;
    private List<ITag> listTagAddable=new ArrayList<>();
    private List<ITag> listTagTrans;
    private List<ITag> appoggio;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        numweeklabel.setVisible(false);
        numweekTransaction.setVisible(false);
        lTags = FXCollections.observableArrayList();
        lTagsAdded=FXCollections.observableArrayList();
        appoggio=controller.getTags();
        listTagAddable = controller.getTags().stream().collect(Collectors.toList());
        listTagTrans=new ArrayList<ITag>();
        updateTags();
        updateAddedTags();
    }
    public ControllerAddTransaction(MainController controller) {
        this.controller = controller;
    }

    public void activeNumWeek(){
        numweeklabel.setVisible(true);
        numweekTransaction.setVisible(true);
    }

    public void deactiveNumWeek(){
        numweeklabel.setVisible(false);
        numweekTransaction.setVisible(false);
    }
    public void saveNewTransaction(){
        try{
            controller.addTransaction(transactionDate.getValue(),listTagTrans);
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.hide();
            FXMLLoader loader =new FXMLLoader(getClass().getResource("/FXMLHome.fxml"));
            loader.setController(new GUIController(controller));

            stage.setTitle("JBudget");
            stage.setScene(new Scene(loader.load(), 640, 400));
            stage.show();

        }catch(Exception e){

        }finally{

        }
    }
    public void backButtonAction(){
        try {
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.hide();
            FXMLLoader loader =new FXMLLoader(getClass().getResource("/FXMLHome.fxml"));
            loader.setController(new GUIController(controller));

            stage.setTitle("JBudget");
            stage.setScene(new Scene(loader.load(), 640, 400));
            stage.show();
        }catch (Exception e){

        }
    }


    public void leaveTag(){
        if(tableAddedTag.getSelectionModel().getSelectedItem() != null){
            try {
                ITag tag = tableAddedTag.getSelectionModel().getSelectedItem();
                listTagTrans.remove(tag);
                listTagAddable.add(tag);
                updateAddedTags();
                updateTags();
            }catch(Exception e){
            }
        }
    }
    public void addTag(){
        if(tableAllTag.getSelectionModel().getSelectedItem() != null){
            try {
                ITag tag = tableAllTag.getSelectionModel().getSelectedItem();
                listTagTrans.add(tag);
                listTagAddable.remove(tag);
                List<ITag> ciao=controller.getTags();
                updateAddedTags();
                updateTags();
            }catch(Exception e){
            }
        }
    }
    private void updateAddedTags(){
        lTagsAdded.removeAll(lTagsAdded);
        lTagsAdded.addAll(listTagTrans);
        tableAddedTag.setItems(lTagsAdded);
        this.columnNameB.setCellValueFactory
                (cellData -> new SimpleObjectProperty<>(cellData.getValue().getNome()));
        this.columnDescriptionB.setCellValueFactory
                (cellData -> new SimpleObjectProperty<>(cellData.getValue().getDescription()));
        this.tableAddedTag.refresh();
    }
    private void updateTags(){
        lTags.removeAll(lTags);
        lTags.addAll(listTagAddable);
        tableAllTag.setItems(lTags);
        this.columnNameA.setCellValueFactory
                (cellData -> new SimpleObjectProperty<>(cellData.getValue().getNome()));
        this.columnDescriptionA.setCellValueFactory
                (cellData -> new SimpleObjectProperty<>(cellData.getValue().getDescription()));
        this.tableAllTag.refresh();
    }

    /*
    *Questo metodo aprirà una nuova finestra che permetterà semplicente di vedere tutti i tag associati a una transazione
     */
    public void viewTagsTransaction(){

    }


}

