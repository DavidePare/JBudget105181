package it.unicam.cs.pa.jbudget105181.Model;

import java.io.IOException;

public interface Writer {

    void write(ILedger object) throws IOException;

    void close() throws IOException;
}
