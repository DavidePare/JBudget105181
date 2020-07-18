package it.unicam.cs.pa.jbudget105181.GUIView;

import it.unicam.cs.pa.jbudget105181.Controller.IController;
import it.unicam.cs.pa.jbudget105181.Controller.MainController;
import it.unicam.cs.pa.jbudget105181.Model.*;
import it.unicam.cs.pa.jbudget105181.Model.Account.AccountType;
import it.unicam.cs.pa.jbudget105181.Model.Account.IAccount;
import it.unicam.cs.pa.jbudget105181.Model.Store.IWriter;
import it.unicam.cs.pa.jbudget105181.Model.Store.JsonReaderJBudget;
import it.unicam.cs.pa.jbudget105181.Model.Store.JsonWriterJBudget;
import it.unicam.cs.pa.jbudget105181.Model.Tag.ITag;
import it.unicam.cs.pa.jbudget105181.Model.Transaction.ITransazione;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * classe che ha la responsabilita' di controllo principale della GUI View
 */
public class GUIController implements Initializable {

    /**
     * controller dell' MVC
     */
    private IController controller;
    /**
     * text field per il nome del tag
     */
    @FXML private TextField tagName;
    /**
     * text field per la descrizione del tag
     */
    @FXML private TextField tagDescription;
    /**
     * tabella dei tag
     */
    @FXML private TableView<ITag> tagTable;
    /**
     * colonna del nome del tag
     */
    @FXML private TableColumn<ITag,String> tagNameColumn;
    /**
     * colonna della descrizione del tag
     */
    @FXML private TableColumn<ITag,String> tagDescriptionColumn;
    /**
     * tabella delle transazioni
     */
    @FXML private TableView<ITransazione> transTable;
    /**
     * colonna ID della transazione
     */
    @FXML private TableColumn<ITransazione,Integer> transIDColumn;
    /**
     * colonna amount della transazione
     */
    @FXML private TableColumn<ITransazione,Double> transAmountColumn;
    /**
     * colonna numero movimenti della transazione
     */
    @FXML private TableColumn<ITransazione,Integer> transNumMovColumn;
    /**
     * colonna data della transazione
     */
    @FXML private TableColumn<ITransazione, LocalDate> transDateColumn;
    /**
     * colonna descrizione della transazione
     */
    @FXML private TableColumn<ITransazione, String> transDescriptionColumn;
    /**
     * label per messaggi di fallimento tag
     */
    @FXML private Label FailedOperationTag;
    /**
     * text field per il nome dell'account
     */
    @FXML private TextField nameAccount;
    /**
     * text field per il bilancio dell'account
     */
    @FXML private TextField balanceAccount;
    /**
     * text field per la descrizione dell'account
     */
    @FXML private TextField descriptionAccount;
    /**
     * tabella degli account
     */
    @FXML private TableView<IAccount> accountTable;
    /**
     * colonna ID della transazione
     */
    @FXML private TableColumn<IAccount,Integer> accountIDColumn;
    /**
     * colonna nome della transazione
     */
    @FXML private TableColumn<IAccount,String> accountNameColumn;
    /**
     * colonna tipo di account della transazione
     */
    @FXML private TableColumn<IAccount, AccountType> accountTypeColumn;
    /**
     * colonna amount della transazione
     */
    @FXML private TableColumn<IAccount,Double> accountAmountColumn;
    /**
     * colonna descrzione della transazione
     */
    @FXML private TableColumn<IAccount,String> accountDescriptionColumn;
    /**
     * choice box per il tipo di account
     */
    @FXML private ChoiceBox<AccountType> accountType;
    /**
     * bottone per modificare l'account
     */
    @FXML private Button modifyAccButton;
    /**
     * bottone per aggiungere un movimento
     */
    @FXML private Button buttonAddMovement;
    /**
     * titled pane per modificare la transazione
     */
    @FXML private TitledPane modifyTransactionMenu;
    /**
     * nuova data transazione
     */
    @FXML private DatePicker dataTransactionNew;
    /**
     * nuova descrizione transazione
     */
    @FXML private TextArea descriptionTransactionNew;
    /**
     * bottone per modificare la transazione
     */
    @FXML private Button modifyTransactionButton;
    /**
     * ID transazione
     */
    private int idTransaction=-1;
    /**
     * ID account
     */
    private int idAcc=-1;
    /**
     * lista degli account
     */
    private ObservableList<IAccount> lAccount;
    /**
     * lista dei tag
     */
    private ObservableList<ITag> lTags;
    /**
     * lista delle transazioni
     */
    private ObservableList<ITransazione> lTransactions;
    /**
     * tipo di account
     */
    private ObservableList<AccountType> typeAccMenu;
    /**
     * text field per l'amount del budget
     */
    @FXML private TextField budgetAmount;
    /**
     * lista dei budget
     */
    private ObservableList<Map.Entry<ITag,Double>> lBudget;

