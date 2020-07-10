package it.unicam.cs.pa.jbudget105181.GUIView;

import it.unicam.cs.pa.jbudget105181.Controller.MainController;
import it.unicam.cs.pa.jbudget105181.Model.IMovement;
import it.unicam.cs.pa.jbudget105181.Model.ITag;
import it.unicam.cs.pa.jbudget105181.Model.ITransazione;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ControllerAddTransaction implements ControllerFXML{
    private MainController controller;
    @FXML private DatePicker transactionDate;
    @FXML private RadioButton rateButton;
    @FXML private RadioButton instantTransaction;
    @FXML private TextField numweekTransaction;
    @FXML private Label numweeklabel;
    @FXML private Button backButton;
    @FXML private Button saveButton;
    @FXML private ChoiceBox<Integer> numberOfTransaction;


    @FXML private TableView<ITag> tableAllTag;
    @FXML private TableView<ITag> tableAddedTag;

    @FXML private TableColumn<ITag,String> columnNameA;
    @FXML private TableColumn<ITag,String> columnDescriptionA;

    @FXML private TableColumn<ITag,String> columnNameB;
    @FXML private TableColumn<ITag,String> columnDescriptionB;

    @FXML private ObservableList<ITag> lTags;
    @FXML private ObservableList<ITag> lTagsAdded;

    @FXML private Label errorText;
    @FXML private TextArea descriptionTransaction;
    private List<ITag> listTagAddable=new ArrayList<>();
    private List<ITag> listTagTrans;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializevisible();
        numberOfTransaction.setItems(FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10,11,12,18,24));
        lTags = FXCollections.observableArrayList();
        lTagsAdded=FXCollections.observableArrayList();
        listTagAddable = controller.getTags().stream().collect(Collectors.toList());
        listTagTrans=new ArrayList<ITag>();
        instantTransaction.fire();
        updateTags();
    }
    public ControllerAddTransaction(MainController controller) {
        this.controller = controller;
    }
    /*
     * Metodo che setta le visibilità iniziali
     */
    private void initializevisible(){
        numweeklabel.setVisible(false);
        numweekTransaction.setVisible(false);
        numberOfTransaction.setVisible(false);
        errorText.setVisible(false);
    }
    public void activeNumWeek(){
        numweeklabel.setVisible(true);
        numweekTransaction.setVisible(true);
        numberOfTransaction.setVisible(true);
    }

    public void deactiveNumWeek(){
        numweeklabel.setVisible(false);
        numweekTransaction.setVisible(false);
        numberOfTransaction.setVisible(false);
    }
    public void saveNewTransaction(){
        try{
            if(transactionDate.getValue() != null){
                if(numweekTransaction.isVisible()){
                    if(!controller.getAccount().isEmpty()) {
                        LocalDate data = transactionDate.getValue();
                        ITransazione t = controller.addTransaction(data, listTagTrans,descriptionTransaction.getText(),transactionDate.getValue().isBefore(LocalDate.now()));
                        openWindow("Add Movement", "/FXMLAddMovement.fxml", new ControllerAddMovement(controller, t));
                        List<IMovement> listMovement=t.movements().stream().filter(p-> p== p ).collect(Collectors.toList());
                        for (int x = 0; x < numberOfTransaction.getValue(); x++) {
                          //  ITransazione t = controller.addTransaction(data, listTagTrans,descriptionTransaction.getText(),transactionDate.getValue().isBefore(LocalDate.now()));
                            data = data.plusDays(Long.parseLong(numweekTransaction.getText()));
                            ITransazione transazione=controller.addTransaction(data,listTagTrans,descriptionTransaction.getText(),transactionDate.getValue().isBefore(LocalDate.now()));
                            controller.addMovementList(transazione,listMovement);
                        }
                    }else errorText.setVisible(true);
                }else{
                    controller.addTransaction(transactionDate.getValue(),listTagTrans,descriptionTransaction.getText(),transactionDate.getValue().isBefore(LocalDate.now()));
                }
                returnHome();
            }else{
                errorText.setVisible(true);
            }
        }catch(Exception e){

        }
    }
    /*
     * Metodo per non duplicare il codice usato sia da save che da delete per tornare alla pagina principale.
     */
    private void returnHome() throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.hide();
        FXMLLoader loader =new FXMLLoader(getClass().getResource("/FXMLHome.fxml"));
        loader.setController(new GUIController(controller));

        stage.setTitle("JBudget");
        stage.setScene(new Scene(loader.load(), 640, 400));
        stage.show();
    }
    public void backButtonAction(){
        try {
            returnHome();
        }catch (Exception e){
        }
    }

    /*
     * Metodo che sposta un tag dalla lista dei tag da aggiungere e lo inserisce nei tag che possono essere aggiunti
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
    private void updateTags(){
        lTags.removeAll(lTags);
        lTags.addAll(listTagAddable);
        lTagsAdded.removeAll(lTagsAdded);
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

    public void openWindow(String title, String fileFXML,ControllerFXML controllerFXML){
        try {
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.hide();
            stage.setTitle(title);
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fileFXML));
            loader.setController(controllerFXML);
            stage.setScene(new Scene(loader.load(), 800, 400));
            stage.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}

