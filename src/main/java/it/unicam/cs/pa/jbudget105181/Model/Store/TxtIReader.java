package it.unicam.cs.pa.jbudget105181.Model.Store;

import it.unicam.cs.pa.jbudget105181.Model.Ledger.ILedger;
import it.unicam.cs.pa.jbudget105181.Model.Store.IReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class TxtIReader implements IReader {
    /**
     * variabile per leggere da file
     */
    private final ObjectInputStream in;

    /**
     * Costruttore lettore da file
     * @param path
     * @throws IOException
     */
    public TxtIReader(String path) throws IOException {
        in = new ObjectInputStream(new FileInputStream(path));
    }

    /**
     * metodo che ha la responsabilita' di leggere un ledger da un file txt
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Override
    public ILedger read() throws IOException, ClassNotFoundException {
        return (ILedger) in.readObject();
    }

    /**
     * metodo che ha la responsabilita' di chiudere la variabile di lettura
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        in.close();
    }
}
