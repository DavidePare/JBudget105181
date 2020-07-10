package it.unicam.cs.pa.jbudget105181.GUIView;

import it.unicam.cs.pa.jbudget105181.Controller.MainController;
import it.unicam.cs.pa.jbudget105181.Model.IAccount;
import it.unicam.cs.pa.jbudget105181.Model.IMovement;
import it.unicam.cs.pa.jbudget105181.Model.ITransazione;
import it.unicam.cs.pa.jbudget105181.Model.MovementType;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerViewMovement  implements ControllerFXML{
    private ObservableList<IMovement> lMovement;
    private MainController controller;
    private ITransazione transaction;
    @FXML private TableView<IMovement> tableMovement;
    /*
    ERRORE QUI
     */
    @FXML private TableColumn<IMovement,String> columnIDAccount;
    @FXML private TableColumn<IMovement,Integer> columnIDMovement;

    @FXML private TableColumn<IMovement,Integer> columnIDTransaction;

    @FXML private TableColumn<IMovement, MovementType> columnType;
    @FXML private TableColumn<IMovement,Double> columnAmount;
    @FXML private TableColumn<IMovement,String> columnDescription;

    public ControllerViewMovement(ITransazione transaction, MainController controller){
        this.transaction=transaction;
        this.controller=controller;
    }
    public void initialize(URL location, ResourceBundle resources){
        lMovement = FXCollections.observableArrayList();
        showmovement();
    }
    private void showmovement(){
        lMovement.addAll(transaction.movements());
        tableMovement.setItems(lMovement);
      /*  this.columnIDAccount.setCellValueFactory
                (cellData -> new SimpleObjectProperty<>(cellData.getValue().getAccount().getNameAccount()));*/
        this.columnIDMovement.setCellValueFactory
                (cellData -> new SimpleObjectProperty<>(cellData.getValue().getID()));
        this.columnIDTransaction.setCellValueFactory
                (cellData -> new SimpleObjectProperty<>(cellData.getValue().getIDTransazione()));
        this.columnType.setCellValueFactory
                (cellData -> new SimpleObjectProperty<>(cellData.getValue().getTipo()));
        this.columnDescription.setCellValueFactory
                (cellData -> new SimpleObjectProperty<>(cellData.getValue().getDescription()));
        this.columnAmount.setCellValueFactory
                (cellData -> new SimpleObjectProperty<>(cellData.getValue().getAmount()));
        this.tableMovement.refresh();
    }
    public void viewTag(){
        try {
            if(tableMovement.getSelectionModel().getSelectedItem() != null) {
                Stage stage = new Stage();
                stage.setTitle("Tag Transaction");
                stage.initModality(Modality.APPLICATION_MODAL);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLViewTags.fxml"));
                loader.setController(new ControllerViewTag(tableMovement.getSelectionModel().getSelectedItem()));
                stage.setScene(new Scene(loader.load(), 345, 400));
                stage.show();
            }
        }catch(Exception e){

        }
    }
    public void deleteMovement(){
        if(tableMovement.getSelectionModel().getSelectedItem() != null) {
            controller.removeMovement(tableMovement.getSelectionModel().getSelectedItem());
            lMovement.remove(tableMovement.getSelectionModel().getSelectedItem());
            showmovement();
        }
    }
    public void modifyMovement(){

    }
}
