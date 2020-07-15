package it.unicam.cs.pa.jbudget105181.Model.Store;

import com.google.gson.Gson;
import it.unicam.cs.pa.jbudget105181.Model.Ledger.ILedger;

public class Deserializer {
    private final ILedger ledger;
    public Deserializer(ILedger ledger){
        this.ledger=ledger;
    }
    public void deserializerLedger(){
        Gson json=new Gson();
        String response=  json.toJson(ledger);

    }
}
