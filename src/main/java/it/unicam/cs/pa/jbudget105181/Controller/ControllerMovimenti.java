package it.unicam.cs.pa.jbudget105181.Controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import it.unicam.cs.pa.jbudget105181.Model.ILedger;
import it.unicam.cs.pa.jbudget105181.Model.IMovement;
import it.unicam.cs.pa.jbudget105181.Model.ITag;
import it.unicam.cs.pa.jbudget105181.Model.MovementType;

public class ControllerMovimenti {
	public boolean checkData(String s) {
		DateTimeFormatter formatter= DateTimeFormatter.ISO_LOCAL_DATE;
		try {
			formatter.parse(s);
			return true;
		}catch(Exception e) {
			System.err.println("Errore"+ e.getMessage());
		}
		return false;	
	}
	
	public LocalDate verifyData(String s) {
		LocalDate data=null;
		if(checkData(s)) {
			data=LocalDate.parse(s);
		}
		return data;
	}
	/*	Ritorni
	 *  id se è un numero corretto
	 *  -3 se deve stampare le transazioni
	 *  -2 se è una stringa
	 *  -1 se non esiste alcun id associato alla transazione
	 */
	public int verificaIDtransazione(String id,ILedger ledger) {
		id=id.toUpperCase();
		if(id.compareTo("TRANSAZIONI")==0) return -3;
		try {
			int idint=Integer.parseInt(id);
			if(idint < 0)	return -1;
			long contatore=ledger.getAllTransactions().stream().filter(t -> idint == t.getID()).count();
			if(contatore==1) return idint;
		}catch(IllegalArgumentException e) {
			System.err.println("Errore"+ e.getMessage());
			return -2;
		}
		return -1;
		
	}
	public int verificaIDAccount(String id,ILedger ledger) {
		try {
			int idint=Integer.parseInt(id);
			if(idint < 0)	return -1;
			long contatore=ledger.getAccounts().stream().filter(t -> idint == t.getID()).count();
			if(contatore==1) return idint;
		}catch(IllegalArgumentException e) {
			System.err.println("Errore"+e.getMessage());
			return -2;
		}
		return -1;
	}
	public double doubleValue(String value) {
		try {
			double x=Double.parseDouble(value);
			return x;
		}
		catch(Exception e) {
			System.err.println("Errore"+ e.getMessage());
			return Double.NaN;
		}
	}
	/*
	 * Controllo sul movimento verifica che sia di debito o di credito
	 */
	public MovementType controlType(String type) {
		if(type.compareTo("CREDIT")==0) return MovementType.CREDIT;
		if(type.compareTo("DEBIT")==0) return MovementType.DEBIT;;	
		return null;
	}
	public LocalDate dataTran(ILedger ledger, int id) {
		//ArrayList<ITransazione> appoggio= new ArrayList<>();
		List<LocalDate> data =new ArrayList<>();
		ledger.getAllTransactions().stream().filter(t -> t.getID()==id).forEach(t-> data.add(t.getData()));
		if(data.isEmpty()) return null;
		return data.get(0);
	}
	
	public int generaIDmovimento(ILedger ledger, int id) {
		ArrayList<IMovement> listamov=new ArrayList<>();
		ledger.getAllTransactions().stream().filter(t -> t.getID()==id).forEach(t -> listamov.addAll(t.movements()));
		return listamov.size()+1;
	}
	public boolean yesornoResponse(String s) {
		s=s.toUpperCase();
		return s.compareTo("SI") == 0 || s.compareTo("Y") == 0;
	}
	public boolean nonPresenteInLista(ITag tag ,List<ITag> lista) {
		if(lista.isEmpty()) {
			return true;
		}
		long num=lista.stream().filter(t -> ( tag.getDescription().compareTo(t.getDescription())==0 && tag.getNome().compareTo(t.getNome())==0)).count();
		return num==0;
	}
	public boolean alreadyExistTag(ITag ricerca, ILedger ledger) {
		List<ITag> appoggio=new ArrayList<>(); 
		if(ledger.getTags().isEmpty()) {
			return true;
		}
		ledger.getTags().stream().filter(t -> ( ricerca.getDescription().compareTo(t.getDescription())==0 && ricerca.getNome().compareTo(t.getNome())==0)  ).forEach(t -> appoggio.add(ricerca));
		return appoggio.isEmpty();
	}
	public int intValue(String s) {
		int c=-1;
		try {
			c=Integer.parseInt(s);
			return c;
		}catch(Exception e) {
			return c;
		}
	}
}
