package it.unicam.cs.pa.jbudget105181.Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public interface ILedger{
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
	public void addMovement(IMovement movement);
}
