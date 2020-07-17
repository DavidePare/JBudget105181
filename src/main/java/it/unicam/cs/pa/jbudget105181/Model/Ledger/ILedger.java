package it.unicam.cs.pa.jbudget105181.Model.Ledger;

import it.unicam.cs.pa.jbudget105181.Model.Account.AccountType;
import it.unicam.cs.pa.jbudget105181.Model.Account.IAccount;
import it.unicam.cs.pa.jbudget105181.Model.Budget.IBudget;
import it.unicam.cs.pa.jbudget105181.Model.Movement.IMovement;
import it.unicam.cs.pa.jbudget105181.Model.Tag.ITag;
import it.unicam.cs.pa.jbudget105181.Model.Transaction.ITransazione;
import it.unicam.cs.pa.jbudget105181.Model.IUtility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

/**
 * interfaccia che ha il compito di gestire un ledger.
 * Un ledger è un libro mastro contenente tutte le informazioni dei movimenti,transazioni,
 * account e tag.
 */
public interface ILedger extends Serializable {

	/**
	 * metodo per aggiungere una transazione
	 * @param c
	 */
	void addTransazione(ITransazione c);

	/**
	 * metodo per ottenere tutte le transazioni
	 * @return
	 */
	ArrayList<ITransazione> getAllTransactions();
//	void addTag(String nome,String descrizione);

	/**
	 * metodi per rimuovere tag dato un predicate
	 * @param p
	 * @return
	 */
	boolean removeTag(Predicate<ITag> p);

	/**
	 * metodo per ottenere la lista di tag
	 * @return
	 */
	List<ITag> getTags();

	/**
	 * metodo per ottenere la lista di account
	 * @return
	 */
	List<IAccount> getAccounts();

	/**
	 * metodo per aggiungere un account
	 * @param acc
	 */
	void addAccount(IAccount acc);

	/**
	 * metodo per ottenere il singolo
	 * account dato l'ID
	 * @param id
	 * @return
	 */
	IAccount getAccountForID(int id);

	/**
	 * metodo per aggiungere un tag
	 * @param t
	 */
	void addTag(ITag t);
	//boolean removeTagPredicate(Predicate <ITag> p);

	/**
	 * metodo per ottenere transazioni dato un predicate
	 * @param p
	 * @return
	 */
	List<ITransazione> getTransaction(Predicate<ITransazione> p);

	/**
	 * metodo per rimuovere tag dalla transazione
	 * @param t
	 * @return
	 */
	boolean removeTagTransaction(List<ITag> t);

	/**
	 * metodo per rimuovere transazioni dato un predicate
	 * @param p
	 */
	void removeTransaction(Predicate<ITransazione> p);
//	void removeMovement(Predicate<IMovement> p,int id);

	//<T extends IUtility> T get(Collection<T> collection, int ID);

	/**
	 * metodo per generare ID
	 * @param collection
	 * @param <T>
	 * @return
	 */
	<T extends IUtility> int generateID(Collection<T> collection);

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
	 * metodo per rimuovere un movimento
	 * @param id
	 * @param idMov
	 */
	void removeMovement(int id, int idMov); // TODO non lo usi

	/**
	 * metodo per rimuovere un account
	 * @param account
	 */
	void removeAccount(IAccount account);

	/**
	 * metodo per aggiungere un movimento
	 * @param movement
	 */
	void addMovement(IMovement movement);

	/**
	 * metodi per rimuovere una transazione
	 * @param transazione
	 */
	void removeTransaction(ITransazione transazione);

	/**
	 * metodo per rimuovere un movimento
	 * @param movement
	 */
	void removeMovement(IMovement movement);

	/**
	 * matodo per aggiungere un movimento reteizzato
	 * @param lTransaction
	 * @param movement
	 */
	void addRateMovement(List<ITransazione> lTransaction, IMovement movement);

	/**
	 * metodo per aggiungere un budget
	 * @param tag
	 * @param value
	 */
	void addBudgetLedger(ITag tag, Double value); // TODO cos'è un budgetLedger?

	/**
	 * metodo per ottenere il budget
	 * @return
	 */
	IBudget<ITag> getBudget();

	/**
	 * metodo per rimuovere il budget di un tag
	 * @param budget
	 */
	void removeBudget(ITag budget);

	/**
	 * metodo per aggiungere una lista di tag
	 * @param tags
	 */
	void addTags(List<ITag> tags);

	/**
	 * metodo per aggiungere una lista di account
	 * @param accountList
	 */
	void addAccounts(List<IAccount> accountList);

	/**
	 * metodo per aggiungere una lista di transazioni
	 * @param transazionelist
	 */
	void addTransactions(List<ITransazione> transazionelist);
	ITag getATag(String name, String description);
	void setBudget(IBudget budget);
}
