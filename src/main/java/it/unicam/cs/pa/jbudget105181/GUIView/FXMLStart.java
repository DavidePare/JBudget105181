package it.unicam.cs.pa.jbudget105181.GUIView;

import it.unicam.cs.pa.jbudget105181.Controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXMLStart extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("/FXMLHome.fxml"));
        FXMLLoader loader =new FXMLLoader(getClass().getResource("/FXMLHome.fxml"));
        loader.setController(new GUIController(new MainController()));

        stage.setTitle("JBudget");
        stage.setScene(new Scene(loader.load(), 640, 400));
        stage.show();
    }
}
