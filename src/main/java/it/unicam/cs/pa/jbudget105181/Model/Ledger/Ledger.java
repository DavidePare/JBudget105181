package it.unicam.cs.pa.jbudget105181.Model.Ledger;

import it.unicam.cs.pa.jbudget105181.Model.*;
import it.unicam.cs.pa.jbudget105181.Model.Account.AccountType;
import it.unicam.cs.pa.jbudget105181.Model.Account.IAccount;
import it.unicam.cs.pa.jbudget105181.Model.Budget.Budget;
import it.unicam.cs.pa.jbudget105181.Model.Budget.IBudget;
import it.unicam.cs.pa.jbudget105181.Model.Movement.IMovement;
import it.unicam.cs.pa.jbudget105181.Model.Tag.ITag;
import it.unicam.cs.pa.jbudget105181.Model.Transaction.ITransazione;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

public class Ledger implements ILedger {
	/*
	 * Contiene tutte le transazioni e quindi di conseguenza ha accesso a tutti i movimenti
	 */
	private ArrayList<ITransazione> allTransaction= new ArrayList<ITransazione>();
	/*
	 * Questa variabile conterra tutti i tag usati sara utile per stamparli a video nel caso l utente lo richieda
	 */
	private List<ITag> tag= new ArrayList<ITag>();
	private List<IAccount> listaAccount= new ArrayList<IAccount>();
	private IBudget listaBudget= new Budget();
	public Ledger() {
		
	}
	public Ledger(ILedger ledger){
		this.allTransaction.addAll(ledger.getAllTransactions());
		this.listaAccount.addAll(ledger.getAccounts());
		this.listaBudget=ledger.getBudget();
		this.tag.addAll(ledger.getTags());
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
	/*
	 * rimuovere
	 */
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
		listaAccount.stream().filter(t-> t.getID() ==x.getAccount().getID()).forEach(t-> t.removeMovementAccount(x));
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
			if(listaAccount.get(i).getID() == id) {
				return listaAccount.get(i);
			}
			i++;
		}
		return null;
	}
	public void removeAccount(IAccount account){
		List<IAccount> app= new ArrayList<>();
		listaAccount.stream().filter(t-> t.getID() == account.getID()).forEach(t -> app.add(t));
		listaAccount.removeAll(app);
	}


	/**
	 * Metodo che ha la responsabilità di ritornare un certo elemento T ricercato per id in una
	 * Collenction parametrizzata rispetto a lui.
	 * @param collection Collection nella quale ricercare il determinato oggetto.
	 * @param ID Identificativo dell'oggetto ricercato.
	 * @param <T> Tipo dell'oggetto ricercato.
	 * @return Oggetto ricercato.
	 */
	private <T extends IUtility> T get(Collection<T> collection, int ID){
		AtomicReference<T> v = new AtomicReference<>();
		collection.stream().filter(t->t.getID()==ID).forEach(t->v.set(t));
		return v.get();
	}

	public <T extends IUtility> int generateID(Collection<T> collection){
		AtomicReference<T> v = new AtomicReference<>();
		//collection.stream().filter(t->t.getID()==ID).forEach(t->v.set(t));
		if(collection.stream().count() ==0) return 1;
		int max=collection.stream().mapToInt(t-> t.getID()).max().orElseThrow(NoSuchElementException::new);
		//int max=ledger.getAccounts().stream().mapToInt(t-> t.getID()).max().orElseThrow(NoSuchElementException::new);
		return max+1;

	}

	public void modifyAccount(int accID, String name, String description, AccountType type, Double value){
		IAccount account=this.get(listaAccount,accID);
		account.setName(name);
		account.setConto(value);
		account.setDescription(description);
		account.setType(type);
	}

	public void addMovement(IMovement movement){
		this.get(listaAccount,movement.getAccount().getID()).addMovement(movement);
		this.get(allTransaction,movement.getIDTransazione()).addMovement(movement);
		addMovementAtTag(movement);
	}
	private void addMovementAtTag(IMovement movement){
		for(ITag tag:movement.tags()){
			tag.addMovement(movement);
		}
	}
	public void removeTransaction(ITransazione transazione){
		for(IMovement movement : transazione.movements()){
			IAccount account=movement.getAccount();
			this.get(listaAccount,account.getID()).removeMovementAccount(movement);
		}
		allTransaction.remove(transazione);
	}
	public void removeMovement(IMovement movement){
		this.get(allTransaction,movement.getIDTransazione()).removeMovement(movement);
		this.get(listaAccount,movement.getAccount().getID()).removeMovementAccount(movement);
	}


	public void addRateMovement(List<ITransazione> lTransaction, IMovement movement){
		for(ITransazione transaction : lTransaction){
			IMovement mov= IFactory.generateMovement(generateID(transaction.movements()),movement.getDescription(),
					movement.getTipo(),movement.getAmount(),movement.getAccount(),movement.tags(),transaction);
			this.get(allTransaction,transaction.getID()).addMovement(mov);
			transaction.addMovement(mov);
			this.get(listaAccount,movement.getAccount().getID()).addMovement(mov);
			addMovementAtTag(movement);
		}
	}
	public void addBudgetLedger(ITag tag, Double value){
		listaBudget.addBudgetType(tag,value);
	}
	public IBudget<ITag> getBudget(){
		return listaBudget;
	}
	public void removeBudget(ITag budget){
		listaBudget.remove(budget);
	}

}