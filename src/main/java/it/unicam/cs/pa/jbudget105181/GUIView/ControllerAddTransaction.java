package it.unicam.cs.pa.jbudget105181.GUIView;

import it.unicam.cs.pa.jbudget105181.Controller.MainController;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerAddTransaction implements ControllerFXML{
    private MainController controller;
    @FXML private DatePicker transactionDate;
    @FXML private RadioButton programmedTransaction;
    @FXML private RadioButton instantTransaction;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public ControllerAddTransaction(MainController controller) {
        this.controller = controller;
    }
    public void saveNewTransaction(){

    }


}
