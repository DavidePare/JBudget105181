package it.unicam.cs.pa.jbudget105181.GUIView;

import it.unicam.cs.pa.jbudget105181.Controller.ControllerMovimenti;
import it.unicam.cs.pa.jbudget105181.Controller.MainController;
import it.unicam.cs.pa.jbudget105181.Model.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class GUIController implements Initializable {



    private MainController controller;
    @FXML private TextField tagName;
    @FXML private TextField tagDescription;
    @FXML private TableView<ITag> tagTable;
    @FXML private TableColumn<ITag,String> tagNameColumn;
    @FXML private TableColumn<ITag,String> tagDescriptionColumn;

    @FXML private DatePicker transactionDate;
    @FXML private RadioButton programmedTransaction;
    @FXML private RadioButton instantTransaction;


    @FXML private TableView<ITransazione> transTable;
    @FXML private TableColumn<ITransazione,Integer> transIDColumn;
    @FXML private TableColumn<ITransazione,Double> transAmountColumn;
    @FXML private TableColumn<ITransazione,Integer> transNumMovColumn;
    @FXML private TableColumn<ITransazione, LocalDate> transDateColumn;


    @FXML private Label FailedOperationTag;
    private ObservableList<ITag> lTags;
    private ObservableList<ITransazione> lTransactions;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = new MainController();
        lTags = FXCollections.observableArrayList();
        lTransactions = FXCollections.observableArrayList();
        updateTags();
    }

    public void openWindow(String title, String fileFXML,ControllerFXML controllerFXML){
        try {
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.initModality(Modality.APPLICATION_MODAL);
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fileFXML));
            loader.setController(controllerFXML);
            stage.setScene(new Scene(loader.load(), 800, 400));
            stage.show();
        }catch (IOException e) {

        }
    }
    public void addTransaction(){
        openWindow("Add Transaction", "/FXMLAddTransaction.fxml", new ControllerAddTransaction(controller));
    }

    public void saveNewTransaction(){

    }

    public void addMovement() {
        if(!controller.getTransaction().isEmpty()) {
            openWindow("Add Movement", "/FXMLAddMovement.fxml", new ControllerAddMovement(controller));
        }
    }

    private void updateTransaction(){
        lTransactions.removeAll(lTransactions);
        lTransactions.addAll(controller.getTransaction());
        transTable.setItems(lTransactions);
        this.transIDColumn.setCellValueFactory
                (cellData -> new SimpleObjectProperty<>(cellData.getValue().getID()));
        this.transAmountColumn.setCellValueFactory
                (cellData -> new SimpleObjectProperty<>(cellData.getValue().getTotalAmount()));

        this.transNumMovColumn.setCellValueFactory
                (cellData -> new SimpleObjectProperty<>(cellData.getValue().getNumMov()));
        this.transDateColumn.setCellValueFactory
                (cellData -> new SimpleObjectProperty<>(cellData.getValue().getData()));
        this.transTable.refresh();
    }

    public void deleteTag() {
        FailedOperationTag.setText("");
        ITag tag = tagTable.getSelectionModel().getSelectedItem();
        if(!tagTable.getItems().isEmpty() && tag != null) {
            controller.removeTag(tag);
            updateTags();
        }
    }

    public void addTag() {
        try{
            FailedOperationTag.setText("");
            if(!(tagName.getText().equals(""))) {
                ITag tag = new Tag(tagName.getText(), tagDescription.getText());
                if(this.controller.alreadyExistTag(tag)) {
                    this.controller.addTag(tag);
                    updateTags();
                }else FailedOperationTag.setText("Failed Operation! If tag alreasy exists unaddable!");

            }
        }catch (Exception e){

        }finally {
            tagName.clear();
            tagDescription.clear();
        }
    }

    private void updateTags(){
        lTags.removeAll(lTags);
        lTags.addAll(controller.getTags());
        tagTable.setItems(lTags);
        this.tagNameColumn.setCellValueFactory
                (cellData -> new SimpleObjectProperty<>(cellData.getValue().getNome()));
        this.tagDescriptionColumn.setCellValueFactory
                (cellData -> new SimpleObjectProperty<>(cellData.getValue().getDescription()));
        this.tagTable.refresh();
    }
}
