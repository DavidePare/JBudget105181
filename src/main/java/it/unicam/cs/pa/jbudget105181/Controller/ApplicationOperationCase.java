package it.unicam.cs.pa.jbudget105181.Controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import it.unicam.cs.pa.jbudget105181.Model.Account;
import it.unicam.cs.pa.jbudget105181.Model.AccountType;
import it.unicam.cs.pa.jbudget105181.Model.IAccount;
import it.unicam.cs.pa.jbudget105181.Model.ILedger;
import it.unicam.cs.pa.jbudget105181.Model.IMovement;
import it.unicam.cs.pa.jbudget105181.Model.ITag;
import it.unicam.cs.pa.jbudget105181.Model.ITransazione;
import it.unicam.cs.pa.jbudget105181.Model.Movement;
import it.unicam.cs.pa.jbudget105181.Model.MovementType;
import it.unicam.cs.pa.jbudget105181.Model.Tag;
import it.unicam.cs.pa.jbudget105181.Model.Transazione;
import it.unicam.cs.pa.jbudget105181.View.AbsConsoleMovimentiTransazioni;
import it.unicam.cs.pa.jbudget105181.View.ConsoleViewAccount;
import it.unicam.cs.pa.jbudget105181.View.ConsoleViewMovimenti;
import it.unicam.cs.pa.jbudget105181.View.ConsoleViewTag;
import it.unicam.cs.pa.jbudget105181.View.ConsoleViewTransazioni;

public class ApplicationOperationCase implements IApplicationOperationCase{
	public ApplicationOperationCase() {	
	}
	//Caso Uno  
	public void caseOneInsertTag(ILedger ledger) {
		ConsoleViewTag vistaTag= new ConsoleViewTag();
		ITag t= new Tag(vistaTag.descriptionInput(),vistaTag.nameInput());
		ControllerTag controlTag= new ControllerTag();
		if(!controlTag.alreadyExist(t,ledger)) {
			vistaTag.alreadyExist();
		}
		else {
			vistaTag.successfull();
			ledger.addTag(t);
		}		
	}
	//Inserimento nuovo Account -- Caso Due
	public void caseTwoInsertAccount(ILedger ledger) {
		ConsoleViewAccount vistaAccount=new ConsoleViewAccount();
		ControllerAccount controlAccount= new ControllerAccount();
		AccountType type=controlAccount.controlType(vistaAccount.TypeAccountInsert());
		if(type != null) {
			System.out.println(type);
			int resID=controlAccount.controlID(ledger, vistaAccount.insertID());
			if(errorCodeAccount(resID,vistaAccount)) {
				String nome=vistaAccount.nameInput();
				String denaroinput=vistaAccount.insertAmount();
				Double denaro= controlAccount.doubleValue(denaroinput);
				if(!denaro.isNaN()) {
					if(controlAccount.controlName(ledger, nome)) {
						IAccount account = new Account(resID,nome,vistaAccount.descriptionInput(),type,denaro);
						ledger.addAccount(account);
						vistaAccount.inserimentoCorretto();
					}else vistaAccount.alreadyExistName();
				}
			}
		} else vistaAccount.accountTypeInesistente();
	}
	
	public boolean errorCodeAccount(int resID, ConsoleViewAccount vistaAccount) {
		if(resID==-1) {
			vistaAccount.IDnegativo();
			return false;
		}
		if(resID ==-2) {
			vistaAccount.alreadyExistID();
			return false;
		}
		if(resID == -3) {
			vistaAccount.IDformatError();
			return false;
		}
		return true;
	}
	
