package it.unicam.cs.pa.jbudget105181.Controller;

import it.unicam.cs.pa.jbudget105181.Model.*;
import it.unicam.cs.pa.jbudget105181.Model.Account.AccountType;
import it.unicam.cs.pa.jbudget105181.Model.Account.IAccount;
import it.unicam.cs.pa.jbudget105181.Model.Budget.IBudget;
import it.unicam.cs.pa.jbudget105181.Model.BudgetReport.BudgetReport;
import it.unicam.cs.pa.jbudget105181.Model.BudgetReport.IBudgetReport;
import it.unicam.cs.pa.jbudget105181.Model.Ledger.ILedger;
import it.unicam.cs.pa.jbudget105181.Model.Ledger.Ledger;

import it.unicam.cs.pa.jbudget105181.Model.Store.IReader;
import it.unicam.cs.pa.jbudget105181.Model.Store.IWriter;
import it.unicam.cs.pa.jbudget105181.Model.Tag.ITag;
import it.unicam.cs.pa.jbudget105181.Model.Movement.IMovement;
import it.unicam.cs.pa.jbudget105181.Model.Transaction.ITransazione;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class MainController implements IController {
    /**
     * Ledger del controller
     */
    private ILedger ledger;
    /**
     * Reporter del controller
     */
    private IBudgetReport reporter;

    /**
     * Costruttore del controller
     */
    public MainController() {
        this.ledger = new Ledger();
        this.reporter=new BudgetReport(ledger);
    }

    /**
     * metodo per aggiungere un tag
     * @param tag
     */
    @Override
    public void addTag(ITag tag){
        ledger.addTag(tag);
    }

    /**
     * metodo per ritornare tutta la lista di tag
     * @return
     */
    @Override
    public List<ITag> getTags(){
        return ledger.getTags();
    }


    /**
     * metodo per aggiungere un account
     * @param account
     */
    @Override
    public void addAccount(IAccount account){
        ledger.addAccount(account);
    }

    /**
     * metodo per rimuovre un account
     * @param account
     */
    @Override
    public void removeAccount(IAccount account){
        ledger.removeAccount(account);
    }

    /**
     * metodo per rimuovere un tag
     * @param tag
     */
    @Override
    public void removeTag(ITag tag){
        ledger.removeTag(t-> ( t.getNome().compareTo(tag.getNome())==0));
    }

    /**
     * metodo per ottenere la lista delle transazioni
     * @return
     */
    @Override
    public ArrayList<ITransazione> getTransaction(){
        return ledger.getAllTransactions();
    }

    /**
     * metodo per la ricerca di un tag
     * @param ricerca
     * @return
     */
    @Override
    public boolean alreadyExistTag(ITag ricerca) {
        List<ITag> appoggio=new ArrayList<>();
        if(ledger.getTags().isEmpty()) {
            return true;
        }
        ledger.getTags().stream().filter(t -> ( ricerca.getDescription().compareTo(t.getDescription())==0 && ricerca.getNome().compareTo(t.getNome())==0)  ).forEach(t -> appoggio.add(ricerca));
        return appoggio.isEmpty();
    }
    /**
     * metodo per la ricerca di un account
     * @param name
     * @return
     */
    @Override
    public boolean alreadyExistNameAccount(String name){
        return ledger.getAccounts().stream().filter(t -> name.compareTo(t.getNameAccount())==0).count()==0;
    }

    /**
     * metodo per ottenere la lista degli account
     * @return
     */
    @Override
    public List<IAccount> getAccount(){
        return ledger.getAccounts();
    }


    /**
     * metodo per generare l'ID dell'account
     * @return
     */
    @Override
    public int generateIDAccount(){
        return ledger.generateID(ledger.getAccounts());
    }

    /**
     * metodo per generare l'ID della transazione
     * @return
     */
    @Override
    public int generateIDTransaction(){
        return ledger.generateID(ledger.getAllTransactions());
    }

    /**
     * metodo per generare l'ID del movimento
     * @param transaction
     * @return
     */
    @Override
    public int generateIDMovement(ITransazione transaction){
        return ledger.generateID(transaction.movements());

    }
    /**
     * metodo per modificare un account
     * @param accID
     * @param name
     * @param description
     * @param type
     * @param value
     */
    @Override
    public void modifyAccount(int accID, String name, String description, AccountType type, Double value){
        ledger.modifyAccount(accID,name,description, type,value);
    }
    /**
     * metodo per aggiungere una transazione
     * @param data
     * @param tag
     * @param description
     * @param pagata
     * @return
     */
    @Override
    public ITransazione addTransaction(LocalDate data, List<ITag> tag, String description, boolean pagata){
        int id=generateIDTransaction();
        ledger.addTransazione(IFactory.generateTransaction(id,data,tag,description,pagata));
        return IFactory.generateTransaction(id,data,tag,description,pagata);
    }

    /*
     * metodo che richiama il costruttore per aggiungere il movimento
     */
    @Override
    public void addMovement(IMovement movimento){
        ledger.addMovement(movimento);

    }
    /*
     * metodo che rimuoverà la transazione
     */
    @Override
    public void removeTransaction(ITransazione transazione){
        ledger.removeTransaction(transazione);
    }
    /**
     * metodo che rimuoverà il movimento
     */
    @Override
    public void removeMovement(IMovement movimento){
        ledger.removeMovement(movimento);
    }
    /**
     * metodo per modificare una transazione
     * @param idTrans
     * @param transazione
     * @param description
     */
    @Override
    public void modifyTransactiond(int idTrans,ITransazione transazione,String description){
        transazione.setDescription(description);

    }

    /**
     * metodo per aggiungere una lista di movimenti
     * @param t
     * @param lMovements
     */
    @Override
    public void addMovementList(ITransazione t,List<IMovement> lMovements){
        List<ITransazione> transazione=ledger.getTransaction(p-> t.getID()==p.getID());
        transazione.get(0).addMovementList(lMovements);
    }
    /**
     * metodo per aggiungere un movimento rateizzato
     * @param lTransaction
     * @param movement
     */
    @Override
    public void addRateMovement(List<ITransazione> lTransaction, IMovement movement){
        ledger.addRateMovement(lTransaction,movement);

    }
    /**
     * metodo per resettare il ledger
     */
    @Override
    public void resetLedger(){
        ledger = new Ledger();
    }
    /**
     * metodo per leggere un ledger
     * @param IReader
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Override
    public void read(IReader IReader) throws IOException, ClassNotFoundException {
        if(IReader !=null) {
            this.ledger = IReader.read();
            this.reporter= new BudgetReport(ledger);
            IReader.close();
        }
    }

    /**
     * metodo per salvare un ledger
     * @param writer
     * @throws IOException
     */
    @Override
    public void save(IWriter writer) throws IOException {
        if(writer!=null) {
            writer.write(ledger);
            writer.close();
        }
    }
    /**
     * metodo per aggiungere un budget di un tag
     * @param tag
     * @param value
     */
    @Override
    public void addBudget(ITag tag, Double value){
        this.ledger.addBudgetLedger(tag,value);
    }
    /**
     * metodo per ottenere i budget
     * @return
     */
    @Override
    public IBudget<ITag> getBudgetTag(){
        return this.ledger.getBudget();
    }
    /**
     * metodo per rimuovere un budget di un tag
     * @param t
     */
    @Override
    public void removeBudget(ITag t){
        this.ledger.removeBudget(t);
    }

    /**
     * metodo per ottenere l'amount del budget di
     * uno specifico tag
     * @param t
     * @return
     */
    @Override
    public Double getBudgetReport(ITag t){
        return this.reporter.check(t);
    }
}
