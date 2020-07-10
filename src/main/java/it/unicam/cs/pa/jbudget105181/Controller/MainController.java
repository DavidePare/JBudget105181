package it.unicam.cs.pa.jbudget105181.Controller;

import it.unicam.cs.pa.jbudget105181.Model.*;
import javafx.scene.input.InputMethodTextRun;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MainController {

    private ILedger ledger;

    public MainController() {
        this.ledger = new Ledger();
    }

    public void addTag(ITag tag){
        ledger.addTag(tag);
    }

    public List<ITag> getTags(){
        return ledger.getTags();
    }
    public void generateID(){

    }
    public void addAccount(IAccount account){
        ledger.addAccount(account);
    }
    public void removeAccount(IAccount account){
        ledger.removeAccount(account);
    }


    //TODO
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

    public void removeTag(ITag tag){
        ledger.removeTag(t-> ( t.getNome().compareTo(tag.getNome())==0));
    }

    public ArrayList<ITransazione> getTransaction(){
        return ledger.getAllTransactions();
    }

    public boolean alreadyExistTag(ITag ricerca) {
        List<ITag> appoggio=new ArrayList<>();
        if(ledger.getTags().isEmpty()) {
            return true;
        }
        ledger.getTags().stream().filter(t -> ( ricerca.getDescription().compareTo(t.getDescription())==0 && ricerca.getNome().compareTo(t.getNome())==0)  ).forEach(t -> appoggio.add(ricerca));
        return appoggio.isEmpty();
    }

    public boolean alreadyExistNameAccount(String name){
        return ledger.getAccounts().stream().filter(t -> name.compareTo(t.getNameAccount())==0).count()==0;
    }

    public List<IAccount> getAccount(){
        return ledger.getAccounts();
    }

    /*
    * This function is used to generate an integer ID for account
     */
    public int generateIDAccount(){
        return ledger.generateID(ledger.getAccounts());
    }

    public int generateIDTransaction(){
        return ledger.generateID(ledger.getAllTransactions());
    }

    /*
    *TODO AGGIUSTARE generazione id movimento
     */
    public int generateIDMovement(ITransazione transaction){
        return ledger.generateID(transaction.movements());

    }
    public void modifyAccount(int accID, String name, String description, AccountType type, Double value){
      /*  ledger.get(ledger.getAccounts(),accID).setName(name);
        ledger.get(ledger.getAccounts(),accID).setDescription(description);
        ledger.get(ledger.getAccounts(),accID).setType(type);
        ledger.get(ledger.getAccounts(),accID).setConto(value);*/
        ledger.modifyAccount(accID,name,description, type,value);
    }
    public ITransazione addTransaction(LocalDate data,List<ITag> tag,String description,boolean pagata){
        int id=generateIDTransaction();
        ITransazione t= new Transazione(id,data,tag,description,pagata);
        ledger.addTransazione(t);
        return t;
    }

    /*
     * metodo che richiama il costruttore per aggiungere il movimento
     */
    public void addMovement(IMovement movimento){
        ledger.addMovement(movimento);
    }
    /*
     * metodo che rimuoverà la transazione
     */
    public void removeTransaction(ITransazione transazione){
        ledger.removeTransaction(transazione);
    }
    public void removeMovement(IMovement movimento){
        ledger.removeMovement(movimento);
    }
    public void modifyTransactiond(int idTrans,ITransazione transazione, LocalDate data , String description){
        //ledger.getAllTransactions().stream().filter(p-> p.getID()== idTrans).peek(x-> x.setDescription(description)).forEach( t->
          //      t.setData(data));//collect(Collectors.toCollection());
        //transaction.get(1).setData(data);
        transazione.setData(data);
        transazione.setDescription(description);
    }

    public void addMovementList(ITransazione t,List<IMovement> lMovements){
        List<ITransazione> transazione=ledger.getTransaction(p-> t.getID()==p.getID());
        transazione.get(0).addMovementList(lMovements);
    }
    public void addRateMovement(List<ITransazione> lTransaction, IMovement movement){
        ledger.addRateMovement(lTransaction,movement);

    }
}