	// Caso Tre
	public void caseThreeInsertTransaction(ILedger ledger) {
		 
		ConsoleViewTransazioni viewTransaction = new ConsoleViewTransazioni();
		ControllerTransazioni controllerTransaction=new ControllerTransazioni();
		LocalDate dataTransazione=controllerTransaction.verifyData(viewTransaction.insertData());
		if(validyDataRitornata(dataTransazione,viewTransaction)) {
			//Take an ID
			int id=returnIDforTransaction(ledger);
			boolean pagamento=booleanPagamentoTransazione(dataTransazione);
			List<ITag> listaTag= insertTagforTransazioni(ledger,controllerTransaction,viewTransaction);
			ITransazione transazioneNew= new Transazione(id,dataTransazione,listaTag,pagamento);
			ledger.addTransazione(transazioneNew);
		}
	}
	


	public List<ITag> insertTagforTransazioni(ILedger l,ControllerTransazioni control ,ConsoleViewTransazioni viewTr) {
		boolean continua=control.yesornoResponse(viewTr.insertTagYorN());;
		List<ITag> listtag = new ArrayList<>();
		while(continua) {
			ITag tagNew=viewTr.inputTag();
			if(control.nonPresenteInLista(tagNew,listtag)) {
				if(!control.alreadyExistTag(tagNew,l)) {
					viewTr.successfull();
					listtag.add(tagNew);
				}else {
					String s=viewTr.aggiungereTag();
					if(control.yesornoResponse(s)) {
						l.addTag(tagNew);;
						listtag.add(tagNew);
					}
				}
				String proseguire=viewTr.continuareTag();
				continua=control.yesornoResponse(proseguire);
			}else viewTr.presenteLista();
			
		}
		return listtag;
	}

	public List<ITag> insertTagforMovements(ILedger l,ControllerMovimenti control ,ConsoleViewMovimenti viewTr) {
		boolean continua=control.yesornoResponse(viewTr.insertTagYorN());
		List<ITag> listtag = new ArrayList<>();
		while(continua) {
			ITag tagNew=viewTr.inputTag();
			if(control.nonPresenteInLista(tagNew,listtag)) {
				if(!control.alreadyExistTag(tagNew,l)) {
					viewTr.successfull();
					listtag.add(tagNew);
				}else {
					String s=viewTr.aggiungereTag();
					if(control.yesornoResponse(s)) {
						l.addTag(tagNew);
						listtag.add(tagNew);
					}
				}
				String proseguire=viewTr.continuareTag();
				continua=control.yesornoResponse(proseguire);
			}else viewTr.presenteLista();
			
		}
		return listtag;
	}
	
	//Sia per Transazioni che per movimenti 
	public boolean booleanPagamentoTransazione(LocalDate dataTransazione) {
		
		if(dataTransazione.isBefore(LocalDate.now())) return true;
		return false; 
	}
	
