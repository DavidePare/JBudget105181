package it.unicam.cs.pa.jbudget105181.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class ConsoleView implements IViews{
	private final BufferedReader reader;
	public ConsoleView() {
        reader = new BufferedReader(new InputStreamReader(System.in));
        
    }
	public void open() {
		hello();
	}
	public void close() {
		try {
			reader.close();
			printGoodbye();
		}catch (IOException e) {
            e.printStackTrace();
		}
	}
	 
	
	private void hello() {
		
        System.out.println("******************************");
        System.out.println("*         Bugdet Fam         *");
        System.out.println("******************************");
		 
    }
	
	private void printGoodbye() {
        System.out.println("\n\nGrazie per aver usato l'applicazione Budget Familiare!");
        System.out.println("Buonagiornata!\n\n");
    }
	public void menu() {
		System.out.println("********Menu********");
		System.out.println("1)Creazione Tag");
		System.out.println("2)Creazione dell'account");
		System.out.println("3)Creazione di una transazione");
		System.out.println("4)Aggiungi movimento ad una transazione");
		System.out.println("5)Creazione di un movimento");
		System.out.println("6)Stampa tutti i movimenti per un account");
		System.out.println("7)Stampa tutte le transazioni");
		System.out.println("8)Stampa tutti account");
		System.out.println("9)Stampa tag");
		System.out.println("10)Rimozione Tag");
		System.out.println("11)Rimozione Transazione");
		System.out.println("12)Rimozione Movimento");
		System.out.println("13)Rimozione Account");
		System.out.println("14)Stampa movimenti transazione");
		System.out.println("15)Esci");

		
		
	}
	public int readInt() {
		
		try{
			String s=reader.readLine();
			int x = Integer.parseInt(s);
			return x;
		}catch(NumberFormatException e) {
			System.out.println("Inserire un numero da 1 a 10");
		}catch(IOException e) {
			System.out.println("Errore"+e.getMessage());	
		}
		return 0;
		
	}
}
