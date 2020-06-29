package it.unicam.cs.pa.jbudget105181.View;

import java.util.Scanner;

import it.unicam.cs.pa.jbudget105181.Model.ITag;

public class ConsoleViewTag extends AVariablesConsoleView{
	Scanner scanner= new Scanner(System.in);
	public String descriptionInput() {
		System.out.println("Inserisci la descrizione del Tag");
		String desc=scanner.nextLine();
		return desc;
	}
	public String nameInput() {
		System.out.println("Inserisci il nome del Tag");
		String name=scanner.nextLine();
		return name;
	}
	public void alreadyExist() {
		System.out.println("Attenzione il tag non è stato creato perchè ne esiste uno identico!");
	}
	public void vuota() {
		System.out.println("Non sono presenti Tag");
	}
	@Override
	public void title() {
		// TODO Auto-generated method stub
		
	}
	public void stampaTag(ITag x) {
		System.out.println("Nome del Tag:"+x.getNome()+" Descrizione del Tag:"+x.getDescription());
	}
}
