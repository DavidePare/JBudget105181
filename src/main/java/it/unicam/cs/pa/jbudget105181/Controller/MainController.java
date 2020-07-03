package it.unicam.cs.pa.jbudget105181.Controller;

import it.unicam.cs.pa.jbudget105181.Model.*;

import java.util.ArrayList;
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
                    if (m.getAccount().getIDAccount() == acc.get(0).getIDAccount()) {
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
        if(ledger.getAccounts().isEmpty()) return 1;
        int max=ledger.getAccounts().stream().mapToInt(t-> t.getIDAccount()).max().orElseThrow(NoSuchElementException::new);
        return max+1;
    }
}
