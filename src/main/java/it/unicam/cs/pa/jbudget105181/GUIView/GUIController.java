package it.unicam.cs.pa.jbudget105181.GUIView;

import it.unicam.cs.pa.jbudget105181.Controller.MainController;
import it.unicam.cs.pa.jbudget105181.Model.*;
import it.unicam.cs.pa.jbudget105181.Model.Account.AccountType;
import it.unicam.cs.pa.jbudget105181.Model.Account.IAccount;
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

public class GUIController implements Initializable {


  //  private GeneratorID<> generatore;
    private MainController controller;
    @FXML private TextField tagName;
    @FXML private TextField tagDescription;
    @FXML private TableView<ITag> tagTable;
    @FXML private TableColumn<ITag,String> tagNameColumn;
    @FXML private TableColumn<ITag,String> tagDescriptionColumn;



    @FXML private TableView<ITransazione> transTable;
    @FXML private TableColumn<ITransazione,Integer> transIDColumn;
    @FXML private TableColumn<ITransazione,Double> transAmountColumn;
    @FXML private TableColumn<ITransazione,Integer> transNumMovColumn;
    @FXML private TableColumn<ITransazione, LocalDate> transDateColumn;
    @FXML private TableColumn<ITransazione, String> transDescriptionColumn;


    @FXML private Label FailedOperationTag;

    @FXML private TextField nameAccount;
    @FXML private TextField balanceAccount;
    @FXML private TextField descriptionAccount;
    @FXML private TableView<IAccount> accountTable;
    @FXML private TableColumn<IAccount,Integer> accountIDColumn;
    @FXML private TableColumn<IAccount,String> accountNameColumn;
    @FXML private TableColumn<IAccount, AccountType> accountTypeColumn;
    @FXML private TableColumn<IAccount,Double> accountAmountColumn;
    @FXML private TableColumn<IAccount,String> accountDescriptionColumn;
    @FXML private ChoiceBox<AccountType> accountType;
    @FXML private Button modifyAccButton;
    @FXML private Button buttonAddMovement;

    @FXML private TitledPane modifyTransactionMenu;
    @FXML private DatePicker dataTransactionNew;
    @FXML private TextArea descriptionTransactionNew;
    @FXML private Button modifyTransactionButton;
    private int idTransaction=-1;
    private int idAcc=-1;
    private ObservableList<IAccount> lAccount;
    private ObservableList<ITag> lTags;
    private ObservableList<ITransazione> lTransactions;
    private ObservableList<AccountType> typeAccMenu;

    /* Budget Tag*/
    @FXML private TextField budgetAmount;
    private ObservableList<Map.Entry<ITag,Double>> lBudget;
  /*  @FXML private TableView<IBudget> tableBudget;
    @FXML private TableColumn<IBudget,String> columnBudgetTag;
    @FXML private TableColumn<IBudget,Double> columnBudgetAmount;*/
    @FXML private TableView<Map.Entry<ITag,Double>> tableBudget;
    @FXML private TableColumn<Map.Entry<ITag,Double>, ITag> columnBudgetTag;
    @FXML private TableColumn<Map.Entry<ITag,Double>,Double> columnBudgetAmount;
    @FXML private ChoiceBox<ITag> tagBudget;
    @FXML private Label resultReport;


