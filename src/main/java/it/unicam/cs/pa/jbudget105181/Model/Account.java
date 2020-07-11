package it.unicam.cs.pa.jbudget105181.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Account implements IAccount {
	private String name;
	private String description;
	private int ID;
	private AccountType type;
	private List<IMovement> movimenti= new ArrayList<IMovement>();
	private Double conto;
	public Account(int ID,String name, String description, AccountType type,Double conto) {
		this.name=name;
		this.description=description;
		this.type=type;
		this.ID=ID;
		this.conto=conto;
	}
	@Override
	public String getNameAccount() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public String getDescriptionAccount() {
		// TODO Auto-generated method stub
		return description;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}

	@Override
	public double getOpeningBalance() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getBalanceOfAccount() {
		// TODO Auto-generated method stub
		return conto;
	}
	@Override
	public AccountType getTypeAccount() {
		// TODO Auto-generated method stub
		return type;
	}
	@Override
	public List<IMovement> getMovementsOfAccount() {
		// TODO Auto-generated method stub
		return movimenti;
	}

	@Override
	public List<IMovement> getMovementsOfAccount(Predicate<IMovement> p) {
		List<IMovement> ricerca= new ArrayList<IMovement>();
		movimenti.stream().filter(p).forEach(t-> ricerca.add(t));
		return ricerca;
	}
	
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
	public void setName(String name){
		this.name=name;
	}
	public void setDescription(String description){
		this.description=description;
	}
	public void setType(AccountType type){
		this.type=type;
	}
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
}
