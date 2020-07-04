package it.unicam.cs.pa.jbudget105181.GUIView;

import it.unicam.cs.pa.jbudget105181.Controller.MainController;
import it.unicam.cs.pa.jbudget105181.Model.ITag;
import it.unicam.cs.pa.jbudget105181.Model.ITransazione;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerViewTag  implements ControllerFXML{
    private ObservableList<ITag> lTags;
    private ITransazione transaction;
    @FXML private TableView<ITag> tableTags;
    @FXML private TableColumn<ITag,String> columnName;
    @FXML private TableColumn<ITag,String> columnDescription;

    public ControllerViewTag(ITransazione transaction){
        this.transaction=transaction;


    }
    public void initialize(URL location, ResourceBundle resources){
        lTags = FXCollections.observableArrayList();
        lTags.addAll(transaction.tags());
        showtags();
    }
    private void showtags(){
        tableTags.setItems(lTags);
        this.columnName.setCellValueFactory
                (cellData -> new SimpleObjectProperty<>(cellData.getValue().getNome()));
        this.columnDescription.setCellValueFactory
                (cellData -> new SimpleObjectProperty<>(cellData.getValue().getDescription()));
        this.tableTags.refresh();
    }
}
