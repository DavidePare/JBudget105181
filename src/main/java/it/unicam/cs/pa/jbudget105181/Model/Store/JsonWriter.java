package it.unicam.cs.pa.jbudget105181.Model.Store;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import it.unicam.cs.pa.jbudget105181.Model.Ledger.ILedger;
import it.unicam.cs.pa.jbudget105181.Model.Ledger.Ledger;

import java.io.FileWriter;
import java.io.IOException;

public class JsonWriter implements IWriter{
    private String path;
    private Gson gson;
    public JsonWriter(String path){
        this.path=path;
    }
    @Override
    public void write(ILedger object) throws IOException {
        if(!path.contains(".json"))
            path = path+".json";
        this.path = path;
        this.gson= new GsonBuilder().setPrettyPrinting().create();
        String js=gson.toJson(object);
     /*   this.gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter
                (Ledger.class,new SerializerDeserializer()).create();*/
    }

    @Override
    public void close() throws IOException {

    }
}