    @FXML private Label errorAccountLabel;
    /**
     * tabella di budget
     */
    @FXML private TableView<Map.Entry<ITag,Double>> tableBudget;
    /**
     * colonna tag del budget
     */
    @FXML private TableColumn<Map.Entry<ITag,Double>, ITag> columnBudgetTag;
    /**
     * colonna amount del budget
     */
    @FXML private TableColumn<Map.Entry<ITag,Double>,Double> columnBudgetAmount;
    /**
     * choice box tag
     */
    @FXML private ChoiceBox<ITag> tagBudget;
    /**
     * label per report
     */
    @FXML private Label resultReport;



    /**
     * metodo per inizializzare le variabili
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inizializeList();
        modifyAccButton.setDisable(true);
        modifyTransactionMenu.setExpanded(false);
        modifyTransactionButton.setDisable(false);
        errorAccountLabel.setVisible(false);
        inizializeTypeAccount();
        inizializeBudgetTag();
        refreshList();
    }

    /**
     * costruttore di GUIController
     */
    public GUIController(){
        controller = new MainController(); // TODO doppio costruttore
    }

    /**
     * costruttore di GUIController
     * @param controller
     */
    public GUIController(IController controller){
        this.controller=controller;
    }

    /**
     * metodo per impostare i tipi di account
     */
    private void inizializeTypeAccount(){
        typeAccMenu.addAll(AccountType.values());
        accountType.setItems(typeAccMenu);
    }

    /**
     * metodo che ha la responsabilita' di inizializzare tutte le liste;
     */
    private void inizializeList(){
        lTags = FXCollections.observableArrayList();
        lTransactions = FXCollections.observableArrayList();
        lAccount = FXCollections.observableArrayList();
        lBudget=  FXCollections.observableArrayList();
        typeAccMenu= FXCollections.observableArrayList();
    }
    /**
     * metodo per impostare i tag
     */
    private void inizializeBudgetTag(){
        tagBudget.setItems(lTags);
    }

