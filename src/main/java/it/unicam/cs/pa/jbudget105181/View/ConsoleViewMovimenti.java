package it.unicam.cs.pa.jbudget105181.View;

import it.unicam.cs.pa.jbudget105181.Model.IMovement;

public class ConsoleViewMovimenti extends AbsConsoleMovimentiTransazioni{
	
	public void errorDouble() {
		System.out.println("Non � stato inserito un numero double!");
	}
	public void errorNoAccOrTran(){
		System.out.println("Errore prima di realizzare un movimento necessiti di una transazione e di un account!");
	}
	public void errorNoIDesiste() {
		System.out.println("l'id scelto non � associato a niente");
	}
	public void errorInaspettaStringa() {
		System.out.println("non va bene una stringa!");
	}
	public String insertAmount() {
		System.out.println("Inserisci valore movimento");
		String s= scanner.nextLine();
		return s;
	}

	
	public String messageidTransazione(){
		System.out.println("Inserire ID della transazione");
		System.out.println("Se si desidere vedere la lista delle transazioni scrivere:transazioni");
		String s=scanner.nextLine();
		return s;
	}
	public void errorType() {
		System.out.println("Errore di tipo movimento inserire o credito o debito");
	}
	public String inputAccount() {
		System.out.println("inserire un ID account esistente");
		String s= scanner.nextLine();
		return s;
	}
	public void errorAccount() {
		System.out.println("L'account inserito non esiste");
	}
	public String TypeMovementInsert() {
		System.out.println("Inserire il tipo dell'account, attualmente ci sono CREDIT/DEBIT");
		String s=scanner.nextLine();
		s=s.toUpperCase();
		if(s.startsWith("C") && s.length()<2) {
			return "CREDIT";
		}
		if(s.startsWith("D") && s.length()<2) {
			return "DEBIT";
		}
		return s;
	}
	public String descrizioneMov() {
		System.out.println("Inserire descrizione account");
		return scanner.nextLine();
	}
	
	@Override
	public void title() {
		// TODO Auto-generated method stub
		
	}
	public void printMovement(IMovement x) {
		System.out.println("[ Account:" +x.getAccount().getID() +"\t TipoMovimento" + x.getTipo() +
				"\t ID Transazione associata:"+ x.getIDTransazione()+"ID Movimento:"+x.getID()+ "\t Importo:" +x.getAmount() +"]");
	//	System.out.println(x.tags().get(0).getNome() +x.tags().get(0).getDescription());
	}

	
}
