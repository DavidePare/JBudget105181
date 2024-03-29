package it.unicam.cs.pa.jbudget105181.GUIView;

import it.unicam.cs.pa.jbudget105181.Controller.IController;
import it.unicam.cs.pa.jbudget105181.Model.*;
import it.unicam.cs.pa.jbudget105181.Model.Account.IAccount;
import it.unicam.cs.pa.jbudget105181.Model.Movement.IMovement;
import it.unicam.cs.pa.jbudget105181.Model.Movement.Movement;
import it.unicam.cs.pa.jbudget105181.Model.Movement.MovementType;
import it.unicam.cs.pa.jbudget105181.Model.Tag.ITag;
import it.unicam.cs.pa.jbudget105181.Model.Transaction.ITransazione;
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

/**
 * classe che ha la responsabilita' di fare da controller alla AddMovement.
 */
public class ControllerAddMovement implements ControllerFXML {

    /**
     *  variabile che verifica se un movimento è rateizzazto cioè deve essere inserito su più transazioni
     */
    private IController controller;
    /**
     * Riferimento della transazione alla quale viene aggiunto il movimento
     */
    private ITransazione transaction;
    /**
     * variabile per verificare se stiamo trattando un movimento rateizzato
     */
    private boolean rated=false;
    /**
     * lista delle transazioni
     */
    private List<ITransazione> listTransaction=new ArrayList<ITransazione>();
    /**
     * TextArea per prendere in input la descrizione dell'account
     */
    @FXML private TextArea descriptionMovement;
    /**
     * TextBox per prendere in input il valore della transazione
     */
    @FXML private TextField amountMovement;
    /**
     * ChoiceBox che fa scegliere all'utente l'account al quale fa riferimento il movimento
     */
    @FXML private ChoiceBox<IAccount> accountChoiceBox;
    /**
     * ChoiceBox che fa scegliere il tipo di movimento se CREDIT o DEBIT
     */
    @FXML private ChoiceBox<MovementType> movementTypeChoiceBox;
    /**
     * Bottone per tornare indietro
     */
    @FXML private Button backButton;
    /**
     * label per messaggi
     */
    @FXML private Label messageAddMovement;
    /**
     * tabella di tutti i tag disponibili (tabella A)
     */
    @FXML private TableView<ITag> tableAllTag;
    /**
     * tabella dei tag aggiunti (tabella B)
     */
    @FXML private TableView<ITag> tableAddedTag;
    /**
     * colonna nome tag della tabella A
     */
    @FXML private TableColumn<ITag,String> columnNameA;
    /**
     * colonna nome tag della descrizione A
     */
    @FXML private TableColumn<ITag,String> columnDescriptionA;
    /**
     * colonna nome tag della nome B
     */
    @FXML private TableColumn<ITag,String> columnNameB;
    /**
     * colonna nome tag della descrizione B
     */
    @FXML private TableColumn<ITag,String> columnDescriptionB;
    /**
     * lista di tag
     */
    @FXML private ObservableList<ITag> lTags;
    /**
     * lista di tag aggiunti
     */
    @FXML private ObservableList<ITag> lTagsAdded;
    /**
     * lista di tutti i tag che possono essere aggiunti
     */
    private List<ITag> listTagAddable=new ArrayList<>();
    /**
     * lista dei tag della transazione
     */
    private List<ITag> listTagTrans;
    /**
     * lista che mostrerà nella choiceBox tutti i nomi degli account associabili a un movimento
     */
    private ObservableList<IAccount> allAccount;
    /**
     * lista che mostrerà nella choiceBox i tipi di movimento
     */
    private ObservableList<MovementType> typeMovement;
    /**
     * bottone per salvare il movimento rateizzato
     */
    @FXML private Button saveButtonRated;
    /**
     * bottone per salvare la transazione istantanea
     */
    @FXML private Button saveButton;

    /**
     * metodo che ha il compito di inizializzare le variabili
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inizializzateSpecificButton();
        inizializzateTags();
        inizializzateTypeMovement();
        inizializzateAccountList();
        updateTags();
    }
    /**
     * metodo che ha la responsabilità di stabilire se attivare il bottone per attivare i movimenti rateizzati o normali
     */
    private void inizializzateSpecificButton(){
        if(!rated) saveButtonRated.setVisible(false);
        else saveButton.setVisible(false);
    }
    /**
     * metodo che inizializza le due tabelle per linserimento dei tag nei movimenti, associa i valori alle due liste
     */
    private void inizializzateTags(){
        lTags = FXCollections.observableArrayList();
        lTagsAdded=FXCollections.observableArrayList();
        listTagAddable.addAll(controller.getTags());
        listTagTrans=new ArrayList<ITag>();
    }
    /**
     * metodo per impostare i tipi di movimento
     */
    private void inizializzateTypeMovement(){
        typeMovement=FXCollections.observableArrayList();
        typeMovement.addAll(MovementType.values());
        movementTypeChoiceBox.setItems(typeMovement);
    }

