package it.unicam.cs.pa.jbudget105181.Model;

import it.unicam.cs.pa.jbudget105181.Model.Ledger.ILedger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class TxtWriter implements IWriter {

    private ObjectOutputStream out;
    private final String path;

    public TxtWriter(String path) throws IOException {
        this.path = path;
    }

    @Override
    public void write(ILedger object) throws IOException {
        out = new ObjectOutputStream(new FileOutputStream(path));
        out.writeObject(object);
        out.flush();
    }

    @Override
    public void close() throws IOException {
        out.close();
    }

}
