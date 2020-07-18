package it.unicam.cs.pa.jbudget105181.Model.Account;

import it.unicam.cs.pa.jbudget105181.Model.Movement.IMovement;
import it.unicam.cs.pa.jbudget105181.Model.Movement.MovementType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Account implements IAccount {
	private String name;
	private String description;
	private final int ID;
	private AccountType type;
	private List<IMovement> movimenti;
	private Double conto;
	public Account(int ID,String name, String description, AccountType type,Double conto) {
		this.movimenti = new ArrayList<IMovement>();
		this.name=name;
		this.description=description;
		this.type=type;
		this.ID=ID;
		this.conto=conto;
	}
	/**
	 * metodo per ottenere il nome dell'account
	 * @return
	 */
	@Override
	public String getNameAccount() {
		// TODO Auto-generated method stub
		return name;
	}
	/**
	 * metodo per ottenere la descrizione dell'account
	 * @return
	 */
	@Override
	public String getDescriptionAccount() {
		// TODO Auto-generated method stub
		return description;
	}

	/**
	 * metodo per ottenere l'id dell'account
	 * @return
	 */
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}

	/**
	 * metodo per ottenere il bilancio dell'account
	 * @return
	 */

	@Override
	public double getBalanceOfAccount() {
		// TODO Auto-generated method stub
		return conto;
	}

	/**
	 * metoo per ottenere il tipo dell'account
	 * @return
	 */
	@Override
	public AccountType getTypeAccount() {
		// TODO Auto-generated method stub
		return type;
	}

	/**
	 * metodo per otterere la lista dei movimenti associati all account
	 * @return
	 */
	@Override
	public List<IMovement> getMovementsOfAccount() {
		// TODO Auto-generated method stub
		return movimenti;
	}

	/**
	 * metodo per ottenere una lista di movimenti che rispettano un determinato parametro
	 * @param p
	 * @return
	 */
	@Override
	public List<IMovement> getMovementsOfAccount(Predicate<IMovement> p) {
		List<IMovement> ricerca= new ArrayList<IMovement>();
		movimenti.stream().filter(p).forEach(t-> ricerca.add(t));
		return ricerca;
	}

	/**
	 * metodo per l'aggiunta di un movimento dentro l'account
	 * @param mov
	 */
	public void addMovement(IMovement mov) {
		movimenti.add(mov);
		if(!mov.getTransaction().getData().isAfter(LocalDate.now()) ) {
			if(type.equals(AccountType.ASSETS)) {
				if(mov.getTipo().equals(MovementType.CREDIT))
					conto+=mov.getAmount();
				else
					conto-=mov.getAmount();
			}else {
				if(mov.getTipo().equals(MovementType.CREDIT))
					conto-=mov.getAmount();
				else
					conto+=mov.getAmount();
			}
			mov.getTransaction().setPagata(true);
		}
	}

	/**
	 * metodo per la rimozione di un movimento dall'account
	 * @param x
	 */
	public void removeMovementAccount(IMovement x) {

		if(movimenti.remove(x)) {
			if (x.getTransaction().getPagata()) {
				if (type.equals(AccountType.ASSETS)) {
					if (x.getTipo().equals(MovementType.CREDIT))
						conto -= x.getAmount();
					else
						conto += x.getAmount();
				} else {
					if (x.getTipo().equals(MovementType.CREDIT))
						conto += x.getAmount();
					else
						conto -= x.getAmount();
				}
			//	x.getTransaction().setPagata(true);
			}
		}
	}

	/**
	 * metodo di setter per il nome
	 * @param name
	 */
	public void setName(String name){
		this.name=name;
	}

	/**
	 * metodo di setter per la descrizione
	 * @param description
	 */
	public void setDescription(String description){
		this.description=description;
	}

	/**
	 * metodo di setter per il tipo di account
	 * @param type
	 */
	public void setType(AccountType type){
		this.type=type;
	}

	/**
	 * metodo per il set dell'account
	 * @param amount
	 */
	public void setConto(Double amount){
		this.conto=amount;
		recalculateConto();
	}
	/*
	 * quando modifichi il conto iniziale dell'account
	 */
	public void recalculateConto(){
		for(IMovement mov : movimenti){
			if(mov.getTransaction().getPagata()) {
				if (type.equals(AccountType.ASSETS)) {
					if (mov.getTipo().equals(MovementType.CREDIT))
						conto += mov.getAmount();
					else
						conto -= mov.getAmount();
				} else {
					if (mov.getTipo().equals(MovementType.CREDIT))
						conto -= mov.getAmount();
					else
						conto += mov.getAmount();
				}
			}
		}
	}
	@Override
	public String toString() {
		return name;
	}

	/**
	 * aggiunta del movimento dalla lettura da file
	 * @param mov
	 */
	public void addMovementDeserialized(IMovement mov){
		movimenti.add(mov);
		if(!mov.getTransaction().getData().isAfter(LocalDate.now()) && !mov.getTransaction().getPagata()) {
			if(type.equals(AccountType.ASSETS)) {
				if(mov.getTipo().equals(MovementType.CREDIT))
					conto+=mov.getAmount();
				else
					conto-=mov.getAmount();
			}else {
				if(mov.getTipo().equals(MovementType.CREDIT))
					conto-=mov.getAmount();
				else
					conto+=mov.getAmount();
			}
			mov.getTransaction().setPagata(true);
		}
	}
}
