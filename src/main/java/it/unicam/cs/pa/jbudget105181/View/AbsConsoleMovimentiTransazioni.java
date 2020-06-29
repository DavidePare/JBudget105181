package it.unicam.cs.pa.jbudget105181.View;

import it.unicam.cs.pa.jbudget105181.Model.ITag;
import it.unicam.cs.pa.jbudget105181.Model.Tag;

public abstract class AbsConsoleMovimentiTransazioni extends AVariablesConsoleView{
	public String aggiungereTag() {
		System.out.println("Il tag inserito non è presente nella lista dei tag vuoi crearlo?");
		String s=scanner.nextLine();
		return s;
	}
	public String continuareTag() {
		System.out.println("vuole aggiungere altri tag? (si, altri casi =no)");
		String s=scanner.nextLine();
		return s;
	}
	public ITag inputTag() {
		System.out.println("Inserire il nome di un tag già inserito altrimenti crealo nuovo");
		String s1= scanner.nextLine();
		System.out.println("Inserire la descrizione di un tag già inserito altrimenti crealo nuovo");
		String s2=scanner.nextLine();
		ITag t=new Tag(s1,s2);
		return t;
	}
	public String insertTagYorN() {
		System.out.println("Gradisci aggiungere un tag  a questo movimento?");
		String s=scanner.nextLine();
		return s;
	}

	public void presenteLista() {
		System.out.println("Il Tag è già presente");
	}
	public void errorDate() {
		System.out.println("Data inserita errata!");
	}
	public String insertData() {
		System.out.println("Inserire una data valida secondo il seguente formato = YYYY-MM-DD");
		System.out.println("Esempio:2010-06-15");
		String s= scanner.nextLine();
		return s;
	}
}
