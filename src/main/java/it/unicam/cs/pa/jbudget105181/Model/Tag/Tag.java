package it.unicam.cs.pa.jbudget105181.Model.Tag;

import it.unicam.cs.pa.jbudget105181.Model.Movement.IMovement;
import it.unicam.cs.pa.jbudget105181.Model.Movement.MovementType;

import java.util.ArrayList;
import java.util.List;

public class Tag implements ITag {
	/**
	 * Descrizione del tag
	 */
	private String descrizione="";
	/**
	 * nome associato al tag
	 */
	private String nome;
	/**
	 * lista movimenti associati al tag
	 */
	private List<IMovement> movements;

	/**
	 * Costruttore del tag
	 * @param nome
	 * @param descrizione
	 */
	public Tag(String nome, String descrizione) {
		if(nome.isEmpty() || descrizione.isEmpty() ) throw new IllegalArgumentException("You don't insert all data!");
		this.descrizione=descrizione;
		this.nome=nome;
		movements= new ArrayList<>();
	}

	/**
	 * ritorna la descrizione del tag
	 * @return descrizione account
	 */
	@Override
	public String getDescription() {
		return descrizione;
	}

	/**
	 * ritorna il nome dell account
	 * @return nome account
	 */
	@Override
	public String getNome() {
		return nome;
	}

	/**
	 * metodo to string
	 * @return Formattazione del tag
	 */
	@Override
	public String toString(){
		return nome;
	}

	/**
	 * aggiunge movimento alla lista dei movimenti
	 * @param movement movimento da aggiungere
	 */
	public void addMovement(IMovement movement){
		movements.add(movement);
	}
	/**
	 * rimuove movimento alla lista dei movimenti
	 * @param delMovement movimento da rimuovere
	 */
	public void deleteMovement(IMovement delMovement){
		movements.remove(delMovement);
	}
	/**
	 * rimuove lista movimenti  alla lista dei movimenti
	 * @param delMovement movimenti da rimuovere
	 */
	public void deleteMovement(List<IMovement> delMovement){
		movements.removeAll(delMovement);
	}

	/**
	 * ritorna conto del tag
	 * @return conto totale associato al tag
	 */
	public Double getAmount(){
		Double total=0.0;
		if(movements.isEmpty()) return total;
		for(IMovement x : movements){
			if(x.getTipo().equals(MovementType.CREDIT)){
				total+= x.getAmount();
			}
			else total-=x.getAmount();
		}
		return total;
	}

}
