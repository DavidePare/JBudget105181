package it.unicam.cs.pa.jbudget105181.GUIView;

import it.unicam.cs.pa.jbudget105181.Controller.ControllerMovimenti;
import it.unicam.cs.pa.jbudget105181.Controller.MainController;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;

import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerAddMovement implements ControllerFXML {


    private MainController controller;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public ControllerAddMovement(MainController controller) {
        this.controller = controller;
    }



}
