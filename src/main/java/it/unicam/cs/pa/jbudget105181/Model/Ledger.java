package it.unicam.cs.pa.jbudget105181.Model;

import java.util.ArrayList;
import java.util.Iterator;
//import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

public class Ledger implements ILedger{
	/*
	 * Contiene tutte le transazioni e quindi di conseguenza ha accesso a tutti i movimenti
	 */
	private ArrayList<ITransazione> allTransaction= new ArrayList<ITransazione>();
	/*
	 * Questa variabile conterrà tutti i tag usati sarà utile per stamparli a video nel caso l utente lo richieda
	 */
	private List<ITag> tag= new ArrayList<ITag>(); 
	private List<IAccount> listaAccount= new ArrayList<IAccount>(); 
	
	public Ledger() {
		
	}
	
	@Override
	public void addTransazione(ITransazione c) {
		allTransaction.add(c);	
	}

	@Override
	public ArrayList<ITransazione> getAllTransactions() {
		
		//allTransaction.stream().filter(predicate);
		return allTransaction;
	}

	@Override
	public void addTag(ITag t) {
	/*	Tag t=new Tag(nome,descrizione); 
		if(!tag.contains(t)) {
			/* Da controllare se contains funziona non so 
			tag.add(t);
		}*/
		tag.add(t);		
	}	
	
	public ArrayList<ITransazione> getTransactionsForYear(int year){
		/*
		 * Metodo che ritorna mediante lo stream tutte le transazioni che hanno una determinata caratteristica 
		 */
		ArrayList<ITransazione> filtrato=new ArrayList<>();
		allTransaction.stream().filter(t -> t.getData().getYear() ==year).forEach(t->filtrato.add(t));
		/*Iterator<ITransazione> I=filtrato.iterator();
		while(I.hasNext()) {
			System.out.println(I.next());
		}*/
		
		return filtrato;
	}
	
	@Override
	public List<ITag> getTags() {
		// TODO Auto-generated method stub
		return tag;
	}
	
	// Metodo che ritorna tutti i movimenti rispetto a un determinato periodo di tempo
	
	
	
	public List<IAccount> getAccounts(){
		return listaAccount;
	}
	@Override
	public void addAccount(IAccount acc) {
		listaAccount.add(acc);
	}


	@Override
	
	public boolean removeTag(Predicate <ITag> p) {
		List<ITag> app= new ArrayList<>(); 
		tag.stream().filter(p).forEach(t -> app.add(t));
		removeTagTransaction(app);
		if(tag.removeAll(app))
			return true;
		else return false;
	}
	@Override
	public boolean removeTagTransaction(List<ITag> t) {
		int i=0;
		/*
		Predicate<ITransazione> p= (x-> x.tags().contaisns(t));
		allTransaction.stream().filter(x-> x.tags().contains(t));*/
		while(i<allTransaction.size()) {
			if(!allTransaction.get(i).movements().isEmpty()) {
				for(IMovement x : allTransaction.get(i).movements()) {
					x.removeTag(t);;
				}
			}
			allTransaction.get(i).tags().removeAll(t);
			i++;
		}
		return true;
	}
	@Override
	public void removeTransaction(Predicate<ITransazione> p) {
		List<ITransazione> elim = new ArrayList<ITransazione>();
		elim=getTransaction(p);
		allTransaction.removeAll(elim);
		if(elim.size()>0) {
			for(IMovement x : elim.get(0).movements()) {
				removeMovementAcc(x);
			}
		}
		
	}
	public void removeMovementAcc(IMovement x) {
	//	List<IMovement> elim = new ArrayList<IMovement>();
		listaAccount.stream().filter(t-> t.getIDAccount() ==x.getAccount().getIDAccount()).forEach(t-> t.removeMovementAccount(x));
	//	System.out.println("errore prima");
	}
	
	//Un po brutto
	@Override
	public void removeMovement(int id,int idMov) {
		List<ITransazione> transazione=new ArrayList<>(); 
		allTransaction.stream().filter(t-> t.getID()==id).forEach(t-> transazione.add(t));
		Iterator<IMovement> iteratore= transazione.get(0).movements().iterator();
		while(iteratore.hasNext()) {
			IMovement app=iteratore.next();
			if(app.getID() == idMov) {
				allTransaction.removeAll(transazione);
				transazione.get(0).movements().remove(app);
				allTransaction.addAll(transazione);
				removeMovementAcc(app);
				break;
			}
		}
	}
	@Override
	public List<ITransazione> getTransaction(Predicate<ITransazione> p) {
		List<ITransazione> l= new ArrayList<ITransazione>();
		allTransaction.stream().filter(p).forEach(t -> l.add(t));
		return l;
		
	}
	public ITransazione getTransactionForID(int id) {
		int i=0;
		while(i<allTransaction.size()) {
			if(allTransaction.get(i).getID() == id) {
				return allTransaction.get(i);
			}
		}
		return null;
	}
	public IAccount getAccountForID(int id) {
		int i=0;
		while(i<listaAccount.size()) {
			if(listaAccount.get(i).getIDAccount() == id) {
				return listaAccount.get(i);
			}
			i++;
		}
		return null;
	}
}