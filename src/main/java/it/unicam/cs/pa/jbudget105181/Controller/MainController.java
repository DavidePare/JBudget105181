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
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MainController implements IController {

    private ILedger ledger;
    private IBudgetReport reporter;

    public MainController() {
        this.ledger = new Ledger();
        this.reporter=new BudgetReport(ledger);
    }

    @Override
    public void addTag(ITag tag){
        ledger.addTag(tag);
    }

    @Override
    public List<ITag> getTags(){
        return ledger.getTags();
    }

    @Override
    public void generateID(){ // TODO a cosa ti serve?

    }

    @Override
    public void addAccount(IAccount account){
        ledger.addAccount(account);
    }

    @Override
    public void removeAccount(IAccount account){
        ledger.removeAccount(account);
    }

    //TODO
    @Override
    public void removeAccount(Predicate<IAccount> p){
        //Sistemare
        List<IAccount> acc=ledger.getAccounts().stream().filter(p).collect(Collectors.toList());
        if(!acc.isEmpty()) {
            for (ITransazione t : ledger.getAllTransactions()) {
                for (IMovement m : t.movements()) {
                    if (m.getAccount().getID() == acc.get(0).getID()) {
                        t.removeMovement(m); //Sistemare
                    }
                }
            }
            this.removeAccount(acc.get(0));
        }
    }

    @Override
    public void removeTag(ITag tag){
        ledger.removeTag(t-> ( t.getNome().compareTo(tag.getNome())==0));
    }

    @Override
    public ArrayList<ITransazione> getTransaction(){
        return ledger.getAllTransactions();
    }

    @Override
    public boolean alreadyExistTag(ITag ricerca) {
        List<ITag> appoggio=new ArrayList<>();
        if(ledger.getTags().isEmpty()) {
            return true;
        }
        ledger.getTags().stream().filter(t -> ( ricerca.getDescription().compareTo(t.getDescription())==0 && ricerca.getNome().compareTo(t.getNome())==0)  ).forEach(t -> appoggio.add(ricerca));
        return appoggio.isEmpty();
    }

    @Override
    public boolean alreadyExistNameAccount(String name){
        return ledger.getAccounts().stream().filter(t -> name.compareTo(t.getNameAccount())==0).count()==0;
    }

    @Override
    public List<IAccount> getAccount(){
        return ledger.getAccounts();
    }


    @Override
    public int generateIDAccount(){
        return ledger.generateID(ledger.getAccounts());
    }

    @Override
    public int generateIDTransaction(){
        return ledger.generateID(ledger.getAllTransactions());
    }

    /*
    *TODO AGGIUSTARE generazione id movimento
    *
     */
    @Override
    public int generateIDMovement(ITransazione transaction){
        return ledger.generateID(transaction.movements());

    }

    @Override
    public void modifyAccount(int accID, String name, String description, AccountType type, Double value){
      /*  ledger.get(ledger.getAccounts(),accID).setName(name);
        ledger.get(ledger.getAccounts(),accID).setDescription(description);
        ledger.get(ledger.getAccounts(),accID).setType(type);
        ledger.get(ledger.getAccounts(),accID).setConto(value);*/
        ledger.modifyAccount(accID,name,description, type,value);
    }

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

    @Override
    public void removeMovement(IMovement movimento){
        ledger.removeMovement(movimento);
    }

    @Override
    public void modifyTransactiond(int idTrans,ITransazione transazione,String description){
        //ledger.getAllTransactions().stream().filter(p-> p.getID()== idTrans).peek(x-> x.setDescription(description)).forEach( t->
          //      t.setData(data));//collect(Collectors.toCollection());
        //transaction.get(1).setData(data);
      //  transazione.setData(data);
        transazione.setDescription(description);

    }

    @Override
    public void addMovementList(ITransazione t,List<IMovement> lMovements){
        List<ITransazione> transazione=ledger.getTransaction(p-> t.getID()==p.getID());
        transazione.get(0).addMovementList(lMovements);
    }

    @Override
    public void addRateMovement(List<ITransazione> lTransaction, IMovement movement){
        ledger.addRateMovement(lTransaction,movement);

    }

    @Override
    public void resetLedger(){
        ledger = new Ledger();
    }

    @Override
    public void read(IReader IReader) throws IOException, ClassNotFoundException {
        if(IReader !=null) {
            this.ledger = IReader.read();
            IReader.close();
        }
    }

    @Override
    public void save(IWriter writer) throws IOException {
        if(writer!=null) {
            writer.write(ledger);
            writer.close();
        }
    }

    @Override
    public void addBudget(ITag tag, Double value){
        this.ledger.addBudgetLedger(tag,value);
    }

    @Override
    public IBudget<ITag> getBudgetTag(){
        return this.ledger.getBudget();
    }

    @Override
    public void removeBudget(ITag t){
        this.ledger.removeBudget(t);
    }

    @Override
    public Double getBudgetReport(ITag t){
        return this.reporter.check(t);
    }
}
