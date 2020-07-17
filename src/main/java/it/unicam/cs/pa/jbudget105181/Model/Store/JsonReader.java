package it.unicam.cs.pa.jbudget105181.Model.Store;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unicam.cs.pa.jbudget105181.Model.Ledger.ILedger;
import it.unicam.cs.pa.jbudget105181.Model.Ledger.Ledger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class JsonReader implements IReader{

    /**
     * Variabile utile per leggere da un file.
     */
    private InputStreamReader in;
    /*
     * Path associato al file da dove avviene la lettura
     */
    private String path;

    /*
     * Variabile con la responsabilità di convertire un file gson in unLedger
     */
    private Gson gson;

    public JsonReader(String path) throws FileNotFoundException {
        if(path.contains(".json")){
            this.path=path;
            this.in= new InputStreamReader(new FileInputStream(path));
            this.gson = new GsonBuilder().registerTypeAdapter(Ledger.class,new AdapterLedger()).create();
        }

    }
    @Override
    public ILedger read() throws IOException, ClassNotFoundException {
        ILedger report = this.gson.fromJson(in,Ledger.class);
        if(report == null)
            throw new NullPointerException();
        return report;
    }

    @Override
    public void close() throws IOException {
        in.close();
    }
}
