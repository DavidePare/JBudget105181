package it.unicam.cs.pa.jbudget105181.View;

import it.unicam.cs.pa.jbudget105181.Model.ITransazione;

public class ConsoleViewTransazioni extends AbsConsoleMovimentiTransazioni{
	
	
	@Override
	public void title() {
		// TODO Auto-generated method stub
		System.out.println("\t\t\t Inserimento nuova Transazione!");
	}
	
	public void successfull() {
		System.out.println("Inserito correttamente!");
	}
	
	public void presenteLista() {
		System.out.println("Il Tag è già presente");
	}
	public void stampaTransazioni(ITransazione x) {
		System.out.print("[ID= "+x.getID()+ "\t Data:"+x.getData() + "\t Totale della transazione" +x.getTotalAmount());
		x.tags().stream().forEach(t-> System.out.print("\nNome del Tag:"+t.getNome()+"\t Descrizione del Tag:"+t.getDescription()));
		System.out.println("]");
		
	}
}
