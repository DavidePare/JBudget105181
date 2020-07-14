package it.unicam.cs.pa.jbudget105181.Model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class TxtIReader implements IReader {

    private final ObjectInputStream in;

    public TxtIReader(String path) throws IOException {
        in = new ObjectInputStream(new FileInputStream(path));
    }

    @Override
    public ILedger read() throws IOException, ClassNotFoundException {
        return (ILedger) in.readObject();
    }

    @Override
    public void close() throws IOException {
        in.close();
    }
}
