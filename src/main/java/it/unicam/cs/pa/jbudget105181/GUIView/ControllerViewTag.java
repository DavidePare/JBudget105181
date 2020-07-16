package it.unicam.cs.pa.jbudget105181.GUIView;

import it.unicam.cs.pa.jbudget105181.Model.Movement.IMovement;
import it.unicam.cs.pa.jbudget105181.Model.Tag.ITag;
import it.unicam.cs.pa.jbudget105181.Model.Transaction.ITransazione;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * classe che ha la responsabilita' di fare da controller alla  ViewTag.
 */
public class ControllerViewTag  implements ControllerFXML{
    /**
     * lista di tag
     */
    private ObservableList<ITag> lTags;
    /**
     * transazione
     */
    private ITransazione transaction;
    /**
     * movimento
     */
    private IMovement movement;
    /**
     * tabella dei tag
     */
    @FXML private TableView<ITag> tableTags;
    /**
     * colonna del nome del tag
     */
    @FXML private TableColumn<ITag,String> columnName;
    /**
     * colonna della descrizione del tag
     */
    @FXML private TableColumn<ITag,String> columnDescription;

    /**
     * costruttore di ControllerViewTag passatogli il movimento
     * @param movement
     */
    public ControllerViewTag(IMovement movement){ // TODO doppio costruttore
        this.movement=movement;
        this.transaction=null;
    }

    /**
     * costruttore di ControllerViewTag passatogli la transazione
     * @param transaction
     */
    public ControllerViewTag(ITransazione transaction){
        this.transaction=transaction;
        this.movement=null;
    }

    /**
     * metdo per inizializzare le variabili
     * @param location
     * @param resources
     */
    public void initialize(URL location, ResourceBundle resources){
        lTags = FXCollections.observableArrayList();
        if(transaction!= null) lTags.addAll(transaction.tags());
        else lTags.addAll(movement.tags());
        showtags();
    }

    /**
     * metodo per aggiornare e mostrare la tabella dei tag
     */
    private void showtags(){
        tableTags.setItems(lTags);
        this.columnName.setCellValueFactory
                (cellData -> new SimpleObjectProperty<>(cellData.getValue().getNome()));
        this.columnDescription.setCellValueFactory
                (cellData -> new SimpleObjectProperty<>(cellData.getValue().getDescription()));
        this.tableTags.refresh();
    }
}