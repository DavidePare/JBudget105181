package it.unicam.cs.pa.jbudget105181.Model.Transaction;

import it.unicam.cs.pa.jbudget105181.Model.Account.AccountType;
import it.unicam.cs.pa.jbudget105181.Model.Movement.IMovement;
import it.unicam.cs.pa.jbudget105181.Model.Tag.ITag;
import it.unicam.cs.pa.jbudget105181.Model.Movement.MovementType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe che ha la responsablita' di gestire una transazione
 */
public class Transazione implements ITransazione {
	/**
	 * variabile che dice se la transazione e' stata pagata
	 */
	private boolean pagata;
	/**
	 * identificativo univoco della transazione
	 */
	private int ID;
	/**
	 * lista di movimenti della transazione
	 */
	private ArrayList<IMovement> movimenti=new ArrayList<IMovement>();
	/**
	 * lista di tag associati alla transazione
	 */
	private List<ITag> tag;
	/**
	 * data in cui viene effettuata
	 */
	private LocalDate data;
	/**
	 * descrizione della transazione
	 */
	private String description;
	/**
	 * denaro totale associato all a transazione
	 */
	private double cost=0;

	/**
	 * Costruttore trandaizone
	 * @param ID identificativo
	 * @param data data transazione
	 * @param tag lista tag
	 * @param description descrizione
	 * @param pagata se pagato
	 */
	public Transazione(int ID, LocalDate data, List<ITag> tag, String description, boolean pagata) {
		this.pagata=pagata;
		this.ID=ID;
		this.tag=tag;
		this.data=data;
		this.description=description;
	}

	/**
	 * getter id
	 * @return id
	 */
	@Override
	public int getID() {
		return ID;
	}
	/**
	 * metodo per aggiungere un movimento
	 * @param addMov mov da aggiungere
	 */
	@Override
	public void addMovement(IMovement addMov) {
		movimenti.add(addMov);
		adjustTotalCost();
	}
	/**
	 * metodo per ritornare i movimenti della transazione
	 * @return lista movimenti
	 */
	@Override
	public ArrayList<IMovement> movements() {
		return movimenti;
	}
	/**
	 * metodi per ritornare i tag della transazione
	 * @return lista tag
	 */
	@Override
	public List<ITag> tags() {
		return tag;
	}
	/**
	 * metodo per aggiungere un tag
	 * @param nuovoTag aggiugnere
	 */
	@Override
	public void addTag(ITag nuovoTag) {
		tag.add(nuovoTag);		
	}

	/**
	 * metodo per rimuovere un tag
	 * @param eliminatag da eliminare
	 * @return nuova lista
	 */
	@Override
	public boolean removeTag(ITag eliminatag) {
		return tag.remove(eliminatag);
	}

	/**
	 * metodo per ritornare la data della transazione
	 * @return data
	 */
	public LocalDate getData() {
		return data;
	}
	/**
	 * metodo per impostare la data della transazione
	 * @param dataNuova data da settare
	 */
	@Override
	public void setData(LocalDate dataNuova) {
		if((LocalDate.now().isAfter(dataNuova) && LocalDate.now().isBefore(data))||
				(!LocalDate.now().isAfter(dataNuova) && !LocalDate.now().isBefore(data))){
			for(IMovement x : movements()){
				x.getAccount().recalculateConto();
			}
			this.setPagata(!pagata);
		}
		this.data=dataNuova;

		
	}

	/**
	 * metodo per ottenere il total amount della transazione
	 * @return cost
	 */
	@Override
	public double getTotalAmount() {
		return cost;
	}

	/**
	 * metodo per rimuovere un movimento
	 * @param m movimento da eliminare
	 */
	@Override
	public void removeMovement(IMovement m) {
		movimenti.remove(m);
		adjustTotalCost();
	}
	/**
	 * metodo per fare il refresh del conto
	 */
	public void adjustTotalCost() {
		cost=0;
		for(int i=0; i<movimenti.size(); i++) {
			if(movimenti.get(i).getAccount().getTypeAccount().equals(AccountType.ASSETS)) {
				if(movimenti.get(i).getTipo().equals(MovementType.CREDIT))
					cost+=movimenti.get(i).getAmount();
				else
					cost-=movimenti.get(i).getAmount();
			}
			else {
				if(movimenti.get(i).getTipo().equals(MovementType.CREDIT))
					cost-=movimenti.get(i).getAmount();
				else
					cost+=movimenti.get(i).getAmount();
			}
		}
	}

	/**
	 * metodo per verifivare se la transazione e' stata effettuata
	 * @return pagata
	 */
	public boolean getPagata() {
		return pagata;
	}
	/**
	 * metodo per ottenere il numero di movimenti della transazione
	 * @return numero movimenti associati
	 */
	public int getNumMov(){
		return movimenti.size();
	}

	/**
	 * metodo per ottenere la descrizione della transazione
	 * @return descrizione
	 */
	@Override
	public String getDescription(){
		return this.description;
	}

	/**
	 * metodo per impostare la descrizione della transazione
	 * @param description nuova
	 */
	@Override
	public void setDescription(String description){
		this.description=description;
	}

	/**
	 * metodo per aggiungere una lista di movimenti
	 * @param lMovent lista da aggiungere
	 */
	@Override
	public void addMovementList(List<IMovement> lMovent){
		movements().addAll(lMovent);
	}
	/**
	 * metodo per impostare la variabile che verifica
	 * se la transazione e' stata effetuata
	 * @param pagata se pagato
	 */
	@Override
	public void setPagata(boolean pagata){
		this.pagata=pagata;
	}
}
