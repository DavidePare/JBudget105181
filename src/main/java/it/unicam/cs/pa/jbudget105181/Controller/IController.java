package it.unicam.cs.pa.jbudget105181.Controller;

import it.unicam.cs.pa.jbudget105181.Model.Account.AccountType;
import it.unicam.cs.pa.jbudget105181.Model.Account.IAccount;
import it.unicam.cs.pa.jbudget105181.Model.Budget.IBudget;
import it.unicam.cs.pa.jbudget105181.Model.Movement.IMovement;
import it.unicam.cs.pa.jbudget105181.Model.Store.IReader;
import it.unicam.cs.pa.jbudget105181.Model.Store.IWriter;
import it.unicam.cs.pa.jbudget105181.Model.Tag.ITag;
import it.unicam.cs.pa.jbudget105181.Model.Transaction.ITransazione;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * interfaccia che ha la responsabilita' di fare da Controller del modello
 * MVC e deve coordinare le varie componenti e funzionalità dell'applicazione.
 */
public interface IController {

    /**
     * metodo per aggiungere un tag
     * @param tag
     */
    void addTag(ITag tag);

    /**
     * metodo per ritornare tutta la lista di tag
     * @return
     */
    List<ITag> getTags();

    /**
     * metodo per generare l'ID
     */
    void generateID(); // TODO a cosa ti serve?

    /**
     * metodo per aggiungere un account
     * @param account
     */
    void addAccount(IAccount account);

    /**
     * metodo per rimuovre un account
     * @param account
     */
    void removeAccount(IAccount account);

    /**
     * metodo per rimuovere un account passato un predicate
     * @param p
     */
    void removeAccount(Predicate<IAccount> p); // TODO non lo utilizzi

    /**
     * metodo per rimuovere un tag
     * @param tag
     */
    void removeTag(ITag tag);

    /**
     * metodo per ottenere la lista delle transazioni
     * @return
     */
    ArrayList<ITransazione> getTransaction();

    /**
     * metodo per la ricerca di un tag
     * @param ricerca
     * @return
     */
    boolean alreadyExistTag(ITag ricerca);

    /**
     * metodo per la ricerca di un account
     * @param name
     * @return
     */
    boolean alreadyExistNameAccount(String name);

    /**
     * metodo per ottenere la lista degli account
     * @return
     */
    List<IAccount> getAccount();

    /**
     * metodo per generare l'ID dell'account
     * @return
     */
    int generateIDAccount();

    /**
     * metodo per generare l'ID della transazione
     * @return
     */
    int generateIDTransaction();

    /**
     * metodo per generare l'ID del movimento
     * @param transaction
     * @return
     */
    int generateIDMovement(ITransazione transaction);

    /**
     * metodo per modificare un account
     * @param accID
     * @param name
     * @param description
     * @param type
     * @param value
     */
    void modifyAccount(int accID, String name, String description, AccountType type, Double value);

    /**
     * metodo per aggiungere una transazione
     * @param data
     * @param tag
     * @param description
     * @param pagata
     * @return
     */
    ITransazione addTransaction(LocalDate data, List<ITag> tag, String description, boolean pagata);

    /**
     * metodo per aggiungere un movimento
     * @param movimento
     */
    void addMovement(IMovement movimento);

    /**
     * metodo per rimuovere una transazione
     * @param transazione
     */
    void removeTransaction(ITransazione transazione);

    /**
     * metodo per rimuovere un movimento
     * @param movimento
     */
    void removeMovement(IMovement movimento);

    /**
     * metodo per modificare una transazione
     * @param idTrans
     * @param transazione
     * @param description
     */
    void modifyTransactiond(int idTrans,ITransazione transazione,String description);

    /**
     * metodo per aggiungere una lista di movimenti
     * @param t
     * @param lMovements
     */
    void addMovementList(ITransazione t,List<IMovement> lMovements); // TODO non la utilizzi

    /**
     * metodo per aggiungere un movimento rateizzato
     * @param lTransaction
     * @param movement
     */
    void addRateMovement(List<ITransazione> lTransaction, IMovement movement);

    /**
     * metodo per resettare il ledger
     */
    void resetLedger();

    /**
     * metodo per leggere un ledger
     * @param IReader
     * @throws IOException
     * @throws ClassNotFoundException
     */
    void read(IReader IReader) throws IOException, ClassNotFoundException;

    /**
     * metodo per salvare un ledger
     * @param writer
     * @throws IOException
     */
    void save(IWriter writer) throws IOException;

    /**
     * metodo per aggiungere un budget di un tag
     * @param tag
     * @param value
     */
    void addBudget(ITag tag, Double value);

    /**
     * metodo per ottenere i budget
     * @return
     */
    IBudget<ITag> getBudgetTag();

    /**
     * metodo per rimuovere un budget di un tag
     * @param t
     */
    void removeBudget(ITag t);

    /**
     * metodo per ottenere l'amount del budget di
     * uno specifico tag
     * @param t
     * @return
     */
    Double getBudgetReport(ITag t);

}
