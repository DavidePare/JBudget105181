package it.unicam.cs.pa.jbudget105181.Model.Transaction;

import it.unicam.cs.pa.jbudget105181.Model.Movement.IMovement;
import it.unicam.cs.pa.jbudget105181.Model.Tag.ITag;
import it.unicam.cs.pa.jbudget105181.Model.IUtility;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

/**
 * interfaccia che hanno la responsabilita' di gestire una transazione.
 * Una transazione rappresenta un insieme di movimenti.
 */
public interface ITransazione extends IUtility {

	/**
	 * metodo per ritornare i movimenti della transazione
	 * @return
	 */
	ArrayList<IMovement> movements();

	/**
	 * metodi per ritornare i tag della transazione
	 * @return
	 */
	List<ITag> tags();

	/**
	 * metodo per aggiungere un tag
	 * @param nuovoTag
	 */
	void addTag(ITag nuovoTag);

	/**
	 * metodo per rimuovere un tag
	 * @param eliminatag
	 * @return
	 */
	boolean removeTag(ITag eliminatag);

	/**
	 * metodo per ritornare a data della transazione
	 * @return
	 */
	LocalDate getData();

	/**
	 * metodo per impostare la data della transazione
	 * @param datanuova
	 */
	void setData(LocalDate datanuova);

	/**
	 * metodo per ottenere il total amount della transazione
	 * @return
	 */
	double getTotalAmount();
//	ArrayList<LocalDate> getDate();

	/**
	 * metodo per aggiungere un movimento
	 * @param addMov
	 */
	void addMovement(IMovement addMov);

	/**
	 * metodo per rimuovere un movimento
	 * @param m
	 */
	void removeMovement(IMovement m);

	/**
	 * metodo per verifivare se la transazione e' stata effettuata
	 * @return
	 */
	boolean getPagata();

	/**
	 * metodo per ottenere il numero di movimenti della transazione
	 * @return
	 */
	int getNumMov();

	/**
	 * metodo per ottenere la descrizione della transazione
	 * @return
	 */
	String getDescription();

	/**
	 * metodo per impostare la descrizione della transazione
	 * @param description
	 */
	void setDescription(String description);

	/**
	 * metodo per aggiungere una lista di movimenti
	 * @param lMovent
	 */
	void addMovementList(List<IMovement> lMovent);

	/**
	 * metodo per impostare la variabile che verifica
	 * se la transazione e' stata effetuata
	 * @param pagata
	 */
	void setPagata(boolean pagata);

	/**
	 * metodo per fare il refresh del conto
	 */
	void adjustTotalCost();
}
