package it.unicam.cs.pa.jbudget105181.View;

import it.unicam.cs.pa.jbudget105181.Model.IAccount;

public class ConsoleViewAccount extends AVariablesConsoleView{
	
	public String descriptionInput() {
		System.out.println("Inserire una descrizione per l'account");
		String descrizione=scanner.nextLine();
		return descrizione;
	}
	public String nameInput() {
		System.out.println("Inserire nome dell'account");
		String nome=scanner.nextLine();
		return nome;
	}
	public String TypeAccountInsert() {
		System.out.println("Inserire il tipo dell'account, attualmente ci sono LIABILITIES/ASSETS");
		String s=scanner.nextLine();
		s=s.toUpperCase();
		if(s.startsWith("L") && s.length()<2) {
			return "LIABILITIES";
		}
		if(s.startsWith("A") && s.length()<2) {
			return "ASSETS";
		}
		return s;
	}
	
	
	public String insertID() {
		System.out.println("Inserire numero account:(ID univoco non si accettano valori negativi)");
	
		String x=scanner.nextLine();
		
		return x;
	}
	
	public void alreadyExistID() {
		System.out.println("L'ID esiste gi�");
	}
	
	public void alreadyExistName() {
		System.out.println("Il nome esiste gi�");
	}
	
	public void accountTypeInesistente() {
		System.out.println("Attenzione il tipo di account inserito � inestitente inserire LIABILITIES oppure ASSETS!");
	}
	
	public void IDnegativo() {
		System.out.println("ID negativo");
	}
	public void inserimentoCorretto() {
		System.out.println("Account inserito correttamente!");
	}
	public void IDformatError() {
		System.out.println("Inserire un intero!");
	}
	public void vuota() {
		System.out.println("Non sono presenti Account");
	}
	@Override
	public void title() {
		// TODO Auto-generated method stub
		System.out.println("\t \t \tInserimento Account!");
	}
	public String insertAmount() {
		System.out.println("Inserisci quando denaro possiede questo Account!"); 
		return scanner.nextLine();
	}
	public void stampaAccount(IAccount account) {
		System.out.println("[ID :"+account.getID()+"\t Nome:" +account.getNameAccount() +"\t Descrizione:" + account.getDescriptionAccount()+
				"\t Tipo di account:"+ account.getTypeAccount()+"\t Denaro: "+account.getBalanceOfAccount() +"]");
	}
}
