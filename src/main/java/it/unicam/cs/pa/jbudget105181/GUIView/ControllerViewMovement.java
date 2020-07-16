package it.unicam.cs.pa.jbudget105181.GUIView;

import it.unicam.cs.pa.jbudget105181.Controller.IController;
import it.unicam.cs.pa.jbudget105181.Model.Account.IAccount;
import it.unicam.cs.pa.jbudget105181.Model.Movement.IMovement;
import it.unicam.cs.pa.jbudget105181.Model.Transaction.ITransazione;
import it.unicam.cs.pa.jbudget105181.Model.Movement.MovementType;
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

/**
 * classe che ha la responsabilita' di fare da controller alla  ViewMovement.
 */
public class ControllerViewMovement  implements ControllerFXML{
    /**
     * lista dei moviementi
     */
    private ObservableList<IMovement> lMovement;
    /**
     * controller
     */
    private IController controller;
    /**
     * transazione del movimento
     */
    private ITransazione transaction;
    /**
     * tabella dei movimenti
     */
    @FXML private TableView<IMovement> tableMovement;
    /**
     * colonna ID account
     */
    @FXML private TableColumn<IMovement,IAccount> columnIDAccount;
    /**
     * colonna ID movimnento
     */
    @FXML private TableColumn<IMovement,Integer> columnIDMovement;
    /**
     * colonna ID transazione
     */
    @FXML private TableColumn<IMovement,Integer> columnIDTransaction;
    /**
     * colonna tipo di movimento
     */
    @FXML private TableColumn<IMovement, MovementType> columnType;
    /**
     * colonna amount movimento
     */
    @FXML private TableColumn<IMovement,Double> columnAmount;
    /**
     * colonna descrizione movimento
     */
    @FXML private TableColumn<IMovement,String> columnDescription;

    /**
     * costruttore ControllerViewMovement
     * @param transaction
     * @param controller
     */
    public ControllerViewMovement(ITransazione transaction, IController controller){
        this.transaction=transaction;
        this.controller=controller;
    }

    /**
     * metodo per inizializzare le variabili
     * @param location
     * @param resources
     */
    public void initialize(URL location, ResourceBundle resources){
        lMovement = FXCollections.observableArrayList();
        showmovement();
    }

    /**
     * metodo per aggiornare e mostrare la tabella dei movimenti
     */
    private void showmovement(){
        lMovement.clear();//removeAll(lMovement);
        lMovement.addAll(transaction.movements());
        tableMovement.setItems(lMovement);
        this.columnIDAccount.setCellValueFactory
                (cellData -> new SimpleObjectProperty<>(cellData.getValue().getAccount()));
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

    /**
     * metodo per mostrare i tag di un movimento
     */
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

    /**
     * metodo per rimuovere un movimento
     */
    public void deleteMovement(){
        if(tableMovement.getSelectionModel().getSelectedItem() != null) {
            controller.removeMovement(tableMovement.getSelectionModel().getSelectedItem());
            lMovement.remove(tableMovement.getSelectionModel().getSelectedItem());
            showmovement();
        }
    }

    /**
     * metodo per modificare un movimento
     */
    public void modifyMovement(){

    }
}