	public boolean validyDataRitornata(LocalDate dataTransazione,AbsConsoleMovimentiTransazioni viewForDate) {
		if(dataTransazione == null) viewForDate.errorDate();
		return dataTransazione != null;
	}
	
	 
	public int returnIDforTransaction(ILedger ledger) {
		ArrayList<ITransazione> lista=ledger.getAllTransactions();
		
		if(lista.isEmpty()) {
			return 1;
		}
		int max=0;
		for(ITransazione t: lista) {
			if(max < t.getID()) max= t.getID();
		}
		return max+1;
	}
	
	
	public void caseFourAddMovementToTransaction(ILedger ledger) {
		ConsoleViewMovimenti viewMovimenti = new ConsoleViewMovimenti();
		ControllerMovimenti control =new ControllerMovimenti();
		if(ledger.getAccounts().isEmpty() || ledger.getAllTransactions().isEmpty()) viewMovimenti.errorNoAccOrTran();
		else {
			String messIDT=viewMovimenti.messageidTransazione();
			int IDT=IDverifica(control.verificaIDtransazione(messIDT, ledger),ledger,viewMovimenti,true);
			if(IDT>0) {
				String amountS=viewMovimenti.insertAmount();
				Double amount=control.doubleValue(amountS);
				if(!Double.isNaN(amount) && amount>0) {
		//			String tipoMovimento=viewMovimenti.TypeMovementInsert();
					MovementType type= control.controlType(viewMovimenti.TypeMovementInsert());
					if(type != null) {
						String accountScelto= viewMovimenti.inputAccount();
						int IDAccount=IDverifica(control.verificaIDAccount(accountScelto, ledger),ledger,viewMovimenti,false);
						if(IDAccount > 0) {
							int idmovimento=control.generaIDmovimento(ledger,IDT);
							List<ITag> listaTag= insertTagforMovements(ledger,control,viewMovimenti);
							String description=viewMovimenti.descrizioneMov();
							Movement mInserito= new Movement(idmovimento,description,type,amount,ledger.getAccountForID(IDAccount),listaTag,IDT);
							risolviMovimento(mInserito,ledger);
						}
					}else viewMovimenti.errorType();
				}else viewMovimenti.errorDouble();
			}
		}
	}
	public void caseFiveInsertMovement(ILedger ledger) {
		ConsoleViewMovimenti viewM = new ConsoleViewMovimenti();
		ControllerMovimenti control = new ControllerMovimenti();
		int idTransazione=returnIDforTransaction(ledger);
		LocalDate dataTransazione=control.verifyData(viewM.insertData());
		if(validyDataRitornata(dataTransazione,viewM)) {
			List<ITag> tags =insertTagforMovements(ledger,control,viewM);
			String amountS=viewM.insertAmount();
			Double amount=control.doubleValue(amountS);
			if(!Double.isNaN(amount) && amount>0) {
				MovementType type= control.controlType(viewM.TypeMovementInsert());
				if(type != null) {
					String accountScelto= viewM.inputAccount();
					int IDAccount=IDverifica(control.verificaIDAccount(accountScelto, ledger),ledger,viewM,false);
					if(IDAccount > 0) {
						String description=viewM.descrizioneMov();
						Transazione t= new Transazione(idTransazione,dataTransazione,tags,false);
						Movement mInserito= new Movement(1,description,type,amount,ledger.getAccountForID(IDAccount),tags,idTransazione);
						ledger.addTransazione(t);
						risolviMovimento(mInserito,ledger);
					}
				}else viewM.errorType();	
			}else viewM.errorDouble();
		}
	}
	public void risolviMovimento(IMovement m,ILedger ledger){
		int idTransazione=m.getIDTransazione();
		ledger.getAllTransactions().stream().filter(t -> t.getID() == idTransazione).forEach(t -> t.addMovement(m));
		ledger.getAccounts().stream().filter(t -> t.getIDAccount() == m.getAccount().getIDAccount()).forEach(t -> t.addMovement(m));
	}
	
	public int IDverifica(int passID,ILedger ledger,ConsoleViewMovimenti viewMovimenti, boolean accOrTransaction) {
		if(accOrTransaction) {
			if(passID>0) 		return passID;
			if(passID==-3)		caseSevenPrintTransaction(ledger);
			if(passID==-1) 		viewMovimenti.errorNoIDesiste();
			if(passID==-2)		viewMovimenti.errorInaspettaStringa();
			return passID;
		}else {
			if(passID==-1) 		viewMovimenti.errorNoIDesiste();
			if(passID==-2)		viewMovimenti.errorInaspettaStringa();
			return passID;
		}
	}
	