    /**
     * metodo per impostare gli account
     */
    private void inizializzateAccountList(){
        allAccount=FXCollections.observableArrayList();
        allAccount.addAll(controller.getAccount());
        accountChoiceBox.setItems(allAccount);
    }

    /**
     * costruttore di ControllerAddMovement
     * @param controller
     */
    public ControllerAddMovement(IController controller) { // TODO DA RIVEDERE
        this.controller = controller;
    }

    /**
     * costruttore di ControllerAddMovement
     * @param controller
     * @param transazione
     */
    public ControllerAddMovement(IController controller, ITransazione transazione){ // TODO DA RIVEDERE
        this.transaction=transazione;
        this.controller=controller;
    }

    /**
     * costruttore di ControllerAddMovement
     * @param controller
     * @param lTransaction
     * @param rated
     */
    public ControllerAddMovement(IController controller, List<ITransazione> lTransaction,boolean rated){
        this.listTransaction=lTransaction;
        this.controller=controller;
        this.rated=rated;
    }

    /**
     * metodo che permetterà di tornare indietro senza attuare alcuna modifica
     */
    public void backButtonAction(){
        try {
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.hide();
            FXMLLoader loader =new FXMLLoader(getClass().getResource("/FXMLHome.fxml"));
            loader.setController(new GUIController(controller));
            stage.setTitle("JBudget");
            stage.setScene(new Scene(loader.load(), 680, 455));
            stage.show();
        }catch (Exception e){

        }
    }

    /**
     * metodo per aggiungere un movimento
     */
    public void saveNewMovement(){
        try{

            controller.addMovement( IFactory.generateMovement(controller.generateIDMovement(transaction), descriptionMovement.getText(), movementTypeChoiceBox.getValue(),
                    Double.parseDouble(amountMovement.getText()), accountChoiceBox.getValue(), listTagTrans, transaction));
            setSuccessfullAddMovement();
        }catch(Exception e){
            setWrongAddMovement();
        }finally {
            movementWindowRefresh();
        }
    }
    /**
     * metodo per aggiornare le due tabelle dei tag
     */
    private void updateTags(){
        updateATable(columnNameA,columnDescriptionA,tableAllTag,lTags,listTagAddable);
        updateATable(columnNameB,columnDescriptionB,tableAddedTag,lTagsAdded,listTagTrans);
    }

    /**
     *
     * @param columnName nome colonna da aggiornare
     * @param columnDescription nome colonna da aggiornare
     * @param table tabella a cui si fa riferimento
     * @param listToupdate observable lista di riferimento
     * @param listTag lista contenente i dati
     * Metodo che ha la responsabilità di aggiornare una tabella per tag.
     */
    private void updateATable(TableColumn<ITag,String> columnName ,TableColumn<ITag,String> columnDescription,
                              TableView<ITag> table, ObservableList<ITag> listToupdate,List<ITag> listTag){
        listToupdate.clear();
        listToupdate.addAll(listTag);
        table.setItems(listToupdate);
        columnName.setCellValueFactory
                (cellData -> new SimpleObjectProperty<>(cellData.getValue().getNome()));
        columnDescription.setCellValueFactory
                (cellData -> new SimpleObjectProperty<>(cellData.getValue().getDescription()));
        table.refresh();
    }
    /**
     * metodo per rimuovere un tag dalla lista dei tag del movimento
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
    /**
     * metodo per aggiungere tag alla lista dei tag relativi al movimento
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

    /**
     * metodo per aggiungere un movimento della transazione rateizzata
     */
    public void saveRatedMovement(){
        try{
            IMovement mov = new Movement(-1, descriptionMovement.getText(), movementTypeChoiceBox.getValue(),
                    Double.parseDouble(amountMovement.getText()), accountChoiceBox.getValue(), listTagTrans, null);
            controller.addRateMovement(listTransaction,mov);
            setSuccessfullAddMovement();
        }catch(Exception e){
            setWrongAddMovement();
        }finally {
            movementWindowRefresh();
        }
    }

    /**
     * metodo che ha la responsabilità di stampare a video il corretto inserimento del movimento
     */
    private void setSuccessfullAddMovement(){
        messageAddMovement.setText("Successfull! Movement correct Added!");
        messageAddMovement.setTextFill(Color.GREEN);
    }

    /**
     * metodo che ha la responsabilità di dire all'utente che il movimento non è stato aggiunto per un errore
     */
    private void setWrongAddMovement(){

        messageAddMovement.setText("Wrong! Insert all Data!");
        messageAddMovement.setTextFill(Color.RED);
    }
    /**
     * metodo con la responsabilità di aggiornare la pagina resettando tutti i valori permettendo all'utente di inserire
     * nuovi dati
     */
    private void movementWindowRefresh(){
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
