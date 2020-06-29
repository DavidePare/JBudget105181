package it.unicam.cs.pa.jbudget105181.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Transazione implements ITransazione{
	private boolean pagata;
	private int ID;
	private ArrayList<IMovement> movimenti=new ArrayList<IMovement>();
	private List<ITag> tag;
	private LocalDate data;
	private double cost=0;
	public Transazione(int ID,LocalDate data, List<ITag> tag, boolean pagata) {
		this.pagata=pagata;
		this.ID=ID;
		this.tag=tag;
		this.data=data;
	}
	
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}
	@Override
	public void addMovement(IMovement addMov) {
		movimenti.add(addMov);
		adjustTotalCost();
	}
	@Override
	public ArrayList<IMovement> movements() {
		// TODO Auto-generated method stub
		return movimenti;
	}

	@Override
	public List<ITag> tags() {
		// TODO Auto-generated method stub
		return tag;
	}

	@Override
	public void addTag(ITag nuovoTag) {
		// TODO Auto-generated method stub
		tag.add(nuovoTag);		
	}

	@Override
	public boolean removeTag(ITag eliminatag) {
		// TODO Auto-generated method stub
		return tag.remove(eliminatag);
	}

	public LocalDate getData() {
		return data;
	}

	@Override
	public void setData(LocalDate dataNuova) {
		// SISTEMARE. 
		// Se la modifica della data � precedente alla data odierna
		if(LocalDate.now().isAfter(dataNuova) ) {
			if(!this.pagata) {
				System.out.println("Devo decrementare budget");
				this.pagata=true;
			}else {
				this.pagata=false;
			}
		}
		this.data=dataNuova;
		
	}

	@Override
	public double getTotalAmount() {
		return cost;
	}

	@Override
	public void removeMovement(IMovement m) {
		// Bisogna effettuare i controlli dopo la rimozione e nel mentre
		movimenti.remove(m);
		adjustTotalCost();
	}
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
	public boolean getPagata() {
		return pagata;
	}

}