    /**
     * Metodo per aprire una nuova finestra
     * @param title
     * @param fileFXML
     * @param controllerFXML
     */
    public void openWindow(String title, String fileFXML,ControllerFXML controllerFXML){
        try {
            Stage stage = (Stage) modifyAccButton.getScene().getWindow();
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

    /**
     * metodo per aggiornare la tabella degli account
     */
    private void updateAccount(){
        lAccount.removeAll(lAccount);
        lAccount.addAll(controller.getAccount());
        accountTable.setItems(lAccount);
        this.accountIDColumn.setCellValueFactory
                (cellData -> new SimpleObjectProperty<>(cellData.getValue().getID()));
        this.accountAmountColumn.setCellValueFactory
                (cellData -> new SimpleObjectProperty<>(cellData.getValue().getBalanceOfAccount()));

        this.accountNameColumn.setCellValueFactory
                (cellData -> new SimpleObjectProperty<>(cellData.getValue().getNameAccount()));
        this.accountTypeColumn.setCellValueFactory
                (cellData -> new SimpleObjectProperty<>(cellData.getValue().getTypeAccount()));
        this.accountDescriptionColumn.setCellValueFactory
                (cellData -> new SimpleObjectProperty<>(cellData.getValue().getDescriptionAccount()));
        this.accountTable.refresh();
    }

    /**
     * metodo per aprire la finisetra ed aggiungere una transazione
     */
    public void addTransaction(){
        openWindow("Add Transaction", "/FXMLAddTransaction.fxml", new ControllerAddTransaction(controller));
    }

    /**
     * metodo per aggiungere la transazione
     */
    public void addAccount(){
        try{
            errorAccountLabel.setVisible(false);
            if(controller.alreadyExistNameAccount(nameAccount.getText()) && accountType.getValue()!=null){
                controller.addAccount(IFactory.generateAccount(controller.generateIDAccount(),nameAccount.getText(),descriptionAccount.getText(),accountType.getValue(),Double.valueOf(balanceAccount.getText())));
            }
            else{
                errorAccountLabel.setText("This account already exits change name!");
                errorAccountLabel.setVisible(true);
            }
        }catch(Exception e){
            setErrorAccountLabel();
        }finally{
            clearAccountOption();
        }
    }

    /**
     * metodo che ha la responsabilita' di ripulire i campi di inserimento dell'account
     */
    private void clearAccountOption(){
        modifyAccButton.setDisable(true);
        nameAccount.clear();
        balanceAccount.clear();
        accountType.setValue(null);
        descriptionAccount.clear();
        updateAccount();
    }

    /**
     * metodo per selezionare un account
     */
    public void selectAccount(){
        IAccount account =accountTable.getSelectionModel().getSelectedItem();
        if(account != null && !accountTable.getItems().isEmpty()){
            nameAccount.setText(account.getNameAccount());
            descriptionAccount.setText(account.getDescriptionAccount());
            balanceAccount.setText(Double.toString(account.getBalanceOfAccount()));
            accountType.setValue(account.getTypeAccount());
            idAcc=account.getID();
            modifyAccButton.setDisable(false);
        }
    }

    /**
     * metodo per modificare un account
     */
    public void modifyAccount(){
        if(!accountTable.getItems().isEmpty()){
            try{

                errorAccountLabel.setVisible(false);
                controller.modifyAccount(idAcc,nameAccount.getText(),descriptionAccount.getText(),
                        accountType.getValue(),Double.valueOf(balanceAccount.getText()));
            }catch(Exception e){
                setErrorAccountLabel();
            }finally{
                clearAccountOption();
            }

        }

    }

    /**
     * metodo per eliminare un account
     */
    public void deleteAccount(){
        try {
            errorAccountLabel.setVisible(false);
            IAccount account = accountTable.getSelectionModel().getSelectedItem();
            if (account != null && !accountTable.getItems().isEmpty()) {
                controller.removeAccount(account);
                updateAccount();
                modifyAccButton.setDisable(true);
            }
        }catch(Exception e){
            setErrorAccountLabel();
        }finally {
            clearAccountOption();
        }
    }

    /**
     * inserisce messaggio di errore in caso di eccezione sull'account
     */
    private void setErrorAccountLabel(){
        errorAccountLabel.setText("Wrong data inserted!");
        errorAccountLabel.setVisible(true);
    }
    /**
     * metodo per aprire la finestra ed aggiungere un movimento
     */
    public void addMovement() {
        ITransazione transaction = transTable.getSelectionModel().getSelectedItem();
        if(transaction != null && !controller.getAccount().isEmpty()) {
            openWindow("Add Movement", "/FXMLAddMovement.fxml", new ControllerAddMovement(controller,transaction));
        }
    }

    /**
     * metodo per aggiorane la tabella delle transazioni
     */
    private void updateTransaction(){
        lTransactions.removeAll(lTransactions);
        lTransactions.addAll(controller.getTransaction());
        transTable.setItems(lTransactions);
        this.transIDColumn.setCellValueFactory
                (cellData -> new SimpleObjectProperty<>(cellData.getValue().getID()));
        this.transAmountColumn.setCellValueFactory
                (cellData -> new SimpleObjectProperty<>(cellData.getValue().getTotalAmount()));

        this.transNumMovColumn.setCellValueFactory
                (cellData -> new SimpleObjectProperty<>(cellData.getValue().getNumMov()));
        this.transDateColumn.setCellValueFactory
                (cellData -> new SimpleObjectProperty<>(cellData.getValue().getData()));
        this.transDescriptionColumn.setCellValueFactory
                (cellData -> new SimpleObjectProperty<>(cellData.getValue().getDescription()));
        this.transTable.refresh();
    }

    /**
     * metodo per eliminare un tag
     */
    public void deleteTag() {
        FailedOperationTag.setText("");
        ITag tag = tagTable.getSelectionModel().getSelectedItem();
        if(!tagTable.getItems().isEmpty() && tag != null) {
            controller.removeTag(tag);
            updateTags();
            updateBudget();
        }
    }

    /**
     * metodo per aggiungere un tag
     */
    public void addTag() {
        try{
            FailedOperationTag.setText("");

            if(this.controller.alreadyExistTag(IFactory.generateTag(tagName.getText(),tagDescription.getText()))) {
                this.controller.addTag(IFactory.generateTag(tagName.getText(),tagDescription.getText()));
                updateTags();
            }else FailedOperationTag.setText("Failed Operation! If tag alreasy exists unaddable!");
        }catch (Exception e){
            FailedOperationTag.setText(e.getMessage());
        }finally {
            tagName.clear();
            tagDescription.clear();
        }
    }

    /**
     * metodo per aggiornare la tabella dei tag
     */
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

    /**
     * metodo per visualizzare i tag della transazione
     */
    public void viewTagsTransaction(){
        try{
            if(transTable.getSelectionModel().getSelectedItem() != null) {
                Stage stage = new Stage();
                stage.setTitle("Tag Transaction");
                stage.initModality(Modality.APPLICATION_MODAL);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLViewTags.fxml"));
                loader.setController(new ControllerViewTag(transTable.getSelectionModel().getSelectedItem()));
                stage.setScene(new Scene(loader.load(), 345, 400));
                stage.show();
            }
        }catch(Exception e){

        }
    }

    /**
     * metodo per rimuovere una transazione
     */
    public void removeTransaction(){
        if(transTable.getSelectionModel().getSelectedItem() != null){
            controller.removeTransaction(transTable.getSelectionModel().getSelectedItem());
            updateTransaction();
            updateAccount();
        }
    }
    /**
     * metodo per aprire una finestra e visualizzare tutti i movimenti associati alla transazione
     */
    public void viewMovementTransaction(){
        try{
            if(transTable.getSelectionModel().getSelectedItem() != null) {
                Stage stage = new Stage();
                stage.setTitle("Movement Transaction");
                stage.initModality(Modality.APPLICATION_MODAL);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLViewMovement.fxml"));
                ControllerViewMovement c= new ControllerViewMovement(transTable.getSelectionModel().getSelectedItem(),controller);
                loader.setController(c);
                stage.setScene(new Scene(loader.load(), 640, 440));
                stage.show();
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            updateTransaction();
            updateAccount();
        }
    }

    /**
     * metodo per selezionare una transazione
     */
    public void selectTransaction(){
        try {
            if(transTable.getSelectionModel().getSelectedItem() != null){
                modifyTransactionMenu.setExpanded(true);
                descriptionTransactionNew.setText(transTable.getSelectionModel().getSelectedItem().getDescription());
                idTransaction=transTable.getSelectionModel().getSelectedItem().getID();
                modifyTransactionButton.setDisable(false);
            }
        }catch(Exception e){

        }
    }

    /**
     * metodo per modificare una transazione
     */
    public void modifyTransaction(){
        try{
            controller.modifyTransactiond(idTransaction,transTable.getSelectionModel().getSelectedItem(),
                    descriptionTransactionNew.getText());
            updateTransaction();
        }catch (Exception e){

        }finally{
            modifyTransactionMenu.setExpanded(false);
            descriptionTransactionNew.clear();
        }
    }

    /**
     * writer
     */
    private IWriter writer = null;

    /**
     * metodo per salvare su file
     */
    private void autoSave(){
        try {
            this.controller.save(this.writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * metodo per aprire un file
     */
    @FXML
    public void open(){
        try {
            String path = createFileChooser().showOpenDialog(new Stage()).getAbsolutePath();
            this.controller.read(new JsonReaderJBudget(path));
            writer = new JsonWriterJBudget(path);
        }catch (Exception e){

        }finally{
            refreshList();
        }
    }

    /**
     * metodo per salvare un file
     */
    @FXML
    public void save(){
        try {
            String path = createFileChooser().showSaveDialog(new Stage()).getAbsolutePath();
            this.writer = new JsonWriterJBudget(path);
            this.controller.save(writer);
            autoSave();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

   private FileChooser createFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters()
                .addAll(new FileChooser.ExtensionFilter("Json File", "*.json"));
        fileChooser.setInitialFileName("JBudget");
        return fileChooser;
    }

    /**
     * metodo per aggiungere un budget
     */
    public void addBudget(){
        try{
            if(tagBudget.getValue()!= null){
                controller.addBudget(tagBudget.getValue(),Double.parseDouble(budgetAmount.getText()));
            }
        }catch(Exception e){
            resultReport.setTextFill(Color.RED);
            resultReport.setText("Error: Data Incorrectly!");
        }finally{
            updateBudget();
            tagBudget.setValue(null);
            budgetAmount.clear();
        }
    }

    /**
     * metodo per eliminare un budget
     */
    public void deleteBudget(){
        if(tableBudget.getSelectionModel().getSelectedItem()!= null) {
            controller.removeBudget(tableBudget.getSelectionModel().getSelectedItem().getKey());
            updateBudget();
        }
    }


    /**
     * metodo per aggiornare la tabella dei budget
     */
    private void updateBudget(){
        lBudget.clear();
        lBudget.addAll(controller.getBudgetTag().getBudgetMap().entrySet());
        tableBudget.setItems(lBudget);
        this.columnBudgetTag.setCellValueFactory
                (cellData -> new SimpleObjectProperty<>(cellData.getValue().getKey()));
        this.columnBudgetAmount.setCellValueFactory
                (cellData -> new SimpleObjectProperty<>(cellData.getValue().getValue()));
        tableBudget.refresh();
    }

    /**
     * metodo per visualizzare il report
     */
    public void viewResult(){
        if(tableBudget.getSelectionModel().getSelectedItem()!= null){
            Double res=controller.getBudgetReport(tableBudget.getSelectionModel().getSelectedItem().getKey());
            if(res > 0){
                resultReport.setTextFill(Color.GREEN);
                resultReport.setText("You are yet in budget! You have :"+res);
            }
            else{
                resultReport.setTextFill(Color.RED);
                resultReport.setText("You are out of budget of :"+res);
            }
        }
    }


    /**
     * Aggiorna tutte le tabelle
     */
    public void refreshList(){
        updateBudget();
        updateTransaction();
        updateAccount();
        updateTags();
    }
}
