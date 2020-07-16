package it.unicam.cs.pa.jbudget105181.Model.Store;

import com.google.gson.*;
import it.unicam.cs.pa.jbudget105181.Model.Account.IAccount;
import it.unicam.cs.pa.jbudget105181.Model.Ledger.ILedger;
import it.unicam.cs.pa.jbudget105181.Model.Movement.IMovement;
import it.unicam.cs.pa.jbudget105181.Model.Movement.MovementType;
import it.unicam.cs.pa.jbudget105181.Model.Tag.ITag;
import it.unicam.cs.pa.jbudget105181.Model.Transaction.ITransazione;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class AdapterLedger implements JsonSerializer<ILedger>, JsonDeserializer<ILedger>{


    @Override
    public ILedger deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        return null;
    }

    @Override
    public JsonElement serialize(ILedger src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jo = new JsonObject();
        jo.add("Tags",tagsSerializer(src.getTags(), context));
        jo.add("Budget",context.serialize(src.getBudget()));
        jo.add("Accounts", accountsSerializer(src.getAccounts(),context));
        jo.add("Transactions", transactionsSerializer(src.getAllTransactions(),context));
        return jo;
    }

    private JsonElement tagsSerializer(List<ITag> src, JsonSerializationContext context){
        JsonArray ja = new JsonArray();
        src.parallelStream().forEach(t->ja.add(tagSerializer(t, context)));
        return ja;
    }


    private JsonElement transactionsSerializer(List<ITransazione> src, JsonSerializationContext context){
        JsonArray ja = new JsonArray();
        src.parallelStream().forEach(t->ja.add(serializeTransazione(t,context)));
        return ja;
    }


    private JsonElement accountsSerializer(List<IAccount> src, JsonSerializationContext context) {
        JsonArray ja = new JsonArray();
        src.parallelStream().forEach(a->ja.add(serializeAccount(a,context)));
        return ja;
    }

    public JsonElement tagSerializer(ITag src, JsonSerializationContext context) {
        JsonObject jo = new JsonObject();
        jo.add("Name", context.serialize(src.getNome()));
        jo.add("Description", context.serialize(src.getDescription()));
        return jo;
    }

    public JsonElement serializeAccount(IAccount src, JsonSerializationContext context) {
        JsonObject jo = new JsonObject();
        jo.add("ID", context.serialize(src.getID()));
        jo.add("Name", context.serialize(src.getNameAccount()));
        jo.add("Description", context.serialize(src.getDescriptionAccount()));
        jo.add("Type", context.serialize(src.getTypeAccount()));
        jo.add("OpeningBalance", context.serialize(src.getOpeningBalance()));
        return jo;
    }

    public JsonElement serializeTransazione(ITransazione src, JsonSerializationContext context) {
        JsonObject jo = new JsonObject();
        jo.add("ID", context.serialize(src.getID()));
        jo.add("Date", context.serialize(src.getData(), LocalDate.class));
      //  jo.add("Date", context.serialize(src.getData(), LocalDateTime.class));
      //  jo.add("Date",new JsonPrimitive(src.getData().format(DateTimeFormatter.ISO_LOCAL_DATE)));
        jo.add("TotalAmounts", context.serialize(src.getTotalAmount()));
        jo.add("Movements", movementsSerializer(src.movements(),context));
        jo.add("Description", context.serialize(src.getDescription()));
        return jo;
    }

    private JsonElement movementsSerializer(List<IMovement> src, JsonSerializationContext context){
        JsonArray ja = new JsonArray();
        src.parallelStream().forEach(m->ja.add(serializeMovement(m,context)));
        return ja;
    }

    public JsonElement serializeMovement(IMovement src, JsonSerializationContext context) {
        JsonObject jo = new JsonObject();
        jo.add("ID", context.serialize(src.getID()));
        jo.add("Type",context.serialize(src.getTipo(), MovementType.class));
        jo.add("Amount", context.serialize(src.getAmount()));
        jo.add("Account", serializeAccount(src.getAccount(),context));
        jo.add("Tag", tagsSerializer(src.tags(), context));
        jo.add("Description", context.serialize(src.getDescription()));
        return jo;
    }
}
