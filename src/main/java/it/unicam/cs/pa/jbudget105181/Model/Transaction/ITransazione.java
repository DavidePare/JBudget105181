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
	 * @return lista movimenti
	 */
	ArrayList<IMovement> movements();

	/**
	 * metodi per ritornare i tag della transazione
	 * @return lista tag
	 */
	List<ITag> tags();

	/**
	 * metodo per aggiungere un tag
	 * @param nuovoTag aggiunta tag
	 */
	void addTag(ITag nuovoTag);

	/**
	 * metodo per rimuovere un tag
	 * @param eliminatag tag da rimuovere
	 * @return se si o no
	 */
	boolean removeTag(ITag eliminatag);

	/**
	 * metodo per ritornare a data della transazione
	 * @return data
	 */
	LocalDate getData();

	/**
	 * metodo per impostare la data della transazione
	 * @param dataNuova imposta nuova data
	 */
	void setData(LocalDate dataNuova);

	/**
	 * metodo per ottenere il total amount della transazione
	 * @return valore transazione
	 */
	double getTotalAmount();

	/**
	 * metodo per aggiungere un movimento
	 * @param addMov movimento da aggiungere
	 */
	void addMovement(IMovement addMov);

	/**
	 * metodo per rimuovere un movimento
	 * @param m mov da rimuovere
	 */
	void removeMovement(IMovement m);

	/**
	 * metodo per verifivare se la transazione e' stata effettuata
	 * @return se la transazione è stata pagata
	 */
	boolean getPagata();

	/**
	 * metodo per ottenere il numero di movimenti della transazione
	 * @return numero movimenti associait
	 */
	int getNumMov();

	/**
	 * metodo per ottenere la descrizione della transazione
	 * @return descrizione
	 */
	String getDescription();

	/**
	 * metodo per impostare la descrizione della transazione
	 * @param description nuova descrizione
	 */
	void setDescription(String description);

	/**
	 * metodo per aggiungere una lista di movimenti
	 * @param lMovent lista movimenti
	 */
	void addMovementList(List<IMovement> lMovent);

	/**
	 * metodo per impostare la variabile che verifica
	 * se la transazione e' stata effetuata
	 * @param pagata nuova impostazione
	 */
	void setPagata(boolean pagata);

	/**
	 * metodo per fare il refresh del conto
	 */
	void adjustTotalCost();
}
