package it.unicam.cs.pa.jbudget105181.Model;

import java.io.IOException;

public interface Reader {

    ILedger read() throws IOException, ClassNotFoundException;

    void close() throws IOException;
}
