package it.unicam.cs.pa.jbudget105181.Model.Store;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unicam.cs.pa.jbudget105181.Model.Ledger.ILedger;
import it.unicam.cs.pa.jbudget105181.Model.Ledger.Ledger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * classe che ha la responsabilita' di leggere da un file json
 */
public class JsonReaderJBudget implements IReader{

    /**
     * variabile per leggere da un file.
     */
    private InputStreamReader in;

    /**
     * Path associato al file da dove avviene la lettura
     */
    private String path;

    /**
     * variabile per convertire un file json in un Ledger
     */
    private Gson gson;

    /**
     * costruttore di JsonReader
     * @param path
     * @throws FileNotFoundException
     */
    public JsonReaderJBudget(String path) throws FileNotFoundException {
        if(path.contains(".json")){
            this.path=path;
            this.in= new InputStreamReader(new FileInputStream(path));
            this.gson = new GsonBuilder().registerTypeAdapter(Ledger.class,new AdapterLedger()).create();
        }

    }

    /**
     * metodo per leggere un Ledger da un file json
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Override
    public ILedger read() throws IOException, ClassNotFoundException {
        ILedger report = this.gson.fromJson(in,Ledger.class);
        if(report == null)
            throw new NullPointerException();
        return report;
    }

    /**
     * metodo per chiudere le variabili istanziate per leggere
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        in.close();
    }
}