	public void caseSixPrintAccountMovement(ILedger ledger) {
		ConsoleViewMovimenti viewM=new ConsoleViewMovimenti();
		ControllerMovimenti control =new ControllerMovimenti();
		String accountScelto= viewM.inputAccount();
		int IDAccount=IDverifica(control.verificaIDAccount(accountScelto, ledger),ledger,viewM,false);
		if(IDAccount>0) {
			ledger.getAccountForID(IDAccount).getMovementsOfAccount().stream().filter(t -> t.getAccount().getIDAccount() ==IDAccount).forEach(t-> viewM.printMovement(t));
		}
	}
	public void caseSevenPrintTransaction(ILedger ledger) {
		ConsoleViewTransazioni viewTr= new ConsoleViewTransazioni();
		if(ledger.getAllTransactions().isEmpty()) viewTr.vuota();
		else{
			for(ITransazione x : ledger.getAllTransactions()) {
				viewTr.stampaTransazioni(x);
			}
		}
	}
	
	
	public void caseEightPrintAccount(ILedger ledger) {
		ConsoleViewAccount vistaAccountSt=new ConsoleViewAccount();
		if(ledger.getAccounts().isEmpty()) {
			vistaAccountSt.vuota();
		}else {
			List<IAccount> accountLista=ledger.getAccounts();
			Iterator<IAccount> accL= accountLista.iterator();
			while(accL.hasNext()) {
				IAccount accLapp=accL.next();
				vistaAccountSt.stampaAccount(accLapp);
			}
		}
	}
	
	public void caseNinePrintTag(ILedger ledger) {
		ConsoleViewTag viewTag= new ConsoleViewTag();
		if(ledger.getTags().isEmpty()) viewTag.vuota();
		else{
			List<ITag> printerList=ledger.getTags();
			Iterator<ITag> iteratore= printerList.iterator();
			while(iteratore.hasNext()) {
				ITag a=iteratore.next();
				viewTag.stampaTag(a);
			}
		}
	}
	public void caseTenRemoveTag(ILedger ledger) {
		ConsoleViewTag vistaTag= new ConsoleViewTag();
		ITag t= new Tag(vistaTag.descriptionInput(),vistaTag.nameInput());
		Predicate<ITag> p= (x-> x.getDescription().compareTo(t.getDescription())==0 && x.getNome().compareTo(t.getNome())==0);
		if(ledger.removeTag(p)) {
			vistaTag.rimozioneAvvenuta();
		}
		else vistaTag.errorRimozione();
	}
	public void caseElevenRemoveTransaction(ILedger ledger) {
		ConsoleViewTransazioni viewT= new ConsoleViewTransazioni();
		ControllerTransazioni controlT= new ControllerTransazioni();
		if(!ledger.getAllTransactions().isEmpty()) {
			int id=controlT.intValue(viewT.inputInt());
			Predicate<ITransazione> p= (t->t.getID()==id);
			ledger.removeTransaction(p);
			viewT.rimozioneAvvenuta();
		}else viewT.vuota();
	}
	public void caseTwelveRemoveMovement(ILedger ledger) {

		ConsoleViewMovimenti viewM = new ConsoleViewMovimenti();
		if(!ledger.getAllTransactions().isEmpty()) {

			ControllerMovimenti controlM= new ControllerMovimenti();
			int id=controlM.intValue(viewM.inputInt());
			printMovimentiTransazine(ledger,id);
			int idMov=controlM.intValue(viewM.inputInt());
			ledger.removeMovement(id, idMov);
		}else viewM.vuota();
	}
	
	public void printMovimentiTransazine(ILedger ledger,int idTrans) {
		ConsoleViewMovimenti viewM= new ConsoleViewMovimenti();
		if(!ledger.getAllTransactions().isEmpty()) {
			Predicate<ITransazione> p= (t-> t.getID()== idTrans);
			if(!ledger.getTransaction(p).isEmpty()) {
				ITransazione list=ledger.getTransaction(p).get(0);
				if(list != null) {
					Iterator<IMovement> lista=list.movements().iterator();
					while(lista.hasNext()) {
						viewM.printMovement(lista.next());
					}
				}
			}
		}
	}
	public void caseFourteenPrintMovForTransaction(ILedger ledger) {
		ConsoleViewTransazioni viewT= new ConsoleViewTransazioni();
		ControllerTransazioni controlT= new ControllerTransazioni();
		int id=controlT.intValue(viewT.inputInt());
		printMovimentiTransazine(ledger,id);
	}
	
	public void caseThirteenRemoveAccount(ILedger ledger) {
		
	}
	
}
