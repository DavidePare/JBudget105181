package it.unicam.cs.pa.jbudget105181.Model.Transaction;

import it.unicam.cs.pa.jbudget105181.Model.Account.AccountType;
import it.unicam.cs.pa.jbudget105181.Model.Movement.IMovement;
import it.unicam.cs.pa.jbudget105181.Model.Tag.ITag;
import it.unicam.cs.pa.jbudget105181.Model.Movement.MovementType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Transazione implements ITransazione {
	private boolean pagata;
	private int ID;
	private ArrayList<IMovement> movimenti=new ArrayList<IMovement>();
	private List<ITag> tag;
	private LocalDate data;
	private String description;
	private double cost=0;
	public Transazione(int ID, LocalDate data, List<ITag> tag, String description, boolean pagata) {
		this.pagata=pagata;
		this.ID=ID;
		this.tag=tag;
		this.data=data;
		this.description=description;
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
		if((LocalDate.now().isAfter(dataNuova) && LocalDate.now().isBefore(data))||
				(!LocalDate.now().isAfter(dataNuova) && !LocalDate.now().isBefore(data))){
			for(IMovement x : movements()){
				x.getAccount().recalculateConto();
			}
			this.setPagata(!pagata);
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
	public int getNumMov(){
		return movimenti.size();
	}

	@Override
	public String getDescription(){
		return this.description;
	}
	@Override
	public void setDescription(String description){
		this.description=description;
	}
	@Override
	public void addMovementList(List<IMovement> lMovent){
		movements().addAll(lMovent);
	}

	@Override
	public void setPagata(boolean pagata){
		this.pagata=pagata;
	}
}
