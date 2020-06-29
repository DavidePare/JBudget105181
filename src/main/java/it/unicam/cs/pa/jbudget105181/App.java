package it.unicam.cs.pa.jbudget105181;

import java.util.InputMismatchException;
/*import java.util.Iterator;
import java.util.List;*/
import java.util.Scanner;

import it.unicam.cs.pa.jbudget105181.Controller.ApplicationOperationCase;
import it.unicam.cs.pa.jbudget105181.Model.Ledger;
import it.unicam.cs.pa.jbudget105181.View.ConsoleView;
import it.unicam.cs.pa.jbudget105181.View.FXMLStart;
import javafx.application.Application;

public class App {
	
	public static void main(String[] args) {
		//Generare vista
		// Apertura vista
		Application.launch(FXMLStart.class);

		int x=0;
		ConsoleView view= new ConsoleView();
		view.open();
		Ledger ledger= new Ledger();
		Scanner sc= new Scanner(System.in);
		while(x!=15) {
			try{
				view.menu();
				x=view.readInt();
				options(x, ledger);
			}catch( IllegalStateException e) {
				System.out.println("Inserire un numero");
			}catch(InputMismatchException e) {
				System.err.println("L'applicazione associa a questo errore:"+e.getMessage());
				continue;
			}finally{
				if(x<0 || x>15) System.out.println("Inserire da 1 a 10");
			}
		}
		sc.close();
		view.close();
		
	}
	public static void options(int x, Ledger ledger) {
		ApplicationOperationCase operation= new ApplicationOperationCase();
		switch(x) {
			case 1:
				operation.caseOneInsertTag(ledger);
				break;
				
				
			case 2:
				//Inserimento nuovo Account
				operation.caseTwoInsertAccount(ledger);
				
				break;
			case 3:
				// Creazione nuova transazione 
				operation.caseThreeInsertTransaction(ledger);
				
				
				break;
			case 4:
				//Inserimento transazione :) :) :) :) :)
				operation.caseFourAddMovementToTransaction(ledger);
				break;
			case 5:
				operation.caseFiveInsertMovement(ledger);
				break;
			case 6:
				operation.caseSixPrintAccountMovement(ledger);
				break;
			case 7:
				//Stampa delle transazioni
				operation.caseSevenPrintTransaction(ledger);
				break;
			case 8:
				//Stampa degli Account
				operation.caseEightPrintAccount(ledger);
			
				break;
			case 9:
				//Stampa dei Tag
				operation.caseNinePrintTag(ledger);
				break;
			case 10:
				operation.caseTenRemoveTag(ledger);
				break;
			case 11:
				operation.caseElevenRemoveTransaction(ledger);
				break;
			case 12:
				operation.caseTwelveRemoveMovement(ledger);
				break;
			case 13:
				operation.caseThirteenRemoveAccount(ledger);
				break;
			case 14:
				operation.caseFourteenPrintMovForTransaction(ledger);
				break;
			case 15:
				
				break;
		}
	}
	
}


//https://docs.oracle.com/javase/8/docs/api/java/time/LocalDate.html