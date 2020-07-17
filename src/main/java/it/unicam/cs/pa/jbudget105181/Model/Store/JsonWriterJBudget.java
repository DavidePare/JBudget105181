package it.unicam.cs.pa.jbudget105181.Model.Store;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import it.unicam.cs.pa.jbudget105181.Model.Ledger.ILedger;
import it.unicam.cs.pa.jbudget105181.Model.Ledger.Ledger;

import java.io.*;

/**
 * classe che ha la responsabilita' di scrivere su un file json
 */
public class JsonWriterJBudget implements IWriter{

    /**
     * Path associato al file da dove avviene la lettura
     */
    private String path;

    /**
     * variabile per convertire un file json in un Ledger
     */
    private Gson gson;

    /**
     * variabile per leggere da un file.
     */
    private OutputStreamWriter out;

    /**
     * costruttore di JsonWriterJBudget
     * @param path
     */
    public JsonWriterJBudget(String path){
        this.path=path;
    }

    /**
     * metodo per scrivere un Ledger
     * @param object
     * @throws IOException
     */
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

    /**
     * metodo per chiudere le variabili istanziate per scrivere
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        out.close();
    }
}
