package it.unicam.cs.pa.jbudget105181.Controller;

import java.util.ArrayList;
import java.util.List;

import it.unicam.cs.pa.jbudget105181.Model.ILedger;
import it.unicam.cs.pa.jbudget105181.Model.ITag;

public class ControllerTag {
	public boolean alreadyExist(ITag ricerca, ILedger ledger) {
		List<ITag> appoggio=new ArrayList<>(); 
		if(ledger.getTags().isEmpty()) {
			return true;
		}
		ledger.getTags().stream().filter(t -> ( ricerca.getDescription().compareTo(t.getDescription())==0 && ricerca.getNome().compareTo(t.getNome())==0)  ).forEach(t -> appoggio.add(ricerca));
		return appoggio.isEmpty();
	}

}
