package it.unicam.cs.pa.jbudget105181.Model.Store;

import it.unicam.cs.pa.jbudget105181.Model.Ledger.ILedger;

public class Deserializer {
    private final ILedger ledger;
    public Deserializer(ILedger ledger){
        this.ledger=ledger;
    }

}
