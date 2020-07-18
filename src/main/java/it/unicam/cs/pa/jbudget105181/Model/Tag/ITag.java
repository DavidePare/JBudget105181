package it.unicam.cs.pa.jbudget105181.Model.Tag;
import it.unicam.cs.pa.jbudget105181.Model.Budget.BudgetInterface;
import it.unicam.cs.pa.jbudget105181.Model.Movement.IMovement;

import java.util.List;

/**
 * interfaccia che ha la responsabilita' di gestire un Tag.
 * Un Tag rappresenta una categoria di un movimento al quale
 * ne possono essere associati diversi.
 */
public interface ITag extends BudgetInterface {

	/**
	 * metodo per ottenere la descrizione del tag
	 * @return descrizione tag
	 */
	String getDescription();

	/**
	 * metodo per ottenere la descrizione del nome
	 * @return nome da ritornare
	 */
	String getNome();

	/**
	 * metodo per aggiungere un movimento
	 * @param movement elemento da aggiugnere
	 */
	void addMovement(IMovement movement);

	/**
	 * metodo per rimuovere un movimento
	 * @param delMovement elemento da rimuovere
	 */
	void deleteMovement(IMovement delMovement);

	/**
	 * metodo per rimuovere una lista di movimenti
	 * @param delMovement elemento da rimuovere
	 */
	void deleteMovement(List<IMovement> delMovement);

	/**
	 * metodo per ottenere l'amount complessivo dei
	 * movimenti del tag
	 * @return valore tag
	 */
	Double getAmount();
}
