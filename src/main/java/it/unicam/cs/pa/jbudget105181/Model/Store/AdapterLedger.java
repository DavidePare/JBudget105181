package it.unicam.cs.pa.jbudget105181.Model.Store;

import com.google.gson.*;
import it.unicam.cs.pa.jbudget105181.Model.Ledger.ILedger;

import java.lang.reflect.Type;


public class AdapterLedger implements JsonSerializer<ILedger>, JsonDeserializer<ILedger>{


    @Override
    public ILedger deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        return null;
    }

    @Override
    public JsonElement serialize(ILedger src, Type typeOfSrc, JsonSerializationContext context) {
        return null;
    }
}
