package it.unicam.cs.pa.jbudget105181.Controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import it.unicam.cs.pa.jbudget105181.Model.ILedger;
import it.unicam.cs.pa.jbudget105181.Model.ITag;

public class ControllerTransazioni {
	
	
	public boolean checkData(String s) {
		DateTimeFormatter formatter= DateTimeFormatter.ISO_LOCAL_DATE;
		try {
			formatter.parse(s);
			return true;
		}catch(Exception e) {
			System.err.println("Errore"+ e.getMessage());
		}
		return false;	
	}
	
	public LocalDate verifyData(String s) {
		LocalDate data=null;
		if(checkData(s)) {
			data=LocalDate.parse(s);
		}
		return data;
	}
	
	public boolean existTag(ILedger ledger,ITag tag) {
		long c= ledger.getTags().stream().filter(t ->(t.getNome().compareTo(tag.getNome())==0 && t.getDescription().compareTo(tag.getDescription())==0)).count();
		return c==1;
	}
	
	
	public boolean alreadyExistTag(ITag ricerca, ILedger ledger) {
		List<ITag> appoggio=new ArrayList<>(); 
		if(ledger.getTags().isEmpty()) {
			return true;
		}
		ledger.getTags().stream().filter(t -> ( ricerca.getDescription().compareTo(t.getDescription())==0 && ricerca.getNome().compareTo(t.getNome())==0)  ).forEach(t -> appoggio.add(ricerca));
		return appoggio.isEmpty();
	}
	
	public boolean yesornoResponse(String s) {
		s=s.toUpperCase();
		return s.compareTo("SI") == 0 || s.compareTo("Y") == 0;
	}
	public boolean nonPresenteInLista(ITag tag ,List<ITag> lista) {
		if(lista.isEmpty()) {
			return true;
		}
		long num=lista.stream().filter(t -> ( tag.getDescription().compareTo(t.getDescription())==0 && tag.getNome().compareTo(t.getNome())==0)).count();
		return num==0;
	}
	public int intValue(String s) {
		int c=-1;
		try {
			c=Integer.parseInt(s);
			return c;
		}catch(Exception e) {
			return c;
		}
	}
	
	
}
