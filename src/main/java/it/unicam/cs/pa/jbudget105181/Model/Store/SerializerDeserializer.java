package it.unicam.cs.pa.jbudget105181.Model.Store;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import it.unicam.cs.pa.jbudget105181.Model.Ledger.ILedger;
import it.unicam.cs.pa.jbudget105181.Model.Ledger.Ledger;

import java.lang.reflect.Type;

public class SerializerDeserializer {
    private ILedger ledger;
    public SerializerDeserializer(){

    }

    public ILedger deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jo = json.getAsJsonObject();
        ILedger ledger = this.deserializerLedger(jo.get("Ledger"),ILedger.class,context);
        return new Ledger(ledger);
    }
    public ILedger deserializerLedger(JsonElement json, Type typeOfT, JsonDeserializationContext context){
      //  ILedger ledger=  json.getAsJsonObject().get(context);
        return null;
    }

}