    @FXML private PieChart graphPieBudget; // eliminare


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //controller = new MainController();
        lTags = FXCollections.observableArrayList();
        lTransactions = FXCollections.observableArrayList();
        lAccount = FXCollections.observableArrayList();
        lBudget=  FXCollections.observableArrayList();
        typeAccMenu= FXCollections.observableArrayList();
        modifyAccButton.setDisable(true);
        modifyTransactionMenu.setExpanded(false);
        modifyTransactionButton.setDisable(false);
        inizializeTypeAccount();
        inizializeBudgetTag();
        updateTags();
        updateTransaction();
        updateAccount();
        updateBudget();
    }
    public GUIController(){
        controller = new MainController();
    }
    public GUIController(MainController controller){
        this.controller=controller;
    }
    private void inizializeTypeAccount(){
        typeAccMenu.addAll(AccountType.values());
        accountType.setItems(typeAccMenu);
    }
    private void inizializeBudgetTag(){
        tagBudget.setItems(lTags);
    }
    public void openWindow(String title, String fileFXML,ControllerFXML controllerFXML){
        try {
            //Stage stage = new Stage();

            //Invece di modifyAccButton sarebbe meglio aggiungere un qualcosa di più specifico
            Stage stage = (Stage) modifyAccButton.getScene().getWindow();
            stage.hide();
            stage.setTitle(title);
            //stage.initModality(Modality.APPLICATION_MODAL);
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fileFXML));
            loader.setController(controllerFXML);
            stage.setScene(new Scene(loader.load(), 800, 400));
            stage.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    public void addTransaction(){
        openWindow("Add Transaction", "/FXMLAddTransaction.fxml", new ControllerAddTransaction(controller));
    }

    public void addAccount(){
        try{
            if(controller.alreadyExistNameAccount(nameAccount.getText()) && accountType.getValue()!=null){
                controller.addAccount(IFactory.generateAccount(controller.generateIDAccount(),nameAccount.getText(),descriptionAccount.getText(),accountType.getValue(),Double.valueOf(balanceAccount.getText())));
            }
        }catch(Exception e){
        }finally{
            modifyAccButton.setDisable(true);
            nameAccount.clear();
            balanceAccount.clear();
            accountType.setValue(null);
            descriptionAccount.clear();
            updateAccount();
        }
    }
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
    public void modifyAccount(){
        if(!accountTable.getItems().isEmpty()){
            //IAccount newAcc= new Account(idAcc,nameAccount.getText(),descriptionAccount.getText(),accountType.getValue(),Double.valueOf(balanceAccount.getText()));
            try{
              /*  IAccount account= new Account(idAcc,nameAccount.getText(),descriptionAccount.getText(),accountType.getValue(),Double.valueOf(balanceAccount.getText()));
                controller.removeAccount(t-> t.getID()==idAcc);
                controller.addAccount(account);*/
                controller.modifyAccount(idAcc,nameAccount.getText(),descriptionAccount.getText(),
                        accountType.getValue(),Double.valueOf(balanceAccount.getText()));
            }catch(Exception e){

            }finally{
                modifyAccButton.setDisable(true);
                nameAccount.clear();
                balanceAccount.clear();
                accountType.setValue(null);
                descriptionAccount.clear();
                updateAccount();
            }

        }

    }
    
    public void deleteAccount(){
       // try {
            IAccount account = accountTable.getSelectionModel().getSelectedItem();
            if (account != null && !accountTable.getItems().isEmpty()) {
                controller.removeAccount(account);
                updateAccount();
                modifyAccButton.setDisable(true);
            }
    /*    }catch(Exception e){
        }finally{
            modifyAccButton.setDisable(true);
            nameAccount.clear();
            balanceAccount.clear();
            accountType.setValue(null);
            descriptionAccount.clear();*/
     //       updateAccount();
   //     }
    }

    /*
     * Nel caso è stata selezionata una transazione ed esiste almeno un account è possibile aggiungere un movimento
     */
    public void addMovement() {

        ITransazione transaction = transTable.getSelectionModel().getSelectedItem();
        if(transaction != null && !controller.getAccount().isEmpty()) {
            openWindow("Add Movement", "/FXMLAddMovement.fxml", new ControllerAddMovement(controller,transaction));
        }
    }

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

    public void deleteTag() {
        FailedOperationTag.setText("");
        ITag tag = tagTable.getSelectionModel().getSelectedItem();
        if(!tagTable.getItems().isEmpty() && tag != null) {
            controller.removeTag(tag);
            updateTags();
        }
    }

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

    public void removeTransaction(){
        if(transTable.getSelectionModel().getSelectedItem() != null){
            controller.removeTransaction(transTable.getSelectionModel().getSelectedItem());
            updateTransaction();
            updateAccount();
        }
    }
    /*
     * Aprirà una finestra dove sarranno mostrati tutti i movimenti associati alla transazione selezionata.
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

    private IWriter writer = null;

    private void autoSave(){
        try {
            this.controller.save(this.writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void open(){
        try {
            String path = createFileChooser().showOpenDialog(new Stage()).getAbsolutePath();
            this.controller.read(new TxtIReader(path));
            writer = new TxtWriter(path);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void save(){
        try {
            String path = createFileChooser().showSaveDialog(new Stage()).getAbsolutePath();
            this.writer = new TxtWriter(path);
            autoSave();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private FileChooser createFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters()
                .addAll(new FileChooser.ExtensionFilter("Txt Files", "*.txt"));
        fileChooser.setInitialFileName("JBudget");
        return fileChooser;
    }



    public void addBudget(){
        try{
            if(tagBudget.getValue()!= null){
                controller.addBudget(tagBudget.getValue(),Double.parseDouble(budgetAmount.getText()));
            }
        }catch(Exception e){
            // TODO print messaggio di errore
        }finally{
            updateBudget();
        }
    }
    public void deleteBudget(){
        if(tableBudget.getSelectionModel().getSelectedItem()!= null) {
            controller.removeBudget(tableBudget.getSelectionModel().getSelectedItem().getKey());
            updateBudget();
        }
    }
    public void viewGraphBudget(){
        graphPieBudget.setVisible(true);
    }
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
}
