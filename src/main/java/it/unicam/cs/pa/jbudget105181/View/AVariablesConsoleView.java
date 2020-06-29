package it.unicam.cs.pa.jbudget105181.View;

import java.util.Scanner;

public abstract class AVariablesConsoleView {
	Scanner scanner= new Scanner(System.in);
	public abstract void title();
	public void successfull() {
		System.out.println("Inserimento corretto");
	}
	public static void clearScreen() {  
	    System.out.print("\033[H\033[2J");  
	    System.out.flush();  
	   }
	public void rimozioneAvvenuta() {
		System.out.println("Rimozione avvenuta con successo!");
	}
	public void errorRimozione() {
		System.out.println("Non è stato possibile effettuare la rimozione!");
	}
	public String inputInt() {
		System.out.println("Inserire un ID");
		return scanner.nextLine();
	}

	
	public void vuota() {
		System.out.println("Non sono presenti elementi!");
	}
}
