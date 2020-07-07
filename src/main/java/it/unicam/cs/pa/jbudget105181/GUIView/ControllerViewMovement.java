package it.unicam.cs.pa.jbudget105181.GUIView;

import it.unicam.cs.pa.jbudget105181.Controller.MainController;
import it.unicam.cs.pa.jbudget105181.Model.IMovement;
import it.unicam.cs.pa.jbudget105181.Model.ITransazione;
import it.unicam.cs.pa.jbudget105181.Model.MovementType;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerViewMovement  implements ControllerFXML{
    private ObservableList<IMovement> lMovement;
    private MainController controller;
    private ITransazione transaction;
    @FXML private TableView<IMovement> tableMovement;
    @FXML private TableColumn<IMovement,Integer> columnIDAccount;
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
        lMovement.addAll(transaction.movements());
        showmovement();
    }
    private void showmovement(){
        tableMovement.setItems(lMovement);
        this.columnIDAccount.setCellValueFactory
                (cellData -> new SimpleObjectProperty<>(cellData.getValue().getAccount().getID()));
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

    }
    public void deleteMovement(){

    }
    public void modifyMovement(){

    }
}
