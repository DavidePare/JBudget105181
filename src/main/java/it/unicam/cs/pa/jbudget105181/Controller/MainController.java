package it.unicam.cs.pa.jbudget105181.Controller;

import it.unicam.cs.pa.jbudget105181.Model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
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
    /*    Collection<ITransazione> collect = ledger.getAllTransactions().stream().filter(t -> t.getID() == transaction.getID()).collect(Collectors.toCollection());
        return ledger.generateID();*/
        return 1;
    }
    public void modifyAccount(int accID, String name, String description, AccountType type, Double value){
      /*  ledger.get(ledger.getAccounts(),accID).setName(name);
        ledger.get(ledger.getAccounts(),accID).setDescription(description);
        ledger.get(ledger.getAccounts(),accID).setType(type);
        ledger.get(ledger.getAccounts(),accID).setConto(value);*/
        ledger.modifyAccount(accID,name,description, type,value);
    }
    public void addTransaction(LocalDate data,List<ITag> tag){
        int id=generateIDTransaction();
        ITransazione t= new Transazione(id,data,tag,false);
        ledger.addTransazione(t);
    }

    /*
     * metodo che richiama il costruttore per aggiungere il movimento
     */
    public void addMovement(IMovement movimento){
        ledger.addMovement(movimento);
    }
}
