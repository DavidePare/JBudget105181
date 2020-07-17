package it.unicam.cs.pa.jbudget105181.Model.Store;

import com.google.gson.*;
import it.unicam.cs.pa.jbudget105181.Model.Account.Account;
import it.unicam.cs.pa.jbudget105181.Model.Account.AccountType;
import it.unicam.cs.pa.jbudget105181.Model.Account.IAccount;
import it.unicam.cs.pa.jbudget105181.Model.IFactory;
import it.unicam.cs.pa.jbudget105181.Model.Ledger.ILedger;
import it.unicam.cs.pa.jbudget105181.Model.Ledger.Ledger;
import it.unicam.cs.pa.jbudget105181.Model.Movement.IMovement;
import it.unicam.cs.pa.jbudget105181.Model.Movement.Movement;
import it.unicam.cs.pa.jbudget105181.Model.Movement.MovementType;
import it.unicam.cs.pa.jbudget105181.Model.Tag.ITag;
import it.unicam.cs.pa.jbudget105181.Model.Tag.Tag;
import it.unicam.cs.pa.jbudget105181.Model.Transaction.ITransazione;
import javafx.scene.input.InputMethodTextRun;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class AdapterLedger implements JsonSerializer<ILedger>, JsonDeserializer<ILedger>{
    private ILedger ledger;

    @Override
    public ILedger deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        this.ledger=new Ledger();
        ledger.addTags(tagsDeserialize(json.getAsJsonObject().get("Tags"), context));
        ledger.addAccounts(accountsDeserialize(json.getAsJsonObject().get("Accounts"),context));
        ledger.addTransactions(transactionsDeserialize(json.getAsJsonObject().get("Transactions"),context));
        return ledger;
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


    /**
     * serializzazione dei tag
     * @param src
     * @param context
     * @return ritorna un array di elementi contenete tutti i tag presenti
     */
    private JsonElement tagsSerializer(List<ITag> src, JsonSerializationContext context){
        JsonArray ja = new JsonArray();
        src.parallelStream().forEach(t->ja.add(tagSerializer(t, context)));
        return ja;
    }

    /**
     * Serializzatore delle transazioni
     * @param src
     * @param context
     * @return
     */
    private JsonElement transactionsSerializer(List<ITransazione> src, JsonSerializationContext context){
        JsonArray ja = new JsonArray();
        src.parallelStream().forEach(t->ja.add(serializeTransazione(t,context)));
        return ja;
    }

    /**
     * Serializzatore degli account
     * @param src
     * @param context
     * @return
     */
    private JsonElement accountsSerializer(List<IAccount> src, JsonSerializationContext context) {
        JsonArray ja = new JsonArray();
        src.parallelStream().forEach(a->ja.add(serializeAccount(a,context)));
        return ja;
    }

    /**
     * Serializzatore di un tag
     * @param src
     * @param context
     * @return
     */
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
        jo.add("Pagata", context.serialize(src.getPagata()));
        jo.add("Movements", movementsSerializer(src.movements(),context));
        jo.add("Tag", tagsSerializer(src.tags(),context));
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
        jo.add("ID Account", context.serialize(src.getAccount().getID()));
        jo.add("Tag", tagsSerializer(src.tags(), context));
        jo.add("Description", context.serialize(src.getDescription()));
        return jo;
    }
    /**
     * Metodo responsabile della deserializzazione di un JsonElemant in una serie di Transazioni.
     * @param json JsonElement da deserializzare.
     * @param context
     * @return Serie di Transazioni deserializzati.
     */
    private List<ITransazione> transactionsDeserialize(JsonElement json, JsonDeserializationContext context){
        List<ITransazione> transactions = new ArrayList<>();
        for(JsonElement je : json.getAsJsonArray())
            transactions.add(transactionsDeserializeElement(je,context));
        return transactions;
    }

    public ITransazione transactionsDeserializeElement(JsonElement json,JsonDeserializationContext context) throws JsonParseException{
        JsonObject jo = json.getAsJsonObject();
        int id=jo.get("ID").getAsInt();
        LocalDate data = context.deserialize(jo.get("Data"), LocalDate.class);// TODO
        String description= jo.get("Description").getAsString();
        boolean pagata=jo.get("Pagata").getAsBoolean();
        List<ITag> tagList = new ArrayList<>(tagsDeserialize(jo.get("Tag"), context));
        ITransazione trans = IFactory.generateTransaction(id,data,tagList,description,pagata);
        trans.addMovementList(movementsDeserialize(json.getAsJsonObject().get("Movements"),context,trans));
        trans.adjustTotalCost();
        return trans;


    }
    private List<IMovement> movementsDeserialize(JsonElement json, JsonDeserializationContext context, ITransazione t) {
        List<IMovement> movements = new ArrayList<>();
        for (JsonElement je : json.getAsJsonArray()) {
            movements.add(movementDeserialize(je,context,t));
        }
        return movements;
    }

    private IMovement movementDeserialize(JsonElement json, JsonDeserializationContext context,ITransazione t){
        JsonObject jo =json.getAsJsonObject();
        int ID = jo.get("ID").getAsInt();
        MovementType type = MovementType.valueOf(jo.get("Type").getAsString());
        Double amount = jo.get("Amount").getAsDouble();
        String description = jo.get("Description").getAsString();
        List<ITag> tag = tagsDeserialize(jo.getAsJsonObject().get("Tag"),context);
        int IDAccount=jo.get("ID Account").getAsInt();
        return IFactory.generateMovement(ID,description,type,amount,ledger.getAccountForID(IDAccount),tag,t);
    }


    /**
     * Metodo responsabile della deserializzazione di un JsonElemant in una serie di Account.
     * @param json JsonElement da deserializzare.
     * @param context
     * @return Serie di Accounts deserializzati.
     */
    private List<IAccount> accountsDeserialize(JsonElement json, JsonDeserializationContext context){
        List<IAccount> accounts = new ArrayList<>();
        for(JsonElement je : json.getAsJsonArray())
            accounts.add(accountDeserialize(je,context));
        return accounts;
    }

    /**
     *
     * @param json
     * @param context
     * @return
     */
    private IAccount accountDeserialize(JsonElement json, JsonDeserializationContext context){
        JsonObject jo =json.getAsJsonObject();
        int id=jo.get("ID").getAsInt();
        String name = jo.get("Name").getAsString();
        String description = jo.get("Description").getAsString();
        AccountType type = AccountType.valueOf(jo.get("Type").getAsString());
        //AccountType type = jo.get("Type");
        Double amount=jo.get("OpeningBalance").getAsDouble();
        return IFactory.generateAccount(id,name,description,type,amount);
    }
    /**
     * Metodo che deserializza i tag letti dal file json
     * @param json
     * @param context
     * @return ritorna lista di tag deserializzati
     */
    private List<ITag> tagsDeserialize(JsonElement json, JsonDeserializationContext context){
        List<ITag> tags = new ArrayList<>();
        for(JsonElement je : json.getAsJsonArray())
            tags.add(tagDeserializeElement(je));
        return tags;
    }

    /**
     * Deserializzatore di un elemnto di un tag
     * @param json
     * @return tag generato
     * @throws JsonParseException
     */
    public ITag tagDeserializeElement(JsonElement json) throws JsonParseException {
        JsonObject jo = json.getAsJsonObject();
        String name = jo.get("Name").getAsString();
        String description = jo.get("Description").getAsString();
        return IFactory.generateTag(name,description);
    }

}
