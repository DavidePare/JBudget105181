package it.unicam.cs.pa.jbudget105181.GUIView;

import it.unicam.cs.pa.jbudget105181.Controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * classe che ha la responsabilita' di far partire lo stage della GUIView.
 */
public class FXMLStart extends Application {

    /**
     * Metodo da cui far partire lo stage delle GUI View
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("/FXMLHome.fxml"));
       /* try {
         //   Stage stage = new Stage();
            stage.setTitle("Movement Transaction");
            stage.initModality(Modality.APPLICATION_MODAL);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLViewMovement.fxml"));
            loader.setController(new ControllerViewMovement(new Transazione(1,null,null,true), new MainController()));
            stage.setScene(new Scene(loader.load(), 640, 450));
            stage.show();
        }catch(Exception E){

        }*/
        FXMLLoader loader =new FXMLLoader(getClass().getResource("/FXMLHome.fxml"));
        loader.setController(new GUIController(new MainController()));
        stage.setResizable(false);
        stage.setTitle("JBudget");
        stage.setScene(new Scene(loader.load(), 680, 455));
        stage.show();
    }
}
