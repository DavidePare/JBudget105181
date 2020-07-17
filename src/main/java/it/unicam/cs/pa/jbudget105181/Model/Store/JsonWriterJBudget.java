package it.unicam.cs.pa.jbudget105181.Model.Store;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import it.unicam.cs.pa.jbudget105181.Model.Ledger.ILedger;
import it.unicam.cs.pa.jbudget105181.Model.Ledger.Ledger;

import java.io.*;

public class JsonWriterJBudget implements IWriter{
    private String path;
    private Gson gson;
    private OutputStreamWriter out;
    public JsonWriterJBudget(String path){
        this.path=path;
    }
    @Override
    public void write(ILedger object) throws IOException {
        if(!path.contains(".json"))
            path = path+".json";
        this.out= new OutputStreamWriter(new FileOutputStream(path));
        this.gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter
                (Ledger.class,new AdapterLedger()).create();
        String write=gson.toJson(object);
        out.write(write);
        out.flush();
    }

    @Override
    public void close() throws IOException {
        out.close();
    }
}
