package it.unicam.cs.pa.jbudget105181.Model;
import java.util.ArrayList;
import java.util.List;
//import java.util.Date;
public class Movement implements IMovement{

	private int ID=0;
	private String descrizione="";
	private double amount;
	private MovementType type;
	private IAccount account;
	private List<ITag> tag=new ArrayList<ITag>();
	private int idTransazione;
	
	public Movement(int ID, String descrizione, MovementType type,double prezzo, IAccount account, List<ITag> tag, int idT) {
		this.ID=ID;
		this.descrizione=descrizione;
		this.type=type;
		this.amount=prezzo;
		this.account=account;
		this.tag=tag;
		this.idTransazione=idT;
		
	}
	
	
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}
	
	
	public MovementType getTipo() {
		return type;
	}
	
	
	public void setAccount(IAccount acc) {
		this.account=acc;
	}
	public IAccount getAccount() {
		return account;
	}
	
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return descrizione;
	}


	@Override
	public double getAmount() {
		// TODO Auto-generated method stub
		return amount;
	}
	public void setAmount(double amount) {
		// MODIFICARE CONTENUTO DELL ACCOUNT E DELLA TRANSAZIONE
		this.amount =amount;
	}

	@Override
	public List<ITag> tags() {
		// TODO Auto-generated method stub
		return tag;
	}


	@Override
	public void addTag(ITag t) {
		// TODO Auto-generated method stub
		tag.add(t);
		
	}

	@Override
	public void removeTag(List<ITag> t) {
		// TODO Auto-generated method stub
		tag.removeAll(t);
	}
	public int getIDTransazione() {
		return idTransazione;
	}
	public int getIDAccount(){
		return account.getID();
	}

	@Override
	public void setIDTransaction(int id){
		idTransazione=id;
	}
	@Override
	public void setID(int idMovement){
		this.ID= idMovement;
	}
}
