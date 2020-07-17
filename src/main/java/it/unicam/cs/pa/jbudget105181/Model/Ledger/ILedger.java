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

public interface ILedger extends Serializable {
	void addTransazione(ITransazione c);
	
	ArrayList<ITransazione> getAllTransactions();
//	void addTag(String nome,String descrizione);
	boolean removeTag(Predicate<ITag> p);
	List<ITag> getTags();
	List<IAccount> getAccounts();
	void addAccount(IAccount acc);
	IAccount getAccountForID(int id);
	void addTag(ITag t);
	//boolean removeTagPredicate(Predicate <ITag> p);
	List<ITransazione> getTransaction(Predicate<ITransazione> p);
	boolean removeTagTransaction(List<ITag> t);
	void removeTransaction(Predicate<ITransazione> p);
//	void removeMovement(Predicate<IMovement> p,int id);

	//<T extends IUtility> T get(Collection<T> collection, int ID);
	<T extends IUtility> int generateID(Collection<T> collection);
	void modifyAccount(int accID, String name, String description, AccountType type, Double value);
	void removeMovement(int id, int idMov);
	void removeAccount(IAccount account);
	void addMovement(IMovement movement);
	void removeTransaction(ITransazione transazione);
	void removeMovement(IMovement movement);


	void addRateMovement(List<ITransazione> lTransaction, IMovement movement);
	void addBudgetLedger(ITag tag, Double value);
	IBudget<ITag> getBudget();
	void removeBudget(ITag budget);


	void addTags(List<ITag> tags);
	void addAccounts(List<IAccount> accountList);
	void addTransactions(List<ITransazione> transazionelist);
	ITag getATag(String name, String description);
}
