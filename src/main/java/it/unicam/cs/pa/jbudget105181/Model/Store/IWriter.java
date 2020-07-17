package it.unicam.cs.pa.jbudget105181.Model.Store;

import it.unicam.cs.pa.jbudget105181.Model.Ledger.ILedger;

import java.io.IOException;

/**
 * interfaccia che ha il compito di scrivere su file.
 */
public interface IWriter {
    /**
     * metodo per scrivere un Ledger
     * @param object
     * @throws IOException
     */
    void write(ILedger object) throws IOException;

    /**
     * metodo per chiudere le variabili istanziate per scrivere
     * @throws IOException
     */
    void close() throws IOException;
}
