package it.unicam.cs.pa.jbudget105181.Model;

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
	public int getIDAccount() {
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
	}
	public void removeMovementAccount(IMovement x) {
		movimenti.remove(x);
	}



}
