package it.unicam.cs.pa.jbudget105181.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXMLStart extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/.fxml"));
        stage.setTitle("JBudget");
        stage.setScene(new Scene(root, 1050, 500));
        stage.show();
    }
}
