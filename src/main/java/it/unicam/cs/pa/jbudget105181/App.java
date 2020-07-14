package it.unicam.cs.pa.jbudget105181;

/*import java.util.Iterator;
import java.util.List;*/

import it.unicam.cs.pa.jbudget105181.GUIView.FXMLStart;
import javafx.application.Application;

public class App {
	
	public static void main(String[] args) {
		//Generare vista
		// Apertura vista
		Application.launch(FXMLStart.class);

	/*	int x=0;
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
		view.close();*/
		
	}
	
}


//https://docs.oracle.com/javase/8/docs/api/java/time/LocalDate.html