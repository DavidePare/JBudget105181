package it.unicam.cs.pa.jbudget105181.Model.Store;

import it.unicam.cs.pa.jbudget105181.Model.Ledger.ILedger;
import it.unicam.cs.pa.jbudget105181.Model.Store.IWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class TxtWriter implements IWriter {
    /**
     * variablie per scrivere su file txt
     */
    private ObjectOutputStream out;
    /**
     * path associato al file dove bisogna scrivere
     */
    private final String path;

    /**
     * Costruttore per TxtWriter
     */
    public TxtWriter(String path) throws IOException {
        this.path = path;
    }

    /**
     * metodo con la responsabilita' di scrivere dentro un file txt
     * @param object
     * @throws IOException
     */
    @Override
    public void write(ILedger object) throws IOException {
        out = new ObjectOutputStream(new FileOutputStream(path));
        out.writeObject(object);
        out.flush();
    }

    /**
     * metoo che ha la responsabilita' di chiudere la variabile di scrittura
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        out.close();
    }

}
