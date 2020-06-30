package it.unicam.cs.pa.jbudget105181.GUIView;

import it.unicam.cs.pa.jbudget105181.Controller.ControllerMovimenti;
import it.unicam.cs.pa.jbudget105181.Controller.MainController;
import it.unicam.cs.pa.jbudget105181.Model.ITag;
import it.unicam.cs.pa.jbudget105181.Model.Tag;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GUIController implements Initializable {


    private MainController controller;
    @FXML private TextField tagName;
    @FXML private TextField tagDescription;
    @FXML private TableView<ITag> tagTable;
    @FXML private TableColumn<ITag,String> tagNameColumn;
    @FXML private TableColumn<ITag,String> tagDescriptionColumn;
    private ObservableList<ITag> lTags;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = new MainController();
        lTags = FXCollections.observableArrayList();
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

    public void addMovement() {
        openWindow("Add Movement","/FXMLAddMovement.fxml",new ControllerAddMovement(controller));
    }

    public void deleteTag() {
        ITag tag = tagTable.getSelectionModel().getSelectedItem();
        if(!tagTable.getItems().isEmpty() && tag != null) {
            controller.removeTag(tag);
            updateTags();
        }
    }

    public void addTag() {
        try{
            if(!tagName.getText().equals("")) {
                ITag tag = new Tag(tagName.getText(), tagDescription.getText());
                this.controller.addTag(tag);
                updateTags();
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
