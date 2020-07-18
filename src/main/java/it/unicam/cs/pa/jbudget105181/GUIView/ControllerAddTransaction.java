package it.unicam.cs.pa.jbudget105181.GUIView;

import it.unicam.cs.pa.jbudget105181.Controller.IController;
import it.unicam.cs.pa.jbudget105181.Model.Tag.ITag;
import it.unicam.cs.pa.jbudget105181.Model.Transaction.ITransazione;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Double.NaN;

/**
 * classe che ha la responsabilita' di fare da controller alla AddTransaction.
 */
public class ControllerAddTransaction implements ControllerFXML{
    /**
     * controller
     */
    private IController controller;
    /**
     * data della transazione
     */
    @FXML private DatePicker transactionDate;
    /**
     * bottone per transazione rateizzata
     */
    @FXML private RadioButton rateButton;
    /**
     * bottone per la transazione rateizzata
     */
    @FXML private RadioButton instantTransaction;
    /**
     * textField per dettare il distanziamento temporale tra le transazioni
     */
    @FXML private TextField numweekTransaction;
    /**
     * label per il distanziamento temporale tra le transazioni
     */
    @FXML private Label numweeklabel;
    /**
     * bottone per tornare indietro
     */
    @FXML private Button backButton;
    /**
     * bottone per salvare la transazione
     */
    @FXML private Button saveButton;
    /**
     * choiceBox per inserire il numero di transazioni rateizzate
     */
    @FXML private ChoiceBox<Integer> numberOfTransaction;
    /**
     * tabella contenente tutti i tag
     */
    @FXML private TableView<ITag> tableAllTag;
    /**
     * tabella contenente tutti i tag aggiunti
     */
    @FXML private TableView<ITag> tableAddedTag;
    /**
     * colonna nome tag della tabella A
     */
    @FXML private TableColumn<ITag,String> columnNameA;
    /**
     * colonna descrizione tag della tabella A
     */
    @FXML private TableColumn<ITag,String> columnDescriptionA;
    /**
     * colonna nome tag della tabella B
     */
    @FXML private TableColumn<ITag,String> columnNameB;
    /**
     * colonna descrizione tag della tabella B
     */
    @FXML private TableColumn<ITag,String> columnDescriptionB;
    /**
     * lista di tutti i tag
     */
    @FXML private ObservableList<ITag> lTags;
    /**
     * lista dei tag aggiunti
     */
    @FXML private ObservableList<ITag> lTagsAdded;
    /**
     * label per i messaggi di errore
     */
    @FXML private Label errorText;
    /**
     * area di testo per la descrizione della transazione
     */
    @FXML private TextArea descriptionTransaction;
    /**
     * lista di tutti i tag che possono essere aggiunti
     */
    private List<ITag> listTagAddable=new ArrayList<>();
    /**
     * lista dei tag della transazione
     */
    private List<ITag> listTagTrans;

    /**
     * metodo che ha il compito di inizializzare le variabili
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializevisible();
        inizializateTag();
        numberOfTransaction.setItems(FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10,11,12,18,24));
        instantTransaction.fire();
        updateTags();
    }

    /**
     * costruttore di ControllerAddTransaction
     * @param controller
     */
    public ControllerAddTransaction(IController controller) {
        this.controller = controller;
    }
    /**
     * Metodo che ha lo scopo di inizializzare le liste dei tag
     */
    private void inizializateTag(){
        lTags = FXCollections.observableArrayList();
        lTagsAdded=FXCollections.observableArrayList();
        listTagAddable = controller.getTags().parallelStream().collect(Collectors.toList());
        listTagTrans=new ArrayList<ITag>();
    }
    /**
     * metodo che imposta le visibilità iniziali
     */
    private void initializevisible(){
        numweeklabel.setVisible(false);
        numweekTransaction.setVisible(false);
        numberOfTransaction.setVisible(false);
        errorText.setVisible(false);
    }

    /**
     * metodo per impostare la visibilità a true delle variabili
     * riguardanti del range temporale
     */
    public void activeNumWeek(){
        numweeklabel.setVisible(true);
        numweekTransaction.setVisible(true);
        numberOfTransaction.setVisible(true);
    }

    /**
     * metodo per impostare la visibilità a false delle variabili
     * riguardanti del range temporale
     */
    public void deactiveNumWeek(){
        numweeklabel.setVisible(false);
        numweekTransaction.setVisible(false);
        numberOfTransaction.setVisible(false);
    }

    /**
     * metodo per aggiungere una nuova transazione
     */
    public void saveNewTransaction(){
        try{
            if(transactionDate.getValue() != null){
                if(numweekTransaction.isVisible()){
                    Double.parseDouble(numweekTransaction.getText());
                    addRatedTransaction();
                }else{
                    controller.addTransaction(transactionDate.getValue(),listTagTrans,descriptionTransaction.getText(),transactionDate.getValue().isBefore(LocalDate.now()));
                }
                returnHome();
            }else{
                errorText.setVisible(true);
            }
        }catch(Exception e){
            errorText.setVisible(true);
        }
    }

    /**
     * metodo che crea transazioni programmate per ogni tot giorni
     */
    private void addRatedTransaction(){
        if(!controller.getAccount().isEmpty()) {
            LocalDate data = transactionDate.getValue();
            List<ITransazione> listTransactionRated= new ArrayList<ITransazione>();
            for (int x = 0; x < numberOfTransaction.getValue(); x++) {
                listTransactionRated.add(controller.addTransaction(data, listTagTrans, descriptionTransaction.getText(), !data.isAfter(LocalDate.now())));
                data = data.plusDays(Long.parseLong(numweekTransaction.getText()));
            }
            openWindow("Add Movement", "/FXMLAddMovement.fxml", new ControllerAddMovement(controller, listTransactionRated,true));
        }else errorText.setVisible(true);
    }
    /**
     * metodo per ritornare alla pagina di Home
     */
    private void returnHome() throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.hide();
        FXMLLoader loader =new FXMLLoader(getClass().getResource("/FXMLHome.fxml"));
        loader.setController(new GUIController(controller));
        stage.setResizable(false);
        stage.setTitle("JBudget");
        stage.setScene(new Scene(loader.load(), 680, 455));
        stage.show();
    }

    /**
     * esegue l'operazione del bottone back
     */
    public void backButtonAction(){
        try {
            returnHome();
        }catch (Exception e){
        }
    }

    /**
     * metodo per rimuovere i tag dalla lista dei tag della transazione
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
     * metodo per aggiungere i tag dalla lista dei tag della transazione
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
     * metodo per aggiornare la tabella dei tag
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
     * Metodo per aprire una nuova finestra
     * @param title
     * @param fileFXML
     * @param controllerFXML
     */
    public void openWindow(String title, String fileFXML,ControllerFXML controllerFXML){
        try {
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.hide();
            stage.setTitle(title);
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fileFXML));
            loader.setController(controllerFXML);
            stage.setResizable(false);
            stage.setScene(new Scene(loader.load(), 800, 400));
            stage.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}

