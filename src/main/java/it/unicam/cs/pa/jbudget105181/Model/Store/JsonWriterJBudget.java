package it.unicam.cs.pa.jbudget105181.Model.Store;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import it.unicam.cs.pa.jbudget105181.Model.Ledger.ILedger;
import it.unicam.cs.pa.jbudget105181.Model.Ledger.Ledger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class JsonWriterJBudget implements IWriter{
    private String path;
    private Gson gson;
    public JsonWriterJBudget(String path){
        this.path=path;
    }
    @Override
    public void write(ILedger object) throws IOException {

        if(!path.contains(".json"))
            path = path+".json";
      //  JsonWriter writer = new JsonWriter(new FileWriter(path));
        //set indentation for pretty print
     //   writer.setIndent("\t");
        //start writing
     //   writer.beginObject(); //{
    //    writer.name("transaction").value(object.getAllTransactions());

        this.path = path;
        this.gson= new GsonBuilder().setPrettyPrinting().create();
        String js=gson.toJson(object);
     /*   this.gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter
                (Ledger.class,new SerializerDeserializer()).create();*/

    }

    @Override
    public void close() throws IOException {

    }
}
