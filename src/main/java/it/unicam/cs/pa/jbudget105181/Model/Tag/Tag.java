package it.unicam.cs.pa.jbudget105181.Model.Tag;

import it.unicam.cs.pa.jbudget105181.Model.Movement.IMovement;
import it.unicam.cs.pa.jbudget105181.Model.Movement.MovementType;

import java.util.ArrayList;
import java.util.List;

public class Tag implements ITag {
	private String descrizione="";
	private String nome;
	private List<IMovement> movements;
	public Tag(String nome, String descrizione) {
		if(nome.isEmpty() || descrizione.isEmpty() ) throw new IllegalArgumentException("You don't insert all data!");
		this.descrizione=descrizione;
		this.nome=nome;
		movements= new ArrayList<>();
	}
	// Overload nel caso l'utente decidesse di omettere la descrizione.
	public Tag(String nome) {
		this.nome=nome;
	}
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return descrizione;
	}

	@Override
	public String getNome() {
		// TODO Auto-generated method stub
		return nome;
	}
	@Override
	public String toString(){
		return nome;
	}
	public void addMovement(IMovement movement){
		movements.add(movement);
	}
	public void deleteMovement(IMovement delMovement){
		movements.remove(delMovement);
	}
	public void deleteMovement(List<IMovement> delMovement){
		movements.removeAll(delMovement);
	}
	public Double getAmount(){
		Double total=0.0;
		for(IMovement x : movements){
			if(x.getTipo().equals(MovementType.CREDIT)){
				total+= x.getAmount();
			}
			else total -=x.getAmount();
		}
		return total;
	}

}
