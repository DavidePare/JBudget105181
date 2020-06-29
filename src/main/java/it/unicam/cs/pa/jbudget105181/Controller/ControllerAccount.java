package it.unicam.cs.pa.jbudget105181.Controller;

import it.unicam.cs.pa.jbudget105181.Model.AccountType;
import it.unicam.cs.pa.jbudget105181.Model.ILedger;

public class ControllerAccount {
	public AccountType controlType(String type) {
		if(type.compareTo("ASSETS")==0) return AccountType.ASSETS;
		if(type.compareTo("LIABILITIES")==0) return AccountType.LIABILITIES;	
		return null;
	}
	
	/*
	 * Codici di errore :
	 * -1 Il numero inserito è negativo 
	 * -2 Il numero inserito è occupato perciò bisogna inserire un altro integer non occupato
	 */
	public int controlID(ILedger ledger,String idStringa) { 
		int ID=-3;
		try {
			ID=Integer.parseInt(idStringa);
			if(ID < 0)	return -1;
			long contatore=ledger.getAccounts().stream().filter(t -> Integer.parseInt(idStringa) == t.getIDAccount()).count();
			if(contatore!=0)	return -2;
		}catch(IllegalArgumentException e) {
			System.err.println("Errore"+ e.getMessage());
		}
		
		return ID;
	}
	public double doubleValue(String s) {
		try {
			double value=Double.parseDouble(s);
			return value;
		}catch(IllegalArgumentException e) {
			System.err.println("Errore"+ e.getMessage());
		}
		return Double.NaN;
	}
	
	
	public boolean controlName(ILedger ledger,String name) {
		long contatore= ledger.getAccounts().stream().filter(t -> name == t.getNameAccount()).count();
		return contatore ==0;
	}
}
