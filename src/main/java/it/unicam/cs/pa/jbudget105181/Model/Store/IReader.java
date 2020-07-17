package it.unicam.cs.pa.jbudget105181.Model.Store;

import it.unicam.cs.pa.jbudget105181.Model.Ledger.ILedger;

import java.io.IOException;

/**
 * interfaccia che ha il compito di leggere da file.
 */
public interface IReader {

    /**
     * metodo per leggere un Ledger
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    ILedger read() throws IOException, ClassNotFoundException;

    /**
     * metodo per chiudere le variabili istanziate per leggere
     * @throws IOException
     */
    void close() throws IOException;
}
