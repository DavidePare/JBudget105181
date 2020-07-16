package it.unicam.cs.pa.jbudget105181.Model.Account;

import it.unicam.cs.pa.jbudget105181.Model.Movement.IMovement;
import it.unicam.cs.pa.jbudget105181.Model.IUtility;

import java.util.List;
import java.util.function.Predicate;

/**
 * interfaccia cha ha la responsabilita' di gestire un account.
 */
public interface IAccount extends IUtility {

	/**
	 * metodo per ottenere il nome dell'account
	 * @return
	 */
	String getNameAccount();

	/**
	 * metodo per ottenere la descrizione dell'account
	 * @return
	 */
	String getDescriptionAccount();

	/**
	 * metodo per ottenere il bilancio di apertura
	 * @return
	 */
	double getOpeningBalance();

	/**
	 * metodo per ottenre il bilancio dell'account
	 * @return
	 */
	double getBalanceOfAccount();

	/**
	 * metodo per ottenre i movimenti dell'account
	 * @return
	 */
	List<IMovement> getMovementsOfAccount();

	/**
	 * metodo per aggiungere un movimento alla lista dei
	 * movimenti dell'account
	 * @param mov
	 */
	void addMovement(IMovement mov);

	/**
	 * metodo per ottenere una lista di movimenti secondo
	 * un determinato predicate
	 * @param p
	 * @return
	 */
	List<IMovement> getMovementsOfAccount(Predicate<IMovement> p);

	/**
	 * metodo per ottenere il tipo di account
	 * @return
	 */
	AccountType getTypeAccount();

	/**
	 * metodo per rimuovere un movimento dalla
	 * lista dei movimenti dell'account
	 * @param x
	 */
	void removeMovementAccount(IMovement x);

	/**
	 * metodo per impostare il nome dell'account
	 * @param name
	 */
	void setName(String name);

	/**
	 * metodo per impostare la descrizione dell'account
	 * @param description
	 */
	void setDescription(String description);

	/**
	 * metodo per impostare il tipo dell'account
	 * @param type
	 */
	void setType(AccountType type);

	/**
	 * metodo per impostare il bilancio dell'account
	 * @param amount
	 */
	void setConto(Double amount);

	/**
	 * metodo per calcolare l'effettivo amount
	 * dell'account
	 */
	void recalculateConto();
}
