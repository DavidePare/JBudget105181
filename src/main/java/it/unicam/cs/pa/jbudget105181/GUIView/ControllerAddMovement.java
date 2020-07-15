package it.unicam.cs.pa.jbudget105181.GUIView;

import it.unicam.cs.pa.jbudget105181.Controller.MainController;
import it.unicam.cs.pa.jbudget105181.Model.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ControllerAddMovement implements ControllerFXML {

    /*
     *  variabile che verifica se un movimento è rateizzazto cioè deve essere inserito su più transazioni
     */
    private MainController controller;
    /*
     * Riferimento della transazione alla quale viene aggiunto il movimento
     */
    private ITransazione transaction;

    private boolean rated=false;

    private List<ITransazione> listTransaction=new ArrayList<ITransazione>();
    /*
     * TextArea per prendere in input la descrizione dell'account
     */
    @FXML private TextArea descriptionMovement;
    /*
     * TextBox per prendere in input il valore della transazione
     */
    @FXML private TextField amountMovement;
    /*
     * ChoiceBox che fa scegliere all'utente l'account al quale fa riferimento il movimento
     */
    @FXML private ChoiceBox<IAccount> accountChoiceBox;
    /*
     * ChoiceBox che fa scegliere il tipo di movimento se CREDIT o DEBIT
     */
    @FXML private ChoiceBox<MovementType> movementTypeChoiceBox;
    /*
     * Bottone per tornare indietro
     */
    @FXML private Button backButton;
    @FXML private Label messageAddMovement;
    @FXML private TableView<ITag> tableAllTag;
    @FXML private TableView<ITag> tableAddedTag;

    @FXML private TableColumn<ITag,String> columnNameA;
    @FXML private TableColumn<ITag,String> columnDescriptionA;

    @FXML private TableColumn<ITag,String> columnNameB;
    @FXML private TableColumn<ITag,String> columnDescriptionB;
    @FXML private ObservableList<ITag> lTags;
    @FXML private ObservableList<ITag> lTagsAdded;
    private List<ITag> listTagAddable=new ArrayList<>();
    private List<ITag> listTagTrans;
    /*
     * Lista che mostrerà nella choiceBox tutti i nomi degli account associabili a un movimento
     */
    private ObservableList<IAccount> allAccount;
    /*
     * Lista che mostrerà nella choiceBox i tipi di movimento
     */
    private ObservableList<MovementType> typeMovement;

    @FXML private Button saveButtonRated;
    @FXML private Button saveButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lTags = FXCollections.observableArrayList();
        lTagsAdded=FXCollections.observableArrayList();
        listTagAddable.addAll(controller.getTags());//.stream().collect(Collectors.toList()); //TODO ricontrolla che non hai testato la nuova modifica dovrebbe funzionare
        listTagTrans=new ArrayList<ITag>();
        if(!rated) saveButtonRated.setVisible(false);
        else saveButton.setVisible(false);
        inizializzateTypeMovement();
        inizializzateAccountList();
        updateTags();
    }
    private void inizializzateTypeMovement(){
        typeMovement=FXCollections.observableArrayList();
        typeMovement.addAll(MovementType.values());
        movementTypeChoiceBox.setItems(typeMovement);
    }
    private void inizializzateAccountList(){
        allAccount=FXCollections.observableArrayList();
        allAccount.addAll(controller.getAccount());
        accountChoiceBox.setItems(allAccount);
    }
    public ControllerAddMovement(MainController controller) {
        this.controller = controller;
    }
    public ControllerAddMovement(MainController controller, ITransazione transazione){
        this.transaction=transazione;
        this.controller=controller;
    }
    public ControllerAddMovement(MainController controller, List<ITransazione> lTransaction,boolean rated){
        this.listTransaction=lTransaction;
        this.controller=controller;
        this.rated=rated;
    }

    /*
     * Metodo che permetterà di tornare indietro senza attuare alcuna modifica
     */
    public void backButtonAction(){
        try {
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.hide();
            FXMLLoader loader =new FXMLLoader(getClass().getResource("/FXMLHome.fxml"));
            loader.setController(new GUIController(controller));

            stage.setTitle("JBudget");
            stage.setScene(new Scene(loader.load(), 640, 400));
            stage.show();
        }catch (Exception e){

        }
    }
    public void saveNewMovement(){
        try{

            controller.addMovement( IFactory.generateMovement(controller.generateIDMovement(transaction), descriptionMovement.getText(), movementTypeChoiceBox.getValue(),
                    Double.parseDouble(amountMovement.getText()), accountChoiceBox.getValue(), listTagTrans, transaction));
            messageAddMovement.setText("Successfull! Movement correct Added!");
            messageAddMovement.setTextFill(Color.GREEN);
        }catch(Exception e){
            messageAddMovement.setText("Wrong! Insert all Data!");
            messageAddMovement.setTextFill(Color.RED);
        }finally {
            descriptionMovement.clear();
            movementTypeChoiceBox.setValue(null);
            lTagsAdded.clear();
            lTags.clear();
            listTagTrans= new ArrayList<>();
            listTagAddable.clear();
            listTagAddable.addAll(controller.getTags());
            lTags.addAll(controller.getTags());
            amountMovement.clear();
        }
    }
    /*
     * Questo metodo aggiornerà le due tabelle con i tag inseriti e quelli che non sono stati inseriti
     */
    private void updateTags(){
        lTags.clear();
        lTags.addAll(listTagAddable);
        lTagsAdded.clear();
        lTagsAdded.addAll(listTagTrans);
        tableAllTag.setItems(lTags);
        this.columnNameA.setCellValueFactory
                (cellData -> new SimpleObjectProperty<>(cellData.getValue().getNome()));
        this.columnDescriptionA.setCellValueFactory
                (cellData -> new SimpleObjectProperty<>(cellData.getValue().getDescription()));

        tableAddedTag.setItems(lTagsAdded);
        this.columnNameB.setCellValueFactory
                (cellData -> new SimpleObjectProperty<>(cellData.getValue().getNome()));
        this.columnDescriptionB.setCellValueFactory
                (cellData -> new SimpleObjectProperty<>(cellData.getValue().getDescription()));
        this.tableAddedTag.refresh();
        this.tableAllTag.refresh();
    }
    /*
     * Metodo che serve per rimuovere un tag dalla lista dei tag che l'utente vuole inserire alla creazione del movimento.
     */
    public void leaveTag(){
        if(tableAddedTag.getSelectionModel().getSelectedItem() != null){
            try {
                ITag tag = tableAddedTag.getSelectionModel().getSelectedItem();
                listTagTrans.remove(tag);
                listTagAddable.add(tag);
                updateTags();
            }catch(Exception e){

            }
        }
    }
    /*
     * Metodo che nel aggiunge tag dalla lista dei tag aggiunti e li rimuove dalla lista dei tag aggiungibili,
     * Mette il tag nella tabella di sinistra e lo rimuove a quella di destra
     */
    public void addTag(){
        if(tableAllTag.getSelectionModel().getSelectedItem() != null){
            try {
                ITag tag = tableAllTag.getSelectionModel().getSelectedItem();
                listTagTrans.add(tag);
                listTagAddable.remove(tag);
                updateTags();
            }catch(Exception e){
            }
        }
    }


    public void saveRatedMovement(){
        try{
            IMovement mov = new Movement(-1, descriptionMovement.getText(), movementTypeChoiceBox.getValue(),
                    Double.parseDouble(amountMovement.getText()), accountChoiceBox.getValue(), listTagTrans, null);
            controller.addRateMovement(listTransaction,mov);
            messageAddMovement.setText("Successfull! Movement correct Added!");
            messageAddMovement.setTextFill(Color.GREEN);
        }catch(Exception e){
            messageAddMovement.setText("Wrong! Insert all Data!");
            messageAddMovement.setTextFill(Color.RED);
        }finally {
            descriptionMovement.clear();
            movementTypeChoiceBox.setValue(null);
            lTagsAdded.clear();
            lTags.clear();
            listTagTrans= new ArrayList<>();
            listTagAddable.clear();
            listTagAddable.addAll(controller.getTags());
            lTags.addAll(controller.getTags());
            amountMovement.clear();
        }
    }
}
