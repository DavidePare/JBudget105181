package it.unicam.cs.pa.jbudget105181.GUIView;

import it.unicam.cs.pa.jbudget105181.Controller.ControllerMovimenti;
import it.unicam.cs.pa.jbudget105181.Controller.MainController;
import it.unicam.cs.pa.jbudget105181.Model.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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


    @FXML private Label FailedOperationTag;

    @FXML private TextField nameAccount;
    @FXML private TextField balanceAccount;
    @FXML private TextField descriptionAccount;
    @FXML private TableView<IAccount> accountTable;
    @FXML private TableColumn<IAccount,Integer> accountIDColumn;
    @FXML private TableColumn<IAccount,String> accountNameColumn;
    @FXML private TableColumn<IAccount,AccountType> accountTypeColumn;
    @FXML private TableColumn<IAccount,Double> accountAmountColumn;
    @FXML private TableColumn<IAccount,String> accountDescriptionColumn;
    @FXML private ChoiceBox<AccountType> accountType;
    @FXML private Button modifyAccButton;
    private int idAcc=-1;
    private ObservableList<IAccount> lAccount;
    private ObservableList<ITag> lTags;
    private ObservableList<ITransazione> lTransactions;
    private ObservableList<AccountType> typeAccMenu;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = new MainController();
        lTags = FXCollections.observableArrayList();
        lTransactions = FXCollections.observableArrayList();
        lAccount = FXCollections.observableArrayList();
        typeAccMenu= FXCollections.observableArrayList();
        modifyAccButton.setDisable(true);
        inizializeTypeAccount();
        updateTags();
        updateTransaction();
        updateAccount();
    }
    private void inizializeTypeAccount(){
        typeAccMenu.addAll(AccountType.values());
        accountType.setItems(typeAccMenu);
    }
    public void openWindow(String title, String fileFXML,ControllerFXML controllerFXML){
        try {
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.initModality(Modality.APPLICATION_MODAL);
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
                (cellData -> new SimpleObjectProperty<>(cellData.getValue().getIDAccount()));
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
                IAccount account= new Account(controller.generateIDAccount(),nameAccount.getText(),descriptionAccount.getText(),accountType.getValue(),Double.valueOf(balanceAccount.getText()));
                controller.addAccount(account);
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
            idAcc=account.getIDAccount();
            modifyAccButton.setDisable(false);
        }
    }
    public void modifyAccount(){
        if(!accountTable.getItems().isEmpty()){
            IAccount newAcc= new Account(idAcc,nameAccount.getText(),descriptionAccount.getText(),accountType.getValue(),Double.valueOf(balanceAccount.getText()));
            try{
                IAccount account= new Account(idAcc,nameAccount.getText(),descriptionAccount.getText(),accountType.getValue(),Double.valueOf(balanceAccount.getText()));
                controller.removeAccount(t-> t.getIDAccount()==idAcc);
                controller.addAccount(account);
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

    public void addMovement() {
        if(!controller.getTransaction().isEmpty()) {
            openWindow("Add Movement", "/FXMLAddMovement.fxml", new ControllerAddMovement(controller));
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
            if(!(tagName.getText().equals(""))) {
                ITag tag = new Tag(tagName.getText(), tagDescription.getText());
                if(this.controller.alreadyExistTag(tag)) {
                    this.controller.addTag(tag);
                    updateTags();
                }else FailedOperationTag.setText("Failed Operation! If tag alreasy exists unaddable!");
            }
        }catch (Exception e){

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
}
