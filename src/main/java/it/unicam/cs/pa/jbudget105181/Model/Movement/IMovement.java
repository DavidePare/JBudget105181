package it.unicam.cs.pa.jbudget105181.Model.Movement;
import it.unicam.cs.pa.jbudget105181.Model.Account.IAccount;
import it.unicam.cs.pa.jbudget105181.Model.IUtility;
import it.unicam.cs.pa.jbudget105181.Model.Movement.MovementType;
import it.unicam.cs.pa.jbudget105181.Model.Tag.ITag;
import it.unicam.cs.pa.jbudget105181.Model.Transaction.ITransazione;

import java.util.List;

/**
 * interfaccia che ha la responsabilita' di gestire un movimento che
 * verrà associato ad una transazione, ad un account ed un tag.
 */
public interface IMovement extends IUtility {

	/**
	 * metodo per ottenere la descrizione
	 * @return
	 */
	String getDescription();

	/**
	 * metodo per ottenere l'amount
	 * @return
	 */
	double getAmount();

	/**
	 * metodo per ottenere l'account del movimento
	 * @return
	 */
	IAccount getAccount();

	/**
	 * metodo per ottenere la lista dei tag
	 * @return
	 */
	List<ITag> tags();

	/**
	 * metodo per aggiungere un tag
	 * @param t
	 */
	void addTag(ITag t);
	//void removeTag(ITag t);

	/**
	 * metodo per ottenere l'ID della transazione
	 * del movimento
	 * @return
	 */
	int getIDTransazione();

	/**
	 * metodo per ottenere il tipo del movimento
	 * @return
	 */
	MovementType getTipo();
	//LocalDate getDate();
	//void modifyMovement(int ID, String description, );

	/**
	 * metodo per rimuovere tag dalla lista di tag
	 * del movimento
	 * @param t
	 */
	void removeTag(List<ITag> t);

	/**
	 * metodo per ottenere l'ID dell'account
	 * del movimento
	 * @return
	 */
	int getIDAccount();

	/**
	 * metodo per impostare l'ID del movimento
	 * @param idMovement
	 */
	void setID(int idMovement);

	/**
	 * metodo per ottenere la transazione del movimento
	 * @return
	 */
	ITransazione getTransaction();

	/**
	 * metodo per impostare la transazione del movimento
	 * @param transaction
	 */
	void setTransaction(ITransazione transaction);
}
