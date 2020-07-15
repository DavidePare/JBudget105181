package it.unicam.cs.pa.jbudget105181.Model.Store;

import it.unicam.cs.pa.jbudget105181.Model.Ledger.ILedger;

import java.io.IOException;

public interface IReader {

    ILedger read() throws IOException, ClassNotFoundException;

    void close() throws IOException;
}
